
package com.rayzr522.funpaintball.util;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

/**
 * Methods to convert objects to and from String form. String forms of objects
 * are generally their important variables separated by a colon
 * 
 * @author Rayzr
 *
 */
public class ConfigUtils {

    /**
     * Converts a {@link Vector} to a string
     * 
     * @param vec
     * @return
     */
    public static String toString(Vector vec) {

        return vec.getX() + ":" + vec.getY() + ":" + vec.getZ();

    }

    /**
     * Converts a {@link Location} to a string
     * 
     * @param loc
     * @return
     */
    public static String toString(Location loc) {

        return toString(loc.getWorld()) + ":" + loc.getX() + ":" + loc.getY() + ":" + loc.getZ() + ":" + loc.getYaw() + ":" + loc.getPitch();

    }

    /**
     * Converts a {@link World} to a string
     * 
     * @param world
     * @return
     */
    public static String toString(World world) {

        return world.getUID().toString();

    }

    /**
     * Gets a {@link Vector} from a string
     * 
     * @param vec
     * @return
     */
    public static Vector vector(String vec) {

        String[] split = vec.split(":");

        if (split.length != 3) {
            return null;
        }

        return new Vector(d(split[0]), d(split[1]), d(split[2]));

    }

    /**
     * Gets a {@link Location} from a string
     * 
     * @param loc
     * @return
     */
    public static Location location(String loc) {

        String[] split = loc.split(":");
        if (split.length != 6) {
            return null;
        }

        if (world(split[0]) == null) {
            System.err.println("'" + split[0] + "' is an invalid world UUID!");
            return null;
        }

        return new Location(world(split[0]), d(split[1]), d(split[2]), d(split[3]), f(split[4]), f(split[5]));

    }

    /**
     * Gets a {@link World} from a string
     * 
     * @param world
     * @return
     */
    public static World world(String world) {

        return Bukkit.getWorld(UUID.fromString(world));

    }

    /**
     * Gets an integer from a string
     * 
     * @param text
     * @return
     */
    public static int i(String text) {
        return Integer.parseInt(text);
    }

    /**
     * Gets a double from a string
     * 
     * @param text
     * @return
     */
    public static double d(String text) {
        return Double.parseDouble(text);
    }

    /**
     * Gets a float from a string
     * 
     * @param text
     * @return
     */
    public static float f(String text) {
        return Float.parseFloat(text);
    }

}
