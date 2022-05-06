package org.brokenarrow.menu.library;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class MenuButton {

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

	public ItemStack getItem(int slot, Object object) {
		return null;
	}

	/**
	 * Set own time it shall update buttons in seconds.
	 *
	 * @return seconds.
	 */
	public long updateTime() {
		return -1;
	}

	/**
	 * Set this to true if you want to update buttons.
	 *
	 * @return true if it shall update button.
	 */
	public boolean updateButton() {
		return false;
	}

}
