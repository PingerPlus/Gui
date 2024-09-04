package io.pnger.gui.contents;

import io.pnger.gui.GuiInventory;
import io.pnger.gui.item.GuiItem;
import io.pnger.gui.item.ItemBuilder;
import io.pnger.gui.template.GuiLayout;
import io.pnger.gui.template.GuiTemplate;
import io.pnger.gui.template.button.GuiButtonTemplate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.UnaryOperator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiContentsImpl implements GuiContents {
    private final UUID uuid; // UUID of the player opening this inventory
    private final GuiInventory inventory;
    private final Map<Integer, GuiItem> items;
    private final GuiTemplate template;
    private final Map<String, UnaryOperator<ItemStack>> remapper;

    public GuiContentsImpl(GuiInventory inventory, UUID uuid, GuiTemplate template) {
        this.inventory = inventory;
        this.uuid = uuid;
        this.template = template;
        this.remapper = new HashMap<>();
        this.items = new HashMap<>();
    }

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

                final ItemBuilder defaultState = button.getDefaultState();
                if (defaultState == null) {
                    continue;
                }

                final GuiItem item = GuiItem.of(defaultState.build());
                contents.setItem(row, col, item);
            }
        }
    }

    public GuiItem createGuiItem(int slot, GuiButtonTemplate template) {
        final ItemStack defaultState = template.getDefaultState();
        if (defaultState == null) {
            return null;
        }

        final ItemStack copyItem = defaultState.clone();
        return GuiItem.of(
            this.buildGuiItem()
        )
    }

    public ItemStack buildGuiItem(GuiButtonTemplate template, ItemStack stack) {
        final String identifier = template.getIdentifier();

        ItemStack currentStack = stack;
        if (identifier != null) {
            final UnaryOperator<ItemStack> remapper = this.remapper.get(identifier);
            if (remapper != null) {
                currentStack = remapper.apply(stack);
            }
        }

        // TODO: Fix th
        return ItemBuilder.create().build();
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

        final Inventory topInv = player.getOpenInventory().getTopInventory();
        topInv.setItem(slot, item.getItemStack());
    }
}
