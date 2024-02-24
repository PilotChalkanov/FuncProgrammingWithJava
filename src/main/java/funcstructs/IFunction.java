package funcstructs;


import java.util.List;
import java.util.function.Function;

import static data_structures.CollectionUtilities.*;

public interface IFunction<T, R> { // U = R
    R apply(T t);

    default <S> IFunction<S, R> compose(IFunction<S, T> before) {
        return (S s) -> apply(before.apply(s));
    }

    static <T, U, V> IFunction<IFunction<U, V>,
            IFunction<IFunction<T, U>,
                    IFunction<T, V>>> higherCompose() {
        return (IFunction<U, V> f) -> (IFunction<T, U> g) -> (T x)
                -> f.apply(g.apply(x));

    }

    static <T> Function<T, T> composeAll(List<Function<T, T>> fList) {
        return x -> {
            T y = x;
            for (Function<T, T> f : fList) {
                y = f.apply(y);
            }
            return y;
        };
    }

    static <T> Function<T, T> composeAllViaFoldRight(List<Function<T, T>> list) {
        return x -> foldRight(list, x, a -> a::apply);
    }

    static <T> Function<T, T> composeAllViaFoldLeft(List<Function<T, T>> list) {
        return x -> foldLeft(reverse(list), x, a -> b -> b.apply(a));
    }

    static <T> Function<T, T> andThanAllViaFoldRight(List<Function<T, T>> list) {
        return x -> foldRight(reverse(list), x, a -> a::apply);
    }

    static <T> Function<T, T> andThanAllViaFoldLeft(List<Function<T, T>> list) {
        return x -> foldLeft(list, x, a -> b -> b.apply(a));
    }
}
