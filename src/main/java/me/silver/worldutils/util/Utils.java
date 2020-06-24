package me.silver.worldutils.util;

import net.minecraft.server.v1_12_R1.Vec3D;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Utils {

    public static Vec3D fromPitchYaw(float pitch, float yaw) {
        double f = Math.cos(-yaw * 0.017453292 - (float)Math.PI);
        double f1 = Math.sin(-yaw * 0.017453292 - (float)Math.PI);
        double f2 = -Math.cos(-pitch * 0.017453292);
        double f3 = Math.sin(-pitch * 0.017453292);

        return new Vec3D((f1 * f2), f3, (f * f2));
    }

    public static void createLine(Location origin, Location destination) {
        Block block = destination.getBlock();
        Location blockLocation = block.getLocation();

        int dx = Math.abs(blockLocation.getBlockX() - origin.getBlockX());
        int dy = Math.abs(blockLocation.getBlockY() - origin.getBlockY());
        int dz = Math.abs(blockLocation.getBlockZ() - origin.getBlockZ());

        double xs;
        double ys;
        double zs;

        if (dx >= dy && dx >= dz) {
            xs = 1 * Math.signum(blockLocation.getBlockX() - origin.getBlockX());
            ys = (double)(blockLocation.getBlockY() - origin.getBlockY()) / dx;
            zs = (double)(blockLocation.getBlockZ() - origin.getBlockZ()) / dx;

            drawSilverLine(origin, xs, ys, zs, dx);
        } else if (dy >= dx && dy >= dz) {
            ys = 1 * Math.signum(blockLocation.getBlockY() - origin.getBlockY());
            xs = (double)(blockLocation.getBlockX() - origin.getBlockX()) / dy;
            zs = (double)(blockLocation.getBlockZ() - origin.getBlockZ()) / dy;

            drawSilverLine(origin, xs, ys, zs, dy);
        } else {
            zs = 1 * Math.signum(blockLocation.getBlockZ() - origin.getBlockZ());
            xs = (double)(blockLocation.getBlockX() - origin.getBlockX()) / dz;
            ys = (double)(blockLocation.getBlockY() - origin.getBlockY()) / dz;

            drawSilverLine(origin, xs, ys, zs, dz);
        }

        // Set final block to stone in case the other function missed it
        block.setType(Material.STONE);
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
