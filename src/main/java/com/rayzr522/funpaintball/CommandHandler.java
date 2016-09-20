
package com.rayzr522.funpaintball;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import com.rayzr522.funpaintball.util.ArrayUtils;
import com.rayzr522.funpaintball.util.Msg;

/**
 * A utility class for command handling. To begin use start by making the root
 * CommandHandler:<br>
 * <br>
 * <code>
 * CommandHandler root = new CommandHandler(plugin.getCommand("yourCommandHere"), "yourCommandName") { ... };<br>
 * </code> <br>
 * Then to add child commands do the following:<br>
 * <br>
 * <code>
 * ...<br>
 * CommandHandler child = new CommandHandler("childCommand") { ... };<br>
 * root.addChild(child);<br>
 * </code> <br>
 * That's all you have to do other than fill in the unimplemented methods for
 * your command.
 * 
 * @see CommandHandler#commandExecuted(String[])
 * @see CommandHandler#player
 * @see CommandHandler#getDescription()
 * @see CommandHandler#getUsage()
 * @see CommandHandler#getCommandTree()
 * 
 * @author Rayzr
 *
 */
public abstract class CommandHandler implements CommandExecutor {

	// All child commands
	private HashMap<String, CommandHandler>	children	= new HashMap<>();
	protected String						name		= null;

	/**
	 * The parent {@link CommandHandler}
	 */
	protected CommandHandler				parent		= null;

	/**
	 * A special variable that is always null except when
	 * {@link CommandHandler#commandExecuted(String[])} is being called.
	 * Attempts to access shorthand methods which utilize this will result in an
	 * {@link IllegalStateException}
	 */
	protected Player						player		= null;

	/**
	 * 
	 * @param command
	 *            the PluginCommand to associate this with or null if it is a
	 *            child command
	 * @param name
	 *            the name of this command (used for child command-matching)
	 */
	public CommandHandler(PluginCommand command, String name) {
		if (command != null) {
			command.setExecutor(this);
		}
		this.name = name;
	}

	/**
	 * Alias of {@code CommandHandler(null, name)}
	 * 
	 * @param name
	 *            the name of this command (used for child command-matching)
	 */
	public CommandHandler(String name) {
		this(null, name);
	}

	/**
	 * Called when the command is executed. This is the internal code of
	 * CommandHandler, if you wish to use this please use
	 * {@link CommandHandler#commandExecuted(Player, String[])} instead.
	 */
	@Override
	public final boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player)) {
			Msg.send(sender, "only-players");
		}

		if (args.length > 0) {

			String arg = args[0].toLowerCase();

			if (children.size() >= 1) {

				if (children.containsKey(arg)) {

					CommandHandler child = children.get(args[0]);
					return child.onCommand(sender, command, label, ArrayUtils.removeFirst(args));

				}

			}

			if (arg.equals("help") || arg.equals("?")) {

				showAllHelp(sender);
				return true;

			}

		}

		player = (Player) sender;
		if (!commandExecuted(args)) {
			showHelp(player);
		}
		player = null;

		return true;

	}

	/**
	 * Called when a command is executed. This is not called if the first
	 * argument is "help" or "?", or if a valid child command is found. To
	 * access the player simply use the {@link CommandHandler#player} variable,
	 * which is part of
	 * 
	 * @param args
	 *            the arguments
	 * @return Whether or not to show help for this command
	 */
	public abstract boolean commandExecuted(String[] args);

	/**
	 * Show help for this command to the player
	 * 
	 * @param sender
	 *            the player
	 */
	public void showHelp(CommandSender sender) {

		Msg.send(sender, "command-help", getUsage(), getDescription());

	}

	/**
	 * Utility alias (only useable from within
	 * {@link CommandHandler#commandExecuted(Player, String[])}
	 */
	public void showHelp() {
		if (player == null) { throw new IllegalStateException("Must be used from within CommandHandler.commandExecuted!"); }
		showHelp(player);
	}

	/**
	 * Show all help and child commands for this command to the player
	 * 
	 * @param sender
	 *            the player
	 */
	public void showAllHelp(CommandSender sender) {

		Msg.send(sender, "command-help", getUsage(), getDescription());
		for (CommandHandler child : children.values()) {
			child.showHelp(sender);
		}

	}

	/**
	 * Utility alias (only useable from within
	 * {@link CommandHandler#commandExecuted(Player, String[])}
	 */
	public void showAllHelp() {
		if (player == null) { throw new IllegalStateException("Must be used from within CommandHandler.commandExecuted!"); }
		showAllHelp(player);
	}

	/**
	 * Add a child CommandHandler. You can't add a parent as a child.
	 * 
	 * @param handler
	 *            the CommandHandler to add
	 */
	public void addChild(CommandHandler handler) {
		// Attempt to prevent CommandHandlers with parents that are their
		// children
		if (handler.getChildren().containsKey(this)) { return; }
		children.put(handler.getName(), handler);
		handler.setParent(this);
	}

	/**
	 * Get the permission for this command
	 * 
	 * @return The permission
	 */
	public abstract String getPermission();

	/**
	 * Get the description for this command
	 * 
	 * @return The description
	 */
	public abstract String getDescription();

	/**
	 * Get the usage for this command. In general it's best to use it like this:
	 * <br>
	 * <br>
	 * <code>
	 * public abstract String getUsage() {<br>
	 * return "/" + getCommandTree() + " <arg1> [arg2]"
	 * }
	 * </code>
	 * 
	 * @return The usage
	 */
	public abstract String getUsage();

	/**
	 * Returns the command tree all the way to the root command. This will error
	 * if it gets caught in an infinite loop, but will not crash.
	 * 
	 * @return The command path
	 */
	public String getCommandTree() {
		String output = getName();
		CommandHandler next = this;
		try {
			int i = 0;
			while ((next = next.getParent()) != null) {
				i++;
				if (i >= 100) { throw new StackOverflowError("Caught in infinite loop while trying to get command tree!"); }
				output = next.getName() + " " + output;
			}
		} catch (StackOverflowError e) {
			e.printStackTrace();
			return "ERR";
		}
		return output;
	}

	/**
	 * Get the name of this CommandHandler (also used for matching sub-commands)
	 * 
	 * @return The name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the child CommandHandlers
	 * 
	 * @return The children
	 */
	public HashMap<String, CommandHandler> getChildren() {
		return children;
	}

	protected void setParent(CommandHandler parent) {
		this.parent = parent;
	}

	/**
	 * Get the parent command handler
	 * 
	 * @return The parent command handler or {@code null} if this is a root
	 *         command
	 */
	public CommandHandler getParent() {
		return parent;
	}

	/**
	 * Alias of {@link Msg#send(Player, String, String...)}. Utility alias (only
	 * useable from within
	 * {@link CommandHandler#commandExecuted(Player, String[])}
	 * 
	 * @param p
	 *            the player
	 * @param key
	 *            the message key
	 * @param strings
	 *            the strings to replace with
	 */

	protected void msg(String key, Object... strings) {
		if (player == null) { throw new IllegalStateException("Must be used from within CommandHandler.commandExecuted!"); }
		Msg.send(player, key, strings);
	}

}
