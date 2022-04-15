package org.brokenarrow.menu.library.utility;

import org.brokenarrow.menu.library.CreateMenus;
import org.brokenarrow.menu.library.MenuMetadataKey;
import org.brokenarrow.menu.library.RegisterMenuAPI;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Class to get or set metadata and this api use this keys internally {@link MenuMetadataKey } and methods below do you
 * get values or a menu from a player (if you use location in the menu api it will store the menu in cache).
 */
public final class Metadata {

	private static final Plugin plugin = RegisterMenuAPI.getPLUGIN();


	public static boolean hasPlayerMetadata(@NotNull final Player player, @NotNull final MenuMetadataKey key) {
		return player.hasMetadata(key + "_" + plugin);
	}

	public static List<MetadataValue> getPlayerMenuMetadataList(@NotNull final Player player, @NotNull final MenuMetadataKey key) {
		return player.getMetadata(key + "_" + plugin);
	}

	@Nullable
	public static CreateMenus getPlayerMenuMetadata(@NotNull final Player player, @NotNull final MenuMetadataKey key) {
		List<MetadataValue> playerMetadata = player.getMetadata(key + "_" + plugin);
		if (playerMetadata.isEmpty())
			return null;
		return (CreateMenus) playerMetadata.get(0).value();
	}

	@Nullable
	public static Object getPlayerMetadata(@NotNull final Player player, @NotNull final MenuMetadataKey key) {
		List<MetadataValue> playerMetadata = player.getMetadata(key + "_" + plugin);
		if (playerMetadata.isEmpty())
			return null;
		return playerMetadata.get(0).value();
	}

	public static void setPlayerMetadata(@NotNull final Player player, @NotNull final String key, @NotNull final Object object) {
		player.setMetadata(key + "_" + plugin, new FixedMetadataValue(plugin, object));
	}

	public static void setPlayerMenuMetadata(@NotNull final Player player, @NotNull final MenuMetadataKey key, @NotNull final CreateMenus menu) {
		player.setMetadata(key + "_" + plugin, new FixedMetadataValue(plugin, menu));
	}

	public static void setPlayerLocationMetadata(@NotNull final Player player, @NotNull final MenuMetadataKey key, @NotNull final Location location) {
		player.setMetadata(key + "_" + plugin, new FixedMetadataValue(plugin, location));
	}

	public static void removePlayerMenuMetadata(@NotNull final Player player, @NotNull final MenuMetadataKey key) {
		player.removeMetadata(key + "_" + plugin, plugin);
	}


}