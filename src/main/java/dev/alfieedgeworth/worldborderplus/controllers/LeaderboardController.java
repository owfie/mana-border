package dev.alfieedgeworth.worldborderplus.controllers;

import dev.alfieedgeworth.worldborderplus.Main;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class LeaderboardController implements Listener {

    private Scoreboard scoreboard;
    private ScoreboardManager manager;
    private Main plugin;

    public LeaderboardController(Main plugin) {
        this.plugin = plugin;
        manager = plugin.getServer().getScoreboardManager();
        plugin.getServer().getPluginManager().registerEvents((Listener) this, plugin);
    }


}
