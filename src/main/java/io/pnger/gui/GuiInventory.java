package io.pnger.gui;

import io.pnger.gui.contents.GuiContents;
import io.pnger.gui.contents.GuiContentsImpl;
import io.pnger.gui.item.GuiItem;
import io.pnger.gui.provider.GuiProvider;
import io.pnger.gui.template.GuiTemplate;
import java.util.function.UnaryOperator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GuiInventory {
    private final GuiManager manager;
    private final GuiTemplate template;
    private final GuiProvider provider;
    private final GuiInventory parent;
    private final UnaryOperator<String> titleModifier;

    private GuiContents contents;
    private Inventory inventory;

    public GuiInventory(Builder builder) {
        this.manager = builder.manager;
        this.template = builder.template;
        this.provider = builder.provider;
        this.parent = builder.parent;
        this.titleModifier = builder.titleModifier;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void open(Player player, int page) {
        this.close(player, false);

        final GuiContents contents = new GuiContentsImpl(this, player.getUniqueId());
        this.contents = contents;

        try {
            this.provider.initialize(player, contents);
            if (!this.contents.equals(contents)) {
                return;
            }

            contents.fillInventory();
            contents.getPagination().setPage(page);

            this.inventory = this.openMenu(player);
            this.manager.registerInventory(player.getUniqueId(), this);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to open the inventory for player! ", e);
        }
    }

    public void open(Player player) {
        this.open(player, 0);
    }

    public void close(Player player, boolean force) {
        this.manager.unregisterInventory(player.getUniqueId());
        this.contents = null;

        // If force is not set, don't close the inventory
        if (!force) {
            return;
        }

        player.closeInventory();
    }

    public void close(Player player) {
        this.close(player, true);
    }

    private Inventory openMenu(Player player) {
        final Inventory menu = Bukkit.createInventory(
            player,
            this.getRows() * 9,
            this.titleModifier.apply(this.template.getTitle())
        );

        // Fill in the inventory first
        for (final GuiItem item : this.contents.getItems()) {
            menu.setItem(item.slot(), item.getItemStack());
        }

        player.openInventory(menu);
        return menu;
    }

    public int getRows() {
        return this.template.getLayout().getRows();
    }

    public GuiManager getManager() {
        return this.manager;
    }

    public GuiTemplate getTemplate() {
        return this.template;
    }

    public GuiProvider getProvider() {
        return this.provider;
    }

    public GuiInventory getParent() {
        return this.parent;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public GuiContents getContents() {
        return this.contents;
    }

    public static class Builder {
        private GuiManager manager;
        private GuiTemplate template;
        private GuiProvider provider;
        private GuiInventory parent;
        private UnaryOperator<String> titleModifier;

        public Builder manager(GuiManager manager) {
            this.manager = manager;
            return this;
        }

        public Builder template(GuiTemplate template) {
            this.template = template;
            return this;
        }

        public Builder provider(GuiProvider provider) {
            this.provider = provider;
            return this;
        }

        public Builder parent(GuiInventory parent) {
            this.parent = parent;
            return this;
        }

        public Builder title(UnaryOperator<String> titleModifier) {
            this.titleModifier = titleModifier;
            return this;
        }

        public GuiInventory build() {
            return new GuiInventory(this);
        }
    }

}
