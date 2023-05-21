package org.brokenarrow.menu.library.builders;

import org.brokenarrow.menu.library.MenuButton;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class MenuDataUtility {

	private final Map<Integer, ButtonData> buttons = new HashMap<>();
	private Map<Integer, MenuButton> fillMenuButtons;
	private MenuButton fillMenuButton;

	public static MenuDataUtility of() {
		return new MenuDataUtility();
	}

	public MenuDataUtility putButton(final int slot, @Nonnull final ButtonData buttonData) {
		return putButton(slot, buttonData, this.getFillMenuButton());
	}

	public MenuDataUtility putButton(final int slot, @Nonnull final ButtonData buttonData, @Nullable final MenuButton fillMenuButton) {
		buttons.put(slot, buttonData);
		if (fillMenuButton != null) {
			if (this.getFillMenuButton() != null && this.getFillMenuButton().getId() != fillMenuButton.getId()) {
				if (this.fillMenuButtons == null)
					this.fillMenuButtons = new HashMap<>();
				this.fillMenuButtons.put(slot, fillMenuButton);
			} else
				return this.setFillMenuButton(fillMenuButton);
		}
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
	public MenuButton getFillMenuButton(@Nonnull MenuButton menuButton) {
		if (this.getFillMenuButton() != null && this.getFillMenuButton().getId() == menuButton.getId()) {
			return this.getFillMenuButton();
		}
		if (fillMenuButtons != null) {
			for (MenuButton button : getFillMenuButtons().values())
				if (button.getId() == menuButton.getId())
					return button;
		}
		return null;
	}

	@Nullable
	public MenuButton getFillMenuButton(int slot) {
		MenuButton menuButton = null;
		if (fillMenuButtons != null)
			menuButton = fillMenuButtons.get(slot);
		if (menuButton == null)
			menuButton = this.getFillMenuButton();
		return menuButton;
	}

	@Nullable
	private MenuButton getFillMenuButton() {
		return fillMenuButton;
	}

	@Nullable
	public ButtonData getButton(final int slot) {
		return buttons.get(slot);
	}

	public Map<Integer, ButtonData> getButtons() {
		return Collections.unmodifiableMap(buttons);
	}

	public Map<Integer, MenuButton> getFillMenuButtons() {
		if (fillMenuButtons == null)
			return new HashMap<>();
		return Collections.unmodifiableMap(fillMenuButtons);
	}

	@Nullable
	public MenuButton getMenuButton(final int slot) {
		ButtonData buttonData = this.getButton(slot);
		MenuButton menuButton = null;
		if (buttonData != null) {
			menuButton = buttonData.getMenuButton();
			if (menuButton == null)
				menuButton = getFillMenuButton(slot);
		}
		return menuButton;
	}

	@Override
	public String toString() {
		return "MenuDataUtility{" +
				"buttons=" + buttons +
				", fillMenuButton=" + fillMenuButton +
				", fillMenuButtons=" + fillMenuButtons +
				'}';
	}
}
