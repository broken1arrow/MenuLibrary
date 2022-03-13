package org.brokenarrow.menu.library.cache;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Set;

public class GetKeyDataFromCache {
	private static final MenuCache menuCache = MenuCache.getInstance();

	public static Object checkMap(Player player, Location location, Object unknownObject) {
		Set<Object> obj = menuCache.getMenusCached().keySet();
		Object object = null;
		for (Object objectType : obj) {
			if (objectType != null) {
				if (objectType instanceof Player) {
					if (objectType == player)
						object = objectType;
				} else if (objectType instanceof Location) {
					if ((objectType).equals(location)) continue;
					object = objectType;
				}

				if (objectType.equals(unknownObject))
					object = objectType;
			}
		}

		return object;
	}
}
