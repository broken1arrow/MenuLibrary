package org.brokenarrow.menu.library;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * Methods to create menu as you want it.
 */

public class MenuHolder extends CreateMenus {

	/**
	 * Create menu instance.
	 *
	 * @param plugin        your main class.
	 * @param inventorySize size if menu.
	 * @deprecated plugin and inventorySize will be removed, recplaced with method with out.
	 */
	public MenuHolder(final Plugin plugin, final int inventorySize) {
		super(plugin, inventorySize);
	}

	/**
	 * Create menu instance.
	 *
	 * @param plugin    Your main class.
	 * @param fillSlots Witch slots you want fill with items.
	 * @param fillItems List of items you want parse inside gui on one or several pages.
	 * @deprecated plugin and inventorySize will be removed, recplaced with method with out.
	 */

	public MenuHolder(final Plugin plugin, final List<Integer> fillSlots, final List<?> fillItems) {
		super(plugin, fillSlots, fillItems);
	}

	/**
	 * Create menu instance.
	 *
	 * @param plugin          your main class.
	 * @param inventorySize   size if menu.
	 * @param shallCacheItems if it shall cache items and slots in this class, other case use {@link CreateMenus#getMenuButtonsCache()} to cache it own class.
	 * @deprecated plugin and inventorySize will be removed, recplaced with method with out.
	 */

	public MenuHolder(final Plugin plugin, final int inventorySize, final boolean shallCacheItems) {
		super(plugin, inventorySize, shallCacheItems);
	}

	/**
	 * Create menu instance.
	 *
	 * @param plugin          Your main class.
	 * @param fillSlots       Witch slots you want fill with items.
	 * @param fillItems       List of items you want parse inside gui.
	 * @param shallCacheItems if it shall cache items and slots in this class, other case use {@link CreateMenus#getMenuButtonsCache()} to cache it own class.
	 * @deprecated plugin and inventorySize will be removed, recplaced with method with out.
	 */

	public MenuHolder(final Plugin plugin, final List<Integer> fillSlots, final List<?> fillItems, final boolean shallCacheItems) {
		super(plugin, fillSlots, fillItems, shallCacheItems);
	}

	/**
	 * Create menu instance. With out any aguments. Recomend you set al lest inventory/menu size.
	 */
	public MenuHolder() {
		super();
	}

	/**
	 * Create menu instance. You have to set {@link #setFillSpace(List)} or it will as defult fill
	 * all slots but not 9 on the bottom.
	 *
	 * @param fillItems List of items you want parse inside gui on one or several pages.
	 */

	public MenuHolder(final List<?> fillItems) {
		super(fillItems);
	}

	/**
	 * Create menu instance.
	 *
	 * @param shallCacheItems if it shall cache items and slots in this class, other case use {@link CreateMenus#getMenuButtonsCache()} to cache it own class.
	 */
	public MenuHolder(final boolean shallCacheItems) {
		super(shallCacheItems);
	}

	/**
	 * Create menu instance.
	 *
	 * @param fillSlots Witch slots you want fill with items.
	 * @param fillItems List of items you want parse inside gui on one or several pages.
	 */
	public MenuHolder(final List<Integer> fillSlots, final List<?> fillItems) {
		super(fillSlots, fillItems);
	}

	/**
	 * Create menu instance.
	 *
	 * @param fillSlots       Witch slots you want fill with items.
	 * @param fillItems       List of items you want parse inside gui.
	 * @param shallCacheItems if it shall cache items and slots in this class, other case use {@link CreateMenus#getMenuButtonsCache()} to cache it own class.
	 */
	public MenuHolder(final List<Integer> fillSlots, final List<?> fillItems, final boolean shallCacheItems) {
		super(fillSlots, fillItems, shallCacheItems);
	}


	/**
	 * When you close the menu
	 *
	 * @param event close inventory
	 * @param menu  class some are now closed.
	 */

	@Override
	public void menuClose(final InventoryCloseEvent event, final CreateMenus menu) {
	}

	/**
	 * open menu and make one instance in cache.
	 * Will be clered on server restart.
	 *
	 * @param player   some open menu.
	 * @param location location you open menu.
	 */
	public void menuOpen(final Player player, final Location location) {
		menuOpen(player, location, true);
	}

