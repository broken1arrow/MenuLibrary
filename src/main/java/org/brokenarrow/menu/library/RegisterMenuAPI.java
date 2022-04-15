package org.brokenarrow.menu.library;

import org.brokenarrow.menu.library.cache.MenuCache;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.UUID;

import static org.brokenarrow.menu.library.utility.Metadata.*;

public class RegisterMenuAPI {

	private static Plugin PLUGIN;

	private RegisterMenuAPI() {
		throw new UnsupportedOperationException("You need specify your main class");
	}

	public RegisterMenuAPI(Plugin plugin) {
		PLUGIN = plugin;
		registerMenuEvent();
	}

	public static Plugin getPLUGIN() {
		return PLUGIN;
	}

	private void registerMenuEvent() {
		Bukkit.getPluginManager().registerEvents(new MenuHolderListener(), PLUGIN);
	}

	private static class MenuHolderListener implements Listener {

		private final MenuCache menuCache = MenuCache.getInstance();
		private final Map<UUID, SwapData> cacheData = new HashMap<>();

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
				MenuButton menuButton = getClickedButton(createMenus, clickedItem, clickedPos, clickedSlot);
				if (menuButton != null) {
					event.setCancelled(true);
					Object objectData = createMenus.getObjectFromList(clickedPos) != null && !createMenus.getObjectFromList(clickedPos).equals("") ? createMenus.getObjectFromList(clickedPos) : clickedItem;
					menuButton.onClickInsideMenu(player, createMenus.getMenu(), event.getClick(), clickedItem, objectData);

					if (event.getClick() == ClickType.SWAP_OFFHAND) {
						SwapData data = cacheData.get(player.getUniqueId());
						ItemStack item = null;
						if (data != null) {
							item = data.getItemInOfBeforeOpenMenuHand();
						}
						cacheData.put(player.getUniqueId(), new SwapData(true, item));
					}
				}
			}
		}

		@EventHandler(priority = EventPriority.LOW)
		public void onMenuOpen(InventoryOpenEvent event) {
			final Player player = (Player) event.getPlayer();

			CreateMenus createMenus = getMenuHolder(player);
			if (createMenus == null) return;

			this.cacheData.put(player.getUniqueId(), new SwapData(false, player.getInventory().getItemInOffHand()));

		}

		@EventHandler(priority = EventPriority.LOW)
		public void onMenuClose(InventoryCloseEvent event) {
			final Player player = (Player) event.getPlayer();

			CreateMenus createMenus = getMenuHolder(player);
			if (createMenus == null) return;

			SwapData data = cacheData.get(player.getUniqueId());
			if (data != null && data.isPlayerUseSwapoffhand())
				if (data.getItemInOfBeforeOpenMenuHand() != null && data.getItemInOfBeforeOpenMenuHand().getType() != Material.AIR)
					player.getInventory().setItemInOffHand(data.getItemInOfBeforeOpenMenuHand());
				else
					player.getInventory().setItemInOffHand(null);
			cacheData.remove(player.getUniqueId());

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
						if (createMenus.getFillSpace().contains(clickedSlot))
							return;
						else
							event.setCancelled(true);
					} else if (!createMenus.isSlotsYouCanAddItems()) {
						event.setCancelled(true);
					}
					if (getClickedButton(createMenus, cursor, clickedPos, clickedSlot) == null)
						event.setCancelled(true);
				}
			}
		}


		public MenuButton getClickedButton(CreateMenus createMenus, ItemStack item, int clickedPos, int clickedSlot) {
			if (item != null)
				for (ListIterator<MenuButton> menuButtons = createMenus.getButtons().listIterator(); menuButtons.hasNext(); ) {
					MenuButton menuButton = menuButtons.next();
					Object objectData = createMenus.getObjectFromList(clickedPos) != null && !createMenus.getObjectFromList(clickedPos).equals("") ? createMenus.getObjectFromList(clickedPos) : item;
					if (createMenus.getAddedButtonsCache().containsKey(createMenus.getPageNumber())) {
						ItemStack itemStack;
						if (!createMenus.getFillSpace().contains(clickedSlot)) {
							itemStack = menuButton.getItem();
						} else {
							itemStack = menuButton.getItem(objectData);
							if (itemStack == null) {
								itemStack = menuButton.getItem();
							}
						}

						if (isItemSimilar(itemStack, item) && isItemSimilar(createMenus.getAddedButtons(createMenus.getPageNumber(), clickedPos).getItemStack(), item)) {
							return menuButton;
						}
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

			if (hasPlayerMetadata(player, MenuMetadataKey.MENU_OPEN_LOCATION)) {
				object = getPlayerMetadata(player, MenuMetadataKey.MENU_OPEN_LOCATION);
				if (object instanceof Location)
					location = (Location) object;
			}

			CreateMenus createMenus;
			if (hasPlayerMetadata(player, MenuMetadataKey.MENU_OPEN)) {
				createMenus = getPlayerMenuMetadata(player, MenuMetadataKey.MENU_OPEN);
			} else {
				createMenus = menuCache.getMenuInCache(location);
			}
			return createMenus;
		}

		private static class SwapData {

			boolean playerUseSwapoffhand;
			ItemStack itemInOfBeforeOpenMenuHand;

			public SwapData(boolean playerUseSwapoffhand, ItemStack itemInOfBeforeOpenMenuHand) {
				this.playerUseSwapoffhand = playerUseSwapoffhand;
				this.itemInOfBeforeOpenMenuHand = itemInOfBeforeOpenMenuHand;
			}

			public boolean isPlayerUseSwapoffhand() {
				return playerUseSwapoffhand;
			}

			public ItemStack getItemInOfBeforeOpenMenuHand() {
				return itemInOfBeforeOpenMenuHand;
			}
		}

	}
}
