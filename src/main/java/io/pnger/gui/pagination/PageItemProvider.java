package io.pnger.gui.pagination;

import io.pnger.gui.template.button.ButtonState;
import io.pnger.gui.template.button.GuiButtonTemplate;

public interface PageItemProvider<T> {

    ButtonState provide(T t, int slot, GuiButtonTemplate template);

}
