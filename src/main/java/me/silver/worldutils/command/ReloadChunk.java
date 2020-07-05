package me.silver.worldutils.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.World;
import org.bukkit.entity.Player;

@CommandAlias("creload")
public class ReloadChunk extends BaseCommand {

    @Default
    @Subcommand("single")
    public void reloadSingleChunk(Player sender) {
        if (sender.getName().equals("_Silver") || sender.getName().equals("KevlarTophat")) {
            World world = sender.getWorld();
            int x = sender.getChunk().getX();
            int z = sender.getChunk().getZ();

            if (world.regenerateChunk(x, z)) {
                sender.sendMessage("Successfully regenerated chunk at (" + x + ", " + z + ")");
            } else {
                sender.sendMessage("Error: failed to regenerate chunk");
            }
        } else {
            sender.sendMessage("Error: this command is highly destructive and can only be run by Silver");
        }
    }

    @Subcommand("multi")
    public void reloadMultipleChunks(Player sender) {

    }

}
