package funcstructs;



import recursion.TailCall;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static java.util.function.Function.identity;
import static recursion.TailCall.*;
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
    
    static <T> Function<T, T> composeAll(List<Function<T, T>> funcList){
        return composeRec(funcList, identity()).eval();
    }

    static <T> TailCall<Function<T,T>> composeRec(List<Function<T,T>> funcList, Function<T, T> acc) {
        return funcList.isEmpty() ?
                ret(acc) :
                sus(() ->composeRec(tail(funcList), acc.compose(head(funcList))));

    }
}
