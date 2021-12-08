package brokenarrow.menu.lib.messages;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SendMsg {

	private static final Pattern HEX_PATTERN = Pattern.compile("(?<!\\\\\\\\)(#[a-fA-F0-9]{6})");
	private static String blacklistMessage;
	private static String dublicatedMessage;

	/**
	 * Set message for when player have added item some are blacklisted.
	 * Suport both hex and & colorcodes and have this placeholders:
	 *
	 * {0} = item type
	 *
	 * @param blacklistMessage set a message.
	 */

	public static void setBlacklistMessage(String blacklistMessage) {
		SendMsg.blacklistMessage = blacklistMessage;
	}

	/**
	 * Set message for when player have added item some are dublicated.
	 * Suport both hex and & colorcodes and have this placeholders:
	 *
	 * {0} = item type
	 * {1} = amount of stacks
	 * {2} = item amount
	 *
	 * @param dublicatedMessage set a message.
	 */

	public static void setDublicatedMessage(String dublicatedMessage) {
		SendMsg.dublicatedMessage = dublicatedMessage;
	}

	public static void sendMessage(Player player, String msg) {
		player.sendMessage(msg);
	}

	public static void sendBlacklistMessage(Player player, Object... placeholders) {
		String message;
		if (blacklistMessage == null)
			message = "&fthis item&6 {0}&f are blacklisted";
		else
			message = blacklistMessage;

		player.sendMessage(translateHexCodes( translatePlaceholders(message, placeholders)));
	}

	public static void sendDublicatedMessage(Player player, Object... placeholders) {
		String message;
		if (dublicatedMessage == null)
			message = "&fYou can't add more than one &6 {0} &ftype, You have added &4{1}&f extra itemstack. You get back &6 {2}&f items.";
		else
			message = dublicatedMessage;

		player.sendMessage(translateHexCodes(translatePlaceholders(message, placeholders)));
	}

	public static String translatePlaceholders(String rawText, Object... placeholders) {
		for (int i = 0; i < placeholders.length; i++) {
			rawText = rawText.replace("{" + i + "}", placeholders[i] != null ? placeholders[i].toString() : "");
		}
		return rawText;
	}

	public static String translateHexCodes(String textToTranslate) {
		String[] hexSplitedToFitRBG = new String[0];
		Matcher matcher = HEX_PATTERN.matcher(textToTranslate);
		StringBuilder buffer = new StringBuilder();

		if (matcher.find()) {
			hexSplitedToFitRBG = matcher.group(1).split("");
			if (!(hexSplitedToFitRBG.length == 7))
				System.out.println("you has wrongly set up the hex color it shall be 6 in length");
		}
		if (hexSplitedToFitRBG.length == 7)
			return ChatColor.translateAlternateColorCodes('&', "&x&" + hexSplitedToFitRBG[1] + "&" + hexSplitedToFitRBG[2] + "&" + hexSplitedToFitRBG[3] + "&" + hexSplitedToFitRBG[4] + "&" + hexSplitedToFitRBG[5] + "&" + hexSplitedToFitRBG[6] + textToTranslate.replace("<" + matcher.group(1) + ">", ""));

		return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString().replace("<", "").replace(">", ""));
	}
}
