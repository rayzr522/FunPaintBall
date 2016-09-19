
package com.rayzr522.funpaintball.minigame;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import com.rayzr522.funpaintball.config.ISerializable;
import com.rayzr522.funpaintball.config.Serialized;

public class Region implements ISerializable {

	@Serialized
	private World	world;
	@Serialized
	private Vector	min;
	@Serialized
	private Vector	max;

	public Region(Vector min, Vector max, World world) {
		this.world = world;
		this.min = Vector.getMinimum(min, max);
		this.max = Vector.getMaximum(min, max);
	}

	public Region(Location min, Location max) {
		this(min.toVector(), max.toVector(), min.getWorld());
	}

	@Override
	public void onDeserialize() {
	}

	@Override
	public void onPreSerialize() {
	}

	/**
	 * @return the world
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * @param world
	 *            the world to set
	 */
	public void setWorld(World world) {
		this.world = world;
	}

	/**
	 * @return the min
	 */
	public Vector getMin() {
		return min;
	}

	/**
	 * @param min
	 *            the min to set
	 */
	public void setMin(Vector min) {
		this.min = min;
	}

	/**
	 * @return the max
	 */
	public Vector getMax() {
		return max;
	}

	/**
	 * @param max
	 *            the max to set
	 */
	public void setMax(Vector max) {
		this.max = max;
	}

	/**
	 * @param min
	 *            the min to set
	 */
	public void setMin(Location min) {
		this.min = min.toVector();
		this.world = min.getWorld();
	}

	/**
	 * @param max
	 *            the max to set
	 */
	public void setMax(Location max) {
		this.max = max.toVector();
		this.world = max.getWorld();
	}

}
