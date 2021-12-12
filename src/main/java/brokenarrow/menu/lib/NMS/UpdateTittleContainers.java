package brokenarrow.menu.lib.NMS;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UpdateTittleContainers {

	static Class<?> packetclass;
	static Method handle;
	static Field playerConnection;
	static Class<?> packetConnectionClass;
	static Class<?> chatBaseCompenent;
	static Class<?> chatCompenentSubClass;
	static Class<?> containersClass;
	static Class<?> containerClass;
	static Constructor<?> packetConstructor;


	public static void update(Player p, String title, Material container, int inventorySize) {

		try {
			if (p != null)
				if (Bukkit.getServer().getClass().getPackageName().split("\\.")[3].startsWith("v1_17")) {
					loadNmsClasses1_17();
					updateInventory1_17(p, title, container, inventorySize);

				} else if (Bukkit.getServer().getClass().getPackageName().split("\\.")[3].startsWith("v1_18")) {
					loadNmsClasses1_18();
					updateInventory1_18(p, title, container, inventorySize);
				} else {
					try {
						loadNmsClasses();
						updateInventory1_16AndLower(p, title, container, inventorySize);
					} catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InstantiationException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
		} catch (NoSuchFieldException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | InstantiationException e) {
			e.printStackTrace();
		}
	}


	private static void loadNmsClasses() throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException {

		if (packetclass == null)
			packetclass = Class.forName(versionCheckNms("Packet"));
		if (handle == null)
			handle = Class.forName(versionCheckBukkit("entity.CraftPlayer")).getMethod("getHandle");
		if (playerConnection == null)
			playerConnection = Class.forName(versionCheckNms("EntityPlayer")).getField("playerConnection");
		if (packetConnectionClass == null)
			packetConnectionClass = Class.forName(versionCheckNms("PlayerConnection"));
		if (chatBaseCompenent == null)
			chatBaseCompenent = Class.forName(versionCheckNms("IChatBaseComponent"));
		if (containersClass == null)
			containersClass = Class.forName(versionCheckNms("Containers"));
		if (containerClass == null)
			containerClass = Class.forName(versionCheckNms("Container"));
		if (chatCompenentSubClass == null)
			chatCompenentSubClass = Class.forName(versionCheckNms("IChatBaseComponent$ChatSerializer"));
		if (packetConstructor == null)
			packetConstructor = Class.forName(versionCheckNms("PacketPlayOutOpenWindow")).getConstructor(int.class, containersClass, chatBaseCompenent);
	}

	private static void loadNmsClasses1_17() throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException {

		if (packetclass == null)
			packetclass = Class.forName("net.minecraft.network.protocol.Packet");
		if (handle == null)
			handle = Class.forName(versionCheckBukkit("entity.CraftPlayer")).getMethod("getHandle");
		if (playerConnection == null)
			playerConnection = Class.forName("net.minecraft.server.level.EntityPlayer").getField("b");
		if (packetConnectionClass == null)
			packetConnectionClass = Class.forName("net.minecraft.server.network.PlayerConnection");
		if (chatBaseCompenent == null)
			chatBaseCompenent = Class.forName("net.minecraft.network.chat.IChatBaseComponent");
		if (containersClass == null)
			containersClass = Class.forName("net.minecraft.world.inventory.Containers");
		if (containerClass == null)
			containerClass = Class.forName("net.minecraft.world.inventory.Container");
		if (chatCompenentSubClass == null)
			chatCompenentSubClass = Class.forName("net.minecraft.network.chat.IChatBaseComponent$ChatSerializer");
		if (packetConstructor == null)
			packetConstructor = Class.forName("net.minecraft.network.protocol.game.PacketPlayOutOpenWindow").getConstructor(int.class, containersClass, chatBaseCompenent);
	}

	private static void loadNmsClasses1_18() throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException {

		if (packetclass == null)
			packetclass = Class.forName("net.minecraft.network.protocol.Packet");
		if (handle == null)
			handle = Class.forName(versionCheckBukkit("entity.CraftPlayer")).getMethod("getHandle");
		if (playerConnection == null)
			playerConnection = Class.forName("net.minecraft.server.level.EntityPlayer").getField("b");
		if (packetConnectionClass == null)
			packetConnectionClass = Class.forName("net.minecraft.server.network.PlayerConnection");
		if (chatBaseCompenent == null)
			chatBaseCompenent = Class.forName("net.minecraft.network.chat.IChatBaseComponent");
		if (containersClass == null)
			containersClass = Class.forName("net.minecraft.world.inventory.Containers");
		if (containerClass == null)
			containerClass = Class.forName("net.minecraft.world.inventory.Container");
		if (chatCompenentSubClass == null)
			chatCompenentSubClass = Class.forName("net.minecraft.network.chat.IChatBaseComponent$ChatSerializer");
		if (packetConstructor == null)
			packetConstructor = Class.forName("net.minecraft.network.protocol.game.PacketPlayOutOpenWindow").getConstructor(int.class, containersClass, chatBaseCompenent);
	}

	private static void updateInventory1_16AndLower(Player p, String title, Material container, int inventorySize) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException, InstantiationException {

		Object player = p.getClass().getMethod("getHandle").invoke(p);
		Object activeContainer = player.getClass().getField("activeContainer").get(player);
		Object windowId = activeContainer.getClass().getField("windowId").get(activeContainer);

		Method declaredMethodChat = chatCompenentSubClass.getMethod("b", String.class);
		Object inventoryTittle = declaredMethodChat.invoke(null, "'" + title + "'");


		Object inventoryType = null;
		if (container == Material.HOPPER)
			inventoryType = containersClass.getField("HOPPER").get(null);
		if (container == Material.CHEST)
			if (inventorySize % 9 == 0)
				inventoryType = containersClass.getField("GENERIC_9X" + inventorySize / 9).get(null);
			else
				inventoryType = containersClass.getField("GENERIC_9X3").get(null);

		Object methods = packetConstructor.newInstance(windowId, inventoryType, inventoryTittle);

		Object handles = handle.invoke(p);
		Object playerconect = playerConnection.get(handles);
		Method packet1 = packetConnectionClass.getMethod("sendPacket", packetclass);

		packet1.invoke(playerconect, methods);
		player.getClass().getMethod("updateInventory", containerClass).invoke(player, activeContainer);

	}

	private static void updateInventory1_17(Player p, String title, Material container, int inventorySize) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException, InstantiationException {


		Object player = p.getClass().getMethod("getHandle").invoke(p);
		Object activeContainer = player.getClass().getField("bV").get(player);
		Object windowId = activeContainer.getClass().getField("j").get(activeContainer);

		Method declaredMethodChat = chatCompenentSubClass.getMethod("b", String.class);
		Object inventoryTittle = declaredMethodChat.invoke(null, "'" + title + "'");


		Object inventoryType;
		String fieldName = "c";
		if (container == Material.HOPPER)
			fieldName = "p";
		if (container == Material.CHEST)
			if (inventorySize / 9 == 1)
				fieldName = "a";
			else if (inventorySize / 9 == 2)
				fieldName = "b";
			else if (inventorySize / 9 == 3)
				fieldName = "c";
			else if (inventorySize / 9 == 4)
				fieldName = "d";
			else if (inventorySize / 9 == 5)
				fieldName = "e";
			else if (inventorySize / 9 == 6)
				fieldName = "f";
			else
				fieldName = "c";


		inventoryType = containersClass.getField(fieldName).get(null);
		Object methods = packetConstructor.newInstance(windowId, inventoryType, inventoryTittle);

		Object handles = handle.invoke(p);
		Object playerconect = playerConnection.get(handles);
		Method packet1 = packetConnectionClass.getMethod("sendPacket", packetclass);

		packet1.invoke(playerconect, methods);
		player.getClass().getMethod("initMenu", containerClass).invoke(player, activeContainer);

	}

	private static void updateInventory1_18(Player p, String title, Material container, int inventorySize) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException, InstantiationException {
		
		Object player = p.getClass().getMethod("getHandle").invoke(p);
		Object activeContainer = player.getClass().getField("bW").get(player);
		Object windowId = activeContainer.getClass().getField("j").get(activeContainer);

		Method declaredMethodChat = chatCompenentSubClass.getMethod("b", String.class);
		Object inventoryTittle = declaredMethodChat.invoke(null, "'" + title + "'");

		Object inventoryType;
		String fieldName = "c";
		if (container == Material.HOPPER)
			fieldName = "p";
		if (container == Material.CHEST)
			if (inventorySize / 9 == 1)
				fieldName = "a";
			else if (inventorySize / 9 == 2)
				fieldName = "b";
			else if (inventorySize / 9 == 3)
				fieldName = "c";
			else if (inventorySize / 9 == 4)
				fieldName = "d";
			else if (inventorySize / 9 == 5)
				fieldName = "e";
			else if (inventorySize / 9 == 6)
				fieldName = "f";
			else
				fieldName = "c";


		inventoryType = containersClass.getField(fieldName).get(null);
		Object methods = packetConstructor.newInstance(windowId, inventoryType, inventoryTittle);

		Object handles = handle.invoke(p);
		Object playerconect = playerConnection.get(handles);
		Method packet1 = packetConnectionClass.getMethod("a", packetclass);

		packet1.invoke(playerconect, methods);
		player.getClass().getMethod("a", containerClass).invoke(player, activeContainer);

	}

	private static String versionCheckNms(String clazzName) {

		return "net.minecraft.server." + Bukkit.getServer().getClass().getPackageName().split("\\.")[3] + "." + clazzName;
	}

	private static String versionCheckBukkit(String clazzName) {

		return "org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getPackageName().split("\\.")[3] + "." + clazzName;
	}

}
