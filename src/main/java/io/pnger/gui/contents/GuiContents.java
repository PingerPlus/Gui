package io.pnger.gui.contents;

import io.pnger.gui.item.GuiItem;
import java.util.Collection;
import java.util.Optional;
import org.bukkit.inventory.ItemStack;

public interface GuiContents {

    /**
     * This method returns all items assigned to this current inventory,
     * but limited only to the page opened.
     * <p>
     * It will not return items not present in this page.
     * </p>
     *
     * @return the items but limited to the current page
     */

    Collection<GuiItem> getItems();

    /**
     * This method adds the item to the specific slot in the inventory.
     * <p>
     * Do note that after the inventory has been built, this method should not
     * be used without caution. We only want to really change an item using this call,
     * if we actually need to handle the data from the {@link GuiItem} in a different manner.
     *
     * @param slot the slot the put the item in
     * @param item the item to put in this slot
     */

    void setItem(int slot, GuiItem item);

    Optional<GuiItem> getItem(int slot);

    default Optional<GuiItem> getItem(int row, int col) {
        return this.getItem(row * 9 + col);
    }

    Optional<ItemStack> getItemStack(int slot);

    default Optional<ItemStack> getItemStack(int row, int col) {
        return this.getItemStack(row * 9 + col);
    }

    default void setItem(int row, int col, GuiItem item) {
        this.setItem(row * 9 + col, item);
    }

}
