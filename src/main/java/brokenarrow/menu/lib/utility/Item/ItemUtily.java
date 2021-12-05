package brokenarrow.menu.lib.utility.Item;

import com.google.common.base.Enums;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Create items and also count number of items of
 * specific type.
 */

public class ItemUtily {

	/**
	 * Will set item to air.
	 *
	 * @return new Itemstack as air.
	 */

	public static ItemStack createItemStack() {
		return createItemStack(new ItemStack(Material.AIR), null, null, null, null, null, null);
	}

	/**
	 * Create itemstack of your item you input.
	 *
	 * @param item the item you want to create.
	 * @return the item.
	 */

	public static ItemStack createItemStack(ItemStack item) {

		return createItemStack(item, null, null, null, null, null, null);
	}

	/**
	 * Create itemstack of your item you input.
	 *
	 * @param material the material you want to create itemstack.
	 * @return one itemstack of your Matrial item.
	 */

	public static ItemStack createItemStack(Material material) {

		return createItemStack(material, 1, null, null, null, null, null, null);
	}

	public static ItemStack[] createItemStack(String[] stringName) {

		return createItemStack(stringName, null, null, null, null, null, null);
	}

	/**
	 * Create itemstack of your item you input.
	 *
	 * @param stringName create itemstack of stringname (most be ENUM name)
	 * @return new itemstack of your stringname.
	 */

	public static ItemStack createItemStack(String stringName) {

		return createItemStack(stringName, null, null, null, null, null, null);
	}


	public static Material createMaterial(String stringName) {

		return createMaterialItem(stringName);
	}

	public static ItemStack createItemStack(ItemStack item, boolean glow) {
		return createItemStack(item, null, null, null, null, glow, null);
	}

	public static ItemStack createItemStack(Material material, boolean glow) {
		return createItemStack(material, 1, null, null, null, null, glow, null);
	}

	public static ItemStack createItemStack(String stringName, boolean glow) {
		return createItemStack(stringName, null, null, null, null, glow, null);
	}

	/**
	 * Create itemstack of your item you input.
	 *
	 * @param item        the ItemStack you want to transform.
	 * @param displayname of the item.
	 * @param lore        of the item.
	 * @return return an itemstack with the values you has set.
	 */

	public static ItemStack createItemStack(ItemStack item, String displayname, List<String> lore) {

		return createItemStack(item, displayname, lore, null, null, null, null);
	}

	public static ItemStack createItemStack(Material material, String displayname, List<String> lore) {

		return createItemStack(material, 1, displayname, lore, null, null, null, null);
	}

	public static ItemStack createItemStack(String stringName, String displayname, List<String> lore) {

		return createItemStack(stringName, displayname, lore, null, null, null, null);
	}

	public static ItemStack createItemStack(ItemStack item, String displayname, List<String> lore, String itemMetaKey, String itemMetaValue) {

		return createItemStack(item, displayname, lore, itemMetaKey, itemMetaValue, null, null);
	}

	public static ItemStack createItemStack(Material material, String displayname, List<String> lore, String itemMetaKey, String itemMetaValue) {

		return createItemStack(material, 1, displayname, lore, itemMetaKey, itemMetaValue, null, null);
	}

	public static ItemStack createItemStack(String stringName, String displayname, List<String> lore, String itemMetaKey, String itemMetaValue) {

		return createItemStack(stringName, displayname, lore, itemMetaKey, itemMetaValue, null, null);
	}

	public static ItemStack createItemStack(ItemStack item, String displayname, List<String> lore, String itemMetaKey, String itemMetaValue, boolean glow) {

		return createItemStack(item, displayname, lore, itemMetaKey, itemMetaValue, glow, null);
	}

	public static ItemStack createItemStack(Material material, String displayname, List<String> lore, String itemMetaKey, String itemMetaValue, boolean glow) {

		return createItemStack(material, 1, displayname, lore, itemMetaKey, itemMetaValue, glow, null);
	}

	public static ItemStack createItemStack(String stringName, String displayname, List<String> lore, String itemMetaKey, String itemMetaValue, boolean glow) {

		return createItemStack(stringName, displayname, lore, itemMetaKey, itemMetaValue, glow, null);
	}

