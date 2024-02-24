package funcstructs;

import java.util.function.Function;

import static data_structures.CollectionUtilities.map2;
import static data_structures.CollectionUtilities.range;
import static funcstructs.IFunction.composeAll;

public class Playground {
    public static void main(String[] args) {
        Function<Integer, Integer> add = y -> y + 2;
        System.out.println(composeAll(map2(range(0, 500), x -> add)).apply(0));
    }
}
