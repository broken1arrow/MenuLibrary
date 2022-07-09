package org.brokenarrow.menu.library.NMS;

import org.broken.lib.rbg.TextTranslator;
import org.brokenarrow.menu.library.utility.ServerVersion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class UpdateTittleContainers {

	private static Class<?> packetclass;
	private static Method handle;
	private static Field playerConnection;
	private static Class<?> packetConnectionClass;
	private static Class<?> chatBaseCompenent;
	private static Class<?> chatCompenentSubClass;
	private static Class<?> containersClass;
	private static Class<?> containerClass;
	private static Constructor<?> packetConstructor;
	private static final Map<Integer, String> containerFieldname = new HashMap<>();
	private static NmsData nmsData;

	public static void update(Player p, String title) {

		try {
			if (p != null) {
				Inventory inventory = p.getOpenInventory().getTopInventory();
				if (ServerVersion.atLeast(ServerVersion.v1_19)) {
					convertFieldNames("9;a", "18;b", "27;c", "36;d", "45;e", "54;f", "5;p");
					if (nmsData == null)
						nmsData = new NmsData("bU", "j",
								"a", "a");
					loadNmsClasses1_18();
					updateInventory(p, title, inventory, nmsData);
				} else if (ServerVersion.atLeast(ServerVersion.v1_18_0)) {
					convertFieldNames("9;a", "18;b", "27;c", "36;d", "45;e", "54;f", "5;p");
					if (nmsData == null)
						nmsData = new NmsData(ServerVersion.atLeast(ServerVersion.v1_18_2) ? "bV" : "bW", "j",
								"a", "a");
					loadNmsClasses1_18();
					updateInventory(p, title, inventory, nmsData);
				} else if (ServerVersion.equals(ServerVersion.v1_17)) {
					convertFieldNames("9;a", "18;b", "27;c", "36;d", "45;e", "54;f", "5;p");
					if (nmsData == null)
						nmsData = new NmsData("bV", "j", "sendPacket", "initMenu");
					loadNmsClasses1_17();
					updateInventory(p, title, inventory, nmsData);
				} else if (ServerVersion.olderThan(ServerVersion.v1_17)) {
					try {
						convertFieldNames("9;1", "18;2", "27;3", "36;4", "45;5", "54;6", "5;HOPPER");
						if (nmsData == null)
							nmsData = new NmsData("activeContainer", "windowId",
									"sendPacket", "updateInventory");
						loadNmsClasses();
						updateInventory(p, title, inventory, nmsData);
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

	private static void updateInventory(Player p, String title, Inventory inventory, NmsData nmsData) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException, InstantiationException {
		int inventorySize = inventory.getSize();
		boolean isOlder = ServerVersion.olderThan(ServerVersion.v1_17);
		Object player = p.getClass().getMethod("getHandle").invoke(p);
		// inside net.minecraft.world.entity.player class EntityHuman do you have this field
		Object activeContainer = player.getClass().getField(nmsData.getContanerField()).get(player);
		// inside net.minecraft.world.inventory class Container do you have this field older version is it j
		Object windowId = activeContainer.getClass().getField(nmsData.getWindowId()).get(activeContainer);

		Method declaredMethodChat;
		Object inventoryTittle;
		Object methods;

		String fieldName = getContainerFieldname().get(inventorySize);
		if (fieldName == null || fieldName.isEmpty()) {
			if (isOlder)
				fieldName = "GENERIC_9X6";
			else
				fieldName = "f";
		} else if (isOlder) {
			if (inventorySize % 9 == 0)
				fieldName = "GENERIC_9X" + fieldName;
		}
		if (ServerVersion.newerThan(ServerVersion.v1_13)) {

			declaredMethodChat = chatCompenentSubClass.getMethod("a", String.class);
			inventoryTittle = declaredMethodChat.invoke(null, TextTranslator.toComponent(title));
			Object inventoryType = containersClass.getField(fieldName).get(null);

			methods = packetConstructor.newInstance(windowId, inventoryType, inventoryTittle);
		} else {

			declaredMethodChat = chatCompenentSubClass.getMethod(ServerVersion.newerThan(ServerVersion.v1_9) ? "b" : "a", String.class);
			inventoryTittle = declaredMethodChat.invoke(null, "'" + TextTranslator.toSpigotFormat(title) + "'");

			methods = packetConstructor.newInstance(windowId, "minecraft:" + inventory.getType().name().toLowerCase(), inventoryTittle, inventorySize);
		}

		Object handles = handle.invoke(p);
		Object playerconect = playerConnection.get(handles);
		// net.minecraft.server.network.PlayerConnection
		Method packet1 = packetConnectionClass.getMethod(nmsData.getSendPacket(), packetclass);

		packet1.invoke(playerconect, methods);
		// inside net.minecraft.world.inventory.Container do you have method a(Container container)
		player.getClass().getMethod(nmsData.getUpdateInventory(), containerClass).invoke(player, activeContainer);

	}

	public static Map<Integer, String> getContainerFieldname() {
		return containerFieldname;
	}

	private static String versionCheckNms(String clazzName) {

		return "net.minecraft.server." + Bukkit.getServer().getClass().toGenericString().split("\\.")[3] + "." + clazzName;
	}

	private static String versionCheckBukkit(String clazzName) {

		return "org.bukkit.craftbukkit." + Bukkit.getServer().getClass().toGenericString().split("\\.")[3] + "." + clazzName;
	}


	/**
	 * Use the method like this 9;a ("9" is inventory size and "a" is the field name).
	 * Is used to get the field for diffrent inventorys in nms class.
	 *
	 * @param fieldNames set the name to get right container inventory.
	 */
	private static void convertFieldNames(String... fieldNames) {
		if (fieldNames.length == 0) return;
		if (!containerFieldname.isEmpty()) return;

		for (String name : fieldNames) {
			String[] splited = name.split(";");
			if (splited.length == 2) {
				containerFieldname.put(Integer.valueOf(splited[0]), splited[1]);
			}
		}
	}

	private static class NmsData {

		private final String contanerField;
		private final String windowId;
		private final String sendPacket;
		private final String updateInventory;


		public NmsData(String contanerField, String windowId, String sendPacket, String updateInventory) {
			this.contanerField = contanerField;
			this.windowId = windowId;
			this.sendPacket = sendPacket;
			this.updateInventory = updateInventory;

		}

		/**
		 * inside net.minecraft.world.entity.player.EntityHuman do you have this field.
		 *
		 * @return field name.
		 */
		public String getContanerField() {
			return contanerField;
		}

		/**
		 * This is uesd to get current id of a inventory (is intriger)
		 * <p>
		 * The field in this class net.minecraft.world.entity.player.EntityHuman.
		 *
		 * @return the field name.
		 */
		public String getWindowId() {
			return windowId;
		}

		/**
		 * take the method from this class net.minecraft.server.network.PlayerConnection .
		 * This method a(Packet packet) older versions is it sendPacket(Packet packet).
		 *
		 * @return method name.
		 */
		public String getSendPacket() {
			return sendPacket;
		}

		/**
		 * take the field from this class net.minecraft.world.inventory.Container .
		 * This method a(Container container) older versions is it initMenu or updateInventory.
		 *
		 * @return method name.
		 */
		public String getUpdateInventory() {
			return updateInventory;
		}
	}

}
