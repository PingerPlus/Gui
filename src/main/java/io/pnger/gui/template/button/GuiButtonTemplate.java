package io.pnger.gui.template.button;

import io.pnger.gui.item.ItemBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;
import javax.annotation.Nonnull;
import org.bukkit.inventory.ItemStack;

public class GuiButtonTemplate {
    private final Map<String, ItemStack> states;

    private char symbol;
    private String identifier;

    private GuiButtonTemplate() {
        this.states = new HashMap<>();
    }

    public static GuiButtonTemplate create() {
        return new GuiButtonTemplate();
    }

    public GuiButtonTemplate symbol(char symbol) {
        this.symbol = symbol;
        return this;
    }

    public GuiButtonTemplate identifier(@Nonnull String identifier) {
        this.identifier = identifier;
        return this;
    }

    public GuiButtonTemplate state(@Nonnull String state, @Nonnull UnaryOperator<ItemBuilder> modifier) {
        this.states.put(state, modifier.apply(ItemBuilder.create()).build());
        return this;
    }

    public GuiButtonTemplate defaultState(@Nonnull UnaryOperator<ItemBuilder> modifier) {
        return this.state("default", modifier);
    }

    public char getSymbol() {
        return this.symbol;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public Map<String, ItemStack> getStates() {
        return this.states;
    }

    public ItemStack getDefaultState() {
        return this.states.get("default");
    }
}
