package com.golim.veo;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class Listeners implements Listener {

    @EventHandler
    public void cellAddition(BlockPlaceEvent e) {

        if (e.getBlock().getType().equals(Material.REDSTONE_BLOCK) &&
                !Main.aliveCells.contains(e.getBlock().getLocation())) {

            Main.aliveCells.add(e.getBlock().getLocation());
            //e.getPlayer().sendMessage(ChatColor.AQUA + "A cell has been added!");

        }

    }

    @EventHandler
    public void cellRemoval(BlockBreakEvent e) {

        if (e.getBlock().getType().equals(Material.REDSTONE_BLOCK) &&
                Main.aliveCells.contains(e.getBlock().getLocation())) {

            Main.aliveCells.remove(Main.aliveCells.indexOf(e.getBlock().getLocation()));
            //e.getPlayer().sendMessage(ChatColor.AQUA + "A cell has been removed!");

        }

    }

}
