
package com.rayzr522.funpaintball.cmd;

import com.rayzr522.funpaintball.CommandHandler;
import com.rayzr522.funpaintball.minigame.Arena;
import com.rayzr522.funpaintball.minigame.Minigame;
import com.rayzr522.funpaintball.util.Msg;

/**
 * The command for deleting maps
 * 
 * @author Rayzr
 *
 */
public class CommandDeleteMap extends CommandHandler {

	private Minigame mg;

	public CommandDeleteMap(Minigame mg) {
		super(null, "deletemap");
		this.mg = mg;
	}

	@Override
	public boolean commandExecuted(String[] args) {

		if (args.length < 1) { return false; }

		Arena arena = mg.getArena(args[0]);
		if (arena == null || !mg.removeArena(arena)) {
			msg("no-such-map", args[0]);
			return false;
		}

		msg("map-removed", args[0]);

		return true;

	}

	@Override
	public String getPermission() {
		return "fpb.admin";
	}

	@Override
	public String getDescription() {
		return "Creates a map";
	}

	@Override
	public String getUsage() {
		return Msg.get("usage.createmap");
	}

}
