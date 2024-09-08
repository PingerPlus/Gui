package io.pnger.gui.contents;

import io.pnger.gui.GuiInventory;
import io.pnger.gui.event.ClickEvent;
import io.pnger.gui.item.GuiItem;
import io.pnger.gui.item.ItemBuilder;
import io.pnger.gui.pagination.GuiPagination;
import io.pnger.gui.pagination.GuiPaginationImpl;
import io.pnger.gui.template.GuiLayout;
import io.pnger.gui.template.GuiTemplate;
import io.pnger.gui.template.button.ButtonState;
import io.pnger.gui.template.button.GuiButtonTemplate;
import io.pnger.gui.util.Iterables;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiContentsImpl implements GuiContents {
    private final UUID uuid; // UUID of the player opening this inventory
    private final GuiInventory inventory;
    private final Map<Integer, GuiItem> items;
    private final GuiTemplate template;
    private final Map<String, UnaryOperator<ItemStack>> remapper;
    private final Map<String, Consumer<ClickEvent>> clickHandlers;

    private GuiPagination<?> pagination;

    public GuiContentsImpl(GuiInventory inventory, UUID uuid) {
        this.inventory = inventory;
        this.uuid = uuid;
        this.template = inventory.getTemplate();
        this.remapper = new HashMap<>();
        this.items = new HashMap<>();
        this.clickHandlers = new HashMap<>();
        this.pagination = new GuiPaginationImpl<>(this);
    }

    @Override
    public void fillInventory() {
        final GuiLayout layouts = this.template.getLayout();
        for (int row = 0; row < layouts.getRows(); row++) {
            final String current = layouts.getLayout()[row];
            for (int col = 0; col < current.length(); col++) {
                final char symbol = current.charAt(col);
                final GuiButtonTemplate button = this.template.findButtonTemplate(symbol);
                if (button == null) {
                    continue; // It should be material air, if this happens
                }

                final int slot = row * 9 + col;
                final GuiItem item = this.createGuiItem(slot, button);
                this.setItem(slot, item);
            }
        }
    }

    private GuiItem createGuiItem(int slot, GuiButtonTemplate template) {
        final ButtonState defaultState = template.getDefaultState();
        if (defaultState == null) {
            return GuiItem.of(
                new ItemStack(Material.AIR),
                slot,
                "default",
                template
            );
        }

        final ItemStack itemStack = defaultState.getItem().clone();
        return GuiItem.of(
            this.buildGuiItem(template, itemStack),
            slot,
            "default",
            template
        );
    }

    private ItemStack buildGuiItem(GuiButtonTemplate template, ItemStack stack) {
        final String identifier = template.getIdentifier();

        ItemStack currentStack = stack;
        if (identifier != null) {
            final UnaryOperator<ItemStack> remapper = this.remapper.get(identifier);
            if (remapper != null) {
                currentStack = remapper.apply(stack);
            }
        }

        return ItemBuilder.create(currentStack).build();
    }

    @Override
    public GuiTemplate getTemplate() {
        return this.template;
    }

    @Override
    public void remapItems(String identifier, UnaryOperator<ItemBuilder> modifier) {
        final UnaryOperator<ItemStack> mapper = (item) -> modifier.apply(ItemBuilder.create(item)).build();
        this.remapper.put(identifier, mapper);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> GuiPagination<T> pagination() {
        return (GuiPagination<T>) this.pagination;
    }

    @Override
    public <T> void createPagination(GuiPagination<T> pagination) {
        this.pagination = pagination;
    }

    @Override
    public Collection<GuiItem> getItems() {
        return this.items.values();
    }

    @Override
    public void setItem(int slot, GuiItem item) {
        final int row = slot / 9;
        if (row >= this.inventory.getRows()) {
            return;
        }

        this.items.put(slot, item);
        this.update(slot, item);
    }

    @Override
    public List<GuiItem> getItems(String identifier) {
        return Iterables.query(this.items.values(), (item) -> item.template().getIdentifier(), identifier);
    }

    @Override
    public void addClickHandler(String identifier, Consumer<ClickEvent> handler) {
        this.clickHandlers.put(identifier, handler);
    }

    @Override
    public void handleClick(String identifier, ClickEvent event) {
        final Consumer<ClickEvent> handler = this.clickHandlers.get(identifier);
        if (handler == null) {
            return;
        }

        handler.accept(event);
    }

    @Override
    public Optional<GuiItem> getItem(int slot) {
        return Optional.ofNullable(this.items.get(slot));
    }

    @Override
    public Optional<ItemStack> getItemStack(int slot) {
        return this.getItem(slot).map(GuiItem::getItemStack);
    }

    private void update(int slot, GuiItem item) {
        final Player player = Bukkit.getPlayer(this.uuid);
        if (player == null) { // Shouldn't happen but in case
            return;
        }

        if (!this.inventory.getManager().isOpenedInventory(this.uuid, this.inventory)) {
            return;
        }

        final Inventory topInv = player.getOpenInventory().getTopInventory();
        topInv.setItem(slot, item.getItemStack());
        player.updateInventory();
    }
}
