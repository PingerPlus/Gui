package io.pnger.gui.replacer.replacers;

import io.pnger.gui.replacer.Replacer;
import io.pnger.gui.replacer.ReplacerManager;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.Map;

@SuppressWarnings({"rawtypes", "unchecked"})
public class CollectionReplacer implements ReplacerProvider<Collection<?>> {

    @Override
    public boolean provides(Class<?> clazz) {
        return Collection.class.isAssignableFrom(clazz);
    }

    @Override
    public Collection<?> provide(Collection<?> object, Replacer replacer) {
        final Collection<Object> copy = new LinkedList<>();
        final Map<Class<?>, ReplacerProvider<?>> providerCache = new IdentityHashMap<>();

        for (final Object content : object) {
            final ReplacerProvider provider = providerCache.computeIfAbsent(content.getClass(), this::findProvider);
            final Object accept = provider.provide(content, replacer);
            copy.add(accept);
        }

        return copy;
    }

    private ReplacerProvider findProvider(Class<?> clazz) {
        final ReplacerProvider provider = ReplacerManager.INSTANCE.findProvider(clazz);
        if (provider == null) {
            throw new IllegalStateException(String.format("No acceptable replacer found for %s", clazz));
        }

        return provider;
    }

}
