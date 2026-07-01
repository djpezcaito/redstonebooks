package com.redstone.books.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.redstone.books.data.BookDefinition;
import com.redstone.books.data.BookLoader;
import com.redstone.books.network.NetworkHandler;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

public class RedstoneBookCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("redstonebook")
                .requires(src -> src.hasPermission(2))
                .then(
                    Commands.literal("open")
                        .then(
                            Commands.argument("id", StringArgumentType.word())
                                .executes(ctx -> {
                                    String id = StringArgumentType.getString(ctx, "id");

                                    if (!(ctx.getSource().getEntity() instanceof ServerPlayer player)) {
                                        ctx.getSource().sendFailure(
                                            Component.literal("This command must be executed by a player or specify a target.")
                                        );
                                        return 0;
                                    }

                                    return openBook(ctx.getSource(), id, java.util.List.of(player));
                                })
                                .then(
                                    Commands.argument("target", EntityArgument.players())
                                        .executes(ctx -> {
                                            String id = StringArgumentType.getString(ctx, "id");
                                            Collection<ServerPlayer> targets = EntityArgument.getPlayers(ctx, "target");

                                            return openBook(ctx.getSource(), id, targets);
                                        })
                                )
                        )
                )
        );
    }

    private static int openBook(CommandSourceStack source, String id, Collection<ServerPlayer> targets) {
        try {
            BookDefinition book = BookLoader.loadFromConfig(id);

            for (ServerPlayer player : targets) {
                NetworkHandler.sendOpenBook(player, book);
            }

            source.sendSuccess(
                () -> Component.literal(
                    "Book sent: " + book.meta.title + " to " + targets.size() + " player(s)."
                ),
                false
            );

            return targets.size();
        } catch (Exception e) {
            source.sendFailure(
                Component.literal("Error loading book '" + id + "': " + e.getMessage())
            );
            return 0;
        }
    }
}