package com.leontg77.animalscatter;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.leontg77.animalscatter.commands.AScatterCommand;

/**
 * Main class of the plugin.
 * 
 * @author LeonTG77
 */
public class Main extends JavaPlugin {
	public static final String PREFIX = "§dAnimalScatter §8» §7";
	
	@Override
	public void onDisable() {
		final PluginDescriptionFile file = getDescription();
		getLogger().info(file.getName() + " has been disabled.");
	}
	
	@Override
	public void onEnable() {
		final PluginDescriptionFile file = getDescription();
		getLogger().info(file.getName() + " v" + file.getVersion() + " has been enabled.");
		getLogger().info("The plugin is made by LeonTG77.");
		
		final AScatterCommand command = new AScatterCommand();
		
		// register command.
		getCommand("ascatter").setExecutor(command);
		getCommand("ascatter").setTabCompleter(command);
	}
}