package brokenarrow.menu.lib;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class RegisterClass {

	private static Plugin PLUGIN;

	public RegisterClass(Plugin plugin) {
		PLUGIN = plugin;
		Bukkit.getPluginManager().registerEvents(new  MenuHolderListener(),plugin);
	}
	public static Plugin getPLUGIN() {
		return PLUGIN;
	}
}
