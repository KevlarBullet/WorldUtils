package me.silver.worldutils.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.Subcommand;
import me.silver.worldutils.WorldUtils;
import org.bukkit.entity.Player;

public class MarkChunk extends BaseCommand {



    @Subcommand("pos1")
    public void markBeginningChunk(Player sender) {
        if (sender.getName().equals("_Silver") || sender.getName().equals("KevlarTophat")) {
            WorldUtils.getGenerator().setChunkOne(sender.getChunk());
            sender.sendMessage("Set first corner chunk to (" + sender.getChunk().getX() + ", " + sender.getChunk().getZ() + ")");
        } else {
            sender.sendMessage("Error: this command is highly destructive and can only be run by Silver");
        }
    }

    @Subcommand("pos2")
    public void markEndChunk(Player sender) {
        if (sender.getName().equals("_Silver") || sender.getName().equals("KevlarTophat")) {
            WorldUtils.getGenerator().setChunkTwo(sender.getChunk());
            sender.sendMessage("Set second corner chunk to (" + sender.getChunk().getX() + ", " + sender.getChunk().getZ() + ")");
        } else {
            sender.sendMessage("Error: this command is highly destructive and can only be run by Silver");
        }

    }
}