	/**
	 * open menu and make one instance, will be removed
	 * when you close menu.
	 *
	 * @param player some open menu.
	 */
	public void menuOpen(final Player player) {
		menuOpen(player, null, false);
	}

	/**
	 * open menu and make one instance. If you set location to null, it will be removed
	 * when you close menu.
	 *
	 * @param player     some open menu.
	 * @param location   location you open menu.
	 * @param loadToCahe if it shall load menu to cache.
	 */
	public void menuOpen(final Player player, final Location location, final boolean loadToCahe) {
		this.player = player;
		this.location = location;

	    if (getMenu() != null)
			player.closeInventory();

		if (location != null)
			setLocationMetaOnPlayer(player, location);

		if (!shallCacheItems) {
			addItemsToCache();
		}
		reddrawInventory();

		final Inventory menu = loadInventory(player, loadToCahe);
		if (menu == null) return;

		player.openInventory(menu);

		Bukkit.getScheduler().runTaskLater(plugin, this::updateTittle, 1);
		onMenuOpenPlaySound();

		setMetadataKey(MenuMetadataKey.MENU_OPEN.name());

		if (!getButtonsToUpdate().isEmpty())
			updateButtonsInList();
	}

	/**
	 * set invetory size
	 *
	 * @param inventorySize size of this menu
	 */

	public void setMenuSize(final int inventorySize) {
		this.inventorySize = inventorySize;
	}

	/**
	 * set menu tittle inside your menu.
	 *
	 * @param title you want to show inside the menu.
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * Set type of inventory, defult will it use chest or hopper. If you set
	 * the type you can´t change size.
	 *
	 * @param inventoryType set type of inventory.
	 */
	public void setInventoryType(final InventoryType inventoryType) {
		this.inventoryType = inventoryType;
	}

	/**
	 * set amount of items on every page
	 *
	 * @param itemsPerPage number of items it shall be on every page.
	 */
	public void setItemsPerPage(final int itemsPerPage) {
		if (itemsPerPage <= 0)
			this.itemsPerPage = this.inventorySize;
		else
			this.itemsPerPage = itemsPerPage;
	}

	/**
	 * String value of slots you want to fill. Suport "1-5,12-18","1-5","1,8,9,20" or
	 * only one number 14. It will auto add all numbers and also between (if you use -, like 0-5) to the list.
	 *
	 * @param fillSpace the string of slots you want to use as fill slots.
	 */
	public void setFillSpace(final String fillSpace) {
		final List<Integer> slotList = new ArrayList<>();
		try {
			for (final String slot : fillSpace.split(",")) {
				if (slot.equals("")) {
					continue;
				}
				if (slot.contains("-")) {
					final int firstSlot = Integer.parseInt(slot.split("-")[0]);
					final int lastSlot = Integer.parseInt(slot.split("-")[1]);
					slotList.addAll(IntStream.rangeClosed(firstSlot, lastSlot).boxed().collect(Collectors.toList()));
				} else slotList.add(Integer.valueOf(slot));
			}
		} catch (final NumberFormatException e) {
			throw new NumberFormatException("can not parse this " + fillSpace + " as numbers.");
		}
		this.setFillSpace(slotList);
	}

	/**
	 * witch slot you want to fill with items.
	 * Recomend use {@link IntStream#rangeClosed} to automatic convert
	 * a range like first number 0 and last 26.
	 * <p>
	 * Like this IntStream.rangeClosed(0, 26).boxed().collect(Collectors.toList());
	 * for a menu some are for example size 36 will it not add items to last 9 slots.
	 *
	 * @param fillSpace set slots you want to use as fill slots.
	 */
	public void setFillSpace(final List<Integer> fillSpace) {
		this.fillSpace = fillSpace;
	}

	/**
	 * Set sound when open menu.
	 * Defult it is BLOCK_NOTE_BLOCK_BASEDRUM , if you set this
	 * to null it will not play any sound.
	 *
	 * @param sound set open sound iin menu or null to disable.
	 */

	public void setMenuOpenSound(final Sound sound) {
		this.menuOpenSound = sound;
	}

	/**
	 * set this to true if you whant players has option to add or remove items
	 *
	 * @param slotsYouCanAddItems true and it will give option to add and remove items on fill slots.
	 */

