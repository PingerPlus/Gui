package io.pnger.gui.populator;

import io.pnger.gui.GuiInventory;
import io.pnger.gui.item.GuiItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface GuiPopulator {

    Inventory open(Player player, GuiInventory inventory);

}
