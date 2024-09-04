package io.pnger.gui.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import javax.annotation.Nonnull;

public interface Iterables {

    static <R, T> List<R> query(Iterable<R> iterable, Function<R, T> function, @Nonnull T param) {
        final List<R> list = new ArrayList<>();
        for (final R r : iterable) {
            final T result = function.apply(r);
            if (result == null || !result.equals(param)) {
                continue;
            }
            list.add(r);
        }
        return list;
    }

}
