package io.pnger.gui.item;

import java.util.function.UnaryOperator;
import org.bukkit.inventory.ItemStack;

public class GuiItemRemapper {
    private final UnaryOperator<ItemStack> remapper;

    private long cacheFor = -1;
    private long lastCached = -1;

    public GuiItemRemapper(UnaryOperator<ItemStack> remapper) {
        this.remapper = remapper;
    }

    public GuiItemRemapper(UnaryOperator<ItemStack> remapper, long cacheFor) {
        this.remapper = remapper;
        this.cacheFor = cacheFor;
    }

    public boolean isCacheEnabled() {
        return this.cacheFor >= 0;
    }

    public UnaryOperator<ItemStack> getRemapper() {
        return this.remapper;
    }

    public long getCacheFor() {
        return this.cacheFor;
    }

    public long getLastCached() {
        return this.lastCached;
    }

    public void setLastCached(long lastCached) {
        this.lastCached = lastCached;
    }
}
