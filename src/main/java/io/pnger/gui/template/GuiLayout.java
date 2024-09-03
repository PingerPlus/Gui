package io.pnger.gui.template;

import java.util.Arrays;
import javax.annotation.Nonnull;

public class GuiLayout {
    private final String[] layout;
    private final int rows;

    public GuiLayout(int rows) {
        this.rows = rows;
        this.layout = new String[rows];
        Arrays.fill(this.layout, "?????????");
    }

    public GuiLayout row(int row, @Nonnull String layout) {
        this.layout[row] = layout;
        return this;
    }

    public GuiLayout row(@Nonnull String pattern, int... rows) {
        for (final int row : rows) {
            this.row(row, pattern);
        }
        return this;
    }

    public String[] getLayout() {
        return this.layout;
    }

    public int getRows() {
        return this.rows;
    }
}
