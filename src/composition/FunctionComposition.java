package composition;

public class FunctionComposition {
    static Function<Integer, Integer> compose(Function<Integer, Integer> f1,
                                              Function<Integer, Integer> f2) {

       return arg -> f1.apply(f2.apply(arg));
    }
}

