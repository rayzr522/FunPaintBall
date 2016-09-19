
package com.rayzr522.funpaintball.minigame;

import java.util.List;

import org.bukkit.Location;

import com.rayzr522.funpaintball.config.ISerializable;
import com.rayzr522.funpaintball.config.Serialized;

public class Arena implements ISerializable {

	public static final int	WAITING		= 0;
	public static final int	STARTING	= 1;
	public static final int	RUNNING		= 2;

	@Serialized
	protected String		name;
	@Serialized
	protected int			minPlayers	= 2;
	@Serialized
	protected int			maxPlayers	= 12;

	@Serialized
	protected Region		arenaRegion;
	@Serialized
	protected Region		lobbyRegion;
	@Serialized
	protected Region		deathBox;

	@Serialized
	protected Location		arenaBlueSpawn;
	@Serialized
	protected Location		arenaRedSpawn;
	@Serialized
	protected Location		lobbySpawn;
	@Serialized
	protected Location		deathBoxSpawn;
	@Serialized
	protected Location		exit;

	protected List<User>	users;

	protected boolean		valid;

	private int				state		= 0;

	public Arena(String name, int minPlayers, int maxPlayers) {

		this.name = name;
		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;

	}

	@Override
	public void onDeserialize() {
	}

	@Override
	public void onPreSerialize() {
	}

	public final boolean join(User user) {

		if (!isValid()) { return false; }
		if (users.contains(user)) { return false; }

		users.add(user);
		onJoin(user);
		return true;

	}

	public void leave(User user) {

		if (users.remove(user)) {
			user.teleport(exit);
		}

	}

	public void switchState(int state) {
		// int oldState = this.state;
		this.state = state;

		if (state == 1) {

			boolean team = false;

			for (User user : users) {

				team = !team;
				user.teleport(team ? arenaBlueSpawn : arenaRedSpawn);

			}

		}
	}

	protected void onJoin(User user) {

	}

	protected void onStart() {

	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the minPlayers
	 */
	public int getMinPlayers() {
		return minPlayers;
	}

	/**
	 * @param minPlayers
	 *            the minPlayers to set
	 */
	public void setMinPlayers(int minPlayers) {
		this.minPlayers = minPlayers;
	}

	/**
	 * @return the maxPlayers
	 */
	public int getMaxPlayers() {
		return maxPlayers;
	}

	/**
	 * @param maxPlayers
	 *            the maxPlayers to set
	 */
	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	/**
	 * @return the arenaRegion
	 */
	public Region getArenaRegion() {
		return arenaRegion;
	}

	/**
	 * @param arenaRegion
	 *            the arenaRegion to set
	 */
	public void setArenaRegion(Region arenaRegion) {
		this.arenaRegion = arenaRegion;
	}

	/**
	 * @return the lobbyRegion
	 */
	public Region getLobbyRegion() {
		return lobbyRegion;
	}

	/**
	 * @param lobbyRegion
	 *            the lobbyRegion to set
	 */
	public void setLobbyRegion(Region lobbyRegion) {
		this.lobbyRegion = lobbyRegion;
	}

	/**
	 * Check whether this Arena is currently valid (has all required data like
	 * regions, spawns, etc.)
	 * 
	 * @return
	 */
	public boolean isValid() {
		return valid = !(arenaRegion == null || lobbyRegion == null || deathBox == null || arenaBlueSpawn == null || arenaRedSpawn == null || lobbySpawn == null || deathBoxSpawn == null || exit == null);
	}

	/**
	 * Get the current state of the arena
	 * 
	 * @return
	 */
	public int currentState() {
		return state;
	}

}
