package brokenarrow.menu.lib;

import java.util.HashMap;
import java.util.Map;

public class MenuCache {

	private static final MenuCache instance = new MenuCache();
	private Map<Object, MenuHolderTest> menusChached = new HashMap<>();

	public void setMenusChached(Object object, MenuHolderTest menusChached) {
		this.menusChached.put(object, menusChached);

	}

	
	/**
	 * You can get a menu with {@link org.bukkit.entity.Player} or
	 * custom methods like location and/or clazz you exstend from.
	 * format look like this.
	 * <p>
	 * player + "*__*" + clazz
	 * location + "*__*" + clazz
	 *
	 * @param object use player name or the custom methods abow.
	 * @return a cached menu.
	 */

	public MenuHolderTest getMenuInCache(Object object) {
		return this.menusChached.get(object);
	}

	public Map<Object, MenuHolderTest> getMenusCached() {
		return menusChached;
	}

	public static MenuCache getInstance() {
		return instance;
	}


}
