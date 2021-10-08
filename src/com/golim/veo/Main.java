package com.golim.veo;

import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class Main extends JavaPlugin {

    public static int y = 0;
    public static World world;
    public static boolean running = false;
    private static Main main;

    public static HashMap<Location, Boolean> cells = new HashMap<>();

    public void onEnable() {

        main = this;
        this.getCommand("gameoflife").setExecutor(new GameOfLife());
        iterate();

    }

    public static Main instance() { return main; }

    private void iterate() {

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {

            if (running) {

                check();
                updateState();
                updateCell();

            }

        }, 20L, 20L);

    }

    private void check() {

        for (Map.Entry<Location, Boolean> set : Main.cells.entrySet()) {

            Location location = set.getKey();

            if (!location.getBlock().getType().equals(Material.REDSTONE_BLOCK))
                location.getBlock().setType(Material.AIR);
            cells.put(location, location.getBlock().getType().equals(Material.REDSTONE_BLOCK) ? true : false);

        }

    }

    private void updateState() {

        for (Map.Entry<Location, Boolean> set : Main.cells.entrySet()) {

            Location location = set.getKey();

            int alive = 0;
            for (int x = -1; x <= 1; x++)
                for (int z = -1; z <= 1; z++) {

                    Location nLoc = new Location(location.getWorld(), location.getX() + x, y, location.getZ() + z);
                    if (nLoc.equals(location)) continue;

                    if (nLoc.getBlock().getType().equals(Material.REDSTONE_BLOCK))
                        alive++;

                }

            if (alive <= 1 || alive >= 4)
                cells.put(location, false);

            if (alive == 3)
                cells.put(location, true);

        }

    }

    private void updateCell() {

        for (Map.Entry<Location, Boolean> set : Main.cells.entrySet())
            set.getKey().getBlock().setType(set.getValue() ? Material.REDSTONE_BLOCK : Material.AIR);

    }

}
