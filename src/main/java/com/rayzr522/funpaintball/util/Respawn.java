
package com.rayzr522.funpaintball.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Utility class thanks to mine-care <br>
 * <a href="https://bukkit.org/threads/auto-respawn-player.350463/">Bukkit
 * thread</a>
 *
 */
@SuppressWarnings("all")
public class Respawn {

	private static String			bukkitversion;
	private static Class<?>			cp;
	private static Class<?>			clientcmd;
	private static Class			enumClientCMD;
	private static Method			handle;
	private static Constructor<?>	packetconstr;
	private static Enum<?>			num;

	private static boolean			SET_UP	= false;

	static {

		try {

			bukkitversion = Bukkit.getServer().getClass().getPackage().getName().substring(23);
			cp = Class.forName("org.bukkit.craftbukkit." + bukkitversion + ".entity.CraftPlayer");
			clientcmd = Class.forName("net.minecraft.server." + bukkitversion + ".PacketPlayInClientCommand");
			enumClientCMD = Class.forName("net.minecraft.server." + bukkitversion + ".EnumClientCommand");
			handle = cp.getDeclaredMethod("getHandle", null);
			packetconstr = clientcmd.getDeclaredConstructor(enumClientCMD);
			num = Enum.valueOf(enumClientCMD, "PERFORM_RESPAWN");

			SET_UP = true;

		} catch (Exception e) {
			System.err.println("Failed to load Respawn utility!");
			e.printStackTrace();
			SET_UP = false;
		}

	}

	/**
	 * Attempts to automatically respawn the player. This will not work if the
	 * static setup failed. You can tell if it failed or not by the presence of
	 * {@code "Failed to load Respawn utility!"} in the console.
	 * 
	 * @param who
	 *            the player
	 * @throws ReflectiveOperationException
	 *             If something failed with the reflection
	 */
	public static void autoRespawnPlayer(Player who) throws ReflectiveOperationException {

		if (!SET_UP) {
			System.err.println("Failed to autorespawn the player because the Respawn utility was not loaded");
			return;
		}

		Object entityPlayer = handle.invoke(who, null);
		Object packet = packetconstr.newInstance(num);
		Object playerconnection = entityPlayer.getClass().getDeclaredField("playerConnection").get(entityPlayer);
		Method send = playerconnection.getClass().getDeclaredMethod("a", clientcmd);

		send.invoke(playerconnection, packet);
	}

}
