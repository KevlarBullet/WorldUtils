package me.silver.worldutils.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import me.silver.worldutils.WorldUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("cv")
@CommandPermission("worldutils.command")
public class ChangeVariable extends BaseCommand {

    @Subcommand("s")
    @CommandPermission("worldutils.command.variables")
    public static void changeScale(CommandSender sender, double scale) {
        WorldUtils.getGenerator().setScale(scale);

        if (sender instanceof Player) {
            Player player = (Player)sender;

            player.sendMessage("Changed scale to " + scale);
        }
    }

    @Subcommand("f")
    @CommandPermission("worldutils.command.variables")
    public static void changeFrequency(CommandSender sender, double frequency) {
        WorldUtils.getGenerator().setFrequency(frequency);

        if (sender instanceof Player) {
            Player player = (Player)sender;

            player.sendMessage("This real sketchy " + frequency);
        }
    }

    @Subcommand("a")
    @CommandPermission("worldutils.command.variables")
    public static void changeAmplitude(CommandSender sender, double amplitude) {
        WorldUtils.getGenerator().setAmplitude(amplitude);

        if (sender instanceof Player) {
            Player player = (Player)sender;

            player.sendMessage("Changed amplitude to " + amplitude);
        }
    }

    @Subcommand("e")
    @CommandPermission("worldutils.command.variables")
    public static void changeExponent(CommandSender sender, double power) {
        WorldUtils.getGenerator().setExponent(power);

        if (sender instanceof Player) {
            Player player = (Player)sender;

            player.sendMessage("Changed exponent to " + power);
        }
    }
}
