package me.silver.worldutils.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import me.silver.worldutils.WorldUtils;
import me.silver.worldutils.util.BlockHolder;
import org.bukkit.Bukkit;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CommandAlias("wmask")
public class Mask extends BaseCommand {

    private HashMap<String, List<BlockHolder>> hiddenBlocks = new HashMap<>();

    @Default
    @Subcommand("hide")
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
                    BlockHolder holder = new BlockHolder(block.getLocation(), material, materialData, data);

                    if (!hiddenBlocks.containsKey(sender.getName())) {
                        hiddenBlocks.put(sender.getName(), new ArrayList<>());
                    }

                    hiddenBlocks.get(sender.getName()).add(holder);

                    block.setType(Material.AIR);

                    Bukkit.getServer().getScheduler().runTaskLater(WorldUtils.getInstance(), () -> {
                        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                            if (!player.getName().equals(sender.getName())) {
                                player.sendBlockChange(block.getLocation(), material, data);
                            }
                        }
                    }, 0L);
                }
                break;
            default:

        }

    }

    @Subcommand("unhide")
    public void unHideBlocks(Player sender) {
        for (BlockHolder holder : hiddenBlocks.get(sender.getName())) {
            holder.createBlock();
        }
    }

}
