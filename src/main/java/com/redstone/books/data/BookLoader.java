package com.redstone.books.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

public class BookLoader {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static BookDefinition loadFromConfig(String id) throws IOException {
        Path path = FMLPaths.CONFIGDIR.get()
                .resolve("redstonebooks")
                .resolve("books")
                .resolve(id + ".json");

        if (!Files.exists(path)) {
            throw new IOException("No existe el archivo: " + path);
        }

        try (Reader reader = Files.newBufferedReader(path)) {
            return GSON.fromJson(reader, BookDefinition.class);
        }
    }
}