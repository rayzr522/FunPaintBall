
package com.rayzr522.funpaintball.minigame;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.rayzr522.funpaintball.util.Respawn;

public class MinigameListener implements Listener {

    private Minigame minigame;

    public MinigameListener(Minigame minigame) {
        this.minigame = minigame;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {

        User user = new User(e.getEntity());

        if (user.getCurrentArena() == null) {
            return;
        }
        try {
            Respawn.autoRespawnPlayer(e.getEntity());
        } catch (ReflectiveOperationException e1) {
            e1.printStackTrace();
        }

        user.getCurrentArena().onDeath(user);

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (minigame.isInArena(e.getBlock().getLocation())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onExplosion(BlockExplodeEvent e) {
        if (minigame.isInArena(e.getBlock().getLocation())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (minigame.isInArena(e.getBlock().getLocation())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent e) {
        if (minigame.isInArena(e.getBlock().getLocation())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        User user = new User(e.getPlayer());
        if (user.getCurrentArena() == null) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        new User(e.getPlayer()).leave();
    }

    @EventHandler
    public void onPlayerShoot(ProjectileLaunchEvent e) {
        if (!(e.getEntity().getShooter() instanceof Player)) {
            return;
        }

        Player p = (Player) e.getEntity().getShooter();

        User u = new User(p);
        if (u.getCurrentArena() == null) {
            return;
        }

        ItemStack item = p.getInventory().getItemInMainHand();
        if (item == null || item.getType() == Material.AIR) {
            new Countdown(p, 4.0, 1.0, "Reloading", minigame.getPlugin()).start();
            u.getCurrentArena().later(new BukkitRunnable() {
                @Override
                public void run() {
                    p.getInventory().addItem(new ItemStack(Material.SNOW_BALL, 64));
                }
            }, 4.0);
        }

    }

    @EventHandler
    public void onProjectHit(EntityDamageByEntityEvent e) {

        if (e.getDamager().getType() != EntityType.SNOWBALL || !(e.getEntity() instanceof Player)) {
            return;
        }

        Player damaged = (Player) e.getEntity();
        Projectile damager = (Projectile) e.getDamager();

        if (damager.getShooter() instanceof Player && new User((Player) damager.getShooter()).getCurrentArena() != null) {
            damaged.setHealth(0);
            e.setCancelled(true);
        }

    }

}
