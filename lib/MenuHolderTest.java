package brokenarrow.menu.lib;

import brokenarrow.menu.lib.NMS.UpdateTittleContainers;
import com.google.common.base.Enums;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public abstract class MenuHolderTest {


	/**
	 * Create menu instance.
	 *
	 * @param plugin        your main class.
	 * @param inventorySize size if menu.
	 */

	public MenuHolderTest(Plugin plugin, int inventorySize) {
		this.inventorySize = inventorySize;
		this.plugin = plugin;
	}

	/**
	 * Create menu instance.
	 *
	 * @param plugin          your main class.
	 * @param inventorySize   size if menu.
	 * @param shallCacheItems if it shall cache items and slots in this class, other case use {@link #getMenuButtonsOwnCache()} ot cache it own class.
	 */
	public MenuHolderTest(Plugin plugin, int inventorySize, boolean shallCacheItems) {

		this.inventorySize = inventorySize;
		this.itemsPerPage = inventorySize;
		this.plugin = plugin;
		this.shallCacheItems = shallCacheItems;
	}

	private final MenuCache menuCache = MenuCache.getInstance();
	private final List<MenuButton> buttons = new ArrayList<>();
	private final Map<Integer, ItemStack> addedButtons = new HashMap<>();
	private final Plugin plugin;
	private Inventory[] inventories;
	private boolean shallCacheItems;
	private boolean slotsYouCanAddItems;
	private boolean startLoadMenuButtons;
	private boolean hasLoadMenuButtons;
	private int slotIndex = 0;
	private int requiredPages;
	private int itemsPerPage = this.inventorySize;
	private int inventorySize;
	private int pageNumber;
	private List<Integer> fillSpace;
	private List<?> listOfFillItems;
	private Player player;
	private Sound menuOpenSound = Sound.BLOCK_NOTE_BLOCK_BASEDRUM;
	private String title;
	private String playermetadataKey;
	private Object object;
	private Location location;

	public ItemStack getItemAt(int slot) {
		return null;
	}

	public ItemStack getFillItemsAt(int slot) {
		return null;
	}

	public ItemStack getFillItemsAt() {
		return null;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void setItemsPerPage(int itemsPerPage) {
		if (itemsPerPage <= 0)
			this.itemsPerPage = this.inventorySize;
		this.itemsPerPage = itemsPerPage;
	}

	public void setFillSpace(List<Integer> fillSpace) {
		this.fillSpace = fillSpace;
	}

	public void setMenuOpenSound(Sound sound) {
		this.menuOpenSound = sound;
	}

	public <T> void setListOfFillItems(List<T> listOfFillItems) {
		this.listOfFillItems = listOfFillItems;
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
	 * Set own data it shall check or it will use
	 * the clicked item. It are both use in
	 * {@link MenuButton#getItem(Object)} and {@link MenuButton#onClickInsideMenu(Player, Inventory, ClickType, ItemStack, Object)}
	 *
	 * @param object you want to compare against.
	 */

	public void setObject(Object object) {
		this.object = object;
	}

	/**
	 * get if it this option will give you option add or remove items.
	 *
	 * @return true will you have option add items.
	 */

	public boolean isSlotsYouCanAddItems() {
		return slotsYouCanAddItems;
	}

	public Map<Integer, ItemStack> getAddedButtons() {
		return addedButtons;
	}

	public List<MenuButton> getButtons() {
		return buttons;
	}

	public Player getViewer() {
		return this.player;
	}

	public Inventory getMenu() {
		if (this.inventories != null)
			return this.inventories[this.pageNumber];
		return null;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public int getRequiredPages() {
		return requiredPages;
	}

	public Map<Integer, ItemStack> getMenuButtonsOwnCache() {
		return generateInventories();
	}

	/**
	 * Get metadataKey you has set on player.
	 *
	 * @return key you has used.
	 */

	public String getPlayermetadataKey() {
		return playermetadataKey;
	}

	/**
	 * Get the objekt you have set.
	 *
	 * @return objekt or null if no are set.
	 */

	public Object getObject() {
		return object;
	}

	public List<Integer> getFillSpace() {
		return fillSpace;
	}

	public Inventory[] getInventories() {
		return inventories;
	}

	public List<?> getListOfFillItems() {
		return listOfFillItems;
	}

	public void onMenuClose(InventoryCloseEvent event, Class<?> metadata) {
		if (metadata != null)
			this.player.removeMetadata(metadata.toString(), plugin);
	}

	public void onMenuOpen(Location location) {
		onMenuOpen(null, location, true);
	}

	public void onMenuOpen(Player player) {
		onMenuOpen(player, null, false);
	}

	private void onMenuOpen(Player player, Location location, boolean loadToCahe) {
		Inventory menu = null;
		this.player = player;
		this.location = location;
		if (!hasLoadMenuButtons) {
			registerFields();
			if (!shallCacheItems) {
				generateInventories();
			}
			hasLoadMenuButtons = true;
		}
		menu = loadInventory(player, loadToCahe);

		if (menu == null) return;

		player.openInventory(menu);

		if (this.title != null && !this.title.equals(""))
			UpdateTittleContainers.update(player, this.title, Material.CHEST, menu.getSize());
		//PlayerUtil.updateInventoryTitle(player, GuiTempletsYaml.getGuiTitle("Settings_Menu"));
		onMenuOpenPlaySound();
		setPlayerLocationmetadata(player, location);
		setMetadataKey(MenuMetadataKey.MENU_OPEN.name());
	}

	public boolean isStartLoadMenuButtons() {
		return startLoadMenuButtons;
	}

	public int getSlotIndex() {
		return slotIndex;
	}

	/**
	 * get previous page if this menu has several pages
	 */
	public void previousPage() {
		int pageNumber = this.pageNumber;

		pageNumber -= 1;
		if (pageNumber < 0) {
			pageNumber = inventories.length - 1;
		} else if (pageNumber >= inventories.length) {
			pageNumber = 0;
		}
		if (pageNumber == -1) {
			pageNumber = 0;
		}
		this.pageNumber = pageNumber;
		player.openInventory(getInventory());
	}

	/**
	 * get next page if this menu has several pages
	 */
	public void nextPage() {
		int pageNumber = this.pageNumber;

		pageNumber += 1;
		if (pageNumber < 0) {
			pageNumber = inventories.length - 1;
		} else if (pageNumber >= inventories.length) {
			pageNumber = 0;
		}
		if (pageNumber == -1) {
			pageNumber = 0;
		}
		this.pageNumber = pageNumber;
		player.openInventory(getInventory());
	}

	public void updateButtons() {
		registerFields();
		generateInventories();
		//saveMenuCache(this.player, this.location, this.playermetadataKey);
		player.openInventory(getInventory());
	}

	private Inventory loadInventory(Player player, boolean loadToCahe) {
		Inventory menu;
		if (loadToCahe) {
			if (menuCache.getMenuInCache(player) == null || menuCache.getMenuInCache(player).getMenu() == null) {
				saveMenuCache(player, location);
			}
			menu = menuCache.getMenuInCache(player).getMenu();
		} else {
			if (player.hasMetadata(MenuMetadataKey.MENU_OPEN.name())) {
				menu = ((MenuHolderTest) player.getMetadata(MenuMetadataKey.MENU_OPEN.name()).get(0).value()).getMenu();
				player.removeMetadata(MenuMetadataKey.MENU_OPEN.name(), plugin);
			} else {
				player.setMetadata(MenuMetadataKey.MENU_OPEN.name(), new FixedMetadataValue(plugin, this));
				menu = getMenu();
			}
		}
		return menu;
	}

	private Inventory getInventory() {
		if (menuCache.getMenuInCache(this.player) != null && menuCache.getMenuInCache(this.player).getMenu() != null)
			return menuCache.getMenuInCache(this.player).getMenu();
		else
			return getMenu();
	}

	private Object toMenuCache(Player player, Location location) {
		Object obj = null;

		if (location != null) {
			obj = location;
		}
		if (player != null) {
			obj = player;
		}
		return obj;
	}

	private void saveMenuCache(Player player, Location location) {
		menuCache.setMenusChached(toMenuCache(player, location), this);
	}

	private void setPlayerLocationmetadata(Player player, Location location) {
		if (location != null)
			player.setMetadata(MenuMetadataKey.MENU_OPEN_LOCATION.name(), new FixedMetadataValue(plugin, location));
	}

	private void setPlayerLocationmetadata(Player player, String setPlayerMetadataKey, String setPlayerMetadataValue) {
		player.setMetadata(setPlayerMetadataKey, new FixedMetadataValue(plugin, setPlayerMetadataValue));
	}

	private void setMetadataKey(String setPlayerMetadataKey) {
		this.playermetadataKey = setPlayerMetadataKey;
	}

	private void onMenuOpenPlaySound() {
		if (Enums.getIfPresent(Sound.class, menuOpenSound.name()).orNull() != null)
			this.player.playSound(player.getLocation(), menuOpenSound, 1, 1);
	}

	private void registerFields() {
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
					this.buttons.add(fielddata);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private double amountpages() {

		if (this.itemsPerPage > 0) {
			if (this.itemsPerPage >= this.inventorySize)
				this.plugin.getLogger().log(Level.SEVERE, "Items per page are biger an Inventory size", new Throwable().fillInStackTrace());
			if (this.fillSpace != null && !this.fillSpace.isEmpty()) {
				return (double) this.fillSpace.size() / this.itemsPerPage;
			} else if (this.listOfFillItems != null && !this.listOfFillItems.isEmpty())
				return (double) this.listOfFillItems.size() / this.itemsPerPage;

		}
		if (this.listOfFillItems != null && !this.listOfFillItems.isEmpty()) {
			return (double) this.listOfFillItems.size() / this.fillSpace.size();
		} else {
			return (double) this.buttons.size() / this.inventorySize;
		}
	}

	private Map<Integer, ItemStack> generateInventories() {

		Map<Integer, ItemStack> addedButtons = new HashMap<>();
		this.requiredPages = Math.max((int) Math.ceil(amountpages()), 1);
		//We need more pages if statically positioned object you add are placed at a higher page index an one page can hold.
		if (this.inventories == null || (this.inventories.length < this.requiredPages)) {
			this.inventories = new Inventory[this.requiredPages];
		}
		for (int i = 0; i < this.requiredPages; i++) {
			if (this.inventories[i] == null) {
				this.inventories[i] = createInventory();
			}
			for (int slot = 0; slot < this.inventories[i].getSize(); slot++) {
				//for (int itemPos : this.fillSpace) {
				ItemStack result = null;
				if (fillSpace != null && fillSpace.contains(slot)) {
					result = getFillItemsAt(this.slotIndex);
					this.slotIndex++;
				} else {
					result = getItemAt(slot);
				}
				//System.out.println("time in loop before add items" + (System.currentTimeMillis() - innloop));
				if (result == null) continue;
				this.inventories[i].setItem(slot, result);
				if (!this.shallCacheItems)
					this.addedButtons.put(i * inventories[i].getSize() + slot, result);
				else
					addedButtons.put(i * inventories[i].getSize() + slot, result);
				//System.out.println("time inn loop" + (System.currentTimeMillis() - innloop));
			}
		}
		this.slotIndex = 0;
		if (pageNumber >= this.inventories.length) {
			pageNumber = this.inventories.length - 1;
		}
		return addedButtons;
	}

	private Inventory createInventory() {
		if (!(this.inventorySize == 5 || this.inventorySize % 9 == 0))
			System.out.println("wrong inverntory size , you has put in " + this.inventorySize + "it need to be valid number.");
		if (this.inventorySize == 5)
			return Bukkit.createInventory(null, InventoryType.HOPPER, this.title);

		return Bukkit.createInventory(null, this.inventorySize, this.title != null ? this.title : "");
	}


}
