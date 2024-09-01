package io.pnger.gui;

import org.bukkit.inventory.Inventory;

public class GuiInventory {
    private final GuiInventory parent;
    private final String title;
    private final int rows;

    private Inventory inventory;

    public GuiInventory(Builder builder) {
        this.parent = builder.parent;
        this.title = builder.title;
        this.rows = builder.rows;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private GuiInventory parent;
        private String title;
        private int rows;

        public GuiInventory build() {
            return new GuiInventory(this);
        }
    }

}
