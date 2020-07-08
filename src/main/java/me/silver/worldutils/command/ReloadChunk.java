package me.silver.worldutils.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import me.silver.worldutils.WorldUtils;
import me.silver.worldutils.util.SilverChunkGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;

@CommandAlias("creload")
public class ReloadChunk extends BaseCommand {

    HashMap<String, Integer> warningMode = new HashMap<>();

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
        if (sender.getName().equals("_Silver") || sender.getName().equals("KevlarTophat")) {
            SilverChunkGenerator generator = WorldUtils.getGenerator();
            Chunk c1 = generator.chunkOne;
            Chunk c2 = generator.chunkTwo;

            if (c1 == null) {
                sender.sendMessage("Error: corner one not set");
                return;
            }

            if (c2 == null) {
                sender.sendMessage("Error: corner two not set");
                return;
            }

            int chunkCount = (Math.abs(c1.getX() - c2.getX()) + 1) * (Math.abs(c1.getZ() - c2.getZ()) + 1);

            if (!warningMode.containsKey(sender.getDisplayName())) {
                sender.sendMessage("Warning: this will permanently delete and reload " + chunkCount + " chunks");
                sender.sendMessage("To continue execution, retype the command within 5 seconds");

                int taskId = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(WorldUtils.getInstance(), () -> {
                    warningMode.remove(sender.getName());
                    sender.sendMessage("CReload timed out");
                }, 100L);

                warningMode.put(sender.getName(), taskId);
            } else {
                generator.reloadChunks(sender.getWorld());
                sender.sendMessage("Successfully reloaded " + chunkCount + " chunks");

                if (warningMode.containsKey(sender.getName())) {
                    Bukkit.getScheduler().cancelTask(warningMode.get(sender.getName()));
                    warningMode.remove(sender.getName());
                }
            }

        } else {
            sender.sendMessage("Error: this command is very highly destructive and can only be run by Silver");
        }

    }
}
