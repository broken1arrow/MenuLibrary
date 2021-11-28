package brokenarrow.menu.lib;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class MenuButton {

	public abstract void onClickInsideMenu(Player player, Inventory menu, ClickType click, ItemStack clickedItem, Object object);

	public abstract ItemStack getItem();

	public ItemStack getItem(Object object) {
		return null;
	}

	public ItemStack getItem(int slot, Object object) {
		return null;
	}
}
