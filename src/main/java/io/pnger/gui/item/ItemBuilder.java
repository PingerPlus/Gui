package io.pnger.gui.item;

import io.pnger.gui.material.XMaterial;
import io.pnger.gui.util.Text;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {
    private final ItemStack item;

    public ItemBuilder(@Nonnull ItemStack item) {
        this.item = item;
    }

    public ItemBuilder(@Nonnull Material material) {
        this.item = new ItemStack(material);
    }

    public ItemBuilder(@Nonnull Material material, int amount) {
        this(new ItemStack(material, amount));
    }

    public ItemBuilder(XMaterial material) {
        this(material.parseItem());
    }

    public ItemBuilder() {
        this.item = new ItemStack(Material.AIR);
    }

    public static ItemBuilder create(ItemStack item) {
        return new ItemBuilder(item);
    }

    public static ItemBuilder create() {
        return new ItemBuilder();
    }

    private ItemBuilder transform(Consumer<ItemStack> consumer) {
        consumer.accept(this.item);
        return this;
    }

    private ItemBuilder transformMeta(Consumer<ItemMeta> consumer) {
        final ItemMeta meta = this.item.getItemMeta();
        if (meta != null) {
            consumer.accept(meta);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder type(Material material) {
        return this.transform((item) -> item.setType(material));
    }

    public ItemBuilder amount(int amount) {
        return this.transform((item) -> item.setAmount(amount));
    }

    public ItemBuilder name(@Nonnull String name) {
        return this.transformMeta((meta) -> meta.setDisplayName(Text.colorize(name)));
    }

    public ItemBuilder lore(Iterable<String> lines) {
        return this.transformMeta((meta) -> {
            final List<String> lore = meta.getLore() == null ? new ArrayList<>() : meta.getLore();
            for (final String line : lines) {
                lore.add(Text.colorize(line));
            }
            meta.setLore(lore);
        });
    }

    public ItemBuilder lore(String line) {
        return this.transformMeta((meta) -> {
            final List<String> lore = meta.getLore() == null ? new ArrayList<>() : meta.getLore();
            lore.add(Text.colorize(line));
            meta.setLore(lore);
        });
    }

    public ItemBuilder lore(String... lines) {
        return this.lore(Arrays.asList(lines));
    }

    public ItemBuilder flag(ItemFlag... flags) {
        return this.transformMeta((meta) -> meta.addItemFlags(flags));
    }

    public ItemBuilder flag() {
        return this.flag(ItemFlag.values());
    }

    public ItemBuilder enchant(Enchantment enchantment, int level) {
        return this.transform(item -> item.addUnsafeEnchantment(enchantment, level));
    }

    public ItemBuilder enchant(Enchantment enchantment) {
        return this.enchant(enchantment, 1);
    }

    public ItemBuilder glow() {
        return this.enchant(Enchantment.DURABILITY, 0);
    }

    public ItemStack build() {
        return this.item;
    }

}
