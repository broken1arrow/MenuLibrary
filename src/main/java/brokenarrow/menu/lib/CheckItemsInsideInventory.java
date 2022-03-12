package brokenarrow.menu.lib;

import brokenarrow.menu.lib.messages.SendMsgDuplicatedItems;
import brokenarrow.menu.lib.utility.Item.ItemUtily;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Simple check if player add items some are ether blacklisted or add more an 1 item or duplicated item.
 */

public class CheckItemsInsideInventory {

	//todo fix this to only create on instance? and add player to cache.
	private static final Map<UUID, Map<ItemStack, Integer>> duplicatedItems = new HashMap<>();
	private boolean sendMsgPlayer = false;
	private final List<String> blacklistedItems = new ArrayList<>();


	/**
	 * set blacklisted items player not shall add to inventory/menu.
	 *
	 * @param blacklistedItems list of items some are not allowed.
	 */
	public void setBlacklistedItems(List<String> blacklistedItems) {
		this.blacklistedItems.addAll(blacklistedItems);
	}

	/**
	 * Metohd to check items inside inventory and remove items
	 * it it more an 1 (giv rest back to player or drop it on grund
	 * if inventory is full).
	 *
	 * @param inv                  inventory you want to check
	 * @param player               player some use menu/inventory
	 * @param shallCheckDuplicates if it shall check if added items are dublicates or more 1 one item.
	 * @return all items add in menu, but only 1 of each if shallCheckDuplicates are set to true.
	 */
	public Map<Integer, ItemStack> getItemsExceptBottomBar(final Inventory inv, Player player, boolean shallCheckDuplicates) {
		return getItemsExceptBottomBar(inv, player, null, shallCheckDuplicates);
	}

	/**
	 * Metohd to check items inside inventory and remove items
	 * it it more an 1 (giv rest back to player or drop it on grund
	 * if inventory is full).
	 *
	 * @param inv    inventory you want to check
	 * @param player player some use menu/inventory
	 * @return all items add in menu, but only 1 of each.
	 */
	public Map<Integer, ItemStack> getItemsExceptBottomBar(final Inventory inv, Player player) {
		return getItemsExceptBottomBar(inv, player, null, true);
	}

	/**
	 * Metohd to check items inside inventory and remove items
	 * it it more an 1 (giv rest back to player or drop it on grund
	 * if inventory is full).
	 *
	 * @param inv      inventory you want to check
	 * @param player   player some use menu/inventory
	 * @param location if player is offline or null, can you return a location where items shall drop.
	 * @return all items add in menu, but only 1 of each.
	 */
	public Map<Integer, ItemStack> getItemsExceptBottomBar(final Inventory inv, Player player, Location location) {
		return getItemsExceptBottomBar(inv, player, location, true);
	}

	/**
	 * Metohd to check items inside inventory and remove items
	 * it it more an 1 (giv rest back to player or drop it on grund
	 * if inventory is full).
	 *
	 * @param inv                  inventory you want to check
	 * @param player               player some use menu/inventory
	 * @param location             if player is offline or null, can you return a location where items shall drop.
	 * @param shallCheckDuplicates if it shall check if added items are dublicates or more 1 one item.
	 * @return all items add in menu, but only 1 of each.
	 */

	public Map<Integer, ItemStack> getItemsExceptBottomBar(final Inventory inv, Player player, Location location, boolean shallCheckDuplicates) {
		final Map<Integer, ItemStack> items = new HashMap<>();

		for (int i = 0; i < inv.getSize() - 9; i++) {
			final ItemStack item = inv.getItem(i);

			if (chekItemAreOnBlacklist(item)) {
				addItemsBackToPlayer(player, item);
			} else {
				items.put(i, item != null && !isAir(item.getType()) ? item : null);
			}
		}
		if (shallCheckDuplicates)
			return addToMuchItems(items, player, inv, location);
		else return items;
	}

