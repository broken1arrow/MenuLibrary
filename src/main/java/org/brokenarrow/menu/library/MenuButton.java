package org.brokenarrow.menu.library;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public abstract class MenuButton {

	private static int counter = 0;
	private final int id;

	public MenuButton() {
		this.id = counter++;
	}

	/**
	 * when you click inside the menu.
	 *
	 * @param player      player some clicked in the menu.
	 * @param menu        menu some are curently open.
	 * @param click       clicktype (right,left or shift click)
	 * @param clickedItem item some are clicked on
	 * @param object      object some are clicked on (defult is it itemstack).
	 */

	public abstract void onClickInsideMenu(Player player, Inventory menu, ClickType click, ItemStack clickedItem, Object object);

	/**
	 * get the item some are added in your menu
	 *
	 * @return itemstack
	 */

	public abstract ItemStack getItem();

	/**
	 * get the item some are added in your menu
	 *
	 * @param object is your list of fill items some get returned
	 * @return itemstack
	 */

	public ItemStack getItem(Object object) {
		return null;
	}

	/**
	 * get the item some are added in your menu
	 *
	 * @param slot   curent slot it add item too.
	 * @param object is your list of fill items some get returned
	 * @return itemstack
	 */

	public ItemStack getItem(int slot, @Nullable Object object) {
		return null;
	}

	/**
	 * Set your own time, if and when it shall update buttons. If this is set to -1
	 * It will use the global from {@link CreateMenus#getUpdateTime()}
	 * <p>
	 * You also need set this to true {@link #shouldUpdateButtons()}
	 *
	 * @return -1 or seconds between updates.
	 */
	public long setUpdateTime() {
		return -1;
	}

	/**
	 * Returns true if the buttons should be updated, when menu is open and no buttons are pushed. By default, this method
	 * returns false. If you want to update the buttons, override this method and return true in your implementation.
	 *
	 * @return true if the buttons should be updated, false otherwise.
	 */
	public boolean shouldUpdateButtons() {
		return false;
	}

	/**
	 * The unique id for this instance.
	 *
	 * @return the id for this instance.
	 */
	protected int getId() {
		return id;
	}
}
