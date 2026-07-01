package com.redstone.books.client;

import com.redstone.books.client.screen.RedstoneBookScreen;
import com.redstone.books.data.BookDefinition;
import net.minecraft.client.Minecraft;

public class ClientBookOpener {

    public static void open(BookDefinition book) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> minecraft.setScreen(new RedstoneBookScreen(book)));
    }
}