	private Map<Integer, ItemStack> addToMuchItems(final Map<Integer, ItemStack> items, Player player, Inventory inventory, Location location) {
		Map<Integer, ItemStack> itemStacksNoDubbleEntity = new HashMap<>();
		Map<ItemStack, Integer> chachedDuplicatedItems = new HashMap<>();
		Set<ItemStack> set = new HashSet<>();
		this.sendMsgPlayer = false;
		for (final Map.Entry<Integer, ItemStack> entitys : items.entrySet()) {

			if (entitys.getValue() != null) {

				if (entitys.getValue().getAmount() > 1) {
					chachedDuplicatedItems.put(ItemUtily.createItemStackAsOne(entitys.getValue()), (ItemUtily.countItemStacks(entitys.getValue(), inventory)) - 1);
					duplicatedItems.put(player.getUniqueId(), chachedDuplicatedItems);
				}
				if (!set.add(ItemUtily.createItemStackAsOne(entitys.getValue()))) {
					chachedDuplicatedItems.put(ItemUtily.createItemStackAsOne(entitys.getValue()), (ItemUtily.countItemStacks(entitys.getValue(), inventory)) - 1);
					duplicatedItems.put(player.getUniqueId(), chachedDuplicatedItems);
				} else {
					itemStacksNoDubbleEntity.put(entitys.getKey(), ItemUtily.createItemStackAsOne(entitys.getValue()));
				}
			}
		}
		addItemsBackToPlayer(location);
		return itemStacksNoDubbleEntity;
	}

	private void addItemsBackToPlayer(Player player, ItemStack itemStack) {

		HashMap<Integer, ItemStack> ifInventorFull = player.getInventory().addItem(itemStack);
		if (!ifInventorFull.isEmpty() && player.getLocation().getWorld() != null)
			player.getLocation().getWorld().dropItemNaturally(player.getLocation(), ifInventorFull.get(0));

		if (!this.sendMsgPlayer) {
			SendMsgDuplicatedItems.sendBlacklistMessage(player, itemStack.getType().name().toLowerCase());
			this.sendMsgPlayer = true;
		}
	}

	private void addItemsBackToPlayer(Location location) {

		for (final UUID playerUUID : CheckItemsInsideInventory.duplicatedItems.keySet()) {
			for (final Map.Entry<ItemStack, Integer> items : duplicatedItems.get(playerUUID).entrySet()) {
				ItemStack itemStack = items.getKey();
				int amount = items.getValue();

				itemStack.setAmount(amount);
				OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerUUID);
				if (offlinePlayer.getPlayer() != null) {
					HashMap<Integer, ItemStack> ifInventorFull = offlinePlayer.getPlayer().getInventory().addItem(itemStack);
					if (!ifInventorFull.isEmpty() && offlinePlayer.getPlayer().getLocation().getWorld() != null)
						offlinePlayer.getPlayer().getLocation().getWorld().dropItemNaturally(offlinePlayer.getPlayer().getLocation(), ifInventorFull.get(0));

					SendMsgDuplicatedItems.sendDublicatedMessage(offlinePlayer.getPlayer(), itemStack.getType(), duplicatedItems.size(), amount);
				} else if (location != null && location.getWorld() != null)
					location.getWorld().dropItemNaturally(location, itemStack);
			}
			CheckItemsInsideInventory.duplicatedItems.remove(playerUUID);
		}

	}

	private boolean chekItemAreOnBlacklist(ItemStack itemStack) {
		List<String> itemStacks = blacklistedItems;
		if (itemStack != null && itemStacks != null)
			for (String item : itemStacks) {
				if (ItemUtily.createItemStack(item).isSimilar(itemStack))
					return true;
			}
		return false;

	}

	public static boolean isAir(final Material material) {
		return nameEquals(material, "AIR", "CAVE_AIR", "VOID_AIR", "LEGACY_AIR");
	}

	private static boolean nameEquals(final Material mat, final String... names) {
		final String matName = mat.toString();

		for (final String name : names)
			if (matName.equals(name))
				return true;

		return false;
	}
}
