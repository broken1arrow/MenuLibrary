package brokenarrow.menu.lib;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;

import java.util.ListIterator;

public class MenuUtiliListener {

	private final Plugin plugin;

	public MenuUtiliListener(Plugin plugin) {
		this.plugin = plugin;
	}

	private final MenuCache menuCache = MenuCache.getInstance();
	private String playermetadata;

	public void onMenuClicking(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		Location location = null;
		Object object = null;

		if (player.hasMetadata(MenuMetadataKey.MENU_OPEN_LOCATION.name())) {
			object = player.getMetadata(MenuMetadataKey.MENU_OPEN_LOCATION.name()).get(0);
			if (object instanceof Location)
				location = (Location) object;
		}

		MenuHolderTest menu;
		if (player.hasMetadata(MenuMetadataKey.MENU_OPEN.name()))
			menu = (MenuHolderTest) player.getMetadata(MenuMetadataKey.MENU_OPEN.name()).get(0).value();
		else
			menu = menuCache.getMenuInCache(GetKeyDataFromCache.checkMap(player, location, object));

		if (!event.getView().getTopInventory().equals(menu.getMenu())) return;


		if (menu != null && !menu.getButtons().isEmpty()) {
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
			if (menu.isStartLoadMenuButtons()) {
				event.setCancelled(true);
				return;
			}

			for (ListIterator<MenuButton> menuButtons = menu.getButtons().listIterator(); menuButtons.hasNext(); ) {
				MenuButton menuButton = menuButtons.next();
				Object objectData = menu.getObject() != null && !menu.getObject().equals("") ? menu.getObject() : clickedItem;

				if (menuButton.getItem() != null && menuButton.getItem().isSimilar(clickedItem)) {
					if (menu.getAddedButtons().containsKey(clickedPos) && menu.getAddedButtons().get(clickedPos).isSimilar(clickedItem)) {
						menuButton.onClickInsideMenu(player, menu.getMenu(), event.getClick(), clickedItem, objectData);
						event.setCancelled(true);
						break;
					}
				}
				if (menu.getListOfFillItems() != null) {
					if (menuButton.getItem(objectData) != null && isItemSimular(menuButton.getItem(objectData), clickedItem) && menu.getAddedButtons().containsKey(clickedPos) && menu.getAddedButtons().get(clickedPos).getType() == clickedItem.getType()) {
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
	}

	public boolean isItemSimular(ItemStack item, ItemStack clickedItem) {
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

	public void onMenuClose(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		if (player.hasMetadata(this.playermetadata))
			player.removeMetadata(this.playermetadata, plugin);
	}

	/**
	 * If you have set metadata, use this method to compere it
	 *
	 * @param playermetadata metadata key you has set on the player.
	 */

	public void setPlayermetadata(String playermetadata) {
		this.playermetadata = playermetadata;
	}

}
