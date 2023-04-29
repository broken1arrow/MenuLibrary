package org.brokenarrow.menu.library;

import com.google.common.base.Enums;
import org.brokenarrow.menu.library.NMS.UpdateTittleContainers;
import org.brokenarrow.menu.library.utility.Function;
import org.brokenarrow.menu.library.utility.Validate;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import static org.brokenarrow.menu.library.RegisterMenuAPI.getPLUGIN;
import static org.brokenarrow.menu.library.utility.Metadata.*;

/**
 * Methods to create menu as you want it.
 */

public class CreateMenus {

	/**
	 * Create menu instance.
	 *
	 * @param fillSlots       Witch slots you want fill with items.
	 * @param fillItems       List of items you want parse inside gui.
	 * @param shallCacheItems if it shall cache items and slots in this class, other case use {@link #getMenuButtonsCache()} to cache it own class.
	 */
	public CreateMenus(final @Nullable List<Integer> fillSlots, @Nullable final List<?> fillItems, final boolean shallCacheItems) {
		this.fillSpace = fillSlots;
		this.listOfFillItems = fillItems;
		this.shallCacheItems = shallCacheItems;
		this.allowShiftClick = true;
		this.autoClearCache = true;
		this.ignoreItemCheck = false;
		this.autoTitleCurrentPage = true;
		this.slotIndex = 0;
		this.updateTime = -1;
		this.menuOpenSound = Enums.getIfPresent(Sound.class, "BLOCK_NOTE_BLOCK_BASEDRUM").orNull() == null ? Enums.getIfPresent(Sound.class, "BLOCK_NOTE_BASEDRUM").orNull() : Enums.getIfPresent(Sound.class, "BLOCK_NOTE_BLOCK_BASEDRUM").orNull();
	}

	private final MenuCache menuCache = MenuCache.getInstance();
	private final List<MenuButton> buttons = new ArrayList<>();
	private final List<MenuButton> buttonsToUpdate = new ArrayList<>();
	private final Map<Integer, Map<Integer, MenuData>> addedButtons = new HashMap<>();
	private final Map<MenuButton, Long> timeWhenUpdatesButtons = new HashMap<>();

	protected List<Integer> fillSpace;
	private final List<?> listOfFillItems;
	protected final Plugin plugin = getPLUGIN();
	private Inventory inventory;
	protected InventoryType inventoryType;
	protected int taskid;
	protected boolean shallCacheItems;
	protected boolean slotsYouCanAddItems;
	protected boolean allowShiftClick;
	protected boolean ignoreValidCheck;
	protected boolean autoClearCache;
	protected boolean ignoreItemCheck;
	protected boolean autoTitleCurrentPage;
	protected int slotIndex;
	private int requiredPages;
	protected int inventorySize;
	protected int itemsPerPage = this.inventorySize;
	protected int pageNumber;
	protected int updateTime;
	protected Player player;
	protected Sound menuOpenSound;
	protected String title;
	protected Function<String> function;
	private String playermetadataKey;
	private String uniqueKey;
	protected Location location;

	/**
	 * Set the item you want in a slot.
	 *
	 * @param slot will return current number till will add item.
	 * @return one itemstack;
	 */
	public ItemStack getItemAt(final int slot) {
		throw new Validate.CatchExceptions("WARN not in use");
	}

	/**
	 * Set the items you want in fill slots.
	 *
	 * @param o will return object you have added as fillitems.
	 * @return one itemstack;
	 */

	public ItemStack getFillItemsAt(final Object o) {
		throw new Validate.CatchExceptions("WARN not in use");
	}

	/**
	 * Set the items you want in fill slots.
	 *
	 * @param slot will return current number till will add item.
	 * @return one itemstack;
	 */

	public ItemStack getFillItemsAt(final int slot) {
		throw new Validate.CatchExceptions("WARN not in use");
	}

	/**
	 * Register your buttons you want inside the menu.
	 *
	 * @param slot will return slot number it will add item.
	 * @return MenuButton you have set.
	 */
	public MenuButton getButtonAt(final int slot) {
		return null;
	}

	/**
	 * Register your fill buttons.
	 *
	 * @param object will return object you have added as fillitems.
	 * @return MenuButton you have set.
	 */
	public MenuButton getFillButtonAt(final Object object) {
		return null;
	}

	/**
	 * Register your fill buttons, this method will return number from 0 to
	 * amount you want inside the inventory.
	 *
	 * @param slot will return current number till will add item.
	 * @return MenuButton you have set.
	 */
	public MenuButton getFillButtonAt(final int slot) {
		return null;
	}

