package me.silver.worldutils;

import co.aikar.commands.PaperCommandManager;
import me.silver.worldutils.command.ChangeVariable;
import me.silver.worldutils.command.Line;
import me.silver.worldutils.command.Tree;
import me.silver.worldutils.util.SilverChunkGenerator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class WorldUtils extends JavaPlugin {

    private static WorldUtils worldUtils;
    private static SilverChunkGenerator generator;

    @Override
    public void onEnable() {
        worldUtils = this;

        registerCommands();
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        generator = new SilverChunkGenerator();
        return generator;
    }

    // Bruh, this is getting mad sketchy
    public static SilverChunkGenerator getGenerator() {
        return generator;
    }

    public static WorldUtils getInstance() {
        return worldUtils;
    }

    private void registerCommands() {
        PaperCommandManager manager = new PaperCommandManager(this);

        manager.registerCommand(new ChangeVariable());
        manager.registerCommand(new Line());
        manager.registerCommand(new Tree());
    }
}
