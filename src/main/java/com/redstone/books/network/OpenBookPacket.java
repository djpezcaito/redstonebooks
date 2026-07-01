package com.redstone.books.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.redstone.books.client.ClientBookOpener;
import com.redstone.books.data.BookDefinition;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenBookPacket {

    private static final Gson GSON = new GsonBuilder().create();
    private static final int MAX_JSON_SIZE = 262144;

    private final BookDefinition book;

    public OpenBookPacket(BookDefinition book) {
        this.book = book;
    }

    public static void encode(OpenBookPacket packet, FriendlyByteBuf buffer) {
        String json = GSON.toJson(packet.book);
        buffer.writeUtf(json, MAX_JSON_SIZE);
    }

    public static OpenBookPacket decode(FriendlyByteBuf buffer) {
        String json = buffer.readUtf(MAX_JSON_SIZE);
        BookDefinition book = GSON.fromJson(json, BookDefinition.class);
        return new OpenBookPacket(book);
    }

    public static void handle(OpenBookPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();

        context.enqueueWork(() -> {
            if (context.getDirection().getReceptionSide().isClient()) {
                ClientBookOpener.open(packet.book);
            }
        });

        context.setPacketHandled(true);
    }
}