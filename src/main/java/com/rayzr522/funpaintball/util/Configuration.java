
package com.rayzr522.funpaintball.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Locale;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Files;

/**
 * The Configuration class is both a utility for saving and loading
 * {@linkplain YamlConfiguration}s and for easily creating config classe. <br>
 * <br>
 * 
 * Just {@code extend Configuration} and then all public variables will be saved
 * and loaded simply with {@link Configuration#save(String)} and
 * {@link Configuration#load(String)}. Underscores in variable names will be
 * converted into periods to denote paths. So the following code: <br>
 * <br>
 * <code>
 * public String PERM_ADMIN = "MyPerm.admin";<br>
 * public String PERM_USER = "MyPerm.user";<br>
 * </code><br>
 * becomes:<br>
 * <br>
 * <code>
 * perm:<br>
 * __admin: "MyPerm.admin"<br>
 * __user: "MyPerm.user"<br>
 * </code><br>
 * <br>
 * (I used underscores instead of spaces in the YAML code above because JavaDocs
 * is stupid and won't let me put spaces)
 * 
 * @author Rayzr
 *
 */
public class Configuration {

    public static JavaPlugin plugin;
    public static File       dataFolder;

    /**
     * Must be called at the start of the plugin to initialize this utility
     * class
     * 
     * @param plugin
     *            the current plugin
     */
    public static void init(JavaPlugin plugin) {
        Configuration.plugin = plugin;
        dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
    }

    public static File getFile(String path) {
        return new File(dataFolder.getPath() + File.separator + path.replace("/", File.separator));
    }

    /**
     * 
     * @param path
     *            the path to the config file
     * @return A {@link YamlConfiguration}. Will be empty if the file doesn't
     *         exist.
     */
    public static YamlConfiguration getConfig(String path) {
        return YamlConfiguration.loadConfiguration(getFile(path));
    }

    /**
     * 
     * Try and load a config file form the JAR file. If {@code update} == true
     * then it attempts to update the config file without overwriting it. This
     * method will not overwrite existing config files if they exist.
     * 
     * @param path
     *            path to file, mirrors path in JAR file
     * @param update
     *            currently broken, leave false
     * @return
     */
    public static boolean loadFromJar(String path, boolean update) {

        if (plugin.getResource(path) == null) {
            return false;
        }

        if (update && getFile(path).exists()) {
            File file = getFile(path);
            try {
                Files.move(file.toPath(), getFile("_updating_" + path).toPath());
            } catch (IOException e) {
                System.err.println("Failed to update config file '" + path + "'");
                e.printStackTrace();
                return false;
            }

            plugin.saveResource(path, false);
            YamlConfiguration config1 = getConfig(path);
            YamlConfiguration config2 = getConfig("_updating_" + path);

            for (String key : config1.getKeys(true)) {
                if (!config2.contains(key)) {
                    config2.set(key, config1.get(key));
                }
            }

            try {
                Files.delete(getFile(path).toPath());
                Files.delete(getFile("_updating_" + path).toPath());
            } catch (IOException e) {
                System.err.println("Failed to update config file '" + path + "'");
                e.printStackTrace();
            }

            saveConfig(config2, path);

        } else {
            if (!getFile(path).exists()) {
                plugin.saveResource(path, false);
            }
        }

        return true;

    }

    /**
     * 
     * Equivalent to executing {@code Configuration.loadFromJar(path, false)}.
     * <br/>
     * <br/>
     * Try and load a config file form the JAR file. This method will not
     * overwrite existing config files if they exist.
     * 
     * @param path
     *            path to file, mirrors path in JAR file
     * @param update
     *            currently broken, leave false
     * @return
     */
    public static boolean loadFromJar(String path) {
        return loadFromJar(path, false);
    }

    /**
     * Save a config file to the given path (relative to the plugin's data
     * folder)
     * 
     * @param config
     *            the config file
     * @param path
     *            the path to save to
     * @return Whether it successfully saved or not
     */
    public static boolean saveConfig(YamlConfiguration config, String path) {

        try {
            config.save(getFile(path));
            return true;
        } catch (Exception e) {
            System.err.println("Failed to save config file '" + path + "':");
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Load this Configuration instance from the given path
     * 
     * @param path
     *            the path
     */
    public void load(String path) {

        YamlConfiguration config = getConfig(path);
        if (config.getKeys(true).size() < 1) {
            save(path);
            return;
        }

        for (Field field : getClass().getDeclaredFields()) {
            if (!Modifier.isPublic(field.getModifiers())) {

                continue;

            }

            Object val = config.get(path(field));
            if (val == null) {
                continue;
            }
            try {
                set(field, this, val);
            } catch (IllegalArgumentException e) {
                System.err.println("Error loading '" + path + "': Invalid type '" + field.getType().getCanonicalName() + "' for field '" + field.getName() + "'");
            } catch (IllegalAccessException | NullPointerException e) {

            }
        }

    }

    /**
     * Saves this Configuration instance to the given path
     * 
     * @param path
     *            the path
     */
    public void save(String path) {

        YamlConfiguration config = getConfig(path);

        for (Field field : getClass().getDeclaredFields()) {
            if (!Modifier.isPublic(field.getModifiers())) {
                continue;
            }

            try {
                config.set(path(field), field.get(this));
            } catch (IllegalArgumentException e) {
                System.err.println("Error loading '" + path + "': Invalid type '" + field.getType().getCanonicalName() + "' for field '" + field.getName() + "'");
            } catch (IllegalAccessException e) {

            }

        }

        saveConfig(config, path);

    }

    /**
     * Utility method to set field values
     * 
     * @param field
     *            the field
     * @param inst
     *            the object instance
     * @param val
     *            the value to set
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private void set(Field field, Object inst, Object val) throws IllegalArgumentException, IllegalAccessException {
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        field.set(inst, val);
        field.setAccessible(accessible);
    }

    /**
     * Converts a field into a valid YAML path
     * 
     * @param field
     *            the path
     * @return A valid YAML path (all lowercase)
     */
    private String path(Field field) {
        return field.getName().replace("_", ".").toLowerCase(Locale.getDefault());
    }

}
