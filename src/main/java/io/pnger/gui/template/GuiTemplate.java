package io.pnger.gui.template;

import com.google.common.base.Preconditions;
import io.pnger.gui.template.button.GuiButtonTemplate;
import io.pnger.gui.util.Iterables;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import javax.annotation.Nonnull;

public class GuiTemplate {
    private final List<GuiButtonTemplate> buttons;
    private final String title;
    private final GuiLayout layout;

    public GuiTemplate(Builder builder) {
        this.buttons = builder.buttons;
        this.title = builder.title;
        this.layout = builder.layout;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<GuiButtonTemplate> getButtons() {
        return this.buttons;
    }

    public GuiButtonTemplate findButtonTemplate(char symbol) {
        return this.getButton(GuiButtonTemplate::getSymbol, symbol);
    }

    public GuiButtonTemplate findButtonTemplate(String identifier) {
        return this.getButton(GuiButtonTemplate::getIdentifier, identifier);
    }

    public <T> GuiButtonTemplate getButton(Function<GuiButtonTemplate, T> function, T param) {
        return Iterables.query(this.buttons, function, param).stream().findAny().orElse(null);
    }

    public String getTitle() {
        return this.title;
    }

    public GuiLayout getLayout() {
        return this.layout;
    }

    public static class Builder {
        private final List<GuiButtonTemplate> buttons;

        private String title;
        private GuiLayout layout;

        public Builder() {
            this.buttons = new ArrayList<>();
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder design(int rows, @Nonnull Consumer<GuiLayout> consumer) {
            Preconditions.checkArgument(rows <= 6, "Row amount must be less or equal than 6");

            final GuiLayout layout = new GuiLayout(rows);
            consumer.accept(layout);

            this.layout = layout;
            return this;
        }

        public Builder button(@Nonnull UnaryOperator<GuiButtonTemplate.Builder> modifier) {
            this.buttons.add(modifier.apply(GuiButtonTemplate.builder()).build());
            return this;
        }

        public GuiTemplate build() {
            return new GuiTemplate(this);
        }
    }
}
