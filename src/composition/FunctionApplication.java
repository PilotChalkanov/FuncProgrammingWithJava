package composition;
// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
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
        Function<Integer, Integer> tripleLambda = x -> x * 3;
        Function<Integer, Integer> squareLambda = x -> x * x;
        Function<Integer, Integer> composedFunction = FunctionComposition.compose(triple, square);
        Function<Integer, Integer> composedLambdaFunction = FunctionComposition.compose(tripleLambda, squareLambda);
        System.out.println(composedFunction.apply(9));
        System.out.println(composedLambdaFunction.apply(9));
    }
}