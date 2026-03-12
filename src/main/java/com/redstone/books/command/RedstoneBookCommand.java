package com.redstone.books.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.redstone.books.client.screen.RedstoneBookScreen;
import com.redstone.books.data.BookDefinition;
import com.redstone.books.data.BookLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class RedstoneBookCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("redstonebook")
                .requires(src -> src.hasPermission(2))

                .executes(ctx -> {
                    ctx.getSource().sendSuccess(
                        () -> Component.literal("Redstone Books activo."),
                        false
                    );
                    return 1;
                })

                .then(
                    Commands.literal("open")
                        .then(
                            Commands.argument("id", StringArgumentType.word())
                                .executes(ctx -> {
                                    String id = StringArgumentType.getString(ctx, "id");

                                    try {
                                        BookDefinition book = BookLoader.loadFromConfig(id);

                                        ctx.getSource().sendSuccess(
                                            () -> Component.literal(
                                                "Libro cargado correctamente: " + book.meta.title +
                                                " (" + book.pages.size() + " páginas)"
                                            ),
                                            false
                                        );

                                        if (FMLEnvironment.dist == Dist.CLIENT) {
                                            Minecraft mc = Minecraft.getInstance();
                                            mc.execute(() -> mc.setScreen(new RedstoneBookScreen(book)));
                                        }

                                        return 1;

                                    } catch (Exception e) {
                                        ctx.getSource().sendFailure(
                                            Component.literal("Error cargando libro '" + id + "': " + e.getMessage())
                                        );
                                        return 0;
                                    }
                                })
                        )
                )
        );
    }
}