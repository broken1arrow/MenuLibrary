import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.Plugin;

public class MenuListener implements Listener {


	public MenuListener(Plugin plugin) {
		this.plugin = plugin;
	}

	private final Plugin plugin;

	@EventHandler(priority = EventPriority.LOW)
	public void onMenuCloseEvent(InventoryCloseEvent event) {
		MenuUtiliListener menuUtiliListener = new MenuUtiliListener(this.plugin);
		menuUtiliListener.onMenuClose(event);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onMenuClickEvent(InventoryClickEvent event) {
		MenuUtiliListener menuUtiliListener = new MenuUtiliListener(this.plugin);
		menuUtiliListener.onMenuClicking(event);
	}


}
