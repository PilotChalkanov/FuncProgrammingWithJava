package function;

import hofs.IFactory;

public interface IFunction<T, R> { // U = R
    R apply(T t);

    default <S> IFunction<S, R> compose(IFunction<S, T> before) {
        return (S s) -> apply(before.apply(s));
    }



}
