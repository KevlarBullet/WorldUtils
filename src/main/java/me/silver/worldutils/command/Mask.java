package me.silver.worldutils.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.craftbukkit.v1_12_R1.block.CraftBlock;
import org.bukkit.entity.Player;
import org.bukkit.material.Directional;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Stairs;

@CommandAlias("wmask")
public class Mask extends BaseCommand {

    @Default
    public void mask(Player sender) {
        Block block = sender.getTargetBlock(null, 5);
        PistonMoveReaction reaction = block.getPistonMoveReaction();

        switch (reaction) {
            case MOVE:
            case PUSH_ONLY:
            case IGNORE:
                if (block.getType() != Material.AIR) {
                    if (block.getWorld().getBlockAt(block.getX(), block.getY(), block.getZ()).getPistonMoveReaction() == PistonMoveReaction.BREAK) return;

                    Material material = block.getType();
                    MaterialData materialData = block.getState().getData().clone();
                    byte data = block.getData();

                }
                break;
            default:

        }


    }

}
