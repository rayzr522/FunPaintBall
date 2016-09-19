
package com.rayzr522.funpaintball.cmd;

import com.rayzr522.funpaintball.CommandHandler;
import com.rayzr522.funpaintball.minigame.Minigame;
import com.rayzr522.funpaintball.util.Msg;

/**
 * The command for setting spawns
 * 
 * @author Rayzr
 *
 */
public class CommandCreateMap extends CommandHandler {

	private Minigame mg;

	public CommandCreateMap(Minigame mg) {
		super(null, "createmap");
		this.mg = mg;
	}

	@Override
	public boolean commandExecuted(String[] args) {

		if (args.length < 1) { return false; }

		if (mg.createArena(args[0]) == null) {
			msg("map-exists", args[0]);
			return false;
		}

		msg("map-created", args[0]);

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
