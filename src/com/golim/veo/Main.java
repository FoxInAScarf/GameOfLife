package com.golim.veo;

import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main extends JavaPlugin {

    public static int y = 0;
    public static World world;
    public static boolean running = false;
    private static Main main;
    public static double timerate = 0.5;
    public static boolean threeD = false;

    //public static HashMap<Location, Boolean> cells = new HashMap<>();

    public static List<Location> aliveCells = new ArrayList<>();

    public void onEnable() {

        main = this;
        this.getCommand("gameoflife").setExecutor(new GameOfLife());
        Bukkit.getPluginManager().registerEvents(new Listeners(), this);
        iterate();

    }

    private void iterate() {

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {

            if (running) {

                if (threeD) {

                    ThreeDimensional.updateCells();
                    ThreeDimensional.update();

                } else {

                    TwoDimensional.updateCells();
                    TwoDimensional.update();

                    Iterator<Location> i = aliveCells.iterator();
                    while (i.hasNext())
                        if (i.next().getY() != y) i.remove();

                }

            }

        }, 20L, Math.round(timerate * 20));

    }



}
