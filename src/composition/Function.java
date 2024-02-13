package composition;

public interface Function<T, U> {
    U apply(T arg);
    static <T, U, V> Function<Function<U, V>,
            Function<Function<T, U>,
                    Function<T, V>>> higherCompose() {
        return (Function<U, V> f) -> (Function<T, U > g) -> (T x)
                -> f.apply(g.apply(x));
    }
}