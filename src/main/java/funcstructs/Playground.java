package funcstructs;

import java.util.function.Function;

import static data_structures.CollectionUtilities.*;
import static funcstructs.IFunction.*;

public class Playground {
    public static void main(String[] args) {
        Function<Integer, Integer> add = y -> y + 2;
        System.out.println(composeAll(map2(range(0, 10000), x -> add)).apply(0));
        Function<String, String> f1 = x -> "(a" + x + ")";
        Function<String, String> f2 = x -> "{b" + x + "}";
        Function<String, String> f3 = x -> "[c" + x + "]";
        System.out.println(composeAllViaFoldLeft(list(f1, f2, f3)).apply("x"));
        System.out.println(andThanAllViaFoldRight(list(f1, f2, f3)).apply("x"));

    }

}
