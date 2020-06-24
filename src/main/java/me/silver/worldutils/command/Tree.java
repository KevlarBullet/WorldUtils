package me.silver.worldutils.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import me.silver.worldutils.tree.BaseTree;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

@CommandAlias("wtree")
@CommandPermission("worldutils.command")
public class Tree extends BaseCommand {

    @Subcommand("grow")
    public static void createTree(Player sender, @Default("F") String treeString, Integer iterations) {
        Block block = sender.getTargetBlock(null, 20);
        Location blockLocation = (block.getLocation().add(0.5, 1.5, 0.5));

        BaseTree tree = new BaseTree(treeString, iterations);
        tree.buildTree(blockLocation);
    }

    @Subcommand("def")
    public static void defineRule(Player sender, String letter, String rule) {
        char character = letter.toLowerCase().charAt(0);

        if (BaseTree.rules.containsKey(character)) {
            sender.sendMessage("Updated rule for character '" + letter.charAt(0) + "' to '" + rule + "'");
        } else {
            sender.sendMessage("Set rule for character '" + letter.charAt(0) + "' to '" + rule + "'");
        }

        BaseTree.rules.put(character, rule);
    }

    @Subcommand("rules")
    public static void listRules(Player sender) {

    }
}
