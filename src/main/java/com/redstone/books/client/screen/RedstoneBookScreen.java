package com.redstone.books.client.screen;

import com.redstone.books.RedstoneBooks;
import com.redstone.books.data.BookDefinition;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

public class RedstoneBookScreen extends Screen {
    private final BookDefinition book;

    private int pageIndex = 0;
    private int ticksLeft = 0;

    private int typewriterIndex = 0;
    private int typewriterDelay = 0;

    private boolean hudWasHidden = false;
    private boolean hudToggledByUs = false;

    private int outroTicks;
    private int pageTurnTicks = 0;

    private boolean playingIntroAnimation = true;
    private boolean playingOutroAnimation = false;
    private boolean closing = false;

    private int animationTickCounter = 0;
    private int animationFrame = 1;

    private boolean inTransitionFadeOut = false;
    private boolean inTransitionHoldBlack = false;
    private boolean inTransitionFadeIn = false;

    private int transitionFadeTicks = 0;
    private int transitionHoldTicks = 0;

    public RedstoneBookScreen(BookDefinition book) {
        super(Component.literal(
                book.meta != null && book.meta.title != null
                        ? book.meta.title
                        : "Redstone Books"
        ));
        this.book = book;
        this.outroTicks = book.options != null ? book.options.outroTicks : 20;
    }

    @Override
    protected void init() {
        super.init();

        if (minecraft != null && book.options.hideHud) {
            hudWasHidden = minecraft.options.hideGui;
            if (!hudWasHidden) {
                minecraft.options.hideGui = true;
                hudToggledByUs = true;
            }
        }

        if (book.pages == null || book.pages.isEmpty()) {
            onClose();
            return;
        }

        if (!book.animation.enabled || book.animation.frameCount <= 0) {
            playingIntroAnimation = false;
            startPage(0);
        }
    }

