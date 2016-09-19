
package com.rayzr522.funpaintball.minigame;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 * A class for storing various statistics about the user
 * 
 * @author Rayzr
 *
 */
public class UserData {

	private static HashMap<UUID, UserData> players = new HashMap<>();

	public static void load(YamlConfiguration config) {

		players.clear();

		for (String key : config.getKeys(false)) {

			UUID id = UUID.fromString(key);
			ConfigurationSection section = config.getConfigurationSection(key);

			players.put(id, new UserData(id, section));

		}

	}

	public static void save(YamlConfiguration config) {

		for (UserData data : players.values()) {
			ConfigurationSection section = config.createSection(data.getId().toString());
			data.save(section);
		}

	}

	public static UserData get(User user) {

		UserData data = players.get(user.getId());
		if (data == null) {
			data = new UserData(user);
			players.put(user.getId(), data);
		}
		return data;

	}

	private UUID		id;
	private int			wins	= 0;
	private int			losses	= 0;

	private int			team	= -1;
	private Arena		currentArena;
	private PlayerData	data;

	public UserData(User user) {
		id = user.getId();
	}

	/**
	 * Load from ConfigurationSection
	 * 
	 * @param id
	 * @param section
	 */
	public UserData(UUID id, ConfigurationSection section) {
		this.id = id;
		this.wins = section.getInt("wins");
		this.losses = section.getInt("losses");
	}

	private void save(ConfigurationSection section) {

		section.set("wins", wins);
		section.set("losses", losses);

	}

	/**
	 * @return the wins
	 */
	public int getWins() {
		return wins;
	}

	/**
	 * @param wins
	 *            the wins to set
	 */
	public void setWins(int wins) {
		this.wins = wins;
	}

	/**
	 * Increment the wins by one
	 * 
	 * @return the wins
	 */
	public int incrWins() {
		return losses++;
	}

	/**
	 * @return the losses
	 */
	public int getLosses() {
		return losses;
	}

	/**
	 * @param losses
	 *            the losses to set
	 */
	public void setLosses(int losses) {
		this.losses = losses;
	}

	/**
	 * Increment the losses by one
	 * 
	 * @return the losses
	 */
	public int incrLosses() {
		return losses++;
	}

	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @return the currentArena
	 */
	public Arena getCurrentArena() {
		return currentArena;
	}

	/**
	 * @param currentArena
	 *            the currentArena to set
	 */
	public void setCurrentArena(Arena currentArena) {
		this.currentArena = currentArena;
	}

	public int getTeam() {
		return team;
	}

	public int setTeam(int team) {
		return this.team = team;
	}

	/**
	 * @param player
	 *            the player to store the data of
	 */
	public void store(Player player) {
		data = new PlayerData(player);
	}

	/**
	 * 
	 * @param player
	 *            the player to restore the data to
	 */
	public void restore(Player player) {
		if (data == null) throw new IllegalStateException("No data was stored!");
		data.restore(player);
		data = null;
	}

}
