package io.pnger.gui.event;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * This class is essentially a wrapper class for the {@link org.bukkit.event.inventory.InventoryClickEvent}
 * <p>
 * Due to how we process click consumers, we use this class to minimize any casting needed with
 * the {@link InventoryClickEvent#getWhoClicked()}, and use more functional code.
 * </p>
 */

public class ClickEvent {
    private final InventoryClickEvent source;

    public ClickEvent(InventoryClickEvent source) {
        this.source = source;
    }

    public Player getPlayer() {
        return (Player) this.source.getWhoClicked();
    }

    public ClickType getClick() {
        return this.source.getClick();
    }

    public boolean isLeftClick() {
        return this.source.isLeftClick();
    }

    public boolean isRightClick() {
        return this.source.isRightClick();
    }

    public boolean isShiftClick() {
        return this.source.isShiftClick();
    }

    public boolean isLeftAndShiftClick() {
        return this.isLeftClick() && this.isShiftClick();
    }

    public boolean isRightAndShiftClick() {
        return this.isRightClick() && this.isShiftClick();
    }

    public InventoryClickEvent getSource() {
        return this.source;
    }
}
