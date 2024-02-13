package composition;

public class FunctionApplication {
    public static void main(String[] args) {

        Function<Integer, Integer> triple = new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer arg) {
                return arg * 3;
            }
        };
        Function<Integer, Integer> square = new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer arg) {
                return arg * arg;
            }
        };

        Function<Integer, Function<Integer, Integer>> add = x -> y -> x + y;

        Function<Integer, Integer> tripleLambda = x -> x * 3;
        Function<Integer, Integer> squareLambda = x -> x * x;

        Function<Integer, Integer> composedLambdaFunction = FunctionComposition.compose(tripleLambda, squareLambda);
        System.out.println(composedLambdaFunction.apply(4));
        System.out.println(add.apply(3).apply(4));

        Integer x = Function.<Integer, Integer, Integer>higherCompose().apply(square).apply(triple).apply(2);
        System.out.println(x);

    }
}