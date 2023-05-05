package org.brokenarrow.menu.library.builders;

import org.brokenarrow.menu.library.MenuButton;
import org.bukkit.inventory.ItemStack;

public class ButtonData {

	private final ItemStack itemStack;
	private final MenuButton menuButtonLinkedToThisItem;
	private final int id;
	private final Object object;

	public ButtonData(final ItemStack itemStack, final MenuButton menuButton, final Object object) {
		this.itemStack = itemStack;
		this.menuButtonLinkedToThisItem = menuButton;
		this.id = menuButton != null ? menuButton.getId() : 0;
		this.object = object;
	}

	/**
	 * the itemstack you want to be displayed in the menu.
	 *
	 * @return the itemstack you added in the menu.
	 */
	public ItemStack getItemStack() {
		return itemStack;
	}

	/**
	 * The button linked to this item.
	 *
	 * @return menuButton.
	 */
	public MenuButton getMenuButton() {
		return menuButtonLinkedToThisItem;
	}

	/**
	 * Get the uniqe id for this button.
	 *
	 * @return the id or 0 if not set.
	 */

	public int getId() {
		return id;
	}

	/**
	 * get the data linked to this item.
	 *
	 * @return object data you want this item contains.
	 */
	public Object getObject() {
		return object;
	}
}

