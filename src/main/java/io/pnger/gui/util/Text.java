package io.pnger.gui.util;

import org.bukkit.ChatColor;

public class Text {
    private static final char COLOR_CHAR = '&';

    public static String colorize(String text) {
        return ChatColor.translateAlternateColorCodes(COLOR_CHAR, text);
    }

}
