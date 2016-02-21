package com.leontg77.animalscatter.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.leontg77.animalscatter.Main;
import com.leontg77.animalscatter.Utils;

/**
 * AScatter command class.
 * 
 * @author LeonTG77
 */
public class AScatterCommand implements CommandExecutor, TabCompleter {
	private static final String PERMISSION = "uhc.config";
	private final Random rand = new Random();
	
	private static final Set<Biome> INVAILD_BIOMES = ImmutableSet.of(
			Biome.BEACH, Biome.DEEP_OCEAN, Biome.DESERT, Biome.DESERT_HILLS, Biome.DESERT_MOUNTAINS,
			Biome.FROZEN_OCEAN, Biome.FROZEN_RIVER, Biome.HELL, Biome.SKY, Biome.MESA, Biome.MESA_BRYCE,
			Biome.MESA_PLATEAU, Biome.MESA_PLATEAU_MOUNTAINS, Biome.MUSHROOM_ISLAND, Biome.MUSHROOM_SHORE,
			Biome.OCEAN, Biome.RIVER, Biome.SMALL_MOUNTAINS, Biome.STONE_BEACH
	);
	
	private static final List<EntityType> TYPES = ImmutableList.of(
			EntityType.PIG, EntityType.COW, EntityType.SHEEP, EntityType.CHICKEN, EntityType.HORSE
	);
	
	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (!sender.hasPermission(PERMISSION)) {
			sender.sendMessage(ChatColor.RED + "You don't have permission.");
			return true;
		}
		
		if (args.length < 2) {
			sender.sendMessage(Main.PREFIX + "Usage: /ascatter <world> <amount>");
			return true;
		}
		
		World world = Bukkit.getWorld(args[0]);
		
		if (world == null) {
			sender.sendMessage(ChatColor.RED + "'" + args[0] + "' is not a vaild world.");
			return true;
		}
		
		int amount;
		
		try {
			amount = Integer.parseInt(args[1]);
		} catch (Exception e) {
			sender.sendMessage(ChatColor.RED + "'" + args[1] + "' is not a vaild amount.");
			return true;
		}
		
		for (int i = 0; i < amount; i++) {
			Location loc = Utils.findRandomLocation(world);
			
			if (INVAILD_BIOMES.contains(loc.getBlock().getBiome())) {
				continue;
			}
			
			EntityType type = TYPES.get(rand.nextInt(TYPES.size()));
			
			loc.getWorld().spawnEntity(loc, type);
			loc.getWorld().spawnEntity(loc, type);
			loc.getWorld().spawnEntity(loc, type);
			loc.getWorld().spawnEntity(loc, type);
			loc.getWorld().spawnEntity(loc, type);
		}
		
		sender.sendMessage(Main.PREFIX + "Scattered §a" + amount + " §7animals in the world '§6" + world.getName() + "§7'.");
		return true;
	}

	@Override
	public List<String> onTabComplete(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		final List<String> toReturn = new ArrayList<String>();
		
		if (args.length != 1) {
			return toReturn;
		}

		// make sure to only tab complete what starts with what they 
		// typed or everything if they didn't type anything.
		for (World world : Bukkit.getWorlds()) {
			if (args[0].isEmpty() || world.getName().startsWith(args[0].toLowerCase())) {
				toReturn.add(world.getName());
			}
		}
		
		return toReturn;
	}
}