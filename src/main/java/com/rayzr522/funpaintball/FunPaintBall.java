
package com.rayzr522.funpaintball;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.rayzr522.funpaintball.cmd.CommandFPB;
import com.rayzr522.funpaintball.util.Configuration;
import com.rayzr522.funpaintball.util.Msg;

/**
 * The main class of the FunPaintBall plugin
 * 
 * @author Rayzr
 *
 */
public class FunPaintBall extends JavaPlugin {

	private Logger			logger;
	@SuppressWarnings("unused")
	private CommandHandler	root;

	@Override
	public void onEnable() {

		// Get the logger
		logger = getLogger();

		// Initialize the Configuration utility
		Configuration.init(this);

		// Load the messages.yml file
		if (!Configuration.loadFromJar("messages.yml")) {
			// Something went wrong, let's go die in a hole
			warn("Something went wrong when loading 'messages.yml'");
			warn("Goodbye cruel world...");
			Bukkit.getPluginManager().disablePlugin(this);
		}

		load();

		// Register the commands
		registerCommands();

		// We're up and running!
		info(versionText() + " enabled");

	}

	@Override
	public void onDisable() {

		save();
		info(versionText() + " disabled");

	}

	public void load() {

		// Load all messages from the config file
		Msg.load(Configuration.getConfig("messages.yml"));

		// Load the config file itself
		Config.load();

	}

	public void save() {

	}

	public void info(String msg) {
		logger.info(msg);
	}

	public void warn(String msg) {
		logger.warning(msg);
	}

	public String versionText() {
		return getName() + " v" + getDescription().getVersion();
	}

	/**
	 * ONLY CALL THIS ONCE<br>
	 * Registers all the commands
	 */
	private void registerCommands() {
		root = new CommandFPB(this);

	}

}
