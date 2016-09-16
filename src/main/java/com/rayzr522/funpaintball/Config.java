
package com.rayzr522.funpaintball;

import com.rayzr522.funpaintball.util.Configuration;

/**
 * The config file for FunPaintBall
 * 
 * @author Rayzr
 * 
 * @see Config#load()
 * @see Config#save()
 *
 */
public class Config extends Configuration {

	/**
	 * The permission for the main command
	 */
	public static String	PERM_FPB	= "fpb.user";

	/**
	 * The permission for the vote command
	 */
	public static String	PERM_VOTE	= "fpb.vote";

	/**
	 * The permission for the score command
	 */
	public static String	PERM_SCORE	= "fpb.score";

	/**
	 * The permission for the setup commands
	 */
	public static String	PERM_SETUP	= "fpb.setup";

	/**
	 * Utility method to allow static access. Equivalent to:<br>
	 * {@code new Config().load("config.yml");}
	 */
	public static void load() {
		new Config().load("config.yml");
	}

	/**
	 * Utility method to allow static access. Equivalent to:<br>
	 * {@code new Config().save("config.yml");}
	 */
	public static void save() {
		new Config().save("config.yml");
	}

}
