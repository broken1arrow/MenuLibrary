package brokenarrow.menu.lib;

import brokenarrow.menu.lib.cache.MenuCache;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ListIterator;

public class MenuHolderListener implements Listener {


	private final MenuCache menuCache = MenuCache.getInstance();

	@EventHandler(priority = EventPriority.LOW)
	public void onMenuClicking(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();

		if (event.getClickedInventory() != null && event.getClickedInventory().getType() == InventoryType.PLAYER)
			return;

		MenuHolder menu = getMenuHolder(player);

		if (menu == null) return;
		System.out.println("menu are not null " + menu.getMenu() + " " + event.getView().getTopInventory());
		if (!event.getView().getTopInventory().equals(menu.getMenu())) return;

		if (!menu.getButtons().isEmpty()) {
			ItemStack clickedItem = event.getCurrentItem();
			int clickedSlot = event.getSlot();
			int clickedPos = menu.getPageNumber() * menu.getMenu().getSize() + clickedSlot;
			if (clickedItem == null || clickedItem.getType() == Material.AIR) return;
			if (menu.isSlotsYouCanAddItems()) {
				if (menu.getFillSpace().contains(clickedPos))
					return;
				else
					event.setCancelled(true);
			}


			for (ListIterator<MenuButton> menuButtons = menu.getButtons().listIterator(); menuButtons.hasNext(); ) {
				MenuButton menuButton = menuButtons.next();
				Object objectData = menu.getObject() != null && !menu.getObject().equals("") ? menu.getObject() : clickedItem;

				if (menuButton.getItem() != null && menuButton.getItem().isSimilar(clickedItem)) {
					if (menu.getAddedButtons().containsKey(menu.getPageNumber()) && menu.getAddedButtons().get(menu.getPageNumber()).get(clickedPos) != null && menu.getAddedButtons().get(menu.getPageNumber()).get(clickedPos).isSimilar(clickedItem)) {
						menuButton.onClickInsideMenu(player, menu.getMenu(), event.getClick(), clickedItem, objectData);
						event.setCancelled(true);
						break;
					}
				} else if (menuButton.getItem(objectData) != null && isItemSimilar(menuButton.getItem(objectData), clickedItem) && menu.getAddedButtons().containsKey(menu.getPageNumber()) && isItemSimilar(menu.getAddedButtons().get(menu.getPageNumber()).get(clickedPos), clickedItem)) {
					menuButton.onClickInsideMenu(player, menu.getMenu(), event.getClick(), clickedItem, objectData);
					event.setCancelled(true);
					break;
				}
			}

		}
		if (!menu.isSlotsYouCanAddItems()) {
			event.setCancelled(true);
		}
	}


	public boolean isItemSimilar(ItemStack item, ItemStack clickedItem) {
		if (item != null && clickedItem != null)
			if (clickedItem.getType() == Material.PLAYER_HEAD && clickedItem.getType() == item.getType()) {
				SkullMeta skullMetaClicked = (SkullMeta) clickedItem.getItemMeta();
				SkullMeta skullMetaItem = (SkullMeta) item.getItemMeta();
				if (item.getItemMeta().getDisplayName().equals(clickedItem.getItemMeta().getDisplayName()))
					return skullMetaClicked.getOwningPlayer() == skullMetaItem.getOwningPlayer();
				else if (item.getItemMeta().getLore() == clickedItem.getItemMeta().getLore())
					return skullMetaClicked.getOwningPlayer() == skullMetaItem.getOwningPlayer();
				else return skullMetaClicked.getOwningPlayer() == skullMetaItem.getOwningPlayer();

			} else item.isSimilar(clickedItem);

		return false;
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onMenuClose(InventoryCloseEvent event) {
		final Player player = (Player) event.getPlayer();

		MenuHolder menu = getMenuHolder(player);
		if (menu == null) return;

		if (!event.getView().getTopInventory().equals(menu.getMenu()))
			return;


		System.out.println("inventory close ");

		menu.onMenuClose(event);
		menu.menuClose(event, menu.getMenu());

	}


	private MenuHolder getMenuHolder(Player player) {

		Location location = null;
		Object object = null;
		if (player.hasMetadata(MenuMetadataKey.MENU_OPEN_LOCATION.name())) {
			object = player.getMetadata(MenuMetadataKey.MENU_OPEN_LOCATION.name()).get(0).value();
			if (object instanceof Location)
				location = (Location) object;
		}

		MenuHolder menu;
		if (player.hasMetadata(MenuMetadataKey.MENU_OPEN.name())) {
			menu = (MenuHolder) player.getMetadata(MenuMetadataKey.MENU_OPEN.name()).get(0).value();
		} else {
			menu = menuCache.getMenuInCache(location);
		}
		return menu;
	}

}