	public static ItemStack createItemStack(ItemStack item, String displayname, List<String> lore, Map<String, String> itemMetas, boolean glow) {

		return createItemStack(item, displayname, lore, null, null, glow, itemMetas);
	}

	public static ItemStack createItemStack(Material material, int amountOfItems, String displayname, List<String> lore, Map<String, String> itemMetas, boolean glow) {

		return createItemStack(material, amountOfItems, displayname, lore, null, null, glow, itemMetas);
	}

	public static ItemStack createItemStack(String stringName, String displayname, List<String> lore, Map<String, String> itemMetas, boolean glow) {

		return createItemStack(stringName, displayname, lore, null, null, glow, itemMetas);
	}

	private static final Pattern HEX_PATTERN = Pattern.compile("(?<!\\\\\\\\)(#[a-fA-F0-9]{6})");


	/**
	 * Create a new item, you can use ether of this three methods.
	 *
	 * @param itemstack       if want add name or lore on the item.
	 * @param displaynamename set the name on the item or set null if
	 *                        you want it unchange.
	 * @param lore            set the lore on the item or set null if
	 *                        you want it unchange.
	 * @param itemMetaKey     Set metadata on the item.
	 * @param itemMetaValue   Set the value on the item.
	 * @return itemstack with your set values.
	 */

	protected static ItemStack createItemStack(ItemStack itemstack, String displaynamename, List<String> lore, String itemMetaKey, String itemMetaValue, Boolean glow, Map<String, String> itemMetaMap) {
		ItemMeta itemMeta;

		if (itemstack != null) {

			itemMeta = itemstack.getItemMeta();
			if (displaynamename != null) {
				itemMeta.setDisplayName(translateHex(displaynamename));
			}
			if (lore != null) {
				List<String> lores = translateHex(lore);
				itemMeta.setLore(lores);
			}
			if (glow != null && glow) {
				itemMeta.addEnchant(Enchantment.SILK_TOUCH, 1, false);
				itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

			}

			itemstack.setItemMeta(itemMeta);
		}


		return itemstack != null ? itemstack : new ItemStack(Material.AIR, 1);
	}

	protected static ItemStack createItemStack(Material matrial, int amountOfItems, String displaynamename, List<String> lore, String itemMetaKey, String itemMetaValue, Boolean glow, Map<String, String> itemMetaMap) {
		ItemStack itemstack = null;

		if (matrial != null)
			itemstack = new ItemStack(matrial);

		if (itemstack != null) {
			ItemMeta itemMeta = itemstack.getItemMeta();
			if (displaynamename != null) {
				itemMeta.setDisplayName(translateHex(displaynamename));
			}
			if (lore != null) {
				List<String> lores = translateHex(lore);
				itemMeta.setLore(lores);
			}
			if (glow != null && glow) {
				itemMeta.addEnchant(Enchantment.SILK_TOUCH, 1, false);
				itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

			}

			itemstack.setItemMeta(itemMeta);
			itemstack.setAmount(amountOfItems == 0 ? 1 : amountOfItems);
		}
		return itemstack != null ? itemstack : new ItemStack(Material.AIR);
	}

	protected static ItemStack createItemStack(String itemStringName, String displaynamename, List<String> lore, String itemMetaKey, String itemMetaValue, Boolean glow, Map<String, String> itemMetaMap) {
		ItemStack itemstack = null;
		if (itemStringName != null && !itemStringName.equals("")) {
			itemstack = new ItemStack(Enums.getIfPresent(Material.class, itemStringName).orNull() == null ? Material.AIR : Material.valueOf(itemStringName));
			if (itemstack.getType() == Material.AIR)
				System.out.println("MaterialType is null " + itemStringName);
		}
		if (itemstack != null) {

			 	ItemMeta itemMeta = itemstack.getItemMeta();
			if (displaynamename != null) {
				itemMeta.setDisplayName(translateHex(displaynamename));
			}
			if (lore != null) {
				List<String> lores = translateHex(lore);
				itemMeta.setLore(lores);
			}
			if (glow != null && glow) {
				itemMeta.addEnchant(Enchantment.SILK_TOUCH, 1, false);
				itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

			}

			itemstack.setItemMeta(itemMeta);
		}
		return itemstack != null ? itemstack : new ItemStack(Material.AIR);

	}

