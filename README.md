# MenuLibrary
[![](https://jitpack.io/v/broken1arrow/MenuLibrary.svg)](https://jitpack.io/#broken1arrow/MenuLibrary)

# Important changes I made in 0.28
You can´t use RegisterClass(this) in newest update, use RegisterMenuAPI(this).
I made this change becuse the confusing name on the class.
# Important changes I made in 0.31
change packet name and how it set metadata on player.

## Import the liberary

### Maven

```
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
	
	
    <dependencies>
        <dependency>
            <groupId>com.github.broken1arrow</groupId>
            <artifactId>MenuLibrary</artifactId>
            <version>typeRightVersion</version>
            <scope>compile</scope>
        </dependency>
    </dependencies>
```

### How to use api
 First in your main class (I only use an example name for the main class). You register for the menu event. 
 And you are done, only you have to do is extend MenuHolder.
 
 I have updated register method 2021:12:22.

```
	public class MenuMain extends JavaPlugin {


		@Override
		public void onEnable() {
			// this = your main class.
			//this will not work any more in 0.28
			RegisterClass(this); 
			//use this.
			new RegisterMenuAPI(this);
			
			// own code here
		}

		@Override
		public void onDisable() {
			// own code here
		}
		public static MenuMain getInstance() {
			return MenuMain.getPlugin(MenuMain.class);
		}

	}

```
## If you want to clear one value from cache (if you use location).
You only need do this in your code. I have not right now added a clear metod
(if that are something you need I can add that to). 
 ```
MenuCache.getInstance().removeMenuChached(location);
```

## Make simple onepage menu

```

	public class MainMenu extends MenuHolder {

		private final MenuButton examplebutton;
		private final MenuButton examplebutton1;
		private final MenuButton examplebutton2;

		public MainMenu() {
			super();
			
                        setMenuSize(45);
			setTitle("menu titel");

			examplebutton = new MenuButton() {
				@Override
				public void onClickInsideMenu(Player player, Inventory menu, ClickType click, ItemStack clickedItem, Object object) {
					//stuff you want to execute here, when clicking on item.
				}

				@Override
				public ItemStack getItem() {

					//Item you want this button return. 
					return null;
				}
			};

			examplebutton1 = new MenuButton() {
				@Override
				public void onClickInsideMenu(Player player, Inventory menu, ClickType click, ItemStack clickedItem, Object object) {
					//stuff you want to execute here when clicking on item.
				}

				@Override
				public ItemStack getItem() {
					//Item you want this button return. 
					return null;
				}
			};

			examplebutton2 = new MenuButton() {
				@Override
				public void onClickInsideMenu(Player player, Inventory menu, ClickType click, ItemStack clickedItem, Object object) {
					//stuff you want to execute here, when clicking on item.
				}

				@Override
				public ItemStack getItem() {
					//Item you want this button return. 
					return null;
				}
			};
		}

		@Override
		public ItemStack getItemAt(int slot) {

			if (slot == 1)
				return examplebutton.getItem();
			if (slot == 5)
				return examplebutton1.getItem();
			if (slot == 7)
				return examplebutton2.getItem();

			return null;
		}

```

## Make severalpage menu
Have also added code as an example of how I use it (I have removed some code I use because make it easier to read). 

```

public class PagedMenu extends MenuHolder {

	private final MenuButton removeAllPlayers;
	private final MenuButton addPlayers;
	private final MenuButton backButton;
	private final MenuButton forward;
	private final MenuButton previous;
	private final MenuButton listOfObjects;

	private final PreferenceSettingsRegisteryApi preferenceRegistery = PreferenceSettingsRegisteryApi.getInstance();

	public PagedMenu(Player player) {
		super(PreferenceSettingsRegisteryApi.getInstance().getPlayers(player));
                setMenuSize(45);
		setTitle("players in party");
                  // Witch slot you want to fill with items in.
		setFillSpace(IntStream.rangeClosed(0, 35).boxed().collect(Collectors.toList()));
	        // add this if you want players add or remove items from slots you set with setFillSpace().	
	        //setSlotsYouCanAddItems();

		removeAllPlayers = new MenuButton() {
			@Override
			public void onClickInsideMenu(Player player, Inventory menu, ClickType click, ItemStack clickedItem, Object object) {
				player.closeInventory();
				AOtherMenuClass.onMenuOpen(player);
			}

			@Override
			public ItemStack getItem() {
				return null;
			}
		};

		addPlayers = new MenuButton() {
			@Override
			public void onClickInsideMenu(Player player, Inventory menu, ClickType click, ItemStack clickedItem, Object object) {
				player.closeInventory();
				updateButtons();
			}

			@Override
			public ItemStack getItem() {
				return null;
			}
		};
           // Open menu befor this.
		backButton = new MenuButton() {
			@Override
			public void onClickInsideMenu(Player player, Inventory menu, ClickType click, ItemStack clickedItem, Object object) {
				//example code to run.
				new MainGuiMenu().menuOpen(player);
			}

			@Override
			public ItemStack getItem() {
				return null;
			}
		};
		previous = new MenuButton() {
			@Override
			public void onClickInsideMenu(Player player, Inventory menu, ClickType click, ItemStack clickedItem, Object object) {

				if (click.isLeftClick()) {
					previousPage();
				}

				UpdateTittleContainers.update(player, GuiTempletsYaml.getGuiTitle("Settings_Menu",/*placeholder i use here*/ getPageNumber()));
			}

			@Override
			public ItemStack getItem() {
				return null;
			}
		};
		forward = new MenuButton() {
			@Override
			public void onClickInsideMenu(Player player, Inventory menu, ClickType click, ItemStack clickedItem, Object object) {
				if (click.isLeftClick()) {
					nextPage();
				}
				UpdateTittleContainers.update(player, GuiTempletsYaml.getGuiTitle("Settings_Menu",/*placeholder i use here*/ getPageNumber()));
			}

			@Override
			public ItemStack getItem() {
				return null;
			}
		};

		listOfObjects = new MenuButton() {

			@Override
			public void onClickInsideMenu(Player player, Inventory menu, ClickType click, ItemStack clickedItem, Object object) {

				if (object instanceof UUID) {
				
     // your code here
			}
	

			}

			@Override
			public ItemStack getItem() {
				return null;
			}

			@Override
			public ItemStack getItem(Object object) {

				if (object instanceof UUID) {
					return ItemUtily.createItemStack(GuiTempletsYaml.getIcon("PartylistMenu", "Players_List", (UUID) object),
							GuiTempletsYaml.getDisplayName("PartylistMenu", "Players_List", Bukkit.getOfflinePlayer((UUID) object).getName()),
							GuiTempletsYaml.getLore("PartylistMenu", "Players_List"));
				}
				return null;
			}

		};

	}

	@Override
	public ItemStack getFillItemsAt(Object o) {
		return listOfObjects.getItem(o);
	}

	@Override
	public ItemStack getItemAt(int slot) {

		if (slot == 38)
			return forward.getItem();
		if (slot == 35)
			return previous.getItem();
		if (slot == 44)
			return backButton.getItem();

		return null;
	}
