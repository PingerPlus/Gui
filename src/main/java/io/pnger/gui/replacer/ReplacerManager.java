package io.pnger.gui.replacer;

import io.pnger.gui.replacer.replacers.CollectionReplacer;
import io.pnger.gui.replacer.replacers.ComponentReplacer;
import io.pnger.gui.replacer.replacers.ReplacerProvider;
import io.pnger.gui.replacer.replacers.StringReplacer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public class ReplacerManager {
    public static final ReplacerManager INSTANCE = new ReplacerManager();

    private final Set<ReplacerProvider<?>> providers = new HashSet<>();

    public ReplacerManager() {
        this.registerProviders(
            new StringReplacer(),
            new CollectionReplacer(),
            new ComponentReplacer()
        );
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public <T> T accept(@NotNull T object, @NotNull Replacer replacer) {
        for (final ReplacerProvider provider : this.providers) {
            if (!provider.provides(object.getClass())) {
                continue;
            }

            return (T) provider.provide(object, replacer);
        }
        return object;
    }

    public ReplacerProvider<?> findProvider(Class<?> clazz) {
        for (final ReplacerProvider<?> provider : this.providers) {
            if (provider.provides(clazz)) {
                return provider;
            }
        }
        return null;
    }

    private void registerProviders(ReplacerProvider<?>... providers) {
        this.providers.addAll(Arrays.asList(providers));
    }

}
