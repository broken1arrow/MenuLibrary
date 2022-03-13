package org.brokenarrow.menu.library;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Map;
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
	@Deprecated(since = "0.20")
	public MenuHolder(Plugin plugin, int inventorySize) {
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
	@Deprecated(since = "0.20")
	public MenuHolder(Plugin plugin, List<Integer> fillSlots, List<?> fillItems) {
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
	@Deprecated(since = "0.20")
	public MenuHolder(Plugin plugin, int inventorySize, boolean shallCacheItems) {
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
	@Deprecated(since = "0.20")
	public MenuHolder(Plugin plugin, List<Integer> fillSlots, List<?> fillItems, boolean shallCacheItems) {
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

	public MenuHolder(List<?> fillItems) {
		super(fillItems);
	}

	/**
	 * Create menu instance.
	 *
	 * @param shallCacheItems if it shall cache items and slots in this class, other case use {@link CreateMenus#getMenuButtonsCache()} to cache it own class.
	 */
	public MenuHolder(boolean shallCacheItems) {
		super(shallCacheItems);
	}

	/**
	 * Create menu instance.
	 *
	 * @param fillSlots Witch slots you want fill with items.
	 * @param fillItems List of items you want parse inside gui on one or several pages.
	 */
	public MenuHolder(List<Integer> fillSlots, List<?> fillItems) {
		super(fillSlots, fillItems);
	}

	/**
	 * Create menu instance.
	 *
	 * @param fillSlots       Witch slots you want fill with items.
	 * @param fillItems       List of items you want parse inside gui.
	 * @param shallCacheItems if it shall cache items and slots in this class, other case use {@link CreateMenus#getMenuButtonsCache()} to cache it own class.
	 */
	public MenuHolder(List<Integer> fillSlots, List<?> fillItems, boolean shallCacheItems) {
		super(fillSlots, fillItems, shallCacheItems);
	}

	/**
	 * Set the item you want in a slot.
	 *
	 * @param slot will return current number till will add item.
	 * @return one itemstack;
	 */
	@Override
	public ItemStack getItemAt(int slot) {
		return super.getItemAt(slot);
	}

	/**
	 * Set the items you want in fill slots.
	 *
	 * @param o will return object you have added as fillitems.
	 * @return one itemstack;
	 */
	@Override
	public ItemStack getFillItemsAt(Object o) {
		return super.getFillItemsAt(o);
	}

	/**
	 * Set the items you want in fill slots.
	 *
	 * @param slot will return current number till will add item.
	 * @return one itemstack;
	 */
	@Override
	public ItemStack getFillItemsAt(int slot) {
		return super.getFillItemsAt(slot);
	}

	/**
	 * set invetory size
	 *
	 * @param inventorySize size of this menu
	 */
	@Override
	public void setMenuSize(int inventorySize) {
		super.setMenuSize(inventorySize);
	}

	/**
	 * set menu tittle inside your menu.
	 *
	 * @param title you want to show inside the menu.
	 */
	@Override
	public void setTitle(String title) {
		super.setTitle(title);
	}

	/**
	 * set amount of items on every page
	 *
	 * @param itemsPerPage number of items it shall be on every page.
	 */
	@Override
	public void setItemsPerPage(int itemsPerPage) {
		super.setItemsPerPage(itemsPerPage);
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
	@Override
	public void setFillSpace(List<Integer> fillSpace) {
		super.setFillSpace(fillSpace);
	}

	/**
	 * Set sound when open menu.
	 * Defult it is BLOCK_NOTE_BLOCK_BASEDRUM , if you set this
	 * to null it will not play any sound.
	 *
	 * @param sound set open sound iin menu or null to disable.
	 */
	@Override
	public void setMenuOpenSound(Sound sound) {
		super.setMenuOpenSound(sound);
	}

	/**
	 * set this to true if you whant players has option to add or remove items
	 *
	 * @param slotsYouCanAddItems true and it will give option to add and remove items on fill slots.
	 */
	@Override
	public void setSlotsYouCanAddItems(boolean slotsYouCanAddItems) {
		super.setSlotsYouCanAddItems(slotsYouCanAddItems);
	}

	/**
	 * I don´t know if it usefull in current form or need rework.
	 * <p>
	 * Set own data it shall check or it will use
	 * the clicked item. It are both use in
	 * {@link MenuButton#getItem(Object)} and {@link MenuButton#onClickInsideMenu(Player, Inventory, ClickType, ItemStack, Object)}
	 *
	 * @param object you want to compare against.
	 */
	@Override
	public void setObject(Object object) {
		super.setObject(object);
	}

	/**
	 * Set to false if you want to deny shift-click.
	 * You dont need set this to true, becuse it allow
	 * shiftclick as defult.
	 *
	 * @param allowShiftClick set to false if you want to deny shiftclick
	 */
	@Override
	public void setAllowShiftClick(boolean allowShiftClick) {
		super.setAllowShiftClick(allowShiftClick);
	}

	/**
	 * Get if this menu allow shiftclick or not. Defult will
	 * it allow shiftclick.
	 *
	 * @return true if shiftclick shall be allowd.
	 */
	@Override
	public boolean isAllowShiftClick() {
		return super.isAllowShiftClick();
	}

	/**
	 * get if it this option will give you option add or remove items.
	 *
	 * @return true will you have option add items.
	 */
	@Override
	public boolean isSlotsYouCanAddItems() {
		return super.isSlotsYouCanAddItems();
	}

	/**
	 * Get item Some are stored inside cache
	 *
	 * @return map with current amount of pages and slots every item are placed and items.
	 */
	@Override
	public Map<Integer, Map<Integer, ItemStack>> getAddedButtons() {
		return super.getAddedButtons();
	}

	/**
	 * All buttons inside the menu.
	 *
	 * @return list of buttons some currently are registed.
	 */
	@Override
	public List<MenuButton> getButtons() {
		return super.getButtons();
	}

	/**
	 * get player some have open the menu.
	 *
	 * @return player.
	 */
	@Override
	public Player getViewer() {
		return super.getViewer();
	}

	/**
	 * return amount of players look inside the current inventory.
	 *
	 * @return amount of players curently looking in the inventory.
	 */
	@Override
	public int getAmountOfViwers() {
		return super.getAmountOfViwers();
	}

	/**
	 * Get the menu
	 *
	 * @return menu some are curent created.
	 */
	@Override
	public Inventory getMenu() {
		return super.getMenu();
	}

	/**
	 * get current page.
	 *
	 * @return curent page you has open.
	 */
	@Override
	public int getPageNumber() {
		return super.getPageNumber();
	}

	/**
	 * Get amount of pages some are needed.
	 *
	 * @return 1 or amount it need to fit all items.
	 */
	@Override
	public int getRequiredPages() {
		return super.getRequiredPages();
	}

	/**
	 * If you want to cache the items in own class.
	 *
	 * @return map with slot number (can be over one inventory size) and itemstack.
	 */
	@Override
	public Map<Integer, ItemStack> getMenuButtonsCache() {
		return super.getMenuButtonsCache();
	}

	/**
	 * Get metadataKey some are set on player.
	 *
	 * @return key you has used.
	 */
	@Override
	public String getPlayermetadataKey() {
		return super.getPlayermetadataKey();
	}

	/**
	 * Get menuholder instance from player metadata.
	 *
	 * @param player some open menu.
	 * @return menuholder instance.
	 */
	@Override
	public CreateMenus getMenuholder(Player player) {
		return super.getMenuholder(player);
	}

	/**
	 * Get previous menuholder instance from player metadata.
	 *
	 * @param player some open previous menu.
	 * @return older menuholder instance.
	 */
	@Override
	public CreateMenus getPreviousMenuholder(Player player) {
		return super.getPreviousMenuholder(player);
	}

	/**
	 * Get the objekt you have set.
	 *
	 * @return objekt or null if no are set.
	 */
	@Override
	public Object getObject() {
		return super.getObject();
	}

	/**
	 * Get a array of slots some are used as fillslots.
	 *
	 * @return list of slots it will fill with items.
	 */
	@Override
	public List<Integer> getFillSpace() {
		return super.getFillSpace();
	}

	/**
	 * get list of fill items you added to menu.
	 *
	 * @return items you have added.
	 */
	@Override
	public List<?> getListOfFillItems() {
		return super.getListOfFillItems();
	}

	/**
	 * Get inventory size.
	 *
	 * @return inventory size.
	 */
	@Override
	public int getInventorySize() {
		return super.getInventorySize();
	}

	/**
	 * When you close the menu
	 *
	 * @param event close inventory
	 * @param menu  some are closed.
	 */
	@Override
	public void menuClose(InventoryCloseEvent event, Inventory menu) {
		super.menuClose(event, menu);
	}

	/**
	 * open menu and make one instance in cache.
	 * Will be clered on server restart.
	 *
	 * @param player   some open menu.
	 * @param location location you open menu.
	 */
	@Override
	public void menuOpen(Player player, Location location) {
		super.menuOpen(player, location);
	}

	/**
	 * open menu and make one instance, will be removed
	 * when you close menu.
	 *
	 * @param player some open menu.
	 */
	@Override
	public void menuOpen(Player player) {
		super.menuOpen(player);
	}

	/**
	 * get the number it currently fill
	 * items in.
	 *
	 * @return curent number it will fill with one item.
	 */
	@Override
	public int getSlotIndex() {
		return super.getSlotIndex();
	}

	/**
	 * get previous page if this menu has several pages
	 */
	@Override
	public void previousPage() {
		super.previousPage();
	}

	/**
	 * get next page if this menu has several pages
	 */
	@Override
	public void nextPage() {
		super.nextPage();
	}

	/**
	 * Update buttons inside the menu.
	 */
	@Override
	public void updateButtons() {
		super.updateButtons();
	}
}
