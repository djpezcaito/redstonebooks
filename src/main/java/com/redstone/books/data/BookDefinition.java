package com.redstone.books.data;

import java.util.ArrayList;
import java.util.List;

public class BookDefinition {
    public String id;
    public Meta meta = new Meta();
    public Theme theme = new Theme();
    public Animation animation = new Animation();
    public Assets assets = new Assets();
    public Options options = new Options();
    public Style style = new Style();
    public Layout layout = new Layout();
    public Transition transition = new Transition();
    public Typewriter typewriter = new Typewriter();

    public List<Page> pages = new ArrayList<>();

    public static class Meta {
        public String title = "";
        public String subtitle = "";
    }

    public static class Theme {
        public String folder = "guardian";
    }

    public static class Animation {
        public boolean enabled = false;
        public String folder = "guardian/anim";
        public int frameCount = 0;
        public int frameRate = 1;
    }

    public static class Assets {
        public String cover = "book_cover.png";
        public String reading = "book_reading.png";
    }

    public static class Options {
        public boolean hideHud = true;
        public boolean lockInput = true;
        public boolean autoAdvance = true;

        public int introTicks = 20;
        public int outroTicks = 20;
        public int pageTurnTicks = 8;

        public boolean showPageIndicator = true;
    }

    public static class Style {
        public float textScale = 1.0f;
        public String align = "LEFT";
        public int lineSpacing = 1;

        public String textColor = "#241A12";
        public boolean textShadow = true;

        public String pageIndicatorColor = "#F5F2E8";
        public boolean pageIndicatorShadow = true;
    }

    public static class Layout {
        public String mode = "single_page";

        public float closedBookWidth = 0.32f;
        public float closedBookHeight = 0.55f;

        public float openBookWidth = 0.76f;
        public float openBookHeight = 0.76f;

        public float textStartX = 0.58f;
        public float textStartY = 0.14f;
        public float textWidth = 0.30f;
        public float textHeight = 0.60f;

        public float pageIndicatorX = 0.88f;
        public float pageIndicatorY = 0.90f;

        public int pageIndicatorOffsetX = 0;
        public int pageIndicatorOffsetY = 0;
    }

    public static class Transition {
        public boolean useFadeBetweenAnimationAndReading = true;
        public int fadeOutTicks = 6;
        public int blackHoldTicks = 6;
        public int fadeInTicks = 8;
    }

    public static class Typewriter {
        public boolean enabled = false;
        public int charsPerTick = 1;
        public int startDelay = 0;
    }

    public static class Page {
        public int durationTicks = 100;
        public String text = "";
        public String voiceSound = "";
        public float voiceVolume = 1.0f;
        public float voicePitch = 1.0f;
    }
}