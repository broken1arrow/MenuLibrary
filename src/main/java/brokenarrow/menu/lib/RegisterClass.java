package brokenarrow.menu.lib;

import brokenarrow.menu.lib.cache.MenuCache;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;

import java.util.ListIterator;

public class RegisterClass {

	private static Plugin PLUGIN;

	private RegisterClass()  {
		throw new UnsupportedOperationException("You need specify your main class");
	}
	public RegisterClass(Plugin plugin) {
		PLUGIN = plugin;
		registerMenuEvent();
	}
	protected static Plugin getPLUGIN() {
		return PLUGIN;
	}

	private void registerMenuEvent(){
		Bukkit.getPluginManager().registerEvents(new  MenuHolderListener(),PLUGIN);
	}
	private static class MenuHolderListener implements Listener {

		private final MenuCache menuCache = MenuCache.getInstance();

		@EventHandler(priority = EventPriority.LOW)
		public void onMenuClicking(InventoryClickEvent event) {
			Player player = (Player) event.getWhoClicked();

			if (event.getClickedInventory() == null)
				return;
			ItemStack clickedItem = event.getCurrentItem();
			ItemStack cursor = event.getCursor();

			CreateMenus createMenus = getMenuHolder(player);
			if (createMenus == null) return;
			if (!event.getView().getTopInventory().equals(createMenus.getMenu())) return;

			if (!createMenus.getButtons().isEmpty()) {
				int clickedSlot = event.getSlot();
				int clickedPos = createMenus.getPageNumber() * createMenus.getMenu().getSize() + clickedSlot;

				if (!createMenus.isAllowShiftClick() && event.getClick().isShiftClick()) {
					event.setCancelled(true);
					return;
				}
				if (createMenus.isSlotsYouCanAddItems()) {
					if (createMenus.getFillSpace().contains(clickedPos))
						return;
					else if (event.getClickedInventory().getType() != InventoryType.PLAYER)
						event.setCancelled(true);
				} else if (!createMenus.isSlotsYouCanAddItems()) {
					if (event.getClickedInventory().getType() == InventoryType.PLAYER)
						if (event.getClick().isShiftClick()) {
							event.setCancelled(true);
						} else
							event.setCancelled(true);
					if (cursor != null && cursor.getType() != Material.AIR)
						event.setCancelled(true);
				}
				MenuButton menuButton = getClickedButton(createMenus, clickedItem, clickedPos);
				if (menuButton != null) {
					event.setCancelled(true);
					Object objectData = createMenus.getObject() != null && !createMenus.getObject().equals("") ? createMenus.getObject() : clickedItem;
					menuButton.onClickInsideMenu(player, createMenus.getMenu(), event.getClick(), clickedItem, objectData);
				}
			}
		}


		@EventHandler(priority = EventPriority.LOW)
		public void onMenuClose(InventoryCloseEvent event) {
			final Player player = (Player) event.getPlayer();

			CreateMenus createMenus = getMenuHolder(player);
			if (createMenus == null) return;

			if (!event.getView().getTopInventory().equals(createMenus.getMenu()))
				return;

			createMenus.onMenuClose(event);
			createMenus.menuClose(event, createMenus.getMenu());

		}

		@EventHandler(priority = EventPriority.LOW)
		public void onInventoryDragTop(final InventoryDragEvent event) {
			final Player player = (Player) event.getWhoClicked();
			if (event.getView().getType() == InventoryType.PLAYER) return;

			CreateMenus createMenus = getMenuHolder(player);
			if (createMenus == null) return;

			if (!createMenus.getButtons().isEmpty()) {
				final int size = event.getView().getTopInventory().getSize();

				for (int clickedSlot : event.getRawSlots()) {
					if (clickedSlot > size)
						continue;

					int clickedPos = createMenus.getPageNumber() * createMenus.getMenu().getSize() + clickedSlot;

					final ItemStack cursor = checkIfNull(event.getCursor(), event.getOldCursor());
					if (createMenus.isSlotsYouCanAddItems()) {
						if (createMenus.getFillSpace().contains(clickedPos))
							return;
						else
							event.setCancelled(true);
					} else if (!createMenus.isSlotsYouCanAddItems()) {
						event.setCancelled(true);
					}
					if (getClickedButton(createMenus, cursor, clickedPos) == null)
						event.setCancelled(true);
				}
			}
		}


		public MenuButton getClickedButton(CreateMenus createMenus, ItemStack item, int clickedPos) {
			if (item != null)
				for (ListIterator<MenuButton> menuButtons = createMenus.getButtons().listIterator(); menuButtons.hasNext(); ) {
					MenuButton menuButton = menuButtons.next();
					Object objectData = createMenus.getObject() != null && !createMenus.getObject().equals("") ? createMenus.getObject() : item;

					if (menuButton.getItem(objectData) == null && menuButton.getItem() != null && createMenus.getAddedButtons().containsKey(createMenus.getPageNumber())) {
						if (menuButton.getItem().isSimilar(item) && isItemSimilar(createMenus.getAddedButtons().get(createMenus.getPageNumber()).get(clickedPos), item)) {
							return menuButton;
						}
					} else if (menuButton.getItem(objectData) != null && createMenus.getAddedButtons().containsKey(createMenus.getPageNumber()))
						if (isItemSimilar(menuButton.getItem(objectData), item) && isItemSimilar(createMenus.getAddedButtons().get(createMenus.getPageNumber()).get(clickedPos), item)) {
							return menuButton;
						}
				}
			return null;
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
				} else {
					return item.isSimilar(clickedItem);
				}

			return false;
		}

		public ItemStack checkIfNull(ItemStack curentCursor, ItemStack oldCursor) {
			return curentCursor != null ? curentCursor : oldCursor != null ? oldCursor : new ItemStack(Material.AIR);
		}

		private CreateMenus getMenuHolder(Player player) {

			Location location = null;
			Object object;
			if (player.hasMetadata(MenuMetadataKey.MENU_OPEN_LOCATION.name())) {
				object = player.getMetadata(MenuMetadataKey.MENU_OPEN_LOCATION.name()).get(0).value();
				if (object instanceof Location)
					location = (Location) object;
			}

			CreateMenus createMenus;
			if (player.hasMetadata(MenuMetadataKey.MENU_OPEN.name())) {
				createMenus = (CreateMenus) player.getMetadata(MenuMetadataKey.MENU_OPEN.name()).get(0).value();
			} else {
				createMenus = menuCache.getMenuInCache(location);
			}
			return createMenus;
		}


	}
}
