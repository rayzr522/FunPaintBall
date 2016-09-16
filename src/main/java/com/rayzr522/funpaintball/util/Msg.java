
package com.rayzr522.funpaintball.util;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 * A utility class for sending customizable messages to the player. To use this
 * you must first call {@link Msg#load(YamlConfiguration)}, and then to use it
 * you can use {@link Msg#send(Player, String, String...)} or
 * {@link Msg#send(CommandSender, String, String...)}.<br>
 * <br>
 * Just a note, this class is entirely portable (within Bukkit), so you can drag
 * n' drop it anywhere you please. All you have to do is call
 * {@link Msg#load(YamlConfiguration)} with a config file.
 * 
 * @author Rayzr
 * 
 * @see Msg#load(YamlConfiguration)
 * @see Msg#send(Player, String, String...)
 * @see Msg#send(CommandSender, String, String...)
 * @see Msg#DEBUG
 * 
 */
public class Msg {

	private static HashMap<String, String>	messages	= new HashMap<String, String>();

	/**
	 * This controls whether or not to show the messages as they're loading
	 */
	public static boolean					DEBUG		= false;

	/**
	 * Load all messages from the given config file
	 * 
	 * @param config
	 *            the config file
	 */
	public static void load(YamlConfiguration config) {

		// Clear current messages
		messages.clear();

		if (DEBUG) {
			System.out.println("Loading messages:");
		}

		// Make sure the parameter of getKeys is true to get ALL paths, not just
		// first-level paths
		for (String key : config.getKeys(true)) {
			// We only want config values that are strings
			if (config.get(key) instanceof String) {
				// Add it
				messages.put(key, config.getString(key));
			}

		}

		// Match replacement strings. This will (theoretically) match any string
		// that is double square brackets surrounding a valid YAML path string
		// e.g. stuff: "Blah blah [[your.path.here]] blah"
		Pattern regex = Pattern.compile("\\[\\[[a-zA-Z-_.]{1,}\\]\\]");

		// Re-iterate through all messages. This might seem wasteful since we
		// already looped through all messages when we were loading them, but
		// sometimes you might be referencing a messages which hasn't been
		// defined yet... and that wouldn't work particularly well :P
		for (Entry<String, String> entry : messages.entrySet()) {

			String msg = entry.getValue();
			Matcher matcher = regex.matcher(msg);

			// If it can find a match...
			while (matcher.find()) {

				// ...get the match...
				String inputKey = matcher.group();
				// ...remove the square brackets (2 on either side)...
				inputKey = inputKey.substring(2, inputKey.length() - 2);

				// ...if that message exists...
				if (messages.containsKey(inputKey)) {

					// ...replace the reference with the actual message
					msg = msg.replace(matcher.group(), messages.get(inputKey));

				}

			}

			if (DEBUG) {
				System.out.println(entry.getKey() + " - " + msg);
			}

			// Add the message
			messages.put(entry.getKey(), msg);

		}

	}

	/**
	 * Send a player a message with the given key, and replace the parameters
	 * with the given strings.<br>
	 * <br>
	 * If the message with the given key does not exist it will instead send the
	 * player the key given (useful for finding missing messages)
	 * 
	 * @param p
	 *            the player
	 * @param key
	 *            the message key
	 * @param strings
	 *            the strings to use for replacement
	 * 
	 * @see Msg#get(String)
	 */
	public static void send(Player p, String key, String... strings) {

		String msg = get(key);

		for (int i = 0; i < strings.length; i++) {
			msg = msg.replace("{" + i + "}", strings[i]);
		}

		p.sendMessage(msg);

	}

	/**
	 * Alias of {@link Msg#send(Player, String, String...)}
	 * 
	 * @param sender
	 * @param key
	 * @param strings
	 */
	public static void send(CommandSender sender, String key, String... strings) {

		String msg = get(key);

		for (int i = 0; i < strings.length; i++) {
			msg = msg.replace("{" + i + "}", strings[i]);
		}

		sender.sendMessage(msg);

	}

	/**
	 * Get the message for the given key. This translates color codes
	 * automatically.
	 * 
	 * @param key
	 *            the message's key
	 * @return The message, or the key itself if no messages is found with that
	 *         key
	 */
	public static String get(String key) {

		return messages.containsKey(key) ? TextUtils.colorize(messages.get(key)) : key;

	}

}