    @Override
    public void removed() {
        super.removed();
        stopCurrentVoice();

        if (minecraft != null && book.options.hideHud) {
            if (hudToggledByUs) {
                minecraft.options.hideGui = false;
            } else {
                minecraft.options.hideGui = hudWasHidden;
            }
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return !book.options.lockInput;
    }

    @Override
    public void tick() {
        super.tick();

        if (playingIntroAnimation) {
            animationTickCounter++;

            int frameRate = Math.max(1, book.animation.frameRate);
            if (animationTickCounter >= frameRate) {
                animationTickCounter = 0;
                animationFrame++;

                if (animationFrame > book.animation.frameCount) {
                    animationFrame = book.animation.frameCount;

                    if (book.transition.useFadeBetweenAnimationAndReading) {
                        playingIntroAnimation = false;
                        inTransitionFadeOut = true;
                        transitionFadeTicks = 0;
                    } else {
                        playingIntroAnimation = false;
                        startPage(0);
                    }
                }
            }
            return;
        }

        if (playingOutroAnimation) {
            animationTickCounter++;

            int frameRate = Math.max(1, book.animation.frameRate);
            if (animationTickCounter >= frameRate) {
                animationTickCounter = 0;
                animationFrame--;

                if (animationFrame <= 1) {
                    animationFrame = 1;
                    playingOutroAnimation = false;
                    closing = true;
                }
            }
            return;
        }

        if (inTransitionFadeOut) {
            transitionFadeTicks++;

            if (transitionFadeTicks >= Math.max(1, book.transition.fadeOutTicks)) {
                inTransitionFadeOut = false;
                inTransitionHoldBlack = true;
                transitionFadeTicks = 0;
                transitionHoldTicks = 0;
            }
            return;
        }

        if (inTransitionHoldBlack) {
            transitionHoldTicks++;

            if (transitionHoldTicks >= Math.max(0, book.transition.blackHoldTicks)) {
                inTransitionHoldBlack = false;
                inTransitionFadeIn = true;
                transitionHoldTicks = 0;
                transitionFadeTicks = 0;
                startPage(0);
            }
            return;
        }

        if (inTransitionFadeIn) {
            transitionFadeTicks++;

            if (transitionFadeTicks >= Math.max(1, book.transition.fadeInTicks)) {
                inTransitionFadeIn = false;
                transitionFadeTicks = 0;
            }
            return;
        }

        if (closing) {
            outroTicks--;
            if (outroTicks <= 0) {
                onClose();
            }
            return;
        }

        if (pageTurnTicks > 0) {
            pageTurnTicks--;
            if (pageTurnTicks == 0) {
                int next = pageIndex + 1;

                if (next >= book.pages.size()) {
                    if (book.animation.enabled && book.animation.frameCount > 0) {
                        playingOutroAnimation = true;
                        animationFrame = book.animation.frameCount;
                        animationTickCounter = 0;
                    } else {
                        closing = true;
                    }
                } else {
                    startPage(next);
                }
            }
            return;
        }

        if (!book.options.autoAdvance) {
            return;
        }

        if (ticksLeft > 0) {
            ticksLeft--;

            if (book.typewriter.enabled) {
                if (typewriterDelay < book.typewriter.startDelay) {
                    typewriterDelay++;
                } else {
                    typewriterIndex += Math.max(1, book.typewriter.charsPerTick);
                }
            }

            return;
        }

        pageTurnTicks = Math.max(1, book.options.pageTurnTicks);
    }

    private void startPage(int index) {
        stopCurrentVoice();

        pageIndex = index;
        BookDefinition.Page page = book.pages.get(pageIndex);
        ticksLeft = Math.max(1, page.durationTicks);

        typewriterIndex = 0;
        typewriterDelay = 0;

        playVoice(page);
    }

    private void playVoice(BookDefinition.Page page) {
        if (minecraft == null || page.voiceSound == null || page.voiceSound.isBlank()) {
            return;
        }

        try {
            ResourceLocation soundId = ResourceLocation.parse(page.voiceSound);
            SoundEvent event = SoundEvent.createVariableRangeEvent(soundId);

            minecraft.getSoundManager().play(
                    SimpleSoundInstance.forUI(event, page.voicePitch, page.voiceVolume)
            );
        } catch (Exception e) {
            System.out.println("[RedstoneBooks] Error reproduciendo sonido: "
                    + page.voiceSound + " -> " + e.getMessage());
        }
    }

    private void stopCurrentVoice() {
        if (minecraft != null) {
            minecraft.getSoundManager().stop();
        }
    }

    private ResourceLocation tex(String name) {
        String folder = "guardian";

        if (book.theme != null && book.theme.folder != null && !book.theme.folder.isBlank()) {
            folder = book.theme.folder;
        }

        return ResourceLocation.fromNamespaceAndPath(
                RedstoneBooks.MODID,
                "textures/gui/books/" + folder + "/" + name
        );
    }

    private ResourceLocation animFrame(int frame) {
        String folder = (book.animation != null
                && book.animation.folder != null
                && !book.animation.folder.isBlank())
                ? book.animation.folder
                : "guardian/anim";

        String path = String.format("textures/gui/books/%s/frame_%03d.png", folder, frame);

        return ResourceLocation.fromNamespaceAndPath(
                RedstoneBooks.MODID,
                path
        );
    }

    private int parseColor(String hex, int fallback) {
        if (hex == null || hex.isBlank()) {
            return fallback;
        }

        try {
            String clean = hex.startsWith("#") ? hex.substring(1) : hex;
            return Integer.parseInt(clean, 16);
        } catch (Exception e) {
            return fallback;
        }
    }

    @Override
    public void render(GuiGraphics gg, int mouseX, int mouseY, float partialTick) {
        gg.fill(0, 0, this.width, this.height, 0xFF000000);

        if (playingIntroAnimation) {
            renderIntroAnimation(gg);
            return;
        }

        if (playingOutroAnimation) {
            renderIntroAnimation(gg);
            return;
        }

        if (inTransitionFadeOut) {
            renderIntroAnimation(gg);
            renderFadeOverlay(gg, getFadeOutAlpha());
            return;
        }

        if (inTransitionHoldBlack) {
            renderFadeOverlay(gg, 255);
            return;
        }

        if (inTransitionFadeIn) {
            renderReadingBook(gg);
            renderFadeOverlay(gg, getFadeInAlpha());
            return;
        }

        if (closing) {
            renderFadeOverlay(gg, 255);
            return;
        }

        renderReadingBook(gg);
        super.render(gg, mouseX, mouseY, partialTick);
    }

    private void renderIntroAnimation(GuiGraphics gg) {
        ResourceLocation frameTex = animFrame(animationFrame);
        int w = this.width;
        int h = this.height;

        gg.blit(frameTex, 0, 0, 0, 0, w, h, w, h);
    }

    private void renderReadingBook(GuiGraphics gg) {
        ResourceLocation reading = tex(book.assets.reading);

        int bookW = (int) (this.width * book.layout.openBookWidth);
        int bookH = (int) (this.height * book.layout.openBookHeight);

        int x = (this.width - bookW) / 2;
        int y = (this.height - bookH) / 2;

        gg.blit(reading, x, y, 0, 0, bookW, bookH, bookW, bookH);

        BookDefinition.Page page = book.pages.get(pageIndex);

        int textX = x + (int) (bookW * book.layout.textStartX);
        int textY = y + (int) (bookH * book.layout.textStartY);
        int textW = (int) (bookW * book.layout.textWidth);
        int textH = (int) (bookH * book.layout.textHeight);

        int textColor = parseColor(book.style.textColor, 0x241A12);
        boolean textShadow = book.style.textShadow;

        int pageIndicatorColor = parseColor(book.style.pageIndicatorColor, 0xF5F2E8);
        boolean pageIndicatorShadow = book.style.pageIndicatorShadow;

        String text = page.text;
        if (book.typewriter.enabled) {
            int max = Math.min(typewriterIndex, text.length());
            text = text.substring(0, max);
        }

        gg.pose().pushPose();
        gg.pose().scale(book.style.textScale, book.style.textScale, 1.0f);

        int scaledTextX = (int) (textX / book.style.textScale);
        int scaledTextY = (int) (textY / book.style.textScale);
        int scaledTextW = (int) (textW / book.style.textScale);
        int scaledTextH = (int) (textH / book.style.textScale);

        List<FormattedCharSequence> lines = this.font.split(Component.literal(text), scaledTextW);

        int yy = 0;
        for (FormattedCharSequence line : lines) {
            int lineHeight = this.font.lineHeight + book.style.lineSpacing;

            if (yy + lineHeight > scaledTextH) {
                break;
            }

            int drawX = scaledTextX;

            if ("CENTER".equalsIgnoreCase(book.style.align)) {
                drawX = scaledTextX + (scaledTextW - this.font.width(line)) / 2;
            } else if ("RIGHT".equalsIgnoreCase(book.style.align)) {
                drawX = scaledTextX + scaledTextW - this.font.width(line);
            }

            int drawY = scaledTextY + yy;
            gg.drawString(this.font, line, drawX, drawY, textColor, textShadow);

            yy += lineHeight;
        }

        gg.pose().popPose();

        if (book.options.showPageIndicator) {
            String pageText = (pageIndex + 1) + " / " + book.pages.size();

            int pageIndicatorX = x + (int) (bookW * book.layout.pageIndicatorX) + book.layout.pageIndicatorOffsetX;
            int pageIndicatorY = y + (int) (bookH * book.layout.pageIndicatorY) + book.layout.pageIndicatorOffsetY;

            gg.drawString(this.font, pageText, pageIndicatorX, pageIndicatorY, pageIndicatorColor, pageIndicatorShadow);
        }
    }

    private void renderFadeOverlay(GuiGraphics gg, int alpha) {
        alpha = Math.max(0, Math.min(255, alpha));
        int color = (alpha << 24);
        gg.fill(0, 0, this.width, this.height, color);
    }

    private int getFadeOutAlpha() {
        int total = Math.max(1, book.transition.fadeOutTicks);
        float progress = (float) transitionFadeTicks / total;
        return (int) (255 * progress);
    }

    private int getFadeInAlpha() {
        int total = Math.max(1, book.transition.fadeInTicks);
        float progress = (float) transitionFadeTicks / total;
        return (int) (255 * (1.0f - progress));
    }
}