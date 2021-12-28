package dev.alfieedgeworth.worldborderplus;

import dev.alfieedgeworth.worldborderplus.commands.ManaBorder;
import dev.alfieedgeworth.worldborderplus.controllers.BossBarController;
import dev.alfieedgeworth.worldborderplus.controllers.LeaderboardController;
import dev.alfieedgeworth.worldborderplus.controllers.WorldBorderController;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private Main plugin;
    private Bank bank;

    private WorldBorderController manager;
    private BossBarController bossBar;
    private LeaderboardController scoreboard;

    @Override
    public void onEnable() {
        // Plugin startup logic

        this.saveDefaultConfig();

        manager = new WorldBorderController(this);
        bossBar = new BossBarController(this);
        scoreboard = new LeaderboardController(this);
        bank = new Bank(this);

        bossBar.createBar();

        ManaBorder exec = new ManaBorder(this);

        getCommand("balance").setExecutor(exec);
        getCommand("spend").setExecutor(exec);
        getCommand("center").setExecutor(exec);

        if (Bukkit.getOnlinePlayers().size() > 0) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                bossBar.addPlayer(onlinePlayer);
            }
        }

        getLogger().info(ChatColor.GREEN + "ManaBorder enabled.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        if (Bukkit.getOnlinePlayers().size() > 0) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                bossBar.removePlayer(onlinePlayer);
            }
        }

        this.saveConfig();

        getLogger().info(ChatColor.GREEN + "ManaBorder disabled. Goodbye.");
    }

    public Main getPlugin() {
        return plugin;
    }

    public void setPlugin(Main plugin) {
        this.plugin = plugin;
    }

    public BossBarController getBossBar() {
        return bossBar;
    }

    public void setBossBar(BossBarController bossBar) {
        this.bossBar = bossBar;
    }

    public LeaderboardController getScoreboard() {
        return scoreboard;
    }

    public void setScoreboard(LeaderboardController scoreboard) {
        this.scoreboard = scoreboard;
    }

    public WorldBorderController getWorldBorderController() {
        return manager;
    }

    public void setWorldBorderController(WorldBorderController manager) {
        this.manager = manager;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }
}
