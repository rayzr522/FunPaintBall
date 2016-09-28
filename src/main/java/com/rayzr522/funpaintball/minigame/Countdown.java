/**
 * 
 */
package com.rayzr522.funpaintball.minigame;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.rayzr522.funpaintball.util.TextUtils;

/**
 * @author Rayzr
 *
 */
public class Countdown {

    private Player     player;
    private double     time;
    private double     interval;
    private String     label;
    private JavaPlugin plugin;

    public Countdown(Player player, double time, double interval, String label, JavaPlugin plugin) {
        super();
        this.player = player;
        this.time = time;
        this.interval = interval;
        this.label = label;
        this.plugin = plugin;
    }

    /**
     * 
     */
    public void start() {

        new BukkitRunnable() {
            @Override
            public void run() {
                player.sendMessage(TextUtils.colorize(label + " in " + String.format("%.2f", time) + " seconds"));
                time -= interval;
                if (time <= 0.0) {
                    cancel();
                }

            }
        }.runTaskTimer(plugin, 0L, (long) (interval * 20));

    }

}
