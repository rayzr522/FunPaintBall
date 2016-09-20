
package com.rayzr522.funpaintball.minigame;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.rayzr522.funpaintball.util.Respawn;

public class MinigameListener implements Listener {

	private Minigame minigame;

	public MinigameListener(Minigame minigame) {
		this.minigame = minigame;
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {

		User user = new User(e.getEntity());

		if (user.getCurrentArena() == null) { return; }
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
		if (user.getCurrentArena() == null) { return; }
		e.setCancelled(true);
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		new User(e.getPlayer()).leave();
	}

}
