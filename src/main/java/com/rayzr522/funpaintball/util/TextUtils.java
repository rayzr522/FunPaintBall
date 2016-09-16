
package com.rayzr522.funpaintball.util;

import java.util.Locale;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.Material;

/**
 * Utilities for formatting text in various ways
 * 
 * @author Rayzr
 * 
 * @see TextUtils#colorize(String)
 *
 */
public class TextUtils {

	/**
	 * Characters that might be unsafe for use in technical situations
	 */
	public static final Pattern UNSAFE_CHARS = Pattern.compile("[^a-z0-9]");

	/**
	 * Convert '&' color codes to Minecraft color and formatting codes
	 * 
	 * @param text
	 *            the text to convert
	 * @return The converted text
	 */
	public static String colorize(String text) {

		return ChatColor.translateAlternateColorCodes('&', text);

	}

	/**
	 * Convert Minecraft color and formatting codes back to '&' color codes
	 * 
	 * @param text
	 *            the text to convert
	 * @return The converted text
	 */
	public static String uncolorize(String text) {

		return text.replace(ChatColor.COLOR_CHAR, '&');

	}

	/**
	 * Removes all Minecraft color and formatting codes
	 * 
	 * @param text
	 *            the text to strip
	 * @return The stripped text
	 */
	public static String stripColor(String text) {

		return ChatColor.stripColor(text);

	}

	/**
	 * Converts text into the format usually used by Enums. Great for getting a
	 * {@link Material} from a user's input.<br>
	 * <br>
	 * The conversion process does the following:<br>
	 * <ul>
	 * <li>Removes whitespace on the edges of the text</li>
	 * <li>Converts the text to upper case</li>
	 * <li>Replaces all spaces with underscores</li>
	 * </ul>
	 * 
	 * @param text
	 *            the text
	 * @return The formatted text
	 */
	public static String enumFormat(String text) {

		return text.trim().toUpperCase().replace(" ", "_");

	}

	// Yes, this is from Essentials. Stop judging me.
	/**
	 * Method extracted from Essentials to remove special characters
	 * 
	 * @param text
	 * @return The safe text
	 */
	public static String safeString(String text) {
		return UNSAFE_CHARS.matcher(text.toLowerCase(Locale.ENGLISH)).replaceAll("_");
	}

}
