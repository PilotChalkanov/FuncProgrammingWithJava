package chaining;

import java.util.function.Function;

public class Chaining {
    public static void main(String[] args) {
        IConsumer<String> c1 = System.out::println;
        IConsumer<String> c2 = System.out::println;

//        c1.consume("Hello");
//        c2.consume("World");
        IConsumer<String> c3 = c3 = s -> {
            c1.consume(s);
            c2.consume(s);
        };
        c3.consume("Hello");

        IConsumer<String> c4 = c1.thanAccept(c2);
        IConsumer<String> c5 = c1.thanAccept(null);
        c4.consume("Hello");
//        c5.consume("World"); //This will throw a NullPointerException

        //Function chaining with default andThen method of java Function interface
        Function<Integer, Integer> f1 = s -> s + 2;
        Function<Integer, Integer> f2 = s -> s * 2;
        Function<Integer, Integer> f3 = f1.andThen(f2);
        System.out.println(f3.apply(2));
    }
}
