package dev.alfieedgeworth.worldborderplus.controllers;

import dev.alfieedgeworth.worldborderplus.Main;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.UUID;

public class WorldBorderController {
    private Main plugin;
    private final WorldBorder border;
    private final WorldBorder borderNether;
    private final WorldBorder borderEnd;
    private double centerX;
    private double centerZ;
    private int radius;

    public WorldBorderController(Main plugin) {
        this.plugin = plugin;

        World world = plugin.getServer().getWorld("world");
        World worldNether = plugin.getServer().getWorld("world_nether");
        World worldEnd = plugin.getServer().getWorld("world_the_end");

        border = world.getWorldBorder();
        borderNether = worldNether.getWorldBorder();
        borderEnd = worldEnd.getWorldBorder();

        radius = (int) border.getSize();
        centerX = border.getCenter().getX();
        centerZ = border.getCenter().getZ();
    }

    public void incrementBorder() {
        int newRadius = radius+25;
        plugin.getLogger().warning("Increasing radius to " + newRadius);
        setRadius(newRadius);
    }

    public Location getCenter(World world) {
        return world.getWorldBorder().getCenter();
    }

    public void setCenter(double centerX, double centerZ) {
        this.centerZ = centerZ;
        this.centerX = centerX;
        border.setCenter(centerX, centerZ);
        borderNether.setCenter(centerX, centerZ);
        borderEnd.setCenter(centerX, centerZ);
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
        plugin.getLogger().warning("Setting radius to " + radius);
        border.setSize(radius);
        borderNether.setSize(radius);
        borderEnd.setSize(radius);
    }
}
