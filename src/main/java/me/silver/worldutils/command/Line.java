package me.silver.worldutils.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
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

        int dx = Math.abs(blockLocation.getBlockX() - playerLocation.getBlockX());
        int dy = Math.abs(blockLocation.getBlockY() - playerLocation.getBlockY());
        int dz = Math.abs(blockLocation.getBlockZ() - playerLocation.getBlockZ());

        double xs;
        double ys;
        double zs;

        if (dx >= dy && dx >= dz) {
            xs = 1 * Math.signum(blockLocation.getBlockX() - playerLocation.getBlockX());
            ys = (double)(blockLocation.getBlockY() - playerLocation.getBlockY()) / dx;
            zs = (double)(blockLocation.getBlockZ() - playerLocation.getBlockZ()) / dx;

            drawSilverLine(playerLocation, xs, ys, zs, dx);
        } else if (dy >= dx && dy >= dz) {
            ys = 1 * Math.signum(blockLocation.getBlockY() - playerLocation.getBlockY());
            xs = (double)(blockLocation.getBlockX() - playerLocation.getBlockX()) / dy;
            zs = (double)(blockLocation.getBlockZ() - playerLocation.getBlockZ()) / dy;

            drawSilverLine(playerLocation, xs, ys, zs, dy);
        } else {
            zs = 1 * Math.signum(blockLocation.getBlockZ() - playerLocation.getBlockZ());
            xs = (double)(blockLocation.getBlockX() - playerLocation.getBlockX()) / dz;
            ys = (double)(blockLocation.getBlockY() - playerLocation.getBlockY()) / dz;

            drawSilverLine(playerLocation, xs, ys, zs, dz);
        }
    }

    private static void drawSilverLine(Location origin, double xs, double ys, double zs, int maxLength) {
        Location currentLocation = origin.clone();
        World world = origin.getWorld();

        for (int i = 0; i < maxLength; i++) {
            world.getBlockAt(currentLocation).setType(Material.STONE);
            currentLocation.add(xs, ys, zs);
        }
    }
}
