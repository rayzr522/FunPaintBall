
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
	public static String	PERM_FPB			= "fpb.user";

	/**
	 * The permission for the vote command
	 */
	public static String	PERM_VOTE			= "fpb.vote";

	/**
	 * The permission for the score command
	 */
	public static String	PERM_SCORE			= "fpb.score";

	/**
	 * The permission for the setup commands
	 */
	public static String	PERM_SETUP			= "fpb.setup";

	/**
	 * The default minimum number of players
	 */
	public static int		DEFAULT_MINPLAYERS	= 2;

	/**
	 * The default maximum number of players
	 */
	public static int		DEFAULT_MAXPLAYERS	= 20;

	/**
	 * Length of time (in seconds) to wait for the game to start
	 */
	public static double	WAIT_START			= 30.0;

	/**
	 * Length of time (in seconds) to wait for the player to respawn
	 */
	public static double	WAIT_DEATH			= 10.0;

	/**
	 * Length of time (in seconds) to wait for the player's snowballs to reload
	 */
	public static double	WAIT_RELOAD			= 4.0;

	/**
	 * The team name for Blue team
	 */
	public static String	TEAM_BLUE			= "Blue";

	/**
	 * The team name for Red team
	 */
	public static String	TEAM_RED			= "Red";

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
