package org.brokenarrow.menu.library.cache;

import org.brokenarrow.menu.library.CreateMenus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuCache {

	private static final MenuCache instance = new MenuCache();


	private final Map<Object, CreateMenus> menusChached = new HashMap<>();

	public void setMenusChached(Object object, CreateMenus menusChached) {
		this.menusChached.put(object, menusChached);

	}

	/**
	 * You can get a createMenus with {@link org.bukkit.entity.Player} or
	 * location.
	 *
	 * @param object use player name or the custom methods abow.
	 * @return a cached createMenus.
	 */

	public CreateMenus getMenuInCache(Object object) {
		if (object != null)
			return this.menusChached.get(object);
		return null;
	}

	public void removeMenuChached(Object object) {
		this.menusChached.remove(object);
	}

	public int getAmountOfViwers(Object object) {
		if (this.menusChached.get(object) != null)
			return this.menusChached.get(object).getAmountOfViwers();
		return -1;
	}


	public List<?> getListOfItemInCache(Object object) {
		if (this.menusChached.get(object) != null)
			return this.menusChached.get(object).getListOfFillItems();
		return null;
	}

	public Map<Object, CreateMenus> getMenusCached() {
		return menusChached;
	}
/*
	public void setAmountOfViwers(Object object, int amount) {
		savetoCache(DataType.AMOUNT_OF_VIWERS, object, amount);

	}

	public void setListOfItemInCache(Object object, List<?> amount) {
		savetoCache(DataType.LIST_OFF_FILL_ITEMS, object, amount);

	}

	private void savetoCache(DataType dataType, Object obj, Object object) {
		menusChached.put(obj, ChachedData.of(dataType == DataType.MENUHOLDER ? (MenuHolder) object : getMenuInCache(obj),
				dataType == DataType.AMOUNT_OF_VIWERS ? (int) object : getAmountOfViwers(object),
				dataType == DataType.LIST_OFF_FILL_ITEMS ? (List<?>) object : getListOfItemInCache(object)));
	}*/

	public static MenuCache getInstance() {
		return instance;
	}

	public static class ChachedData {

		private CreateMenus createMenus;
		private int amountOfViwers;
		private List<?> listOfFillItems;

		public ChachedData() {

		}

		public ChachedData(CreateMenus inventory, int amountOfViwers, List<?> listOfFillItems) {
			this.createMenus = inventory;
			this.amountOfViwers = amountOfViwers;
			this.listOfFillItems = listOfFillItems;
		}

		public static ChachedData of(CreateMenus inventory, int amountOfViwers, List<?> listOfFillItems) {
			ChachedData chachedData = new ChachedData();

			chachedData.createMenus = inventory;
			chachedData.amountOfViwers = amountOfViwers;
			chachedData.listOfFillItems = listOfFillItems;

			return chachedData;
		}

		public List<?> getListOfFillItems() {
			return listOfFillItems;
		}

		public CreateMenus getMenuHolder() {
			return createMenus;
		}

		public int getAmountOfViwers() {
			return amountOfViwers;
		}
	}

	public enum DataType {
		MENUHOLDER,
		AMOUNT_OF_VIWERS,
		LIST_OFF_FILL_ITEMS
	}

}
