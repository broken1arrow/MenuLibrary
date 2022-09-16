package org.brokenarrow.menu.library;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public enum MenuCache {

	instance;
	private final Map<Object, CreateMenus> menusChached = new HashMap<>();

	void setMenusCached(Object object, CreateMenus menusChached) {
		this.menusChached.put(object, menusChached);

	}

	/**
	 * You can get a createMenus with location or with unique key + location.
	 *
	 * @param object use player name or the custom methods abow.
	 * @return a cached createMenus.
	 */
	@Nullable
	public CreateMenus getMenuInCache(Object object) {
		if (object == null) return null;
		return this.menusChached.get(object);
	}

	/**
	 * Remove menu from cache.
	 *
	 * @param object the key you want to remove.
	 * @return true if it could find the menu.
	 */
	public boolean removeMenuCached(Object object) {
		return this.menusChached.remove(object) != null;
	}

	public Map<Object, CreateMenus> getMenusCached() {
		return this.menusChached;
	}

	public static MenuCache getInstance() {
		return instance;
	}

}