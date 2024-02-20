package function;

public class Composing {
    public static void main(String[] args) {
        IFunction<String, Integer> whatsLength = String::length;
        IFunction<Integer, Double> devided = x -> x / 1.86;
        IFunction<String, Double> stringToDouble = devided.compose(whatsLength);

        System.out.println(stringToDouble.apply("Hello"));



    }

    static <S, R, T> IFunction<IFunction<R, S>, IFunction<IFunction<T, R>, IFunction<T, S>>> higherCompose() {
        return (IFunction<R, S> s) -> (IFunction<T, R> t) -> (T x) -> s.apply(t.apply(x));
    }


}
