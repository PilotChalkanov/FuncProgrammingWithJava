package data_structures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CollectionUtilities<T> {
    public static <T> List<T> list() {
        return Collections.emptyList();
    }

    public static <T> List<T> list(T t) {
        return Collections.singletonList(t);
    }

    public static <T> List<T> list(List<T> ts) {
        return Collections.unmodifiableList(ts);
    }

    public static <T> List<T> list(T... t) {
        return Collections.unmodifiableList(
                Arrays.asList(Arrays.copyOf(t, t.length))
        );
    }

    private static <T> T getHead(List<T> ts) {
        return Collections.unmodifiableList(ts).get(0);
    }

    private static <T> List<T> copy(List<T> ts) {
        return new ArrayList<>(ts);
    }

    public static <T> T head(List<T> ts) {
        if (ts.isEmpty()) return null;
        return getHead(ts);
    }

    public static <T> List<T> tail(List<T> ts) {
        if (ts.isEmpty()) return null;
        List<T> tl = copy(ts.subList(1, ts.size()));
        return Collections.unmodifiableList(tl);
    }

    public static <T> List<T> append(List<T> list, T t) {
        List<T> ts = copy(list);
        ts.add(t);
        return Collections.unmodifiableList(ts);
    }

    public static <T> List<T> prepend(T t, List<T> ts) {
        return foldLeft(ts, list(t), a -> b -> append(a, b));
    }

    public static <T> T foldL(List<T> ts, T acc, BiFunction<T, T, T> f) {
        if (ts.isEmpty()) return acc;
        T accum = f.apply(acc, head(ts));
        List<T> tl = tail(ts);
        return foldL(tl, accum, f);
    }

    static String addSI(String s, Integer i) {
        return "(" + s + " + " + i + ")";
    }

    public static <T, U> U foldLeft(List<T> ts, U identity,
                                    Function<U, Function<T, U>> f) {
        U result = identity;
        for (T t : ts) {
            result = f.apply(result).apply(t);
        }
        return result;
    }

    public static <T, U> U folRight(List<T> ts, U identity,
                                    Function<T, Function<U, U>> f) {
        U result = identity;
        for (int i = ts.size(); i > 0; i--) {
            result = f.apply(ts.get(i - 1)).apply(result);
        }
        return result;
    }

    public static <T, U> U foldR(List<T> ts, U identity,
                                 Function<T, Function<U, U>> f) {
        return ts.isEmpty()
                ? identity
                : f.apply(head(ts)).apply(foldR(tail(ts), identity, f));
    }

    public static <T> List<T> reverse(List<T> list) {
        List<T> accList = new ArrayList<>();
        Function<T, Function<List<T>, List<T>>> addToList = (T el) -> (List<T> xlist) -> copy(append(xlist, el));
        return foldR(list, accList, addToList);
    }

    public static <T, U> List<U> map1 (List<T> ts, Function<T, U> f) {
        List<U> accList = list();
        for (T el : ts)
            accList = append(accList, f.apply(el));
        return accList;
    }

    public static <T, U> List<U> map2 (List<T> ts, Function<T, U> f){
        return foldLeft(ts, list(), x -> y -> append(x, f.apply(y)));
    }

    public static <T, U> List<U> map3 (List<T> ts, Function<T, U> f){
        return foldR(ts, list(), x -> y -> prepend(f.apply(x), y));
    }

    public static <T> List<T> reverse2(List<T> list) {
        return foldLeft(list, list(), x -> y -> prepend(y, x));
    }


    public static void main(String[] args) {
        List<Integer> intList = list(1, 2, 3, 4, 5);
        Integer reducedList = foldL(intList, 0, (x, y) -> x + y);
        Function<String, Function<Integer, String>> f = x -> y -> addSI(x, y);
        String reducedL = foldLeft(intList, "0", f);
        Function<Integer, Function<Integer, Integer>> f2 = x -> y -> x + y;
        Integer reducedR = foldR(intList, 0, f2);
        System.out.println(reverse(intList));
        System.out.println(reducedR);
        System.out.println(map1(intList, x -> x *2));
        System.out.println(map2(intList, x -> x *2));
        System.out.println(map3(intList, x -> x *2));
    }
}
