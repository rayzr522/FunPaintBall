
package com.rayzr522.funpaintball.minigame;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class MinigameListener implements Listener {

	private Minigame minigame;

	public MinigameListener(Minigame minigame) {
		this.minigame = minigame;
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		User user = new User(e.getEntity());

		if (user.getCurrentArena() == null) { return; }

		Arena arena = user.getCurrentArena();

		user.teleport(arena.deathBoxSpawn);

	}

}
