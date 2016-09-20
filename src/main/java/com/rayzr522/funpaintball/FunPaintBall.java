
package com.rayzr522.funpaintball;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.rayzr522.funpaintball.cmd.CommandCreateMap;
import com.rayzr522.funpaintball.cmd.CommandDeleteMap;
import com.rayzr522.funpaintball.cmd.CommandFPB;
import com.rayzr522.funpaintball.cmd.CommandJoin;
import com.rayzr522.funpaintball.cmd.CommandLeave;
import com.rayzr522.funpaintball.cmd.CommandSetPoint;
import com.rayzr522.funpaintball.cmd.CommandSetSpawn;
import com.rayzr522.funpaintball.minigame.Minigame;
import com.rayzr522.funpaintball.util.Configuration;
import com.rayzr522.funpaintball.util.Msg;

/**
 * The main class of the FunPaintBall plugin
 * 
 * @author Rayzr
 * 
 * @see Minigame
 *
 */
public class FunPaintBall extends JavaPlugin {

	/**
	 * The instance of this plugin
	 */
	public static FunPaintBall	INSTANCE;

	/**
	 * The logger
	 */
	private Logger				logger;

	/**
	 * The root command for this plugin
	 */
	private CommandHandler		root;

	/**
	 * The minigame instance for this plugin
	 */
	private Minigame			mg;

	@Override
	public void onEnable() {

		INSTANCE = this;

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

		// Initialize the minigame
		mg = new Minigame(this, "FunPaintBall");

		// Load all config files
		load();

		// Register the commands
		registerCommands();

		// We're up and running!
		info(versionText() + " enabled");

	}

	@Override
	public void onDisable() {

		// Force stop all matches
		mg.stop();

		save();
		info(versionText() + " disabled");

	}

	/**
	 * Load all config files
	 */
	public void load() {

		// Load all messages from the config file
		Msg.load(Configuration.getConfig("messages.yml"));

		// Load the config file itself
		Config.load();

		// Load the minigame
		mg.load();

	}

	/**
	 * Save all config files
	 */
	public void save() {

		// Save the minigame
		mg.save();

	}

	/**
	 * Alias of {@link Logger#info(String)}
	 * 
	 * @param msg
	 *            the message
	 */
	public void info(String msg) {
		logger.info(msg);
	}

	/**
	 * Alias of {@link Logger#warning(String)}
	 * 
	 * @param msg
	 *            the message
	 */
	public void warn(String msg) {
		logger.warning(msg);
	}

	/**
	 * 
	 * @return The version text in the format of {@code <name> v<version>}
	 */
	public String versionText() {
		return getName() + " v" + getDescription().getVersion();
	}

	/**
	 * ONLY CALL THIS ONCE<br>
	 * Registers all the commands
	 */
	private void registerCommands() {

		root = new CommandFPB(this);
		root.addChild(new CommandSetSpawn(mg));
		root.addChild(new CommandSetPoint(mg));
		root.addChild(new CommandCreateMap(mg));
		root.addChild(new CommandDeleteMap(mg));
		root.addChild(new CommandJoin(mg));
		root.addChild(new CommandLeave(mg));

	}

}
