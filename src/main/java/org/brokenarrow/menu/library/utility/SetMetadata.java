package org.brokenarrow.menu.library.utility;

import org.brokenarrow.menu.library.CreateMenus;
import org.brokenarrow.menu.library.MenuMetadataKey;
import org.brokenarrow.menu.library.RegisterMenuAPI;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class SetMetadata {

	private static final Plugin plugin = RegisterMenuAPI.getPLUGIN();


	public static boolean hasPlayerMetadata(Player player, MenuMetadataKey key) {
		return player.hasMetadata(key + "_" + plugin);
	}

	public static List<MetadataValue> getPlayerMenuMetadataList(Player player, MenuMetadataKey key) {
		return player.getMetadata(key + "_" + plugin);
	}

	public static CreateMenus getPlayerMenuMetadata(Player player, MenuMetadataKey key) {
		return (CreateMenus) player.getMetadata(key + "_" + plugin).get(0).value();
	}

	public static Object getPlayerMetadata(Player player, MenuMetadataKey key) {
		return player.getMetadata(key + "_" + plugin).get(0).value();
	}

	public static void setPlayerMetadata(Player player, String key, Object object) {
		player.setMetadata(key + "_" + plugin, new FixedMetadataValue(plugin, object));
	}

	public static void setPlayerMenuMetadata(Player player, MenuMetadataKey key, CreateMenus menu) {
		player.setMetadata(key + "_" + plugin, new FixedMetadataValue(plugin, menu));
	}

	public static void setPlayerLocationMetadata(Player player, MenuMetadataKey key, Location location) {
		player.setMetadata(key + "_" + plugin, new FixedMetadataValue(plugin, location));
	}

	public static void removePlayerMenuMetadata(Player player, MenuMetadataKey key) {
		player.removeMetadata(key + "_" + plugin, plugin);
	}


}