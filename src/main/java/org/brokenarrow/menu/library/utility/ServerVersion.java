package org.brokenarrow.menu.library.utility;

import org.bukkit.plugin.Plugin;

public enum ServerVersion {
	v1_18_2(18.2),
	v1_18_1(18.1),
	v1_18_0(18.0),
	v1_17(17.0),
	v1_16(16.0),
	v1_15(15.0),
	v1_14(14.0),
	v1_13(13.0),
	v1_12(12.0),
	v1_11(11.0),
	v1_10(10.0),
	v1_9(9.0),
	v1_8(8.0),
	v1_7(7.0),
	v1_6(6.0),
	v1_5(5.0),
	v1_4(4.0),
	v1_3_AND_BELOW(3.0);

	private final double version;
	private static double currentServerVersion;

	public static boolean equals(ServerVersion version) {
		return serverVersion(version) == 0;
	}

	public static boolean atLeast(ServerVersion version) {
		return equals(version) || newerThan(version);
	}

	public static boolean newerThan(ServerVersion version) {
		return serverVersion(version) > 0;
	}

	public static boolean olderThan(ServerVersion version) {
		return serverVersion(version) < 0;
	}

	public static double serverVersion(ServerVersion version) {
		return currentServerVersion - version.getVersion();
	}

	public static void setServerVersion(Plugin plugin) {
		String[] version = plugin.getServer().getBukkitVersion().split("\\.");
		if (version[1].startsWith("18"))
			currentServerVersion = Double.parseDouble(version[1] + "." + version[2].substring(0, version[2].lastIndexOf("-")));
		else
			currentServerVersion = Double.parseDouble(version[1]);
	}

	public double getVersion() {
		return version;
	}

	public static double getCurrentServerVersion() {
		return currentServerVersion;
	}

	ServerVersion(double version) {
		this.version = version;

	}
}