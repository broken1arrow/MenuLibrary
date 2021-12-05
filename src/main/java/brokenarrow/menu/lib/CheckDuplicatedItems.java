package brokenarrow.menu.lib;

import brokenarrow.menu.lib.messages.SendMsg;
import brokenarrow.menu.lib.utility.Item.ItemUtily;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class CheckDuplicatedItems {

	//todo fix this to only create on instance? and add player to cache.
	private static final Map<ItemStack, Integer> duplicatedItems = new HashMap<>();
	private boolean sendMsgPlayer = false;
	private final List<String> blacklistedItems;

	/**
	 * set blacklisted items player not shall add to inventory/menu.
	 *
	 * @param blacklistedItems list of items some are not alloed.
	 */

	public CheckDuplicatedItems(List<String> blacklistedItems) {
		this.blacklistedItems = blacklistedItems;
	}

	/**
	 * Metohd to check items inside inventory and remove items
	 * it it more an 1 (giv rest back to player or drop it on grund
	 * if inventory is full).
	 *
	 * @param inv inventory you want to check
	 * @param player player some use menu/inventory
	 * @return all items add in menu, but only 1 of each.
	 */

	public Map<Integer, ItemStack> getItemsExceptBottomBar(final Inventory inv, Player player) {
		final Map<Integer, ItemStack> items = new HashMap<>();

		for (int i = 0; i < inv.getSize() - 9; i++) {
			final ItemStack item = inv.getItem(i);

			if (chekItemAreOnBlacklist(item)) {
				addItemsBackToPlayer(player, item);
			} else {
				items.put(i, item != null && !isAir(item.getType()) ? item : null);
			}
		}

		return addToMuchItems(items, player, inv);
	}

	private Map<Integer, ItemStack> addToMuchItems(final Map<Integer, ItemStack> items, Player player, Inventory inventory) {
		Map<Integer, ItemStack> itemStacksNoDubbleEntity = new HashMap<>();
		Set<ItemStack> set = new HashSet<>();
		this.sendMsgPlayer = false;
		for (final Map.Entry<Integer, ItemStack> entitys : items.entrySet()) {

			if (entitys.getValue() != null) {

				if (entitys.getValue().getAmount() > 1) {

					duplicatedItems.put(ItemUtily.createItemStackAsOne(entitys.getValue()), (ItemUtily.countItemStacks(entitys.getValue(), inventory)) - 1);
				}
				if (!set.add(ItemUtily.createItemStackAsOne(entitys.getValue()))) {

					duplicatedItems.put(ItemUtily.createItemStackAsOne(entitys.getValue()), (ItemUtily.countItemStacks(entitys.getValue(), inventory)) - 1);
				} else {
					itemStacksNoDubbleEntity.put(entitys.getKey(), ItemUtily.createItemStackAsOne(entitys.getValue()));
				}
			}
		}
		addItemsBackToPlayer(player);
		return itemStacksNoDubbleEntity;
	}

	private void addItemsBackToPlayer(Player player, ItemStack itemStack) {

		HashMap<Integer, ItemStack> ifInventorFull = player.getInventory().addItem(itemStack);
		if (!ifInventorFull.isEmpty() && player.getLocation().getWorld() != null)
			player.getLocation().getWorld().dropItemNaturally(player.getLocation(), ifInventorFull.get(0));

		if (!this.sendMsgPlayer) {
			SendMsg.sendMessage(player,"this item are blacklisted");
			this.sendMsgPlayer = true;
		}
	}

	private void addItemsBackToPlayer(Player player) {
		int conter = 0;
		for (final Map.Entry<ItemStack, Integer> entitys : CheckDuplicatedItems.duplicatedItems.entrySet()) {
			entitys.getKey().setAmount(entitys.getValue());
			HashMap<Integer, ItemStack> ifInventorFull = player.getInventory().addItem(entitys.getKey());
			if (!ifInventorFull.isEmpty() && player.getLocation().getWorld() != null)
				player.getLocation().getWorld().dropItemNaturally(player.getLocation(), ifInventorFull.get(0));
			SendMsg.sendMessage(player,"You have added dublicated items");
			conter++;
			if (conter >= CheckDuplicatedItems.duplicatedItems.size()) {
				CheckDuplicatedItems.duplicatedItems.remove(entitys.getKey());
				CheckDuplicatedItems.duplicatedItems.clear();

			}

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
