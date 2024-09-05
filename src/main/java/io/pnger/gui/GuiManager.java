package io.pnger.gui;

import io.pnger.gui.listener.GuiListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class GuiManager {
    private final Map<UUID, GuiInventory> inventories;
    private final JavaPlugin plugin;

    public GuiManager(JavaPlugin plugin) {
        this.inventories = Collections.synchronizedMap(new HashMap<>());
        this.plugin = plugin;

        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            new HashMap<>(this.inventories).forEach((uuid, gui) -> {
                final Player player = Bukkit.getPlayer(uuid);
                if (player == null) {
                    return;
                }

                try {
                    gui.getProvider().update(player, gui.getContents());
                } catch (Exception e) {
                    throw new IllegalArgumentException("Failed to update inventory! ", e);
                }
            });
        }, 1L, 1L);

        plugin.getServer().getPluginManager().registerEvents(new GuiListener(this), this.plugin);
    }

    public boolean isOpenedInventory(UUID uuid, GuiInventory inventory) {
        final GuiInventory currentInventory = this.getInventory(uuid);
        if (currentInventory == null) {
            return false;
        }

        return currentInventory.equals(inventory);
    }

    public GuiInventory getInventory(UUID uuid) {
        return this.inventories.get(uuid);
    }

    public void handleInventory(Player player, Consumer<GuiInventory> consumer) {
        Optional.ofNullable(this.getInventory(player.getUniqueId())).ifPresent(consumer);
    }

    public void registerInventory(UUID id, GuiInventory inventory) {
        this.inventories.put(id, inventory);
    }

    public void unregisterInventory(UUID id) {
        this.inventories.remove(id);
    }

    public Map<UUID, GuiInventory> getInventories() {
        return this.inventories;
    }

    public JavaPlugin getPlugin() {
        return this.plugin;
    }
}
