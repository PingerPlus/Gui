package io.pnger.gui.replacer.replacers;

import io.pnger.gui.replacer.ComponentStyler;
import io.pnger.gui.replacer.Replacer;
import io.pnger.gui.replacer.ReplacerEntry;
import io.pnger.gui.util.Text;

public class StringReplacer implements ReplacerProvider<String> {

    @Override
    public boolean provides(Class<?> clazz) {
        return String.class.isAssignableFrom(clazz);
    }

    @Override
    public String provide(String object, Replacer replacer) {
        String replaced = object;

        for (final ReplacerEntry replacerEntry : replacer.entries()) {
            final String replaceWith = ComponentStyler.contentFromComponent(replacerEntry.supplyContent());
            replaced = replaced.replace(replacerEntry.key(), Text.colorize(replaceWith));
        }

        return replaced;
    }
}
