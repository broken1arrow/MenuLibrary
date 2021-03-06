package org.brokenarrow.menu.library;

import org.bukkit.event.Listener;

public final class MenuHolderListener implements Listener {

/*
	private final MenuCache menuCache = MenuCache.getInstance();

	@EventHandler(priority = EventPriority.LOW)
	public void onMenuClicking(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();

		if (event.getClickedInventory() == null)
			return;
		ItemStack clickedItem = event.getCurrentItem();
		ItemStack cursor = event.getCursor();

		MenuHolder menu = getMenuHolder(player);
		if (menu == null) return;
		if (!event.getView().getTopInventory().equals(menu.getMenu())) return;

		if (!menu.getButtons().isEmpty()) {
			int clickedSlot = event.getSlot();
			int clickedPos = menu.getPageNumber() * menu.getMenu().getSize() + clickedSlot;

			if (!menu.isAllowShiftClick() && event.getClick().isShiftClick()) {
				event.setCancelled(true);
				return;
			}
			if (menu.isSlotsYouCanAddItems()) {
				if (menu.getFillSpace().contains(clickedPos))
					return;
				else if (event.getClickedInventory().getType() != InventoryType.PLAYER)
					event.setCancelled(true);
			} else if (!menu.isSlotsYouCanAddItems()) {
				if (event.getClickedInventory().getType() == InventoryType.PLAYER)
					if (event.getClick().isShiftClick()) {
						event.setCancelled(true);
					} else
						event.setCancelled(true);
				if (cursor != null && cursor.getType() != Material.AIR)
					event.setCancelled(true);
			}
			MenuButton menuButton = getClickedButton(menu, clickedItem, clickedPos);
			if (menuButton != null) {
				event.setCancelled(true);
				Object objectData = menu.getObject() != null && !menu.getObject().equals("") ? menu.getObject() : clickedItem;
				menuButton.onClickInsideMenu(player, menu.getMenu(), event.getClick(), clickedItem, objectData);
			}
		}
	}


	@EventHandler(priority = EventPriority.LOW)
	public void onMenuClose(InventoryCloseEvent event) {
		final Player player = (Player) event.getPlayer();

		MenuHolder menu = getMenuHolder(player);
		if (menu == null) return;

		if (!event.getView().getTopInventory().equals(menu.getMenu()))
			return;

		menu.onMenuClose(event);
		menu.menuClose(event, menu.getMenu());

	}

	@EventHandler(priority = EventPriority.LOW)
	public void onInventoryDragTop(final InventoryDragEvent event) {
		final Player player = (Player) event.getWhoClicked();
		if (event.getView().getType() == InventoryType.PLAYER) return;

		MenuHolder menu = getMenuHolder(player);
		if (menu == null) return;

		if (!menu.getButtons().isEmpty()) {
			final int size = event.getView().getTopInventory().getSize();

			for (int clickedSlot : event.getRawSlots()) {
				if (clickedSlot > size)
					continue;

				int clickedPos = menu.getPageNumber() * menu.getMenu().getSize() + clickedSlot;

				final ItemStack cursor = checkIfNull(event.getCursor(), event.getOldCursor());
				if (menu.isSlotsYouCanAddItems()) {
					if (menu.getFillSpace().contains(clickedPos))
						return;
					else
						event.setCancelled(true);
				} else if (!menu.isSlotsYouCanAddItems()) {
					event.setCancelled(true);
				}
				if (getClickedButton(menu, cursor, clickedPos) == null)
					event.setCancelled(true);
			}
		}
	}


	public MenuButton getClickedButton(MenuHolder menu, ItemStack item, int clickedPos) {
		if (item != null)
			for (ListIterator<MenuButton> menuButtons = menu.getButtons().listIterator(); menuButtons.hasNext(); ) {
				MenuButton menuButton = menuButtons.next();
				Object objectData = menu.getObject() != null && !menu.getObject().equals("") ? menu.getObject() : item;

				if (menuButton.getItem(objectData) == null && menuButton.getItem() != null && menu.getAddedButtons().containsKey(menu.getPageNumber())) {
					if (menuButton.getItem().isSimilar(item) && isItemSimilar(menu.getAddedButtons().get(menu.getPageNumber()).get(clickedPos), item)) {
						return menuButton;
					}
				} else if (menuButton.getItem(objectData) != null && menu.getAddedButtons().containsKey(menu.getPageNumber()))
					if (isItemSimilar(menuButton.getItem(objectData), item) && isItemSimilar(menu.getAddedButtons().get(menu.getPageNumber()).get(clickedPos), item)) {
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

	private MenuHolder getMenuHolder(Player player) {

		Location location = null;
		Object object;
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

*/

}
