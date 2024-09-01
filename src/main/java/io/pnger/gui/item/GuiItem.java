package io.pnger.gui.item;

import io.pnger.gui.event.ClickEvent;
import io.pnger.gui.slot.GuiSlot;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import org.bukkit.inventory.ItemStack;

public class GuiItem {
    private final ItemStack item;
    private final GuiSlot slot;

    private Consumer<ClickEvent> onClick;
    private boolean dragging;

    private GuiItem(@Nonnull ItemStack item, @Nonnull GuiSlot slot, Consumer<ClickEvent> onClick) {
        this.item = item;
        this.slot = slot;
        this.onClick = onClick;
    }

    public static GuiItem of(@Nonnull ItemStack item, @Nonnull GuiSlot slot, Consumer<ClickEvent> onClick) {
        return new GuiItem(item, slot, onClick);
    }

    public static GuiItem of(@Nonnull ItemStack item, @Nonnull GuiSlot slot) {
        return new GuiItem(item, slot, ($) -> {});
    }

    public static GuiItem of(@Nonnull ItemStack item, int row, int column) {
        return GuiItem.of(item, GuiSlot.of(row, column));
    }

    /**
     * This method returns whether we can drag items from the bottom inventory
     * into this slot.
     * <p>
     * Usually, we never want to allow items from the bottom inventory to be dragged into slots
     * on the top inventory like this, but in special cases like in Trade System plugins, we need
     * an option to do this.
     * </p>
     *
     * @return whether we can drag an item from the bottom inventory to this slot in the top inventory
     */

    public boolean isDragging() {
        return this.dragging;
    }
}