	protected static Material createMaterialItem(String itemStringName) {
		return Enums.getIfPresent(Material.class, itemStringName).orNull() == null ? null : Material.valueOf(itemStringName);

	}

	/**
	 * Create itemstack with an String array.
	 *
	 * @param itemsStringName the name of the item (need to bee right ENUM name).
	 * @param displaynamename name on the item.
	 * @param lore            lore on the item.
	 * @param itemMetaKey     Set one metadata key.
	 * @param itemMetaValue   Set one metadata value.
	 * @param glow            if the item shall be enchantat
	 * @param itemMetaMap     a map with keys and values you want to set metadata.
	 * @return item with your settings.
	 */

	public static ItemStack[] createItemStack(String[] itemsStringName, String displaynamename, List<String> lore, String itemMetaKey, String itemMetaValue, Boolean glow, Map<String, String> itemMetaMap) {
		ItemStack itemstack = null;
		List<ItemStack> list = new ArrayList<>();

		for (String itemStringName : itemsStringName) {
			if (itemStringName != null)
				itemstack = new ItemStack(Enums.getIfPresent(Material.class, itemStringName).orNull() == null ? Material.AIR : Material.valueOf(itemStringName));

			if (itemstack != null) {
				ItemMeta itemMeta = itemstack.getItemMeta();
				if (displaynamename != null) {
					itemMeta.setDisplayName(translateHex(displaynamename));
				}
				if (lore != null) {
					List<String> lores = translateHex(lore);
					itemMeta.setLore(lores);
				}
				if (glow != null && glow) {
					itemMeta.addEnchant(Enchantment.SILK_TOUCH, 1, false);
					itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
					itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

				}

				itemstack.setItemMeta(itemMeta);
				list.add(itemstack);
			}

		}

		return itemstack != null ? list.toArray(new ItemStack[0]) : new ItemStack[]{new ItemStack(Material.AIR)};
	}

	public static ItemStack createItemStackAsOne(Material material) {
		ItemStack itemstack = null;
		if (material != null)
			itemstack = new ItemStack(material);

		return createItemStackAsOne(itemstack != null ? itemstack : new ItemStack(Material.AIR));
	}

	public static ItemStack createItemStackAsOne(ItemStack itemstacks) {
		ItemStack itemstack = null;
		if (itemstacks != null && !itemstacks.getType().equals(Material.AIR)) {
			itemstack = itemstacks.clone();
			ItemMeta meta = itemstack.getItemMeta();
			itemstack.setItemMeta(meta);
			itemstack.setAmount(1);
		}
		return itemstack != null ? itemstack : new ItemStack(Material.AIR);
	}

	public static ItemStack[] createItemStackAsOne(ItemStack[] itemstacks) {
		ItemStack itemstack = null;
		if (itemstacks != null) {
			for (ItemStack item : itemstacks)
				if (!(item.getType() == Material.AIR)) {
					itemstack = item.clone();
					ItemMeta meta = itemstack.getItemMeta();
					itemstack.setItemMeta(meta);
					itemstack.setAmount(1);
					return new ItemStack[]{itemstack};
				}
		}
		return new ItemStack[]{new ItemStack(Material.AIR)};
	}

	public static ItemStack createItemStackWhitAmount(Material matrial, int amount) {
		ItemStack itemstack = null;
		if (matrial != null) {
			itemstack = new ItemStack(matrial);
			itemstack.setAmount(amount);
		}
		return itemstack != null ? itemstack : new ItemStack(Material.AIR);
	}

	/**
	 * Count the amount of matched items in two item arrys.
	 *
	 * @param itemStacks compere this array with items array
	 * @param items      compere this array with itemStacks array
	 * @return amount of simular items in the array.
	 */
	public static int countItemStacks(ItemStack[] itemStacks, ItemStack[] items) {
		int countItems = 0;

		for (ItemStack itemStack : itemStacks)
			for (ItemStack item : items)
				if (itemStack != null && itemStack.isSimilar(item) && !(itemStack.getType() == Material.AIR)) {
					countItems += itemStack.getAmount();
				}
		return countItems;
	}

