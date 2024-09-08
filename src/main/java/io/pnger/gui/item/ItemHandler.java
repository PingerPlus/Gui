package io.pnger.gui.item;

import io.pnger.gui.replacer.ComponentStyler;
import java.util.LinkedList;
import java.util.List;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ItemHandler {

    public static ItemStack setItemLore(@NonNull ItemStack itemStack, @NonNull List<Component> lore) {
        final List<String> newLore = new LinkedList<>();
        final List<Component> stuff = new LinkedList<>();

        if (itemStack.getType() == Material.AIR) {
            return itemStack;
        }

        for (final Component component : lore) {
            boolean newLine = false;
            boolean anyMatch = false;

            for (final Component child : component.children()) {
                if (child.equals(Component.newline())) {
                    newLine = true;
                    anyMatch = true;
                    continue;
                }

                if (newLine) {
                    newLine = false;
                    stuff.add(child);
                }
            }

            if (!anyMatch) {
                stuff.add(component);
            }
        }

        for (final Component component : stuff) {
            newLore.add(ComponentStyler.contentFromComponent(component));
        }

        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(newLore);

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static List<Component> getItemLore(@NonNull ItemStack itemStack) {
        final List<Component> lore = new LinkedList<>();
        final ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return lore;
        }

        if (!itemMeta.hasLore()) {
            return lore;
        }

        for (final String line : itemMeta.getLore()) {
            lore.add(ComponentStyler.componentFromContent(line));
        }

        return lore;
    }

}
