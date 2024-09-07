package io.pnger.gui.replacer.replacers;

import io.pnger.gui.replacer.Replacer;

public interface ReplacerProvider<T> {

    boolean provides(Class<?> clazz);

    T provide(T object, Replacer replacer);

}
