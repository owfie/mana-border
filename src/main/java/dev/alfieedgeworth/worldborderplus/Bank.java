package dev.alfieedgeworth.worldborderplus;

import dev.alfieedgeworth.worldborderplus.controllers.BossBarController;
import dev.alfieedgeworth.worldborderplus.controllers.WorldBorderController;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Bank {
    private Main plugin;
    private BossBarController bossBar;
    private WorldBorderController worldBorder;

    private int balance;
    private int nextCost;

    public Bank(Main plugin) {
        this.plugin = plugin;
        this.bossBar = plugin.getBossBar();
        this.worldBorder = plugin.getWorldBorderController();
        balance = this.plugin.getConfig().getInt("current-balance");
        nextCost = calculateNextCost();
    }

    public int calculateNextCost() {
        return worldBorder.getRadius()*10;
    }

    public void spend(int xp, Player spender) {
        nextCost = calculateNextCost();
        String name = spender.getName();
        int count = 0;
        while (true) {
            if (xp + balance < nextCost) {
                balance += xp;
                break;
            }
            xp = xp-(nextCost-balance);
            balance = 0;
            increase();
            count++;
        }

        bossBar.refreshBar();

        plugin.getConfig().set("current-balance",balance);
        plugin.saveConfig();

        Bukkit.broadcastMessage(
                ChatColor.GREEN + name +
                        ChatColor.GRAY + " just spent " +
                        ChatColor.GREEN + xp +
                        ChatColor.GRAY + " experience!"
        );
        if (count > 0) {
            count = count*10;
            Bukkit.broadcastMessage(
                    ChatColor.DARK_GREEN + "The world border was increased by " + count + " blocks!"
            );
        }
    }

    public void increase() {
        worldBorder.incrementBorder();
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getNextCost() {
        return calculateNextCost();
    }

    public void setNextCost(int nextCost) {
        this.nextCost = nextCost;
    }
}
