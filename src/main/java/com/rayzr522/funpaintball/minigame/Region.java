
package com.rayzr522.funpaintball.minigame;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import com.rayzr522.funpaintball.config.ISerializable;
import com.rayzr522.funpaintball.config.Serialized;

public class Region implements ISerializable {

    @Serialized
    private World  world;
    @Serialized
    private Vector min;
    @Serialized
    private Vector max;

    /**
     * Only use this for creating partial or unfinished regions
     */
    public Region() {

    }

    public Region(Vector min, Vector max, World world) {
        this.world = world;
        this.min = min;
        this.max = max;
        evaluate();
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
        evaluate();
    }

    /**
     * @param max
     *            the max to set
     */
    public void setMax(Location max) {
        this.max = max.toVector();
        this.world = max.getWorld();
        evaluate();
    }

    /**
     * Re-evaluate which point is the minimum and which point is the maximum
     */
    private void evaluate() {

        if (min == null || max == null) {
            return;
        }

        Vector oldMin = min;
        Vector oldMax = max;

        min = Vector.getMinimum(oldMin, oldMax);
        max = Vector.getMaximum(oldMin, oldMax);

    }

    /**
     * Check whether this region is fully setup
     * 
     * @return
     */
    public boolean isValid() {
        return !(min == null || max == null || world == null);
    }

    public boolean inRegion(Location loc) {

        if (!isValid()) {
            return false;
        }
        if (loc.getWorld() != world) {
            return false;
        }
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        return x >= min.getX() && x <= max.getX() && y >= min.getY() && y <= max.getY() && z >= min.getZ() && z <= max.getZ();

    }

}
