package com.redstone.books;

import com.redstone.books.command.RedstoneBookCommand;
import com.redstone.books.network.NetworkHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(RedstoneBooks.MODID)
public class RedstoneBooks {

    public static final String MODID = "redstonebooks";

    public RedstoneBooks() {
        NetworkHandler.register();
        MinecraftForge.EVENT_BUS.addListener(this::onRegisterCommands);
    }

    private void onRegisterCommands(RegisterCommandsEvent event) {
        RedstoneBookCommand.register(event.getDispatcher());
    }
}