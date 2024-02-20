package function;

import java.util.function.Function;

public class PartialApplication {
    public static void main(String[] args) {
        IFunction<Integer, IFunction<Integer, Integer>> add = x -> y -> x + y;
        IFunction<Integer, Integer> add10 = partialApply(10, add);
        System.out.println(add10);

        System.out.println(factorial.apply(5));

    }

    static  <A, B, C> IFunction<A, C> partialApply(B b, IFunction<B, IFunction<A, C>> f) {
        return f.apply(b);
    }
    static  <A, B, C> IFunction<A, C> partialB(B b, IFunction<A, IFunction<B, C>> f) {
        return a -> f.apply(a).apply(b);
    }

    static <A, B, C, D> IFunction<A, IFunction <B, IFunction<C, IFunction<D, String>>>> partialString() {
        return (A a) -> (B b) -> c -> d -> String.format("%s, %s, %s, %s", a, b, c, d);
    }
    static  <A, B, C> IFunction<B, IFunction<A, C>> swapArgs(IFunction<A, IFunction<B, C>> f) {
        return b -> a -> f.apply(a).apply(b);
    }
    static public IFunction<Integer, Integer> factorial;
    static {

        factorial = n -> n <= 1 ? n : n * factorial.apply(n - 1);
    }

}