	public void setSlotsYouCanAddItems(final boolean slotsYouCanAddItems) {
		this.slotsYouCanAddItems = slotsYouCanAddItems;
	}

	/**
	 * Set to false if you want to deny shift-click.
	 * You dont need set this to true, becuse it allow
	 * shiftclick as defult.
	 *
	 * @param allowShiftClick set to false if you want to deny shiftclick
	 */

	public void setAllowShiftClick(final boolean allowShiftClick) {
		this.allowShiftClick = allowShiftClick;
	}

	/**
	 * set the page you want to open.
	 *
	 * @return true if it could set the page.
	 */
	public boolean setPage(final int page) {
		if (this.getAddedButtonsCache().containsKey(page))
			return false;

		this.pageNumber = page;
		updateButtons();
		updateTittle();
		return true;
	}

	/**
	 * get the number it currently fill
	 * items in.
	 *
	 * @return curent number it will fill with one item.
	 */
	public int getSlotIndex() {
		return this.slotIndex;
	}

	/**
	 * get previous page if this menu has several pages
	 */
	public void previousPage() {
		changePage(false);
	}

	/**
	 * get next page if this menu has several pages
	 */
	public void nextPage() {
		changePage(true);
	}


	/**
	 * Update only one button. Set this inside the {@link MenuButton#onClickInsideMenu(Player, Inventory, org.bukkit.event.inventory.ClickType, ItemStack, Object)}}
	 * method and use this to tell what button some shal be updated.
	 * <p>
	 * You has to do this "this.YourClass.updateButton(MenuButton)" to acces this method.
	 *
	 * @param menuButton the current button.
	 */
	public void updateButton(final MenuButton menuButton) {
		final Map<Integer, MenuData> menuDataMap = getMenuData(getPageNumber());
		final Set<Integer> buttonSlots = this.getButtonSlots(menuButton);
		if (!buttonSlots.isEmpty()) {
			for (final int slot : buttonSlots) {

				final MenuData menuData = menuDataMap.get(getSlotFromCache(slot));
				final ItemStack menuItem = getMenuButton(menuButton, menuData, slot, true);
				this.getMenu().setItem(slot, menuItem);
				menuDataMap.put(getSlotFromCache(slot), new MenuData(menuItem, menuButton, menuData.getObject()));
			}
		} else {
			final int buttonSlot = this.getButtonSlot(menuButton);
			final ItemStack itemStack = getMenuButton(menuButton, menuDataMap.get(getSlotFromCache(buttonSlot)), buttonSlot, true);
			this.getMenu().setItem(buttonSlot, itemStack);
			menuDataMap.put(getSlotFromCache(buttonSlot), new MenuData(itemStack, menuButton, ""));
		}
		this.getAddedButtonsCache().put(this.getPageNumber(), menuDataMap);
	}

	/**
	 * Update all buttons inside the menu.
	 */
	@Override
	public void updateButtons() {
		super.updateButtons();
	}

	/**
	 * Set if it shall ignore the set item in the slot and deny
	 * player from remove items no mater if the item match set item
	 * inside inventory and the item you set in getItem() method.
	 *
	 * @param ignoreItemCheck set to true and it will deny player from take items
	 *                        , even if the item in inventory not match item you has set.
	 */
	public void setignoreItemCheck(final boolean ignoreItemCheck) {
		this.ignoreItemCheck = ignoreItemCheck;
	}

	/**
	 * Set time it shall update the buttons.
	 *
	 * @param updateTime the seconds between updates.
	 */
	public void setUpdateTime(final int updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * Set this to false if you not want it to auto clear from
	 * the cache when last player close inventory. It defult will clear the menu so you not have
	 * to set this to true.
	 *
	 * @param autoClearCache set to false if you not want it to clear menu cache.
	 */
	public void setAutoClearCache(final boolean autoClearCache) {
		this.autoClearCache = autoClearCache;
	}

	/**
	 * If it shall not valid check when open menu with location if you have several
	 * menus on same location and not set {@link #setUniqueKeyMenuCache(String)}. The effect of
	 * override old menu is if player is left in the old menu, they can take all items.
	 *
	 * @param ignoreValidCheck set this to true if you want to ignore the consequences of override the old menu.
	 */
	public void setIgnoreValidCheck(final boolean ignoreValidCheck) {
		this.ignoreValidCheck = ignoreValidCheck;
	}
}
