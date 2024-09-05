package io.pnger.gui.provider;

import io.pnger.gui.contents.GuiContents;
import org.bukkit.entity.Player;

/**
 * An abstract base class for implementing the {@link GuiProvider} interface.
 * <p>
 * This class provides a standard implementation for initializing providers by filling it with
 * initial items and then delegating to {@link #setup(Player, GuiContents)} for additional setup.
 * It ensures that essential items are placed in the inventory before any further customization
 * is performed.
 * </p>
 */
public abstract class AbstractGuiProvider implements GuiProvider {

    @Override
    public final void initialize(Player player, GuiContents contents) {
        contents.fillInventory();
        this.setup(player, contents);
    }

    /**
     * Abstract method which defines additional setup logic for the GUI after the default
     * items have been set.
     *
     * @param player   The {@link Player} for whom the GUI is being set up. This player will interact with the customized GUI.
     * @param contents The {@link GuiContents} object representing the contents of the GUI to be customized.
     */
    public abstract void setup(Player player, GuiContents contents);

}
