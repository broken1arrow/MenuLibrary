package org.brokenarrow.menu.library;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public enum MenuCache {

	instance;
	private final Map<Object, CreateMenus> menusChached = new HashMap<>();
	
	public void setMenusCached(Object object, CreateMenus menusChached) {
		this.menusChached.put(object, menusChached);

	}

	/**
	 * You can get a createMenus with location.
	 *
	 * @param object use player name or the custom methods abow.
	 * @return a cached createMenus.
	 */
	@Nullable
	public CreateMenus getMenuInCache(Object object) {
		if (object == null) return null;
		return this.menusChached.get(object);
	}

	public void removeMenuCached(Object object) {
		this.menusChached.remove(object);
	}

	public Map<Object, CreateMenus> getMenusCached() {
		return menusChached;
	}

	static MenuCache getInstance() {
		return instance;
	}

}