	/**
	 * Count amount of stacks inside the inventry.
	 *
	 * @param inventoryItems the inventory you whant to check items.
	 * @param item           the item you whant to check for.
	 * @return amount of stacks.
	 */

	public static int countItemStacks(Inventory inventoryItems, ItemStack item) {
		int countItems = 0;
		for (ItemStack itemStack : inventoryItems.getContents())
			if (itemStack != null && itemStack.isSimilar(item) && !(itemStack.getType() == Material.AIR)) {
				countItems++;
			}
		return countItems;
	}

	/**
	 * Count amount of items inside the inventry.
	 *
	 * @param inventoryItems the inventory you whant to check items.
	 * @param item           the item you whant to check for.
	 * @return amount of items.
	 */
	public static int countItemStacks(ItemStack item, Inventory inventoryItems) {
		return countItemStacks(item, inventoryItems, false);
	}

	public static int countItemStacks(ItemStack item, Inventory inventoryItems, boolean onlyNoFullItems) {
		int countItems = 0;
		for (ItemStack itemStack : inventoryItems.getContents()) {
			if (onlyNoFullItems) {
				if (!(itemStack.getAmount() == itemStack.getMaxStackSize())) {
					if (itemStack != null && itemStack.isSimilar(item) && !(itemStack.getType() == Material.AIR)) {
						countItems += itemStack.getAmount();
					}
				}
			} else if (itemStack != null && itemStack.isSimilar(item) && !(itemStack.getType() == Material.AIR)) {
				countItems += itemStack.getAmount();
			}
		}
		return countItems;
	}

	/**
	 * Count amount of all items inside the inventry.
	 *
	 * @param inventoryItems the inventory you whant to check items.
	 * @param itemStacks     you whant to count.
	 * @return amount of items.
	 */
	public static int countItemStacks(ItemStack[] itemStacks, Inventory inventoryItems) {
		int countItems = 0;
		for (ItemStack itemStack : inventoryItems.getContents())
			for (ItemStack item : itemStacks)
				if (itemStack != null && itemStack.isSimilar(item) && !(itemStack.getType() == Material.AIR)) {
					countItems += itemStack.getAmount();
				}
		return countItems;
	}

	/**
	 * Count amount of all items inside one ItemStack array.
	 *
	 * @param itemStacks you whant to count.
	 * @return amount of items.
	 */
	public static int countItemStacks(ItemStack[] itemStacks) {
		int countItems = 0;
		for (ItemStack item : itemStacks)
			if (item != null && !(item.getType() == Material.AIR)) {
				countItems += item.getAmount();
			}
		return countItems;
	}

	public static boolean isPlaceLeftInventory(Inventory inventory, int amount, Material materialToMatch) {

		for (ItemStack item : inventory) {
			if (item != null) {
				if (item.getType() == materialToMatch && amount > countItemStacks(item, inventory, true)) {
					continue;
				}
				if (item.getType() == materialToMatch && amount + item.getAmount() <= item.getMaxStackSize()) {
					return true;
				}
			}
		}
		return false;
	}

	public static List<String> translateHex(List<String> rawLore) {
		List<String> lores = new ArrayList<>();
		for (String lore : rawLore)
			lores.add(translateHexCodes(lore));
		return lores;
	}

	public static String translateHex(String rawSingelLine) {
		return translateHexCodes(rawSingelLine);
	}

	private static String translateHexCodes(String textTranslate) {

		Matcher matcher = HEX_PATTERN.matcher(textTranslate);
		while (matcher.find()) {
			String match = matcher.group(0);
			String[] hexSplitedToFitRBG = match.split("");
			if (!(hexSplitedToFitRBG.length == 7))
				System.out.println("you has wrongly set up the hex color it shall be 6 in length");
			textTranslate = textTranslate.replace("<" + match + ">", "&x&" + hexSplitedToFitRBG[1] + "&" + hexSplitedToFitRBG[2] + "&" + hexSplitedToFitRBG[3] + "&" + hexSplitedToFitRBG[4] + "&" + hexSplitedToFitRBG[5] + "&" + hexSplitedToFitRBG[6]);
		}
		return ChatColor.translateAlternateColorCodes('&', textTranslate);
	}
}
