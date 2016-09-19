
package com.rayzr522.funpaintball.cmd;

import com.rayzr522.funpaintball.CommandHandler;
import com.rayzr522.funpaintball.FunPaintBall;
import com.rayzr522.funpaintball.util.Msg;

/**
 * The root command for FunPaintBall
 * 
 * @author Rayzr
 *
 */
public class CommandFPB extends CommandHandler {

	private FunPaintBall plugin;

	public CommandFPB(FunPaintBall plugin) {
		super(plugin.getCommand("funpaintball"), "fpb");
		this.plugin = plugin;
	}

	@Override
	public boolean commandExecuted(String[] args) {
		msg("plugin-info", plugin.versionText());
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
		return Msg.get("usage.fpb");
	}

}
