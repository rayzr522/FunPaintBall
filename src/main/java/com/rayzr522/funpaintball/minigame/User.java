
package com.rayzr522.funpaintball.minigame;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

public class User {

	private Player		player;
	private UserData	data;

	/**
	 * Creates a new User instance with the given player as a parameter, then
	 * loads the {@link UserData} for that player.
	 * 
	 * @param player
	 */
	public User(Player player) {
		this.player = player;
		data = UserData.get(this);
	}

	/**
	 * Attempts to join an arena
	 * 
	 * @param arena
	 * @return Whether or not the user was able to join
	 */
	public boolean join(Arena arena) {

		return arena.join(this);

	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @return
	 * @see org.bukkit.entity.Entity#getCustomName()
	 */
	public String getCustomName() {
		return player.getCustomName();
	}

	/**
	 * @return
	 * @see org.bukkit.entity.Player#getDisplayName()
	 */
	public String getDisplayName() {
		return player.getDisplayName();
	}

	/**
	 * @return
	 * @see org.bukkit.entity.Damageable#getHealth()
	 */
	public double getHealth() {
		return player.getHealth();
	}

	/**
	 * @return
	 * @see org.bukkit.entity.HumanEntity#getInventory()
	 */
	public PlayerInventory getInventory() {
		return player.getInventory();
	}

	/**
	 * @return
	 * @see org.bukkit.entity.HumanEntity#getItemInHand()
	 */
	public ItemStack getItemInHand() {
		return getInventory().getItemInMainHand();
	}

	/**
	 * @return
	 * @see org.bukkit.entity.Entity#getLocation()
	 */
	public Location getLocation() {
		return player.getLocation();
	}

	/**
	 * @return
	 * @see org.bukkit.entity.HumanEntity#getName()
	 */
	public String getName() {
		return player.getName();
	}

	/**
	 * @return
	 * @see org.bukkit.entity.Entity#getUniqueId()
	 */
	public UUID getId() {
		return player.getUniqueId();
	}

	/**
	 * @return
	 * @see org.bukkit.entity.Entity#getVelocity()
	 */
	public Vector getVelocity() {
		return player.getVelocity();
	}

	/**
	 * @return
	 * @see com.rayzr522.funpaintball.minigame.UserData#getWins()
	 */
	public int getWins() {
		return data.getWins();
	}

	/**
	 * @param wins
	 * @see com.rayzr522.funpaintball.minigame.UserData#setWins(int)
	 */
	public void setWins(int wins) {
		data.setWins(wins);
	}

	/**
	 * @return
	 * @see com.rayzr522.funpaintball.minigame.UserData#incrWins()
	 */
	public int incrWins() {
		return data.incrWins();
	}

	/**
	 * @return
	 * @see com.rayzr522.funpaintball.minigame.UserData#getLosses()
	 */
	public int getLosses() {
		return data.getLosses();
	}

	/**
	 * @param losses
	 * @see com.rayzr522.funpaintball.minigame.UserData#setLosses(int)
	 */
	public void setLosses(int losses) {
		data.setLosses(losses);
	}

	/**
	 * @return
	 * @see com.rayzr522.funpaintball.minigame.UserData#incrLosses()
	 */
	public int incrLosses() {
		return data.incrLosses();
	}

}
