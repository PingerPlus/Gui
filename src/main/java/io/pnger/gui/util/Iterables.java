package io.pnger.gui.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import javax.annotation.Nonnull;

public interface Iterables {

    /**
     * Filters the given iterable based on the result of applying the specified function to each element
     * and comparing it to the given parameter.
     *
     * <p>For each element in the provided iterable, the function is applied. If the result of the function
     * is not {@code null} and equals the specified parameter, the element is added to the resulting list.
     *
     * @param iterable the iterable containing the elements to filter
     * @param function the function to apply to each element of the iterable
     * @param param the parameter to compare the result of the function against, must not be null
     * @param <R> the type of elements in the iterable
     * @param <T> the type of the parameter to compare the function's result against
     * @return a list containing all elements from the iterable for which the function result matches the specified parameter
     */

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
