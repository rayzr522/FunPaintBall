
package com.rayzr522.funpaintball.minigame;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

import com.rayzr522.funpaintball.util.Msg;
import com.rayzr522.funpaintball.util.TextUtils;

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

		if (data.getCurrentArena() != null) { return false; }

		if (!arena.join(this)) { return false; }

		data.setCurrentArena(arena);

		return true;

	}

	public void leave() {

		if (data.getCurrentArena() != null) {
			data.getCurrentArena().leave(this);
			data.setCurrentArena(null);
		}

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
		return player.getItemInHand();
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
	 * @param ent
	 *            the entity to teleport to
	 * @return
	 * @see org.bukkit.entity.Entity#teleport(org.bukkit.entity.Entity)
	 */
	public boolean teleport(Entity ent) {
		return player.teleport(ent);
	}

	/**
	 * @param loc
	 *            the location to teleport to
	 * @return
	 * @see org.bukkit.entity.Entity#teleport(org.bukkit.Location)
	 */
	public boolean teleport(Location loc) {
		return player.teleport(loc);
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

	/**
	 * @return the currentArena
	 */
	public Arena getCurrentArena() {
		return data.getCurrentArena();
	}

	/**
	 * @param currentArena
	 *            the currentArena to set
	 */
	public void setCurrentArena(Arena currentArena) {
		data.setCurrentArena(currentArena);
	}

	/**
	 * @param player
	 *            the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Send a message to the player
	 * 
	 * @param key
	 *            the key of the message. See {@link Msg}
	 * @param strings
	 *            the strings to replace with
	 * 
	 * @see Msg#send(Player, String, String...)
	 */
	public void send(String key, Object... strings) {
		Msg.send(player, key, strings);
	}

	/**
	 * @param msg
	 *            the message to send
	 * @see Player#sendMessage(String)
	 */
	public void sendMessage(String msg) {
		player.sendMessage(TextUtils.colorize(msg));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			return ((User) obj).getPlayer().equals(player);
		} else if (obj instanceof Player) {
			return ((Player) obj).equals(player);
		} else {
			return super.equals(obj);
		}
	}

	public void setTeam(int team) {
		data.setTeam(team);
	}

	public int getTeam() {
		return data.getTeam();
	}

	/**
	 * Stores the player's data
	 */
	public void storeData() {
		data.store(player);
	}

	/**
	 * Restores the player's data
	 */
	public void restoreData() {
		data.restore(player);
	}

}
