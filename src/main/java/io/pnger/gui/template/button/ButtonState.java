package io.pnger.gui.template.button;

import io.pnger.gui.item.ItemBuilder;
import java.util.function.UnaryOperator;
import org.bukkit.inventory.ItemStack;

public class ButtonState {
    private String name;
    private ItemStack item;

    public ButtonState(String name, ItemStack item) {
        this.name = name;
        this.item = item;
    }

    public static Builder builder() {
        return new Builder();
    }

    public ButtonState copy() {
        return new ButtonState(this.name, this.item.clone());
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public void setItem(UnaryOperator<ItemBuilder> modifier) {
        this.setItem(modifier.apply(ItemBuilder.create(this.item)).build());
    }

    public static class Builder {
        private String name;
        private ItemStack item;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder item(ItemStack item) {
            this.item = item;
            return this;
        }

        public Builder item(UnaryOperator<ItemBuilder> modifier) {
            return this.item(modifier.apply(ItemBuilder.create(this.item)).build());
        }

        public ButtonState build() {
            return new ButtonState(this.name, this.item);
        }
    }
}
