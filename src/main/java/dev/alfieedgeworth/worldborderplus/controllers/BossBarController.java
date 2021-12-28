package dev.alfieedgeworth.worldborderplus.controllers;

import dev.alfieedgeworth.worldborderplus.Bank;
import dev.alfieedgeworth.worldborderplus.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class BossBarController implements Listener {
    private BossBar bossBar;
    private WorldBorderController worldBorder;
    private Bank bank;
    private Main plugin;

    public BossBarController(Main plugin) {
        this.plugin = plugin;
        this.bank = plugin.getBank();
        this.worldBorder = plugin.getWorldBorderController();
        plugin.getServer().getPluginManager().registerEvents((Listener) this, plugin);
    }

    public void createBar() {
        if (bank == null)
            this.bank = plugin.getBank();
        bossBar = Bukkit.createBossBar(generateTitle(), BarColor.GREEN, BarStyle.SOLID);
        bossBar.setVisible(true);
        refreshBar();
    }

    public void refreshBar() {
        bossBar.setTitle(generateTitle());
        double progress = (double) bank.getBalance()/bank.getNextCost();
        bossBar.setProgress(progress);
    }

    public String generateTitle () {
        return
            ChatColor.BOLD + "" + ChatColor.WHITE + "Current radius: " +
            ChatColor.GREEN + worldBorder.getRadius() +
            ChatColor.WHITE + " blocks. " +
            ChatColor.RESET + "" + ChatColor.GREEN + bank.getBalance() +
            ChatColor.WHITE + "/" + bank.getNextCost() + " XP until next expansion.";
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        bossBar.removePlayer(e.getPlayer());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        bossBar.addPlayer(e.getPlayer());
    }

    public void setColor(BarColor b) {
        bossBar.setColor(b);
    }

    public void setTitle(String s) {
        bossBar.setTitle(s);
    }

    public void addPlayer(@NotNull Player p) {
        if (!bossBar.getPlayers().contains(p))
            bossBar.addPlayer(p);
    }

    public void removePlayer(@NotNull Player p) {
        if (bossBar.getPlayers().contains(p))
            bossBar.removePlayer(p);
    }
}
