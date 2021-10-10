package com.golim.veo;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ThreeDimensional {

    static List<Location> removable = new ArrayList<>(), reference = new ArrayList<>();
    public static void updateCells() {

        // #1 put all dead neighbours into a new list + add currently aliveCells to new list

        for (Location c1 : Main.aliveCells) {

            reference.add(c1);
            for (Location c2 : getDeadNeighbours(c1))
                if (!reference.contains(c2)) reference.add(c2);

        }

        //Bukkit.broadcastMessage("The amount of elements in new reference list is: " + reference.size());

        // #2 run operation on newly made list -> remove all cells from list that don't obey rules

        Iterator<Location> i2 = reference.iterator();
        while (i2.hasNext()) {

            Location c = i2.next();

            int alive = 0;
            for (int y = -1; y <= 1; y++)
                for (int x = -1; x <= 1; x++)
                    for (int z = -1; z <= 1; z++) {

                        Location nLoc = new Location(c.getWorld(), c.getX() + x, c.getY() + y, c.getZ() + z);
                        if (nLoc.equals(c)) continue;

                        if (nLoc.getBlock().getType().equals(Material.REDSTONE_BLOCK))
                            alive++;

                    }


            if (alive <= 1 || alive >= 4 || (!c.getBlock().getType().equals(Material.REDSTONE_BLOCK) && alive != 3)) {

                i2.remove();
                removable.add(c);

            }

        }

        //Bukkit.broadcastMessage("Amount of elements left in reference list is: " + reference.size());
        //Bukkit.broadcastMessage("Amount of removables: " + removable.size());

    }

    public static void update() {

        Main.aliveCells.clear();
        Main.aliveCells.addAll(reference);

        Iterator<Location> i1 = reference.iterator(),
                i2 = removable.iterator();
        while (i1.hasNext()) {

            Location c = i1.next();
            c.getBlock().setType(Material.REDSTONE_BLOCK);
            i1.remove();

        }
        while (i2.hasNext()) {

            Location c = i2.next();
            c.getBlock().setType(Material.AIR);
            i2.remove();

        }

        //Bukkit.broadcastMessage("Number of new cells: " + Main.aliveCells.size());

    }

    private static List<Location> getDeadNeighbours(Location location) {

        List<Location> deadCells = new ArrayList<>();
        for (int y = -1; y <= 1; y++)
            for (int x = -1; x <= 1; x++)
                for (int z = -1; z <= 1; z++) {

                    Location nLoc = new Location(location.getWorld(), location.getX() + x, location.getY() + y, location.getZ() + z);
                    if (nLoc.equals(location)) continue;

                    if (!nLoc.getBlock().getType().equals(Material.REDSTONE_BLOCK))
                        deadCells.add(nLoc);

                }

        return deadCells;

    }

}