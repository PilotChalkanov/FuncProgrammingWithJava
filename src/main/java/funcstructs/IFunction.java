package funcstructs;

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
}
