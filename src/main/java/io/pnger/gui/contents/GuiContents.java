package io.pnger.gui.contents;

import io.pnger.gui.event.ClickEvent;
import io.pnger.gui.item.GuiItem;
import io.pnger.gui.item.GuiItemRemapper;
import io.pnger.gui.item.ItemBuilder;
import io.pnger.gui.pagination.GuiPagination;
import io.pnger.gui.template.GuiTemplate;
import io.pnger.gui.template.button.GuiButtonTemplate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import org.bukkit.inventory.ItemStack;

public interface GuiContents {

    void fillInventory();

    /**
     * This method returns the {@link GuiTemplate} associated with this {@link GuiContents}.
     * <p>
     * The {@link GuiTemplate} contains the layout, button configuration, and general design
     * settings for the GUI, defining how the GUI should be structured and which elements it includes.
     *
     * @return The {@link GuiTemplate} that defines the structure and elements of the GUI.
     */
    GuiTemplate getTemplate();

    /**
     * Finds and returns the {@link GuiButtonTemplate} associated with the specified symbol
     * in the current {@link GuiTemplate}. The symbol is used to identify a specific button
     * configuration within the template.
     *
     * @param symbol The character symbol representing the button to find.
     * @return The {@link GuiButtonTemplate} associated with the specified symbol,
     *         or {@code null} if no button template is found.
     */
    default GuiButtonTemplate findButtonTemplate(char symbol) {
        return this.getTemplate().findButtonTemplate(symbol);
    }

    /**
     * Finds and returns the {@link GuiButtonTemplate} associated with the specified identifier
     * in the current {@link GuiTemplate}. The identifier is used to identify a specific button
     * configuration within the template.
     *
     * @param identifier The string identifier representing the button to find.
     * @return The {@link GuiButtonTemplate} associated with the specified identifier,
     *         or {@code null} if no button template is found.
     */
    default GuiButtonTemplate findButtonTemplate(String identifier) {
        return this.getTemplate().findButtonTemplate(identifier);
    }

    /**
     * Remaps an item and optionally caches the remapping for a specified duration.
     * <p>
     * If caching is enabled (`cacheFor` > 0), the item will be remapped and the result cached
     * for the specified duration in milliseconds. If `cacheFor` is 0, the remapping is applied every tick.
     * If `cacheFor` is negative, the item will be remapped once without caching.
     * </p>
     *
     * @param identifier A unique identifier for the item to be remapped.
     * @param modifier   The {@link UnaryOperator} that modifies the {@link ItemBuilder}.
     * @param cacheFor   The duration in milliseconds to cache the remapping. A value of 0 updates every tick,
     *                   a positive value caches the result for the given duration, and a negative value remaps without caching.
     */
    void remapItems(String identifier, UnaryOperator<ItemBuilder> modifier, long cacheFor);

    /**
     * Remaps an item without caching.
     * <p>
     * This method applies the remapping operation to the item once without any caching,
     * effectively calling {@link #remapItems(String, UnaryOperator, long)} with a cache duration of -1.
     * </p>
     *
     * @param identifier A unique identifier for the item to be remapped.
     * @param modifier   The {@link UnaryOperator} that modifies the {@link ItemBuilder}.
     */
    default void remapItems(String identifier, UnaryOperator<ItemBuilder> modifier) {
        this.remapItems(identifier, modifier, -1);
    }

    /**
     * Remaps an item and updates it every tick.
     * <p>
     * This method applies the remapping operation to the item every tick, effectively calling
     * {@link #remapItems(String, UnaryOperator, long)} with a cache duration of 0.
     * </p>
     *
     * @param identifier A unique identifier for the item to be remapped.
     * @param modifier   The {@link UnaryOperator} that modifies the {@link ItemBuilder}.
     */
    default void remapItemsAndTick(String identifier, UnaryOperator<ItemBuilder> modifier) {
        this.remapItems(identifier, modifier, 0);
    }

    void reloadRemappedItems();

    <T> GuiPagination<T> getPagination();

    <T> void createPagination(GuiPagination<T> pagination);

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

    List<GuiItem> getItems(String identifier);

    default List<Integer> getItemSlots(String identifier) {
        return this.getItems(identifier).stream().map(GuiItem::slot).toList();
    }

    void addClickHandler(String identifier, Consumer<ClickEvent> handler);

    void handleClick(String identifier, ClickEvent event);

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
