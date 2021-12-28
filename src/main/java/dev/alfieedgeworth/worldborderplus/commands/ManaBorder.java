package dev.alfieedgeworth.worldborderplus.commands;

import dev.alfieedgeworth.worldborderplus.Bank;
import dev.alfieedgeworth.worldborderplus.Main;
import dev.alfieedgeworth.worldborderplus.controllers.WorldBorderController;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import javax.xml.stream.events.Namespace;

import static java.lang.Integer.parseInt;
import static java.lang.Math.round;

public class ManaBorder implements CommandExecutor {
    private Main plugin;
    private Bank bank;
    private WorldBorderController worldBorder;

    public ManaBorder(Main plugin) {
        this.plugin = plugin;
        this.bank = plugin.getBank();
        this.worldBorder = plugin.getWorldBorderController();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("balance")) {
            sender.sendMessage(
                ChatColor.GRAY + "You have " +
                ChatColor.GREEN + player.getTotalExperience() +
                ChatColor.GRAY + " total experience."
            );

            NamespacedKey spent = new NamespacedKey(plugin, "spent-xp");
            PersistentDataContainer container = player.getPersistentDataContainer();
            if (container.has(spent, PersistentDataType.INTEGER)) {
                int totalSpent = container.get(spent, PersistentDataType.INTEGER);
                player.sendMessage(ChatColor.GRAY + "You have spent " +
                        ChatColor.GREEN + totalSpent +
                        ChatColor.GRAY + " experience on expanding the world border.");
            }
        }

        if (cmd.getName().equalsIgnoreCase("center")) {
            Location center = worldBorder.getCenter(player.getWorld());
            String coordinates = "x=" + round(center.getX()) + ", z=" + round(center.getZ());
            sender.sendMessage(
                ChatColor.GRAY + "World border center is " +
                ChatColor.GREEN + coordinates +
                ChatColor.GRAY + "."
            );
        }

        if (cmd.getName().equalsIgnoreCase("spend")) {
            if (args[0] != null) {
                int xp = 0;

                try {
                    xp = parseInt(args[0]);
                } catch (Exception e) {
                    sender.sendMessage(
                        ChatColor.RED + "Please enter an integer value of experience."
                    );
                    return true;
                }

                if (player.getTotalExperience() >= xp) {
                    bank.spend(xp, player);

                    int newExperience = player.getTotalExperience() - xp;
                    player.setExp(0);
                    player.setLevel(0);
                    player.setTotalExperience(0);
                    player.giveExp(newExperience);
                    PersistentDataContainer container = player.getPersistentDataContainer();

                    NamespacedKey spent = new NamespacedKey(plugin, "spent-xp");

                    if (container.has(spent, PersistentDataType.INTEGER)) {
                        int previousXp = container.get(spent, PersistentDataType.INTEGER);
                        int newXp = previousXp+xp;
                        player.getPersistentDataContainer().set(spent, PersistentDataType.INTEGER, newXp);
                    } else {
                        container.set(spent, PersistentDataType.INTEGER, xp);
                    }

                } else {
                    sender.sendMessage(
                        ChatColor.RED + "You do not have enough experience."
                    );
                }
            }
        }

        return true;
    }
}
