package org.brokenarrow.menu.library;

import com.google.common.base.Enums;
import org.brokenarrow.menu.library.NMS.UpdateTittleContainers;
import org.brokenarrow.menu.library.cache.MenuCache;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.IntStream;

import static org.brokenarrow.menu.library.RegisterMenuAPI.getPLUGIN;
import static org.brokenarrow.menu.library.utility.Metadata.*;

/**
 * Methods to create menu as you want it.
 */

public class CreateMenus {


	/**
	 * Create menu instance.
	 *
	 * @param plugin        your main class.
	 * @param inventorySize size if menu.
	 * @deprecated plugin and inventorySize will be removed, recplaced with method with out.
	 */

	public CreateMenus(Plugin plugin, int inventorySize) {
		this.inventorySize = inventorySize;
	}

	/**
	 * Create menu instance.
	 *
	 * @param plugin    Your main class.
	 * @param fillSlots Witch slots you want fill with items.
	 * @param fillItems List of items you want parse inside gui on one or several pages.
	 * @deprecated plugin and inventorySize will be removed, recplaced with method with out.
	 */

	public CreateMenus(Plugin plugin, List<Integer> fillSlots, List<?> fillItems) {
		this.fillSpace = fillSlots;
		this.listOfFillItems = fillItems;
	}


	/**
	 * Create menu instance.
	 *
	 * @param plugin          your main class.
	 * @param inventorySize   size if menu.
	 * @param shallCacheItems if it shall cache items and slots in this class, other case use {@link #getMenuButtonsCache()} to cache it own class.
	 * @deprecated plugin and inventorySize will be removed, recplaced with method with out.
	 */

	public CreateMenus(Plugin plugin, int inventorySize, boolean shallCacheItems) {
		this.inventorySize = inventorySize;
		this.itemsPerPage = inventorySize;
		this.shallCacheItems = shallCacheItems;
	}

	/**
	 * Create menu instance.
	 *
	 * @param plugin          Your main class.
	 * @param fillSlots       Witch slots you want fill with items.
	 * @param fillItems       List of items you want parse inside gui.
	 * @param shallCacheItems if it shall cache items and slots in this class, other case use {@link #getMenuButtonsCache()} to cache it own class.
	 * @deprecated plugin and inventorySize will be removed, recplaced with method with out.
	 */

	public CreateMenus(Plugin plugin, List<Integer> fillSlots, List<?> fillItems, boolean shallCacheItems) {
		this.fillSpace = fillSlots;
		this.listOfFillItems = fillItems;
		this.shallCacheItems = shallCacheItems;
	}
//################## new contractors ###################################
//######################################################################

	/**
	 * Create menu instance.
	 */

	public CreateMenus() {
	}


	/**
	 * Create menu instance. You have to set {@link #setFillSpace(List)} or it will as defult fill
	 * all slots but not 9 on the bottom.
	 *
	 * @param fillItems List of items you want parse inside gui on one or several pages.
	 */

	public CreateMenus(List<?> fillItems) {
		this.listOfFillItems = fillItems;
	}

	/**
	 * Create menu instance.
	 *
	 * @param shallCacheItems if it shall cache items and slots in this class, other case use {@link #getMenuButtonsCache()} to  cache it own class.
	 */

	public CreateMenus(boolean shallCacheItems) {
		this.shallCacheItems = shallCacheItems;
	}

	/**
	 * Create menu instance.
	 *
	 * @param fillSlots Witch slots you want fill with items.
	 * @param fillItems List of items you want parse inside gui on one or several pages.
	 */


	public CreateMenus(List<Integer> fillSlots, List<?> fillItems) {
		this.fillSpace = fillSlots;
		this.listOfFillItems = fillItems;
	}

	/**
	 * Create menu instance.
	 *
	 * @param fillSlots       Witch slots you want fill with items.
	 * @param fillItems       List of items you want parse inside gui.
	 * @param shallCacheItems if it shall cache items and slots in this class, other case use {@link #getMenuButtonsCache()} to cache it own class.
	 */

	public CreateMenus(List<Integer> fillSlots, List<?> fillItems, boolean shallCacheItems) {
		this.fillSpace = fillSlots;
		this.listOfFillItems = fillItems;
		this.shallCacheItems = shallCacheItems;
	}

