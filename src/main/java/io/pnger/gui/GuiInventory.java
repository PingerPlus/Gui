package io.pnger.gui;

import io.pnger.gui.contents.GuiContentsImpl;
import io.pnger.gui.pagination.GuiPagination;
import io.pnger.gui.pagination.GuiPaginationImpl;
import io.pnger.gui.pagination.PageItemProvider;
import io.pnger.gui.template.button.ButtonState;
import io.pnger.gui.template.button.GuiButtonTemplate;
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

    public int getRows() {
        return this.rows;
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
