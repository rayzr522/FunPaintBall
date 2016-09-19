
package com.rayzr522.funpaintball.cmd;

import com.rayzr522.funpaintball.CommandHandler;
import com.rayzr522.funpaintball.minigame.Arena;
import com.rayzr522.funpaintball.minigame.Minigame;

/**
 * The command for setting spawns
 * 
 * @author Rayzr
 *
 */
public class CommandSetSpawn extends CommandHandler {

	private Minigame mg;

	public CommandSetSpawn(Minigame mg) {
		super(null, "fpb");
		this.mg = mg;
	}

	@Override
	public boolean commandExecuted(String[] args) {

		if (args.length < 1) {
			msg("usage.setspawn");
		}

		String arenaName = "default";
		if (args.length > 1) {
			arenaName = args[1];
		}

		Arena arena = mg.getArena(arenaName);
		if (arena == null) {
			msg("no-such-arena", arenaName);
		}

		String point = args[0].toLowerCase();
		switch (point) {

		case "blue":
			break;
		case "red":
			break;
		case "lobby":
			break;
		case "exit":
			break;
		case "death":
			break;
		default:
			msg("valid-spawns", "blue, red, lobby, exit, death");
			return false;
		}

		return true;
	}

	@Override
	public String getPermission() {
		return "fpb.user";
	}

	@Override
	public String getDescription() {
		return "The base command of FunPaintBall";
	}

	@Override
	public String getUsage() {
		return getCommandTree() + " [cmd]";
	}

}
