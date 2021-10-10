package com.golim.veo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GameOfLife implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {

            sender.sendMessage(ChatColor.RED + "ERROR! This command requires at least one argument! Please try again.");
            return false;

        }

        switch (args[0]) {

            case "field":

                if (sender instanceof Player) Main.world = ((Player) sender).getWorld();
                else if (Main.world == null) sender.sendMessage(ChatColor.GOLD + "WARNING! ");

                if (args.length == 1) {

                    sender.sendMessage(ChatColor.RED + "ERROR! Missing arguments! Possible arguments are:\n" +
                            "     - setY <y>\n" +
                            "     - setBoundaries <x1> <z1> <x2> <z2>\n" +
                            "     - clear");
                    return false;

                }

                switch (args[1]) {

                    case "setY": //gameoflife field setY <y>

                        if (args.length < 3) {

                            sender.sendMessage(ChatColor.RED + "ERROR! Missing argument! Correct use: /gameoflife field setY <y>");
                            return false;

                        }

                        try {

                            Main.y = Integer.parseInt(args[2]);
                            sender.sendMessage(ChatColor.AQUA + "Y level has been set to: " + Main.y);

                        }
                        catch (Exception e) {

                            e.printStackTrace();
                            sender.sendMessage(ChatColor.RED + "ERROR! The number entered is not a number! Please try again and check for any errors!");

                        }
                        break;

                    case "timerate":

                        try {

                            Main.y = Integer.parseInt(args[2]);
                            sender.sendMessage(ChatColor.AQUA + "Time-rate has been set to: " + Main.y);

                        }
                        catch (Exception e) {

                            e.printStackTrace();
                            sender.sendMessage(ChatColor.RED + "ERROR! The number entered is not a number! Please try again and check for any errors!");

                        }
                        break;

                    /*case "setBoundaries": //gameoflife field setBoundaries <x1> <z1> <x2> <z2>

                        if (args.length < 6) {

                            sender.sendMessage(ChatColor.RED + "ERROR! Missing arguments! Correct use: /gameoflife field setBoundaries <x1> <z1> <x2> <z2>");
                            return false;

                        }

                        try {

                            setBoundaries(

                                    Integer.parseInt(args[2]),
                                    Integer.parseInt(args[3]),
                                    Integer.parseInt(args[4]),
                                    Integer.parseInt(args[5])

                            );
                            sender.sendMessage(ChatColor.AQUA + "Field boundary set from (" + args[2] + "; " + args[3]
                                    + ")" + " to (" + args[4] + "; " + args[5] + ")!");

                        } catch (Exception e) {

                            e.printStackTrace();
                            sender.sendMessage(ChatColor.RED + "ERROR! One of the numbers entered is not a number! Please try again and check for any errors!");

                        }
                        break;*/

                    case "clear":

                        Main.running = false;
                        clear();
                        sender.sendMessage(ChatColor.AQUA + "Field cleared!");
                        break;

                    default:

                        sender.sendMessage(ChatColor.RED + "ERROR! There is no such argument as '" + args[1] + "'!");
                        break;

                }
                break;

            case "toggle":

                if (Main.running) Main.running = false;
                else Main.running = true;

                sender.sendMessage(Main.running ? ChatColor.DARK_GREEN + "Game of Life has been turned on!" : ChatColor.DARK_RED + "Game of Life has been turned off!");
                break;

            /*case "test":

                for (Map.Entry<Location, Boolean> set : Main.cells.entrySet()) {

                    Bukkit.getScheduler().runTaskLater(Main.instance(), () -> {

                        if (set.getValue())
                            set.getKey().clone().subtract(0, 1, 0).getBlock().setType(Material.RED_CONCRETE);

                        else
                            set.getKey().clone().subtract(0, 1, 0).getBlock().setType(Material.LIME_CONCRETE);

                    }, 10L);

                }
                break;*/

            default:

                sender.sendMessage(ChatColor.RED + "ERROR! There is no such argument as '" + args[0] + "'!");
                break;

        }
        return false;

    }

    private static void clear() {



    }

    /*private static void setBoundaries(int x1, int z1, int x2, int z2) {

        if (x1 < x2) {

            for (int x = x1; x < x2; x++) {

                if (z1 < z2) {

                    for (int z = z1; z < z2; z++)
                        addCell(new Location(Main.world, x, Main.y, z));
                    continue;

                }
                for (int z = z2; z > z1; z--)
                    addCell(new Location(Main.world, x, Main.y, z));


            }
            return;

        }
        for (int x = x2; x > x1; x--) {

            if (z1 < z2) {

                for (int z = z1; z < z2; z++)
                    addCell(new Location(Main.world, x, Main.y, z));
                continue;

            }
            for (int z = z2; z > z1; z--)
                addCell(new Location(Main.world, x, Main.y, z));

        }

    }

    private static void addCell(Location location) {

        if (Main.cells.containsKey(location))
            return;

        if (location.getBlock().getType().equals(Material.REDSTONE_BLOCK))
            Main.cells.put(location, true);

        else {

            Main.cells.put(location, false);
            location.getBlock().setType(Material.AIR);

        }

    }*/

}
