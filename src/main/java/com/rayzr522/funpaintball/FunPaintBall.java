
package com.rayzr522.funpaintball;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.rayzr522.funpaintball.cmd.CommandCreateMap;
import com.rayzr522.funpaintball.cmd.CommandDeleteMap;
import com.rayzr522.funpaintball.cmd.CommandFPB;
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
 */
public class FunPaintBall extends JavaPlugin {

	public static FunPaintBall	INSTANCE;

	private Logger				logger;

	private CommandHandler		root;

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

		mg = new Minigame(this, "FunPaintBall");

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

		// Load the minigame
		mg.load();

	}

	public void save() {

		// Save the minigame
		mg.save();

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
		root.addChild(new CommandSetSpawn(mg));
		root.addChild(new CommandSetPoint(mg));
		root.addChild(new CommandCreateMap(mg));
		root.addChild(new CommandDeleteMap(mg));

	}

}