	private int taskid;
	private final MenuCache menuCache = MenuCache.getInstance();
	private final List<MenuButton> buttons = new ArrayList<>();
	private final List<MenuButton> buttonsToUpdate = new ArrayList<>();
	private final Map<Integer, Map<Integer, MenuData>> addedButtons = new HashMap<>();
	private final Map<MenuButton, Long> timeWhenUpdatesButtons = new HashMap<>();
	private final Plugin plugin = getPLUGIN();
	private Inventory inventory;
	private boolean shallCacheItems;
	private boolean slotsYouCanAddItems;
	private boolean updateButtons;
	private boolean allowShiftClick = true;
	private int slotIndex = 0;
	private int requiredPages;
	private int itemsPerPage = this.inventorySize;
	private int inventorySize;
	private int pageNumber;
	private int amountOfViwers;
	private int updateTime;
	private List<Integer> fillSpace;
	private List<?> listOfFillItems;
	private Player player;
	private Sound menuOpenSound = Enums.getIfPresent(Sound.class, "BLOCK_NOTE_BLOCK_BASEDRUM").orNull() == null ? Enums.getIfPresent(Sound.class, "BLOCK_NOTE_BASEDRUM").orNull() : Enums.getIfPresent(Sound.class, "BLOCK_NOTE_BLOCK_BASEDRUM").orNull();
	private String title;
	private String playermetadataKey;

	private Location location;

	/**
	 * Set the item you want in a slot.
	 *
	 * @param slot will return current number till will add item.
	 * @return one itemstack;
	 */
	public ItemStack getItemAt(int slot) {
		return null;
	}

	/**
	 * Set the items you want in fill slots.
	 *
	 * @param o will return object you have added as fillitems.
	 * @return one itemstack;
	 */

	public ItemStack getFillItemsAt(Object o) {
		return null;
	}

	/**
	 * Set the items you want in fill slots.
	 *
	 * @param slot will return current number till will add item.
	 * @return one itemstack;
	 */

	public ItemStack getFillItemsAt(int slot) {
		return null;
	}

	/**
	 * Register your buttons you want inside the menu.
	 *
	 * @param slot will return slot number it will add item.
	 * @return MenuButton you have set.
	 */
	public MenuButton getButtonAt(int slot) {
		return null;
	}

	/**
	 * Register your fill buttons.
	 *
	 * @param object will return object you have added as fillitems.
	 * @return MenuButton you have set.
	 */
	public MenuButton getFillButtonAt(Object object) {
		return null;
	}

	/**
	 * Register your fill buttons, this method will return number from 0 to
	 * amount you want inside the inventory.
	 *
	 * @param slot will return current number till will add item.
	 * @return MenuButton you have set.
	 */
	public MenuButton getFillButtonAt(int slot) {
		return null;
	}

	/**
	 * set invetory size
	 *
	 * @param inventorySize size of this menu
	 */

	public void setMenuSize(int inventorySize) {
		this.inventorySize = inventorySize;
	}

	/**
	 * set menu tittle inside your menu.
	 *
	 * @param title you want to show inside the menu.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * set amount of items on every page
	 *
	 * @param itemsPerPage number of items it shall be on every page.
	 */

