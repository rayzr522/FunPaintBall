
package com.rayzr522.funpaintball.minigame;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.rayzr522.funpaintball.config.ConfigManager;

public class Minigame {

	private JavaPlugin		plugin;
	private ConfigManager	cm;
	private List<Arena>		arenas;

	public Minigame(JavaPlugin plugin, String name) {

		this.plugin = plugin;
		cm = new ConfigManager(plugin);

		arenas = new ArrayList<>();

	}

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
	 * @param config
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
	 * @return
	 */
	public YamlConfiguration getConfig(String path) {
		return YamlConfiguration.loadConfiguration(getFile(path));
	}

	/**
	 * Gets a file
	 * 
	 * @param path
	 * @return
	 */
	public File getFile(String path) {
		return new File(plugin.getDataFolder() + File.separator + path);
	}

}
