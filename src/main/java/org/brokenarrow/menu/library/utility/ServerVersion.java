package org.brokenarrow.menu.library.utility;

import org.bukkit.plugin.Plugin;

public enum ServerVersion {
	v1_19((float) 19.0),
	v1_18_2((float) 18.2),
	v1_18_1((float) 18.1),
	v1_18_0((float) 18.0),
	v1_17((float) 17.0),
	v1_16((float) 16.0),
	v1_15((float) 15.0),
	v1_14((float) 14.0),
	v1_13((float) 13.0),
	v1_12((float) 12.0),
	v1_11((float) 11.0),
	v1_10((float) 10.0),
	v1_9((float) 9.0),
	v1_8((float) 8.0),
	v1_7((float) 7.0),
	v1_6((float) 6.0),
	v1_5((float) 5.0),
	v1_4((float) 4.0),
	v1_3_AND_BELOW((float) 3.0);

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
		String[] strings = plugin.getServer().getBukkitVersion().split("\\.");
		String firstNumber;
		String secondNumber;
		String firstString = strings[1];
		if (firstString.contains("-")) {
			firstNumber = firstString.substring(0, firstString.lastIndexOf("-"));

			secondNumber = firstString.substring(firstString.lastIndexOf("-") + 1);
			int index = secondNumber.toUpperCase().indexOf("R");
			if (index >= 0)
				secondNumber = secondNumber.substring(index + 1);
		} else {
			String secondString = strings[2];
			firstNumber = firstString;
			secondNumber = secondString.substring(0, secondString.lastIndexOf("-"));
		}
		float version = Float.parseFloat(firstNumber + "." + secondNumber);
		if (version < 18)
			currentServerVersion = Math.floor(version);
		else
			currentServerVersion = version;
	}

	public double getVersion() {
		return version;
	}

	public static double getCurrentServerVersion() {
		return currentServerVersion;
	}

	ServerVersion(final float version) {
		this.version = version;

	}
}