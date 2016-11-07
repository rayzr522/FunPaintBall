
package com.rayzr522.funpaintball.cmd;

import com.rayzr522.funpaintball.CommandHandler;
import com.rayzr522.funpaintball.minigame.Minigame;
import com.rayzr522.funpaintball.minigame.User;
import com.rayzr522.funpaintball.util.Msg;

/**
 * The command for join
 * 
 * @author Rayzr
 *
 */
public class CommandLeave extends CommandHandler {

    @SuppressWarnings("unused")
    private Minigame mg;

    public CommandLeave(Minigame mg) {
        super(null, "leave");
        this.mg = mg;
    }

    @Override
    public boolean commandExecuted(String[] args) {

        if (new User(player).leave()) {
            msg("left");
        } else {
            msg("not-in-map");
        }

        return true;

    }

    @Override
    public String getPermission() {
        return "fpb.user";
    }

    @Override
    public String getDescription() {
        return "Leaves the queue";
    }

    @Override
    public String getUsage() {
        return Msg.get("usage.leave");
    }

}
