package brokenarrow.menu.lib.messages;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SendMsg {

	public static void sendMessage(Player player,String msg){
		player.sendMessage(msg);
	}
}
