package brokenarrow.menu.lib.menuexample;

import brokenarrow.menu.lib.MenuButton;
import brokenarrow.menu.lib.MenuHolderTest;
import brokenarrow.menu.lib.NMS.UpdateTittleContainers;
import org.brokenarrow.storage.api.PreferenceSettingsRegisteryApi;
import org.brokenarrow.storage.util.GuiTempletsYaml;
import org.brokenarrow.storage.util.ItemUtily;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.mineacademy.fo.Common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class testMenu extends MenuHolderTest {
	private final MenuButton LinkButton;
	private final MenuButton otherTest;
	private final MenuButton tett;
	private final MenuButton forward;
	private final MenuButton previous;
	private final MenuButton fillItems;
	private int number;

	private final PreferenceSettingsRegisteryApi preferenceRegistery = PreferenceSettingsRegisteryApi.getInstance();

	public testMenu() {
		super("your plugin", 54);


		setTitle("test tittle for menu");
		List<Integer> list = new ArrayList<>();
		Collections.addAll(list, 1, 2);
		setFillSpace(list);
		//setItemsPerPage(3);
		LinkButton = new MenuButton() {
			@Override
			public void onClickInsideMenu(Player player, Inventory menu, ClickType click, ItemStack clickedItem, Object object) {

				System.out.println("send this test for LinkButton menu " + player);
			}

			@Override
			public ItemStack getItem() {
				return new ItemStack(Material.HOPPER);
			}
		};

		otherTest = new MenuButton() {
			@Override
			public void onClickInsideMenu(Player player, Inventory menu, ClickType click, ItemStack clickedItem, Object object) {
				System.out.println("send this test for otherTest menu " + player);
				if (click.isLeftClick())
					number = 1;
				else
					number = 0;
				updateButtons();
			}

			@Override
			public ItemStack getItem() {
				if (number == 1)
					return new ItemStack(Material.BEACON);
				else return new ItemStack(Material.ACACIA_LOG);
			}
		};

		tett = new MenuButton() {
			@Override
			public void onClickInsideMenu(Player player, Inventory menu, ClickType click, ItemStack clickedItem, Object object) {
				System.out.println("send this test for otherTest menu " + click);
			}

			@Override
			public ItemStack getItem() {
				return new ItemStack(Material.GLASS_PANE);
			}
		};
		previous = new MenuButton() {
			@Override
			public void onClickInsideMenu(Player player, Inventory menu, ClickType click, ItemStack clickedItem, Object object) {

				if (click.isLeftClick()) {
					previousPage();
				}

				Common.runLater(() -> UpdateTittleContainers.update(player, GuiTempletsYaml.getGuiTitle("Settings_Menu", getPageNumber()), Material.CHEST, getMenu().getSize()));
				updateButtons();
			}

			@Override
			public ItemStack getItem() {
				return new ItemStack(Material.ARROW);
			}
		};
		forward = new MenuButton() {
			@Override
			public void onClickInsideMenu(Player player, Inventory menu, ClickType click, ItemStack clickedItem, Object object) {
				if (click.isLeftClick()) {
					nextPage();
				}
				Common.runLater(() -> UpdateTittleContainers.update(player, GuiTempletsYaml.getGuiTitle("Settings_Menu", getPageNumber()), Material.CHEST, getMenu().getSize()));
				updateButtons();
			}

			@Override
			public ItemStack getItem() {
				return new ItemStack(Material.RABBIT_FOOT);
			}
		};

		fillItems = new MenuButton() {
			@Override
			public void onClickInsideMenu(Player player, Inventory menu, ClickType click, ItemStack clickedItem, Object object) {

				if (clickedItem.getType() == Material.PLAYER_HEAD) {
					SkullMeta meta = (SkullMeta) clickedItem.getItemMeta();
					if (meta.getOwningPlayer().getPlayer() != null && meta.getOwningPlayer().getPlayer().equals(getViewer()))
						System.out.println("this is owner ");
					else System.out.println("tis is clied " + meta.getOwningPlayer().getName());
				}

				System.out.println("fil items return ");
			}

			@Override
			public ItemStack getItem() {
				return null;
			}

			@Override
			public ItemStack getItem(Object object) {
				int slot = getSlotIndex();
				ItemStack result = null;
				OfflinePlayer player = null;
				if (object instanceof Integer) {
					if (!(preferenceRegistery.getPlayers(getViewer()).size() > (Integer) object)) return null;
					result = preferenceRegistery.getPlayers(getViewer()) != null ? ItemUtily.createItemStack(GuiTempletsYaml.getIcon("PartylistMenu", "Players_List", preferenceRegistery.getPlayers(getViewer()).get((Integer) object))) : null;
					player = preferenceRegistery.getPlayers(getViewer()) != null ? Bukkit.getOfflinePlayer(preferenceRegistery.getPlayers(getViewer()).get((Integer) object)) : null;
				}
				setListOfFillItems(preferenceRegistery.getPlayers(getViewer()));

				if (object instanceof ItemStack) {
					if (((ItemStack) object).getType() == Material.PLAYER_HEAD) {
						SkullMeta meta = (SkullMeta) ((ItemStack) object).getItemMeta();
						if (meta.getOwningPlayer() != null)
							for (UUID players : preferenceRegistery.getPlayers(getViewer())) {
								UUID OfflinePlayer = meta.getOwningPlayer().getUniqueId();
								if (OfflinePlayer.equals(players)) {
									result = ItemUtily.createItemStack(GuiTempletsYaml.getIcon("PartylistMenu", "Players_List", players));
									return ItemUtily.createItemStack(result,
											GuiTempletsYaml.getDisplayName("PartylistMenu", "Players_List", meta.getOwningPlayer().getName()),
											GuiTempletsYaml.getLore("PartylistMenu", "Players_List"));
								}
							}
					}
				}
				return ItemUtily.createItemStack(result,
						GuiTempletsYaml.getDisplayName("PartylistMenu", "Players_List", player != null ? player.getName() : null),
						GuiTempletsYaml.getLore("PartylistMenu", "Players_List"));

			}
		};

	}

	@Override
	public ItemStack getFillItemsAt(int slot) {
		return fillItems.getItem(slot);
	}

	@Override
	public ItemStack getItemAt(int slot) {

		if (slot == 48) {
			return LinkButton.getItem();
		}
		if (slot == 47)
			return otherTest.getItem();

		if (slot == 52)
			return previous.getItem();

		if (slot == 50)
			return forward.getItem();


		return null;
	}
}
