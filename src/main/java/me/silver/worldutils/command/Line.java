package me.silver.worldutils.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import me.silver.worldutils.util.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

@CommandAlias("line")
@CommandPermission("worldutils.command")
public class Line extends BaseCommand {

    @Default
    @CommandPermission("worldutils.command.line")
    public static void createLine(Player sender) {
        Block block = sender.getTargetBlock(null, 20);
        Location blockLocation = block.getLocation();
        Location playerLocation = sender.getLocation();

        Utils.createLine(playerLocation, blockLocation);
    }

}
