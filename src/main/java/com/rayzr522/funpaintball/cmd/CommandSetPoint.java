
package com.rayzr522.funpaintball.cmd;

import org.bukkit.Location;

import com.rayzr522.funpaintball.CommandHandler;
import com.rayzr522.funpaintball.minigame.Arena;
import com.rayzr522.funpaintball.minigame.Minigame;
import com.rayzr522.funpaintball.util.Msg;

/**
 * The command for setting region points
 * 
 * @author Rayzr
 *
 */
public class CommandSetPoint extends CommandHandler {

    private Minigame mg;

    public CommandSetPoint(Minigame mg) {
        super(null, "setpoint");
        this.mg = mg;
    }

    @Override
    public boolean commandExecuted(String[] args) {

        if (args.length < 1) {
            return false;
        }

        String arenaName = "default";
        if (args.length > 1) {
            arenaName = args[1];
        }

        Arena arena = mg.getArena(arenaName);
        if (arena == null) {
            msg("no-such-map", arenaName);
            return false;
        }

        String point = args[0].toLowerCase();

        Location loc = player.getLocation();

        switch (point) {
            case "arena1":
                arena.getArenaRegion().setMin(loc);
                break;
            case "arena2":
                arena.getArenaRegion().setMax(loc);
                break;
            case "lobby1":
                arena.getLobbyRegion().setMin(loc);
                break;
            case "lobby2":
                arena.getLobbyRegion().setMax(loc);
                break;
            case "death1":
                arena.getDeathBox().setMin(loc);
                break;
            case "death2":
                arena.getDeathBox().setMax(loc);
                break;
            default:
                msg("valid-points", "arena1, arena2, lobby1, lobby2, death1, death2");
                return false;
        }

        msg("point-set", point);

        return true;

    }

    @Override
    public String getPermission() {
        return "fpb.admin";
    }

    @Override
    public String getDescription() {
        return "Sets one of the various region points for an arena";
    }

    @Override
    public String getUsage() {
        return Msg.get("usage.setpoint");
    }

}
