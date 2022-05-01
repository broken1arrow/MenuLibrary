package org.brokenarrow.menu.library.NMS;

import org.broken.lib.rbg.TextTranslator;
import org.brokenarrow.menu.library.utility.ServerVersion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

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


	public static void update(Player p, String title) {

		try {
			if (p != null) {
				Inventory inventory = p.getOpenInventory().getTopInventory();
				int size = inventory.getSize();
				if (ServerVersion.equals(ServerVersion.v1_17)) {
					loadNmsClasses1_17();
					updateInventory1_17(p, title, inventory, size);

				} else if (ServerVersion.newerThan(ServerVersion.v1_17)) {
					loadNmsClasses1_18();
					updateInventory1_18(p, title, inventory, size);
				} else if (ServerVersion.olderThan(ServerVersion.v1_17)) {
					try {
						loadNmsClasses();
						updateInventory1_16AndLower(p, title, inventory, size);
					} catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InstantiationException | InvocationTargetException e) {
						e.printStackTrace();
					}
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
			if (ServerVersion.newerThan(ServerVersion.v1_13))
				containersClass = Class.forName(versionCheckNms("Containers"));
			else
				containersClass = String.class;
		if (containerClass == null)
			containerClass = Class.forName(versionCheckNms("Container"));
		if (chatCompenentSubClass == null)
			chatCompenentSubClass = Class.forName(versionCheckNms("IChatBaseComponent$ChatSerializer"));
		if (packetConstructor == null)
			if (ServerVersion.newerThan(ServerVersion.v1_13))
				packetConstructor = Class.forName(versionCheckNms("PacketPlayOutOpenWindow")).getConstructor(int.class, containersClass, chatBaseCompenent);
			else
				packetConstructor = Class.forName(versionCheckNms("PacketPlayOutOpenWindow")).getConstructor(int.class, containersClass, chatBaseCompenent, int.class);
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

	private static void updateInventory1_16AndLower(Player p, String title, Inventory inventory, int size) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException, InstantiationException {

		Object player = p.getClass().getMethod("getHandle").invoke(p);
		Object activeContainer = player.getClass().getField("activeContainer").get(player);
		Object windowId = activeContainer.getClass().getField("windowId").get(activeContainer);
		//todo add this instead of use arguments in method p.getOpenInventory().getTopInventory().getType()
		Object methods;
		if (ServerVersion.newerThan(ServerVersion.v1_13)) {
			Object inventoryType = null;
			Method declaredMethodChat = chatCompenentSubClass.getMethod("a", String.class);
			Object inventoryTittle = declaredMethodChat.invoke(null, TextTranslator.toComponent(title));

			if (inventory.getType() == InventoryType.HOPPER)
				inventoryType = containersClass.getField("HOPPER").get(null);
			if (inventory.getType() == InventoryType.CHEST) {
				if (size % 9 == 0)
					inventoryType = containersClass.getField("GENERIC_9X" + size / 9).get(null);
				else
					inventoryType = containersClass.getField("GENERIC_9X5").get(null);
			}

			methods = packetConstructor.newInstance(windowId, inventoryType, inventoryTittle);
		} else {
			Method declaredMethodChat = chatCompenentSubClass.getMethod(ServerVersion.newerThan(ServerVersion.v1_9) ? "b" : "a", String.class);
			Object inventoryTittle = declaredMethodChat.invoke(null, "'" + TextTranslator.toSpigotFormat(title) + "'");

			methods = packetConstructor.newInstance(windowId, "minecraft:" + inventory.getType().name().toLowerCase(), inventoryTittle, size);
		}

		Object handles = handle.invoke(p);
		Object playerconect = playerConnection.get(handles);
		Method packet1 = packetConnectionClass.getMethod("sendPacket", packetclass);

		packet1.invoke(playerconect, methods);
		player.getClass().getMethod("updateInventory", containerClass).invoke(player, activeContainer);

	}

	private static void updateInventory1_17(Player p, String title, Inventory inventory, int inventorySize) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException, InstantiationException {

		Object player = p.getClass().getMethod("getHandle").invoke(p);
		Object activeContainer = player.getClass().getField("bV").get(player);
		Object windowId = activeContainer.getClass().getField("j").get(activeContainer);

		Method declaredMethodChat = chatCompenentSubClass.getMethod("a", String.class);
		Object inventoryTittle = declaredMethodChat.invoke(null, TextTranslator.toComponent(title));

		Object inventoryType;
		String fieldName = "f";
		if (inventory.getType() == InventoryType.HOPPER)
			fieldName = "p";
		if (inventory.getType() == InventoryType.CHEST)
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

	private static void updateInventory1_18(Player p, String title, Inventory inventory, int inventorySize) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException, InstantiationException {

		Object player = p.getClass().getMethod("getHandle").invoke(p);
		Object activeContainer = player.getClass().getField("bW").get(player);
		Object windowId = activeContainer.getClass().getField("j").get(activeContainer);

		Method declaredMethodChat = chatCompenentSubClass.getMethod("a", String.class);
		Object inventoryTittle = declaredMethodChat.invoke(null, TextTranslator.toComponent(title));

		Object inventoryType;
		String fieldName = "f";

		if (inventory.getType() == InventoryType.HOPPER)
			fieldName = "p";
		if (inventory.getType() == InventoryType.CHEST)
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

		return "net.minecraft.server." + Bukkit.getServer().getClass().toGenericString().split("\\.")[3] + "." + clazzName;
	}

	private static String versionCheckBukkit(String clazzName) {

		return "org.bukkit.craftbukkit." + Bukkit.getServer().getClass().toGenericString().split("\\.")[3] + "." + clazzName;
	}

}