	public void setItemsPerPage(int itemsPerPage) {
		if (itemsPerPage <= 0)
			this.itemsPerPage = this.inventorySize;
		this.itemsPerPage = itemsPerPage;
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


	public void setFillSpace(List<Integer> fillSpace) {
		this.fillSpace = fillSpace;
	}

	/**
	 * Set sound when open menu.
	 * Defult it is BLOCK_NOTE_BLOCK_BASEDRUM , if you set this
	 * to null it will not play any sound.
	 *
	 * @param sound set open sound iin menu or null to disable.
	 */

	public void setMenuOpenSound(Sound sound) {
		this.menuOpenSound = sound;
	}

	/**
	 * set this to true if you whant players has option to add or remove items
	 *
	 * @param slotsYouCanAddItems true and it will give option to add and remove items on fill slots.
	 */

	public void setSlotsYouCanAddItems(boolean slotsYouCanAddItems) {
		this.slotsYouCanAddItems = slotsYouCanAddItems;
	}

	/**
	 * Set to false if you want to deny shift-click.
	 * You dont need set this to true, becuse it allow
	 * shiftclick as defult.
	 *
	 * @param allowShiftClick set to false if you want to deny shiftclick
	 */

	public void setAllowShiftClick(boolean allowShiftClick) {
		this.allowShiftClick = allowShiftClick;
	}

	/**
	 * Get if this menu allow shiftclick or not. Defult will
	 * it allow shiftclick.
	 *
	 * @return true if shiftclick shall be allowd.
	 */

	public boolean isAllowShiftClick() {
		return allowShiftClick;
	}

	/**
	 * get if it this option will give you option add or remove items.
	 *
	 * @return true will you have option add items.
	 */

	public boolean isSlotsYouCanAddItems() {
		return slotsYouCanAddItems;
	}

	/**
	 * Get update buttons time, this is general time
	 * for all buttins.
	 *
	 * @return seconds between it shall run.
	 */
	public int getUpdateTime() {
		return updateTime;
	}

	/**
	 * Set time it shall update the buttons.
	 *
	 * @param updateTime the seconds between updates.
	 */
	public void setUpdateTime(int updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * Check if update buttons is on.
	 *
	 * @return true if you want to update all buttons.
	 */
	public boolean isUpdateButtons() {
		return updateButtons;
	}

	/**
	 * Set if you want to update buttons.
	 *
	 * @param updateButtons true if it shall update all buttons.
	 */
	public void setUpdateButtons(boolean updateButtons) {
		this.updateButtons = updateButtons;
	}

	/**
	 * Get item Some are stored inside cache
	 *
	 * @return map with current amount of pages and slots every item are placed and items.
	 */

	public Map<Integer, Map<Integer, MenuData>> getAddedButtonsCache() {
		return addedButtons;
	}

	/**
	 * Get slots and items inside the cache, on this page.
	 *
	 * @param pageNumber of the page you want to get.
	 * @return map with slots for every item are placed and items.
	 */

	public Map<Integer, MenuData> getMenuData(int pageNumber) {
		return addedButtons.get(pageNumber);
	}

	/**
	 * Get both object and itemstack for current page and slot.
	 * If you set @link {@link #listOfFillItems} in the constructor super,
	 * can you get the objects from the list too.
	 *
	 * @param pageNumber with page you want to get.
	 * @param slotIndex  the slot you want to get both the object and/or the itemstack stored in cache.
	 * @return Menudata with itemstack and/or object
	 */
	public MenuData getAddedButtons(int pageNumber, int slotIndex) {
		Map<Integer, MenuData> data = addedButtons.get(pageNumber);
		if (data != null)
			return data.get(slotIndex);
		return new MenuData(null, null, "");
	}

	/**
	 * All buttons inside the menu.
	 *
	 * @return list of buttons some currently are registed.
	 */

	public List<MenuButton> getButtons() {
		return buttons;
	}

	/**
	 * Get all buttons some shal update when menu is open.
	 *
	 * @return list of buttons some shall be updated when invetory is open.
	 */
	public List<MenuButton> getButtonsToUpdate() {
		return buttonsToUpdate;
	}

	/**
	 * get player some have open the menu.
	 *
	 * @return player.
	 */

	public Player getViewer() {
		return this.player;
	}

	/**
	 * return amount of players look inside the current inventory.
	 *
	 * @return amount of players curently looking in the inventory.
	 */
	public int getAmountOfViwers() {

		return amountOfViwers;
	}

	/**
	 * Get the menu
	 *
	 * @return menu some are curent created.
	 */
	public Inventory getMenu() {
		return inventory;
	}

	/**
	 * get current page.
	 *
	 * @return curent page you has open.
	 */

	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * Get amount of pages some are needed.
	 *
	 * @return 1 or amount it need to fit all items.
	 */

	public int getRequiredPages() {
		return requiredPages;
	}

	/**
	 * If you want to cache the items in own class.
	 *
	 * @return map with slot number (can be over one inventory size) and itemstack.
	 */

	public Map<Integer, MenuData> getMenuButtonsCache() {
		return addItemsToCache();
	}


	/**
	 * Get metadataKey some are set on player.
	 *
	 * @return key you has used.
	 */

	public String getPlayermetadataKey() {
		return playermetadataKey;
	}

	/**
	 * Get menuholder instance from player metadata.
	 *
	 * @return menuholder instance.
	 */

	public CreateMenus getMenuholder(Player player) {
		return getMenuholder(player, MenuMetadataKey.MENU_OPEN);
	}

	/**
	 * Get previous menuholder instance from player metadata.
	 *
	 * @return older menuholder instance.
	 */

	public CreateMenus getPreviousMenuholder(Player player) {
		return getMenuholder(player, MenuMetadataKey.MENU_OPEN_PREVIOUS);
	}

	private CreateMenus getMenuholder(Player player, final MenuMetadataKey metadataKey) {

		if (hasPlayerMetadata(player, metadataKey))
			return getPlayerMenuMetadata(player, metadataKey);
		return null;
	}

	/**
	 * Get the Object/entity from the @link {@link #listOfFillItems}.
	 *
	 * @param clickedPos the curent pos player clicking on, you need also add the page player currently have open and inventory size.
	 * @return Object/entity from the listOfFillItems list.
	 */
	public Object getObjectFromList(int clickedPos) {
		return getAddedButtons(this.pageNumber, clickedPos).getObject();
	}

	/**
	 * Get a array of slots some are used as fillslots.
	 *
	 * @return list of slots it will fill with items.
	 */

	public List<Integer> getFillSpace() {
		return fillSpace != null ? fillSpace : new ArrayList<>();
	}

	/**
	 * get list of fill items you added to menu.
	 *
	 * @return items you have added.
	 */

	public List<?> getListOfFillItems() {
		return listOfFillItems;
	}

	/**
	 * Get inventory size.
	 *
	 * @return inventory size.
	 */

	public int getInventorySize() {
		return inventorySize;
	}

	/**
	 * When you close the menu
	 *
	 * @param event close inventory
	 * @param menu  some are closed.
	 */

	public void menuClose(InventoryCloseEvent event, Inventory menu) {
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
	private void menuOpen(final Player player, final Location location, final boolean loadToCahe) {
		this.player = player;
		this.location = location;


		if (player.getOpenInventory().getTopInventory().getHolder() != null)
			player.closeInventory();

		if (location != null)
			setPlayermetadata(player, location);

		//registerFields();
		if (!shallCacheItems) {
			addItemsToCache();
		}
		reddrawInventory();
		//}
		final Inventory menu = loadInventory(player, loadToCahe);

		if (menu == null) return;
		player.openInventory(menu);

		if (this.title == null || this.title.equals(""))
			this.title = "Menu" + (getRequiredPages() > 1 ? " page: " : "");

		Bukkit.getScheduler().runTaskLater(plugin, this::updateTittle, 1);
		onMenuOpenPlaySound();

		setMetadataKey(MenuMetadataKey.MENU_OPEN.name());
		amountOfViwers++;

		if (!getButtonsToUpdate().isEmpty())
			updateButtonsInList();
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
		int pageNumber = this.pageNumber;

		pageNumber -= 1;
		if (pageNumber < 0) {
			pageNumber = addedButtons.size() - 1;
		} else if (pageNumber >= addedButtons.size()) {
			pageNumber = 0;
		}
		if (pageNumber == -1) {
			pageNumber = 0;
		}
		this.pageNumber = pageNumber;

		updateButtons();
		updateTittle();
	}

	/**
	 * get next page if this menu has several pages
	 */
	public void nextPage() {
		int pageNumber = this.pageNumber;

		pageNumber += 1;
		if (pageNumber < 0) {
			pageNumber = addedButtons.size() - 1;
		} else if (pageNumber >= addedButtons.size()) {
			pageNumber = 0;
		}
		if (pageNumber == -1) {
			pageNumber = 0;
		}
		this.pageNumber = pageNumber;

		updateButtons();
		updateTittle();
	}

	/**
	 * Update buttons inside the menu.
	 */

	public void updateButtons() {
		boolean cancelTask = false;
		if (this.taskid > 0)
			if (Bukkit.getScheduler().isCurrentlyRunning(this.taskid) || Bukkit.getScheduler().isQueued(this.taskid)) {
				Bukkit.getScheduler().cancelTask(this.taskid);
				cancelTask = true;
			}
		addItemsToCache();
		reddrawInventory();

		if (cancelTask) {
			updateButtonsInList();
			timeWhenUpdatesButtons.clear();

		}
	}

	/**
	 * Get a slot from cache. I use pagenumber and inventory size to get right
	 * item inside the cache.
	 * <p>
	 *
	 * @param slot from 0 to 53 (depending on your inventory size).
	 * @return slot inside the inventory.
	 */
	public int getSlotFromCache(int slot) {
		return this.getPageNumber() * this.getInventorySize() + slot;
	}
	//========================================================

	/**
	 * Do not try use methods below.
	 */

	private void updateTittle() {
		if (this.title != null && !this.title.equals(""))
			UpdateTittleContainers.update(player, this.title + (getRequiredPages() > 1 ? (getPageNumber() + 1) + "" : ""));
	}

	private Object toMenuCache(Player player, Location location) {
		Object obj = null;
		if (player != null && location != null) {
			obj = location;
		}
		if (player != null && location == null) {
			obj = player;
		}
		return obj;
	}

	private void saveMenuCache(Player player, Location location) {
		menuCache.setMenusChached(location, this);
	}

	private boolean checkLastOpenMenu() {
		if (getPreviousMenuholder(this.player) != null) {
			if (hasPlayerMetadata(this.player, MenuMetadataKey.MENU_OPEN_PREVIOUS))
				removePlayerMenuMetadata(this.player, MenuMetadataKey.MENU_OPEN_PREVIOUS);
			return false;
		}
		return true;
	}

	private void setPlayermetadata(Player player, Location location) {
		setPlayerLocationMetadata(player, MenuMetadataKey.MENU_OPEN_LOCATION, location);
	}

	private void setPlayermetadata(Player player, String setPlayerMetadataKey, Object setPlayerMetadataValue) {
		player.setMetadata(setPlayerMetadataKey, new FixedMetadataValue(plugin, setPlayerMetadataValue));
	}

	private void setMetadataKey(String setPlayerMetadataKey) {
		this.playermetadataKey = setPlayerMetadataKey;
	}

	private void onMenuOpenPlaySound() {
		if (this.menuOpenSound == null) return;

		if (Enums.getIfPresent(Sound.class, this.menuOpenSound.name()).orNull() != null)
			this.player.playSound(player.getLocation(), this.menuOpenSound, 1, 1);
	}

	/**
	 * Do not use this method, use {@link #menuClose}
	 *
	 * @param event some get fierd.
	 * @deprecated is only for internal use, do not override this.
	 */
	@Deprecated
	protected void onMenuClose(InventoryCloseEvent event) {

		if (Bukkit.getScheduler().isCurrentlyRunning(this.taskid) || Bukkit.getScheduler().isQueued(this.taskid)) {
			Bukkit.getScheduler().cancelTask(this.taskid);

		}
		if (hasPlayerMetadata(player, MenuMetadataKey.MENU_OPEN))
			removePlayerMenuMetadata(this.player, MenuMetadataKey.MENU_OPEN);

		amountOfViwers--;
		if (amountOfViwers < 0)
			amountOfViwers = 0;
	}

	private Inventory loadInventory(Player player, boolean loadToCahe) {
		Inventory menu = null;
		if (loadToCahe) {
			if (menuCache.getMenuInCache(this.location) == null || menuCache.getMenuInCache(this.location).getMenu() == null) {
				saveMenuCache(player, this.location);
			}
			menu = menuCache.getMenuInCache(this.location).getMenu();
		} else {
			CreateMenus previous = getMenuholder(this.player);
			if (previous != null && !hasPlayerMetadata(player, MenuMetadataKey.MENU_OPEN)) {
				setPlayerMenuMetadata(player, MenuMetadataKey.MENU_OPEN_PREVIOUS, this);
				setPlayerMenuMetadata(player, MenuMetadataKey.MENU_OPEN, this);
				menu = getPlayerMenuMetadata(player, MenuMetadataKey.MENU_OPEN).getMenu();
			} else {
				setPlayerMenuMetadata(player, MenuMetadataKey.MENU_OPEN_PREVIOUS, this);
				setPlayerMenuMetadata(player, MenuMetadataKey.MENU_OPEN, this);
				menu = getPlayerMenuMetadata(player, MenuMetadataKey.MENU_OPEN).getMenu();
			}
		}
		return menu;
	}

	private void registerFields() {
		this.buttons.clear();
		Class<?> iteratedClass = getClass();
		try {
			do {
				registerFieldsIn(iteratedClass);
			} while (!(iteratedClass = iteratedClass.getSuperclass()).isAssignableFrom(Object.class));

		} catch (final Exception ex) {
			ex.printStackTrace();
		}
	}

	private void registerFieldsIn(Class<?> clazz) {
		for (Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			if (MenuButton.class.isAssignableFrom(field.getType())) {
				try {
					MenuButton fielddata = (MenuButton) field.get(this);
					if (fielddata != null && (fielddata.updateButton() || this.isUpdateButtons())) {
						this.buttonsToUpdate.add(fielddata);
					}
					this.buttons.add(fielddata);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private double amountpages() {

		if (this.itemsPerPage > 0) {
			if (this.itemsPerPage > this.inventorySize)
				this.plugin.getLogger().log(Level.SEVERE, "Items per page are biger an Inventory size, items items per page " + this.itemsPerPage + ". Inventory size " + this.inventorySize, new Throwable().fillInStackTrace());
			if (this.fillSpace != null && !this.fillSpace.isEmpty()) {
				return (double) this.fillSpace.size() / this.itemsPerPage;
			} else if (this.listOfFillItems != null && !this.listOfFillItems.isEmpty())
				return (double) this.listOfFillItems.size() / this.itemsPerPage;
			else
				return (double) this.buttons.size() / this.itemsPerPage;
		}
		if (this.listOfFillItems != null && !this.listOfFillItems.isEmpty()) {
			return (double) this.listOfFillItems.size() / (this.fillSpace == null || this.fillSpace.isEmpty() ? this.inventorySize - 9 : this.fillSpace.size());
		} else return (double) this.buttons.size() / this.inventorySize;
	}

	private Map<Integer, MenuData> addItemsToCache() {
		Map<Integer, MenuData> addedButtons = new HashMap<>();
		this.requiredPages = Math.max((int) Math.ceil(amountpages()), 1);
		for (int i = 0; i < this.requiredPages; i++) {

			Map<Integer, MenuData> addedMenuData = cacheMenuData(i);
			if (!this.shallCacheItems) {
				this.addedButtons.put(i, addedMenuData);
			} else
				addedButtons = addedMenuData;
		}
		this.slotIndex = 0;
		return addedButtons;
	}

	private Map<Integer, MenuData> cacheMenuData(int pageNumber) {
		Map<Integer, MenuData> addedButtons = new HashMap<>();
		for (int slot = 0; slot < this.inventorySize; slot++) {

			Object objectFromlistOfFillItems = "";
			int slotIndexOld = this.slotIndex;
			if (this.getFillSpace() != null && this.getFillSpace().contains(slot)) {
				objectFromlistOfFillItems = getObjectFromlistOfFillItems(slotIndexOld);
				this.slotIndex++;
			}
			MenuButton menuButton = getMenuButtonAtSlot(slot, slotIndexOld, objectFromlistOfFillItems);
			ItemStack result = getItemAtSlot(menuButton, slot, slotIndexOld, objectFromlistOfFillItems);
			if (menuButton != null) {
				if (menuButton.updateButton())
					this.buttonsToUpdate.add(menuButton);
				this.buttons.add(menuButton);
			}
			addedButtons.put(pageNumber * this.getInventorySize() + slot, new MenuData(result, menuButton, objectFromlistOfFillItems));
		}
		return addedButtons;
	}

	private MenuButton getMenuButtonAtSlot(int slot, int oldSlotIndex, Object objectFromlistOfFillItems) {
		MenuButton result;
		if (this.getFillSpace() != null && this.getFillSpace().contains(slot)) {
			if (objectFromlistOfFillItems != null && !objectFromlistOfFillItems.equals(""))
				result = getFillButtonAt(objectFromlistOfFillItems);
			else
				result = getFillButtonAt(oldSlotIndex);
		} else {
			result = getButtonAt(slot);
		}
		return result;
	}

	private ItemStack getItemAtSlot(MenuButton menuButton, int slot, int oldSlotIndex, Object objectFromlistOfFillItems) {
		if (menuButton == null) return null;

		ItemStack result = null;
		if (this.getFillSpace() != null && this.getFillSpace().contains(slot)) {
			if (objectFromlistOfFillItems != null && !objectFromlistOfFillItems.equals("")) {
				result = menuButton.getItem(objectFromlistOfFillItems);
				if (result == null)
					result = menuButton.getItem(oldSlotIndex, objectFromlistOfFillItems);
			}
		} else {
			result = menuButton.getItem();
		}
		return result;
	}

	private Object getObjectFromlistOfFillItems(int slotIndex) {
		if (listOfFillItems != null && listOfFillItems.size() > slotIndex)
			return listOfFillItems.get(slotIndex);
		else return null;
	}

	private void reddrawInventory() {
		if (this.inventory == null || this.inventorySize > this.inventory.getSize())
			this.inventory = createInventory();

		int fillSpace = getFillSpace() != null ? getFillSpace().size() : this.inventory.getSize();

		for (int i = getFillSpace().stream().findFirst().orElse(0); i < fillSpace; i++) {
			this.inventory.setItem(i, new ItemStack(Material.AIR));
		}

		Map<Integer, MenuData> entity = this.addedButtons.get(pageNumber);
		if (entity != null && !entity.isEmpty())
			for (int i = 0; i < inventory.getSize(); i++) {
				inventory.setItem(i, entity.get(pageNumber * inventorySize + i).getItemStack());
			}
	}

	private Inventory createInventory() {
		if (!(this.inventorySize == 5 || this.inventorySize % 9 == 0))
			plugin.getLogger().log(Level.WARNING, "wrong inverntory size , you has put in " + this.inventorySize + "it need to be valid number.");
		if (this.inventorySize == 5)
			return Bukkit.createInventory(null, InventoryType.HOPPER, this.title != null ? this.title : "");
		return Bukkit.createInventory(null, this.inventorySize % 9 == 0 ? this.inventorySize : 9, this.title != null ? this.title : "");
	}

	private long getupdateTime(MenuButton menuButton) {
		if (menuButton.updateTime() == -1)
			return getUpdateTime();
		return menuButton.updateTime();
	}

	private void updateButtonsInList() {
		taskid = new BukkitRunnable() {
			private int counter = 0;

			@Override
			public void run() {
				for (MenuButton menuButton : getButtonsToUpdate()) {

					Long timeleft = timeWhenUpdatesButtons.get(menuButton);
					if (timeleft == null || timeleft == 0)
						timeWhenUpdatesButtons.put(menuButton, counter + getupdateTime(menuButton));
					else if (counter >= timeleft) {
						Map<Integer, MenuData> menuDataMap = getMenuData(getPageNumber());
						if (menuDataMap == null) {
							cancel();
							return;
						}

						Set<Integer> itemSlots = getItemSlotsMap(menuButton);

						if (itemSlots.isEmpty())
							timeWhenUpdatesButtons.put(menuButton, counter + getupdateTime(menuButton));
						else {
							Iterator<Integer> slotList = itemSlots.iterator();
							while (slotList.hasNext()) {
								Integer slot = slotList.next();

								MenuData menuData = menuDataMap.get(getSlotFromCache(slot));

								ItemStack menuItem = getMenuButton(menuButton, menuData, slot);
								menuDataMap.put(getSlotFromCache(slot), new MenuData(menuItem, menuData.getMenuButton(), menuData.getObject()));

								addedButtons.put(getPageNumber(), menuDataMap);
								inventory.setItem(slot, menuItem);
								slotList.remove();
							}
							timeWhenUpdatesButtons.put(menuButton, counter + getupdateTime(menuButton));
						}
					}
				}
				counter++;
			}
		}.runTaskTimer(plugin, 1L, 20L).getTaskId();

	}

	private ItemStack getMenuButton(MenuButton menuButton, MenuData cachedButtons, int slot) {
		if (menuButton == null) return null;
		boolean updateButton = menuButton.updateButton();

		if (menuButton.getItem() != null && updateButton)
			return menuButton.getItem();
		if (menuButton.getItem(cachedButtons.getObject()) != null && updateButton)
			return menuButton.getItem(cachedButtons.getObject());
		if (menuButton.getItem(getSlotFromCache(slot), cachedButtons.getObject()) != null && updateButton)
			return menuButton.getItem(getSlotFromCache(slot), cachedButtons.getObject());

		return null;
	}

	private Set<Integer> getItemSlotsMap(MenuButton menuButton) {
		Set<Integer> slotList = new HashSet<>();

		for (int slot = 0; slot < this.inventorySize; slot++) {
			MenuData addedButtons = this.getAddedButtons(this.getPageNumber(), this.getSlotFromCache(slot));
			if (addedButtons == null) continue;

			if (addedButtons.getMenuButton() == menuButton)
				slotList.add(slot);
		}
		return slotList;
	}

	protected class MenuData {

		private final ItemStack itemStack;
		private final MenuButton menuButtonLinkedToThisItem;
		private final Object object;

		public MenuData(ItemStack itemStack, MenuButton menuButton, Object object) {
			this.itemStack = itemStack;
			this.menuButtonLinkedToThisItem = menuButton;
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
		 * get the data linked to this item.
		 *
		 * @return object data you want this item contains.
		 */
		public Object getObject() {
			return object;
		}
	}
}
