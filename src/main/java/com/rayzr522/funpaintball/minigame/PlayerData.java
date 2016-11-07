/**
 * 
 */

package com.rayzr522.funpaintball.minigame;

import java.util.Collection;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

/**
 * 
 * A class for storing and restoring a player's data
 * 
 * @author Rayzr
 *
 */
public class PlayerData {

    private GameMode                 gamemode;
    private double                   health;
    private int                      food;
    private float                    xp;
    private ItemStack[]              items;
    private ItemStack[]              armor;
    private ItemStack[]              extra;
    private Collection<PotionEffect> effects;

    /**
     * Stores all the data of the player
     * 
     * @param player
     *            the player to store the data of
     */
    public PlayerData(Player player) {

        this.gamemode = player.getGameMode();
        this.health = player.getHealth();
        this.food = player.getFoodLevel();
        this.xp = player.getExp();
        this.items = player.getInventory().getStorageContents();
        this.armor = player.getInventory().getArmorContents();
        this.extra = player.getInventory().getExtraContents();
        this.effects = player.getActivePotionEffects();

    }

    /**
     * @return the gamemode
     */
    public GameMode getGamemode() {
        return gamemode;
    }

    /**
     * @param gamemode
     *            the gamemode to set
     */
    public void setGamemode(GameMode gamemode) {
        this.gamemode = gamemode;
    }

    /**
     * @return the health
     */
    public double getHealth() {
        return health;
    }

    /**
     * @param health
     *            the health to set
     */
    public void setHealth(double health) {
        this.health = health;
    }

    /**
     * @return the food
     */
    public int getFood() {
        return food;
    }

    /**
     * @param food
     *            the food to set
     */
    public void setFood(int food) {
        this.food = food;
    }

    /**
     * @return the xp
     */
    public float getXp() {
        return xp;
    }

    /**
     * @param xp
     *            the xp to set
     */
    public void setXp(float xp) {
        this.xp = xp;
    }

    /**
     * @return the items
     */
    public ItemStack[] getItems() {
        return items;
    }

    /**
     * @param items
     *            the items to set
     */
    public void setItems(ItemStack[] items) {
        this.items = items;
    }

    /**
     * @return the armor
     */
    public ItemStack[] getArmor() {
        return armor;
    }

    /**
     * @param armor
     *            the armor to set
     */
    public void setArmor(ItemStack[] armor) {
        this.armor = armor;
    }

    /**
     * @return the extra
     */
    public ItemStack[] getExtra() {
        return extra;
    }

    /**
     * @param extra
     *            the extra to set
     */
    public void setExtra(ItemStack[] extra) {
        this.extra = extra;
    }

    /**
     * @return the effects
     */
    public Collection<PotionEffect> getEffects() {
        return effects;
    }

    /**
     * @param effects
     *            the effects to set
     */
    public void setEffects(Collection<PotionEffect> effects) {
        this.effects = effects;
    }

    /**
     * Restores all data to the player
     * 
     * @param player
     *            the player to restore the data to
     */
    public void restore(Player player) {

        player.setGameMode(this.gamemode);
        player.setHealth(this.health);
        player.setFoodLevel(this.food);
        player.setExp(this.xp);
        player.getInventory().setStorageContents(this.items);
        player.getInventory().setArmorContents(this.armor);
        player.getInventory().setExtraContents(this.extra);
        player.getActivePotionEffects().clear();
        player.addPotionEffects(this.effects);

    }

}
