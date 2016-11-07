
package com.rayzr522.funpaintball.cmd;

import com.rayzr522.funpaintball.CommandHandler;
import com.rayzr522.funpaintball.minigame.Arena;
import com.rayzr522.funpaintball.minigame.Minigame;
import com.rayzr522.funpaintball.util.Msg;

/**
 * The command for setting spawns
 * 
 * @author Rayzr
 *
 */
public class CommandSetSpawn extends CommandHandler {

    private Minigame mg;

    public CommandSetSpawn(Minigame mg) {
        super(null, "setspawn");
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

        switch (point) {
            case "blue":
                arena.setArenaBlueSpawn(player.getLocation());
                break;
            case "red":
                arena.setArenaRedSpawn(player.getLocation());
                break;
            case "lobby":
                arena.setLobbySpawn(player.getLocation());
                break;
            case "exit":
                arena.setExit(player.getLocation());
                break;
            case "death":
                arena.setDeathBoxSpawn(player.getLocation());
                break;
            default:
                msg("valid-spawns", "blue, red, lobby, exit, death");
                return false;
        }

        msg("spawn-set", point);

        return true;

    }

    @Override
    public String getPermission() {
        return "fpb.admin";
    }

    @Override
    public String getDescription() {
        return "Sets one of the various spawns for an arena";
    }

    @Override
    public String getUsage() {
        return Msg.get("usage.setspawn");
    }

}
