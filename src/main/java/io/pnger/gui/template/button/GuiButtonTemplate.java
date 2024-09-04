package io.pnger.gui.template.button;

import io.pnger.gui.item.ItemBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import org.bukkit.inventory.ItemStack;

public class GuiButtonTemplate {
    private final Map<String, ButtonState> states;
    private final char symbol;
    private final String identifier;

    private GuiButtonTemplate(String identifier, char symbol, Map<String, ButtonState> states) {
        this.symbol = symbol;
        this.identifier = identifier;
        this.states = states;
    }

    public static Builder builder() {
        return new Builder();
    }

    public char getSymbol() {
        return this.symbol;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public Map<String, ButtonState> getStates() {
        return this.states;
    }

    public ButtonState getDefaultState() {
        return this.states.get("default");
    }

    public ButtonState getState(String name) {
        return this.states.get(name);
    }

    public static class Builder {
        private final Map<String, ButtonState> states = new HashMap<>();
        private String identifier = null;
        private char symbol;

        Builder() {
        }

        public Builder identifier(@Nullable String identifier) {
            this.identifier = identifier;
            return this;
        }

        public Builder symbol(char symbol) {
            this.symbol = symbol;
            return this;
        }

        public Builder state(String name, UnaryOperator<ItemBuilder> builder) {
            return this.state(name, builder.apply(ItemBuilder.create()).build());
        }

        public Builder state(String name, ItemStack item) {
            this.states.put(name, ButtonState.builder().item(item).name(name).build());
            return this;
        }

        public Builder defaultState(UnaryOperator<ItemBuilder> builder) {
            return this.defaultState(builder.apply(ItemBuilder.create()).build());
        }

        public Builder defaultState(ItemStack item) {
            this.states.put("default", ButtonState.builder().item(item).name("default").build());
            return this;
        }

        public GuiButtonTemplate build() {
            return new GuiButtonTemplate(this.identifier, this.symbol, this.states);
        }
    }
}
