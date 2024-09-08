package io.pnger.gui.listener;

import io.pnger.gui.GuiManager;
import io.pnger.gui.event.ClickEvent;
import io.pnger.gui.item.GuiItem;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

public class GuiListener implements Listener {
    private final GuiManager manager;

    public GuiListener(GuiManager manager) {
        this.manager = manager;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onClose(InventoryCloseEvent e) {
        final Player player = (Player) e.getPlayer();

        // Clear the inventory and unregister it from the player
        this.manager.handleInventory(player, (inv) -> {
            inv.getInventory().clear();
            this.manager.unregisterInventory(player.getUniqueId());
        });
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onQuit(PlayerQuitEvent e) {
        this.manager.unregisterInventory(e.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDrag(InventoryDragEvent e) {
        final Player player = (Player) e.getWhoClicked();

        this.manager.handleInventory(player, (inv) -> {
            for (final int slot : e.getRawSlots()) {
                if (slot >= player.getOpenInventory().getTopInventory().getSize()) {
                    continue;
                }

                e.setCancelled(true);
                break;
            }
        });
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPluginDisable(PluginDisableEvent e) {
        final Plugin plugin = e.getPlugin();
        if (!plugin.equals(this.manager.getPlugin())) {
            return;
        }

        new HashMap<>(this.manager.getInventories()).forEach((id, inv) -> {
            final Player player = Bukkit.getPlayer(id);
            if (player == null) {
                return;
            }

            inv.close(player);
        });
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        final Player player = (Player) e.getWhoClicked();
        final InventoryAction action = e.getAction();
        final ClickType click = e.getClick();

        this.manager.handleInventory(player, (inv) -> {
            final Inventory clickedInventory = e.getClickedInventory();
            if (clickedInventory == player.getOpenInventory().getBottomInventory()) {
                if (action == InventoryAction.COLLECT_TO_CURSOR || action == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                    e.setCancelled(true);
                    return;
                }

                if (action == InventoryAction.NOTHING && click != ClickType.MIDDLE) {
                    e.setCancelled(true);
                    return;
                }
                return;
            }

            // It's the top inventory
            final int row = e.getSlot() / 9;
            final int column = e.getSlot() % 9;
            if (row < 0 || column < 0) {
                e.setCancelled(true);
                return;
            }

            if (row >= inv.getRows()) {
                e.setCancelled(true);
                return;
            }

            final GuiItem item = inv.getContents().getItem(row, column).orElse(null);
            if (item == null) {
                e.setCancelled(true);
                return;
            }

            final String identifier = item.template().getIdentifier();
            final ClickEvent event = new ClickEvent(e);
            if (item.isDragging()) {
                inv.getContents().handleClick(identifier, event);
                return;
            }

            e.setCancelled(true);
            inv.getContents().handleClick(identifier, event);
        });
    }
}
