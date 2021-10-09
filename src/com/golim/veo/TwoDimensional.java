package com.golim.veo;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TwoDimensional {

    static List<Location> removable = new ArrayList<>(), reference = new ArrayList<>();
    public static void updateCells() {

        // #1 put all dead neighbours into a new list + add currently aliveCells to new list

        Iterator<Location> i1 = reference.iterator();
        while (i1.hasNext()) {

            Location c1 = i1.next();

            reference.add(c1);
            for (Location c2 : getDeadNeighbours(c1))
                if (!reference.contains(c2)) reference.add(c2);

        }

        // #2 run operation on newly created list -> add all to newAliveCells, new list

        Iterator<Location> i2 = reference.iterator();
        while (i2.hasNext()) {

            Location c = i2.next();

            int alive = 0;
            for (int x = -1; x <= 1; x++)
                for (int z = -1; z <= 1; z++) {

                    Location nLoc = new Location(c.getWorld(), c.getX() + x, Main.y, c.getZ() + z);
                    if (nLoc.equals(c)) continue;

                    if (!nLoc.getBlock().getType().equals(Material.REDSTONE_BLOCK))
                        alive++;

                }

            if (alive <= 1 || alive >= 4 && c.getBlock().getType().equals(Material.REDSTONE_BLOCK))
                i2.remove(); // clears up reference list instead of having a new one

            if (alive != 3 && !c.getBlock().getType().equals(Material.REDSTONE_BLOCK))
                i2.remove(); // clears up reference list instead of having a new one

        }

        // #3 parse aliveCells and newAliveCells -> new lists: toRemove, aliveCells = newAliveCells

        Main.aliveCells.forEach((c) -> {

           if (!reference.contains(c))
               removable.add(c);

        });

    }

    public static void update() {

        Main.aliveCells = reference;

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

    }

    private static List<Location> getDeadNeighbours(Location location) {

        List<Location> deadCells = new ArrayList<>();
        for (int x = -1; x <= 1; x++)
            for (int z = -1; z <= 1; z++) {

                Location nLoc = new Location(location.getWorld(), location.getX() + x, Main.y, location.getZ() + z);
                if (nLoc.equals(location)) continue;

                if (!nLoc.getBlock().getType().equals(Material.REDSTONE_BLOCK))
                    deadCells.add(nLoc);

            }

        return deadCells;

    }

}
