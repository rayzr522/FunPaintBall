
package com.rayzr522.funpaintball.minigame;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.rayzr522.funpaintball.config.ConfigManager;

public class Minigame {

	private JavaPlugin			plugin;
	private ConfigManager		cm;
	private List<Arena>			arenas;

	private MinigameListener	listener;

	public Minigame(JavaPlugin plugin, String name) {

		this.plugin = plugin;
		// Initialize the ConfigManager
		cm = new ConfigManager(plugin);

		arenas = new ArrayList<>();

		// Register listener. This is required for game logic
		listener = new MinigameListener(this);
		plugin.getServer().getPluginManager().registerEvents(listener, plugin);

	}

	/**
	 * Load all config files related to the minigame
	 */
	public void load() {

		YamlConfiguration arenaConfig = getConfig("arenas.yml");
		for (String key : arenaConfig.getKeys(false)) {
			Arena arena = cm.load(Arena.class, arenaConfig.getConfigurationSection(key));
			if (arena == null) {
				System.err.println("Failed to load arena with key '" + key + "'... skipping");
				continue;
			}
			arenas.add(arena);
		}

	}

	/**
	 * Save all config files related to the minigame
	 */
	public void save() {

		YamlConfiguration arenaConfig = getConfig("arenas.yml");

		for (Arena arena : arenas) {
			cm.save(arena, arenaConfig.createSection(arena.getName()));
		}
		saveConfig("arenas.yml", arenaConfig);

		YamlConfiguration playersConfig = getConfig("userData.yml");
		UserData.save(playersConfig);
		saveConfig("users.yml", playersConfig);

	}

	/**
	 * Force stops all arenas
	 * 
	 * @see Arena#forceStop()
	 */
	public void stop() {
		for (Arena arena : arenas) {
			arena.forceStop();
		}
	}

	/**
	 * Get an arena
	 * 
	 * @param name
	 *            the name of the arena (non case-specific)
	 * @return The arena, or {@code null} if no arena was found
	 */
	public Arena getArena(String name) {

		for (Arena arena : arenas) {
			if (arena.getName().equalsIgnoreCase(name)) { return arena; }
		}
		return null;

	}

	/**
	 * Saves a config file
	 * 
	 * @param path
	 *            the path to the config file
	 * @param config
	 *            the config file
	 */
	public void saveConfig(String path, YamlConfiguration config) {
		try {
			File file = getFile(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			config.save(file);
		} catch (Exception e) {
			System.out.println("Failed to write config to path '" + path + "':");
			e.printStackTrace();
		}
	}

	/**
	 * Gets a config file
	 * 
	 * @param path
	 *            the path to the config file
	 * @return The config file (will be an empty config if there was no file at
	 *         the given path)
	 * 
	 */
	public YamlConfiguration getConfig(String path) {
		return YamlConfiguration.loadConfiguration(getFile(path));
	}

	/**
	 * Gets a file
	 * 
	 * @param path
	 *            the path. This is relative to the data folder of the pluign.
	 * @return The file
	 */
	public File getFile(String path) {
		return new File(plugin.getDataFolder() + File.separator + path);
	}

	/**
	 * Create an arena, add it to the arenas list, and return it for further
	 * modification
	 * 
	 * @param name
	 *            the name of the arena
	 * @return The new arena, or {@code null} if an arena already existed under
	 *         that name
	 */
	public Arena createArena(String name) {

		if (getArena(name) != null) { return null; }

		Arena arena = new Arena(name);
		arenas.add(arena);
		return arena;
	}

	/**
	 * Removes an arena
	 * 
	 * @param arena
	 *            the arena to remove
	 * @return Whether or not the arena was removed. Will return false if the
	 *         arena wasn't in the list of arenas.
	 */
	public boolean removeArena(Arena arena) {
		return arenas.remove(arena);
	}

	/**
	 * Alias of {@code removeArena(getArena(name))}
	 * 
	 * @param name
	 *            the name of the arena
	 * @return Whether or not the arena was removed. Will return false if the
	 *         arena wasn't in the list of arenas or if there was no arena for
	 *         the given name.
	 */
	public boolean removeArena(String name) {
		Arena arena = getArena(name);
		if (arena == null) { return false; }
		return arenas.remove(arena);
	}

	/**
	 * Check if a location is within any arena
	 * 
	 * @param location
	 *            the location to check
	 * @return Whether or not it's within any of the known arenas
	 */
	public boolean isInArena(Location location) {
		for (Arena arena : arenas) {
			if (arena.isInArena(location)) { return true; }
		}
		return false;
	}
}