	/**
	 * Get the set inventory type.
	 *
	 * @return inventory type.
	 */
	@Nullable
	public InventoryType getInventoryType() {
		return inventoryType;
	}

	@Nonnull
	protected Map<MenuButton, Long> getTimeWhenUpdatesButtons() {
		return timeWhenUpdatesButtons;
	}

	@Nullable
	protected Long getTimeWhenUpdatesButton(MenuButton menuButton) {
		System.out.println("menuButton " + menuButton.getId());
		return getTimeWhenUpdatesButtons().getOrDefault(menuButton, null);
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
	 * for all buttons.
	 * <p>
	 * You also need set this to true {@link MenuButton#shouldUpdateButtons()}
	 *
	 * @return seconds between the updates, defult it will return -1 and don't update the buttons.
	 */
	public int getUpdateTime() {
		return updateTime;
	}

	/**
	 * check if it shall ignore the check
	 * if the item match or not.
	 *
	 * @return true if it shall ignore the set item inside inventory.
	 */
	public boolean isIgnoreItemCheck() {
		return ignoreItemCheck;
	}

	/**
	 * Check if the cache contains the requested page.
	 *
	 * @return true if the cache is empty.
	 */
	public boolean isAddedButtonsCacheEmpty() {
		return this.addedButtons.isEmpty();
	}

	/**
	 * Check if the cache contains the requested page.
	 *
	 * @return true if the page exist.
	 */
	public boolean containsPage(Integer pageNumber) {
		return this.addedButtons.containsKey(pageNumber);
	}

	/**
	 * Get slots and items inside the cache, on this page.
	 *
	 * @param pageNumber of the page you want to get.
	 * @return map with slots for every item are placed and items.
	 */
	public Map<Integer, MenuData> getMenuButtons(final int pageNumber) {
		return this.addedButtons.get(pageNumber);
	}

	/**
	 * Get both object and itemstack for current page and slot.
	 * If you set @link {@link #getListOfFillItems()} in the constructor super,
	 * can you get the objects from the list too.
	 *
	 * @param pageNumber with page you want to get.
	 * @param slotIndex  the slot you want to get both the object and/or the itemstack stored in cache.
	 * @return Menudata with itemstack and/or object
	 */
	public MenuData getAddedButtons(final int pageNumber, final int slotIndex) {
		final Map<Integer, MenuData> data = getMenuButtons(pageNumber);
		if (data != null)
			return data.get(slotIndex);
		return new MenuData(null, null, "");
	}

	/**
	 * Get slot this menu button is added to, if you want get fillslots
	 * will this only return first slot. Use {@link #getButtonSlots(MenuButton)}
	 * if you want to get all slots this button are connected to.
	 *
	 * @param menuButton to get slots connectet to this button.
	 * @return slot number or -1 if not find data or if cache is null.
	 */
	public int getButtonSlot(final MenuButton menuButton) {
		final Map<Integer, MenuData> data = this.getMenuButtons(this.getPageNumber());
		if (data == null) return -1;
		for (final Map.Entry<Integer, MenuData> entry : data.entrySet()) {
			if (entry.getValue().getMenuButton() == menuButton)
				return entry.getKey() - (this.getPageNumber() * this.getInventorySize());
		}
		return -1;
	}

	/**
	 * Get all slots this menu button is added to.
	 *
	 * @param menuButton to get slots conectet to this button.
	 * @return list of slot number or empty if not find data or if cache is null.
	 */
	public Set<Integer> getButtonSlots(final MenuButton menuButton) {
		final Set<Integer> slots = new HashSet<>();
		final Map<Integer, MenuData> data = this.getMenuButtons(this.getPageNumber());
		if (data == null) return slots;
		for (final Map.Entry<Integer, MenuData> entry : data.entrySet()) {
			if (entry.getValue().getMenuButton() == menuButton)
				slots.add(entry.getKey() - (this.getPageNumber() * this.getInventorySize()));
		}
		return slots;
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
	 * get player that have open the menu.
	 *
	 * @return player.
	 */
	public Player getViewer() {
		return this.player;
	}

	/**
	 * Get if several players to look inside the current inventory. If it's zero
	 * then is only one player currently looking inside the inventory.
	 *
	 * @return amount of players curently looking in the inventory.
	 */
	public int getAmountOfViewers() {
		return (int) this.getMenu().getViewers().stream().filter(entity -> entity instanceof Player).count() - 1;
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
	 * Get if this option is on or off.
	 *
	 * @return true if you want the current page added automatic.
	 */
	public boolean isAutoTitleCurrentPage() {
		return autoTitleCurrentPage;
	}


	/**
	 * If you want to cache the items in own class.
	 *
	 * @return map with slot number (can be more than one inventory in number of buttons) and itemstack.
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

	public CreateMenus getMenuholder(final Player player) {
		return getMenuholder(player, MenuMetadataKey.MENU_OPEN);
	}

	/**
	 * Get previous menuholder instance from player metadata.
	 *
	 * @return older menuholder instance.
	 */

	public CreateMenus getPreviousMenuholder(final Player player) {
		return getMenuholder(player, MenuMetadataKey.MENU_OPEN_PREVIOUS);
	}

	private CreateMenus getMenuholder(final Player player, final MenuMetadataKey metadataKey) {

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
	public Object getObjectFromList(final int clickedPos) {
		return getAddedButtons(this.getPageNumber(), clickedPos).getObject();
	}

	/**
	 * Get a array of slots some are used as fillslots.
	 *
	 * @return list of slots it will fill with items.
	 */
	@Nonnull
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


	public boolean isIgnoreValidCheck() {
		return ignoreValidCheck;
	}

	/**
	 * Get if it shall automatic clear cache or not.
	 *
	 * @return true if it shall clear menu after last viewer close gui.
	 */

	public boolean isAutoClearCache() {
		return autoClearCache;
	}

	/**
	 * When you close the menu
	 *
	 * @param event close inventory
	 * @param menu  class some are now closed.
	 */

	public void menuClose(final InventoryCloseEvent event, final CreateMenus menu) {
	}

	/**
	 * Get a slot from cache. I use pagenumber and inventory size to get right
	 * item inside the cache.
	 * <p>
	 *
	 * @param slot from 0 to 53 (depending on your inventory size).
	 * @return slot inside the inventory.
	 */
	public int getSlotFromCache(final int slot) {
		return this.getPageNumber() * this.getInventorySize() + slot;
	}

	public String getTitle() {
		String title = this.title;
		if (function != null) {
			title = function.apply();
			if (title == null)
				title = this.title;
		}
		return title;
	}

	//========================================================

	/**
	 * Do not try use methods below.
	 */

	/**
	 * Put data to menu cache.
	 *
	 * @param pageNumber  the page number to set in cache
	 * @param menuDataMap the map with slot and menu data where both button,item and code execution is set.
	 */
	protected void putAddedButtonsCache(Integer pageNumber, Map<Integer, MenuData> menuDataMap) {
		this.addedButtons.put(pageNumber, menuDataMap);
	}

	protected void putTimeWhenUpdatesButtons(MenuButton menuButton, Long time) {
		this.getTimeWhenUpdatesButtons().put(menuButton, time);
	}

	protected void changePage(final boolean nextPage) {
		int pageNumber = this.pageNumber;

		if (nextPage)
			pageNumber += 1;
		else
			pageNumber -= 1;
		if (pageNumber < 0) {
			pageNumber = this.getRequiredPages() - 1;
		} else if (pageNumber >= this.getRequiredPages()) {
			pageNumber = 0;
		}
		if (pageNumber == -1) {
			pageNumber = 0;
		}
		this.pageNumber = pageNumber;

		updateButtons();
		updateTittle();
	}

	protected void updateButtons() {
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
			this.getTimeWhenUpdatesButtons().clear();
		}
	}

	protected void updateTittle() {
		String title = getTitle();
		if (title == null || title.equals("")) {
			this.title = "Menu" + (getRequiredPages() > 1 ? " page: " : "");
			title = getTitle();
		}
		UpdateTittleContainers.update(player, title + (getRequiredPages() > 1 && this.isAutoTitleCurrentPage() ? " " + (getPageNumber() + 1) + "" : ""));
	}

	private Object toMenuCache(final Player player, final Location location) {
		Object obj = null;
		if (player != null && location != null) {
			obj = location;
		}
		if (player != null && location == null) {
			obj = player;
		}
		return obj;
	}

	private void saveMenuCache(final Location location) {
		final Object obj;
		if (this.uniqueKey == null || this.uniqueKey.isEmpty()) {
			obj = location;
		} else {
			obj = this.uniqueKey + ":" + location;
		}
		menuCache.setMenusCached(obj, this);
	}

	private CreateMenus getMenuCache(final Location location) {
		final Object obj;
		if (this.uniqueKey == null || this.uniqueKey.isEmpty()) {
			obj = location;
		} else {
			obj = this.uniqueKey + ":" + location;
		}
		return menuCache.getMenuInCache(obj);
	}

	/**
	 * Remove the cached menu. if you use location.
	 *
	 * @param location you stored in the cache.
	 */
	public void removeMenuCache(final Location location) {
		final Object obj;
		if (this.uniqueKey == null || this.uniqueKey.isEmpty()) {
			obj = location;
		} else {
			obj = this.uniqueKey + ":" + location;
		}
		menuCache.removeMenuCached(obj);
	}

	/**
	 * Set uniqueKey for the cached menu. If you want several menus on same location
	 * (so several players can interact with same menu). You need set this in the constructor call
	 * to make it work as it should.
	 *
	 * @param uniqueKey you use as part of the key in the cache.
	 */
	public void setUniqueKeyMenuCache(final String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	private boolean checkLastOpenMenu() {
		if (getPreviousMenuholder(this.player) != null) {
			if (hasPlayerMetadata(this.player, MenuMetadataKey.MENU_OPEN_PREVIOUS))
				removePlayerMenuMetadata(this.player, MenuMetadataKey.MENU_OPEN_PREVIOUS);
			return false;
		}
		return true;
	}

	protected void setLocationMetaOnPlayer(final Player player, final Location location) {
		final Object obj;
		if (this.uniqueKey == null || this.uniqueKey.isEmpty()) {
			obj = location;
		} else {
			obj = this.uniqueKey + ":" + location;
		}
		setPlayerLocationMetadata(player, MenuMetadataKey.MENU_OPEN_LOCATION, obj);
	}

	private void setPlayermetadata(final Player player, final String setPlayerMetadataKey, final Object setPlayerMetadataValue) {
		player.setMetadata(setPlayerMetadataKey, new FixedMetadataValue(plugin, setPlayerMetadataValue));
	}

	protected void setMetadataKey(final String setPlayerMetadataKey) {
		this.playermetadataKey = setPlayerMetadataKey;
	}

	protected void onMenuOpenPlaySound() {
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
	protected void onMenuClose(final InventoryCloseEvent event) {
		if (Bukkit.getScheduler().isCurrentlyRunning(this.taskid) || Bukkit.getScheduler().isQueued(this.taskid)) {
			Bukkit.getScheduler().cancelTask(this.taskid);

		}
	}

	protected Inventory loadInventory(final Player player, final boolean loadToCahe) {
		Inventory menu = null;
		if (loadToCahe) {
			CreateMenus menuCached = this.getMenuCache(this.location);

			if (menuCached == null || menuCached.getMenu() == null) {
				saveMenuCache(this.location);
				menuCached = this.getMenuCache(this.location);
			}
			if (!this.isIgnoreValidCheck()) {
				Validate.checkBoolean(!menuCached.getClass().equals(this.getClass()) && (this.uniqueKey == null || this.uniqueKey.isEmpty()), "You need set uniqueKey for this menu " + menuCached.getClass() + " or it will replace the old menu and players left can take items, set method setIgnoreValidCheck() to ignore this or set the uniqueKey");
			} else {
				saveMenuCache(this.location);
				menuCached = this.getMenuCache(this.location);
			}
			menu = menuCached.getMenu();
		} else {
			final CreateMenus previous = getMenuholder(this.player);
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

/*
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

	private void registerFieldsIn(final Class<?> clazz) {
		for (final Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			if (MenuButton.class.isAssignableFrom(field.getType())) {
				try {
					final MenuButton fielddata = (MenuButton) field.get(this);
					if (fielddata != null && fielddata.shouldUpdateButtons()) {
						this.buttonsToUpdate.add(fielddata);
					}
					this.buttons.add(fielddata);
				} catch (final IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
*/

	private double amountpages() {

		if (this.itemsPerPage > 0) {
			if (this.itemsPerPage > this.inventorySize)
				this.plugin.getLogger().log(Level.SEVERE, "Items per page are biger an Inventory size, items items per page " + this.itemsPerPage + ". Inventory size " + this.inventorySize, new Throwable().fillInStackTrace());
			if (!this.getFillSpace().isEmpty()) {
				return (double) this.getFillSpace().size() / this.itemsPerPage;
			} else if (this.getListOfFillItems() != null && !this.getListOfFillItems().isEmpty())
				return (double) this.getListOfFillItems().size() / this.itemsPerPage;
			else
				return (double) this.addedButtons.size() / this.itemsPerPage;
		}
		if (this.getListOfFillItems() != null && !this.getListOfFillItems().isEmpty()) {
			return (double) this.getListOfFillItems().size() / (this.getFillSpace().isEmpty() ? this.inventorySize - 9 : this.getFillSpace().size());
		} else return (double) this.buttons.size() / this.inventorySize;
	}

	protected Map<Integer, MenuData> addItemsToCache() {
		Map<Integer, MenuData> addedButtons = new HashMap<>();
		this.requiredPages = Math.max((int) Math.ceil(amountpages()), 1);
		for (int i = 0; i < this.requiredPages; i++) {

			final Map<Integer, MenuData> addedMenuData = cacheMenuData(i);
			if (!this.shallCacheItems) {
				this.putAddedButtonsCache(i, addedMenuData);
			} else
				addedButtons = addedMenuData;
		}
		this.slotIndex = 0;
		return addedButtons;
	}

	private Map<Integer, MenuData> cacheMenuData(final int pageNumber) {
		final Map<Integer, MenuData> addedButtons = new HashMap<>();
		for (int slot = 0; slot < this.inventorySize; slot++) {

			Object objectFromlistOfFillItems = "";
			final int slotIndexOld = this.slotIndex;
			if (this.getFillSpace() != null && this.getFillSpace().contains(slot)) {
				objectFromlistOfFillItems = getObjectFromlistOfFillItems(slotIndexOld);
				this.slotIndex++;
			}
			final MenuButton menuButton = getMenuButtonAtSlot(slot, slotIndexOld, objectFromlistOfFillItems);
			final ItemStack result = getItemAtSlot(menuButton, slot, slotIndexOld, objectFromlistOfFillItems);
			if (menuButton != null) {
				if (menuButton.shouldUpdateButtons())
					this.buttonsToUpdate.add(menuButton);
				//this.buttons.add(menuButton);
			}
			addedButtons.put(pageNumber * this.getInventorySize() + slot, new MenuData(result, menuButton, objectFromlistOfFillItems));
		}
		return addedButtons;
	}

	private MenuButton getMenuButtonAtSlot(final int slot, final int oldSlotIndex, final Object objectFromlistOfFillItems) {
		final MenuButton result;
		if (this.getFillSpace() != null && this.getFillSpace().contains(slot)) {
			if (objectFromlistOfFillItems != null && !objectFromlistOfFillItems.equals("")) {
				result = getFillButtonAt(objectFromlistOfFillItems);
			} else
				result = getFillButtonAt(oldSlotIndex);
		} else {
			result = getButtonAt(slot);
		}
		return result;
	}

	private ItemStack getItemAtSlot(final MenuButton menuButton, final int slot, final int oldSlotIndex, final Object objectFromlistOfFillItems) {
		if (menuButton == null) return null;

		ItemStack result = null;
		if (this.getFillSpace() != null && this.getFillSpace().contains(slot)) {
			if (objectFromlistOfFillItems != null && !objectFromlistOfFillItems.equals("")) {
				result = menuButton.getItem(objectFromlistOfFillItems);
				if (result == null)
					result = menuButton.getItem(oldSlotIndex, objectFromlistOfFillItems);
			} else {
				result = menuButton.getItem(oldSlotIndex, objectFromlistOfFillItems);
			}
			if (result == null)
				result = menuButton.getItem();
		} else {
			result = menuButton.getItem();
			if (result == null)
				result = menuButton.getItem(oldSlotIndex, objectFromlistOfFillItems);
		}
		return result;
	}

	private Object getObjectFromlistOfFillItems(final int slotIndex) {
		if (getListOfFillItems() != null && getListOfFillItems().size() > slotIndex)
			return getListOfFillItems().get(slotIndex);
		else return null;
	}

	protected void reddrawInventory() {
		if (this.getMenu() == null || this.inventorySize > this.getMenu().getSize())
			this.inventory = createInventory();

		final int fillSpace = !getFillSpace().isEmpty() ? getFillSpace().size() : this.getMenu().getSize();

		for (int i = getFillSpace().stream().findFirst().orElse(0); i < fillSpace; i++) {
			this.getMenu().setItem(i, new ItemStack(Material.AIR));
		}

		final Map<Integer, MenuData> entity = this.getMenuButtons(this.getPageNumber());
		if (entity != null && !entity.isEmpty())
			for (int i = 0; i < this.getMenu().getSize(); i++) {
				this.getMenu().setItem(i, entity.get(this.getPageNumber() * inventorySize + i).getItemStack());
			}
	}

	private Inventory createInventory() {
		String title = this.getTitle();

		if (this.getInventoryType() != null)
			return Bukkit.createInventory(null, this.getInventoryType(), title != null ? title : "");
		if (!(this.inventorySize == 5 || this.inventorySize % 9 == 0))
			plugin.getLogger().log(Level.WARNING, "wrong inverntory size , you has put in " + this.inventorySize + " it need to be valid number.");
		if (this.inventorySize == 5)
			return Bukkit.createInventory(null, InventoryType.HOPPER, title != null ? title : "");
		return Bukkit.createInventory(null, this.inventorySize % 9 == 0 ? this.inventorySize : 9, title != null ? title : "");
	}

	private long getupdateTime(final MenuButton menuButton) {
		if (menuButton.setUpdateTime() == -1)
			return getUpdateTime();
		return menuButton.setUpdateTime();
	}

	protected void updateButtonsInList() {
		taskid = new BukkitRunnable() {
			private int counter = 0;

			@Override
			public void run() {
				for (final MenuButton menuButton : getButtonsToUpdate()) {

					final Long timeleft = getTimeWhenUpdatesButton(menuButton);
					if (timeleft != null && timeleft == -1) continue;

					if (timeleft == null || timeleft == 0)
						putTimeWhenUpdatesButtons(menuButton, counter + getupdateTime(menuButton));
					else if (counter >= timeleft) {
						final Map<Integer, MenuData> menuDataMap = getMenuButtons(getPageNumber());
						if (menuDataMap == null) {
							cancel();
							return;
						}

						final Set<Integer> itemSlots = getItemSlotsMap(menuButton);

						if (itemSlots.isEmpty())
							putTimeWhenUpdatesButtons(menuButton, counter + getupdateTime(menuButton));
						else {
							final Iterator<Integer> slotList = itemSlots.iterator();
							while (slotList.hasNext()) {
								final Integer slot = slotList.next();

								final MenuData menuData = menuDataMap.get(getSlotFromCache(slot));

								final ItemStack menuItem = getMenuButton(menuButton, menuData, slot);
								menuDataMap.put(getSlotFromCache(slot), new MenuData(menuItem, menuData.getMenuButton(), menuData.getObject()));

								putAddedButtonsCache(getPageNumber(), menuDataMap);
								getMenu().setItem(slot, menuItem);
								slotList.remove();
							}
							putTimeWhenUpdatesButtons(menuButton, counter + getupdateTime(menuButton));
						}
					}
				}
				counter++;
			}
		}.runTaskTimer(plugin, 1L, 20L).getTaskId();

	}

	private ItemStack getMenuButton(final MenuButton menuButton, final MenuData cachedButtons, final int slot) {
		return getMenuButton(menuButton, cachedButtons, slot, menuButton.shouldUpdateButtons());
	}

	protected ItemStack getMenuButton(final MenuButton menuButton, final MenuData cachedButtons, final int slot, final boolean updateButton) {
		if (menuButton == null) return null;


		if (menuButton.getItem() != null && updateButton)
			return menuButton.getItem();
		if (menuButton.getItem(cachedButtons.getObject()) != null && updateButton)
			return menuButton.getItem(cachedButtons.getObject());
		if (menuButton.getItem(getSlotFromCache(slot), cachedButtons.getObject()) != null && updateButton)
			return menuButton.getItem(getSlotFromCache(slot), cachedButtons.getObject());

		return null;
	}

	private Set<Integer> getItemSlotsMap(final MenuButton menuButton) {
		final Set<Integer> slotList = new HashSet<>();

		for (int slot = 0; slot < this.inventorySize; slot++) {
			final MenuData addedButtons = this.getAddedButtons(this.getPageNumber(), this.getSlotFromCache(slot));
			if (addedButtons == null) continue;

			if (addedButtons.getMenuButton() == menuButton)
				slotList.add(slot);
		}
		return slotList;
	}


	protected static class MenuData {

		private final ItemStack itemStack;
		private final MenuButton menuButtonLinkedToThisItem;
		private final Object object;

		public MenuData(final ItemStack itemStack, final MenuButton menuButton, final Object object) {
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
