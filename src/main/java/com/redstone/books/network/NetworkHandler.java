package com.redstone.books.network;

import com.redstone.books.RedstoneBooks;
import com.redstone.books.data.BookDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {

    private static final String PROTOCOL_VERSION = "1";
    private static int packetId = 0;

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
        ResourceLocation.fromNamespaceAndPath(RedstoneBooks.MODID, "main"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );

    public static void register() {
        CHANNEL.registerMessage(
            packetId++,
            OpenBookPacket.class,
            OpenBookPacket::encode,
            OpenBookPacket::decode,
            OpenBookPacket::handle
        );
    }

    public static void sendOpenBook(ServerPlayer player, BookDefinition book) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new OpenBookPacket(book));
    }
}