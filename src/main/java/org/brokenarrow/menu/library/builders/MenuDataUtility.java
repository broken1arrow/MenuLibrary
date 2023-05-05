package org.brokenarrow.menu.library.builders;

import org.brokenarrow.menu.library.MenuButton;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class MenuDataUtility {

	private final Map<Integer, ButtonData> buttons = new HashMap<>();
	private MenuButton fillMenuButton;

	private MenuDataUtility() {
		this(null);
	}

	private MenuDataUtility(final MenuButton fillMenuButton) {
		this.fillMenuButton = fillMenuButton;
	}

	public static MenuDataUtility of() {
		return new MenuDataUtility();
	}

	public static MenuDataUtility of(@Nullable final MenuButton fillMenuButton) {
		return new MenuDataUtility(fillMenuButton);
	}

	public MenuDataUtility putButton(final int slot, @Nonnull final ButtonData buttonData) {
		return putButton(slot, buttonData, this.getFillMenuButton());
	}

	public MenuDataUtility putButton(final int slot, @Nonnull final ButtonData buttonData, @Nullable final MenuButton fillMenuButton) {
		buttons.put(slot, buttonData);
		if (fillMenuButton != null)
			return this.setFillMenuButton(fillMenuButton);
		return this;
	}

	public MenuDataUtility setFillMenuButton(final MenuButton fillMenuButton) {
		this.fillMenuButton = fillMenuButton;
		return this;
	}

	@Nullable
	public MenuButton getSimilarFillMenuButton(@Nullable final MenuButton button) {
		final MenuButton menuButton = this.fillMenuButton;
		if (menuButton == null || button == null) return null;
		if (menuButton.getId() != button.getId()) return null;

		return menuButton;
	}

	@Nullable
	public MenuButton getFillMenuButton() {
		return fillMenuButton;
	}

	@Nullable
	public ButtonData getButton(final int slot) {
		return buttons.get(slot);
	}

	public Map<Integer, ButtonData> getButtons() {
		return Collections.unmodifiableMap(buttons);
	}

	@Nullable
	public MenuButton getMenuButton(final int slot) {
		ButtonData buttonData = this.getButton(slot);
		MenuButton menuButton = null;
		if (buttonData != null) {
			menuButton = buttonData.getMenuButton();
			if (menuButton == null)
				menuButton = getFillMenuButton();
		}
		return menuButton;
	}

	@Override
	public String toString() {
		return "MenuDataUtility{" +
				"buttons=" + buttons +
				", fillMenuButton=" + fillMenuButton +
				'}';
	}
}
