package com.golim.veo;

import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main extends JavaPlugin {

    public static int y = 0;
    public static World world;
    public static boolean running = false;
    private static Main main;

    public static HashMap<Location, Boolean> cells = new HashMap<>();

    public static List<Location> aliveCells = new ArrayList<>();

    public void onEnable() {

        main = this;
        this.getCommand("gameoflife").setExecutor(new GameOfLife());
        Bukkit.getPluginManager().registerEvents(new Listeners(), this);
        iterate();

    }

    public static Main instance() { return main; }

    private void iterate() {

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {

            if (running) {

                TwoDimensional.updateCells();
                TwoDimensional.update();

            }

        }, 20L, 20L);

    }



}
