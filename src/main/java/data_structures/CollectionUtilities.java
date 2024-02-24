package data_structures;

import caseclass.Effect;

import recursion.TailCall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import static recursion.TailCall.*;

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

    static String addSI(Integer s, String i) {
        return "(" + s + " + " + i + ")";
    }


    public static <T, U> U foldLeft(List<T> ts, U identity,
                                    Function<U, Function<T, U>> f) {
        return foldLRec(ts, identity, f).eval();   }

    public static <T, U> U foldRight(List<T> ts, U identity,
                                     Function<T, Function<U, U>> f) {
        return _foldRight(ts, identity, f).eval();
    }

    public static <T, U> U foldR(List<T> ts, U identity,
                                 Function<T, Function<U, U>> f) {
        U result = identity;
        for (int i = ts.size(); i > 0; i--) {
            result = f.apply(ts.get(i - 1)).apply(result);
        }
        return result;    }

    private static <U, T> TailCall<U> foldLRec(List<T> ts, U identity, Function<U, Function<T, U>> f) {
        return ts.isEmpty() ? ret(identity) : sus(
                () -> foldLRec(tail(ts), f.apply(identity).apply(head(ts)), f)
        );

    }

    public static <T, U> TailCall<U> _foldRight(List<T> ts, U identity,
                                                Function<T, Function<U, U>> f){
        return ts.isEmpty() ?
                ret(identity) :
                sus(() -> _foldRight(tail(ts), f.apply(head(ts)).apply(identity), f));
    }


    public static <T> List<T> reverse(List<T> list) {
        List<T> accList = new ArrayList<>();
        Function<T, Function<List<T>, List<T>>> addToList = (T el) -> (List<T> xlist) -> copy(append(xlist, el));
        return foldR(list, accList, addToList);
    }

    public static <T, U> List<U> map1(List<T> ts, Function<T, U> f) {
        List<U> accList = list();
        for (T el : ts)
            accList = append(accList, f.apply(el));
        return accList;
    }

    public static <T, U> List<U> map2(List<T> ts, Function<T, U> f) {
        return foldLeft(ts, list(), x -> y -> append(x, f.apply(y)));
    }

    public static <T, U> List<U> map3(List<T> ts, Function<T, U> f) {
        return foldR(ts, list(), x -> y -> prepend(f.apply(x), y));
    }

    public static <T> List<T> reverse2(List<T> list) {
        return foldLeft(list, list(), x -> y -> prepend(y, x));
    }


    public static <T> void forEach(List<T> ts, Effect<T> e) {
        for (T t : ts) e.apply(t);
    }

    public static List<Number> range(int start, int end) {
        return rangeRec(start, end, list()).eval();
    }

    public static <T> List<T> unfold(T seed, Function<T, T> f, Function<T, Boolean> p) {
        List<T> ls = new ArrayList<>();
        while (p.apply(seed)) {
            ls = append(ls, f.apply(seed));
            seed = f.apply(seed);
        }
        return ls;
    }

    public static List<Integer> range2(int start, int end) {
        return unfold(start, x -> x + 1, y -> y < end);
    }

    // HIGHLIGHTS
    public static TailCall<List<Number>> rangeRec(Integer start, Integer end, List<Number> ls) {
        return start >= end ?
                ret(ls) :
                sus(() -> rangeRec(start + 1, end, append(ls, start)));
    }

    public static void main(String[] args) {
        List<Integer> intList = list(1, 2, 3, 4, 5);
        Integer reducedList = foldL(intList, 0, (x, y) -> x + y);
//        Function<String, Function<String, Integer>> f = ;
//        String reducedL = foldLeft(intList, "0", x -> y -> addSI(x, y));
//        System.out.println(reducedL);
//        Function<Integer, Function<Integer, Integer>> f2 = x -> y -> x + y;
//        Integer reducedR = foldR(intList, 0, f2);
//        System.out.println(reverse(intList));
//        System.out.println(reducedR);
//        System.out.println(map1(intList, x -> x * 2));
//        System.out.println(map2(intList, x -> x * 2));
//        System.out.println(map3(intList, x -> x * 2));
//
//        Function<Double, Double> addTax = x -> x * 1.09;
//        Function<Double, Double> addShipping = x -> x + 3.50;
//        List<Double> prices = list(10.10, 23.45, 32.07, 9.23);
//        List<Double> pricesIncludingTax = map2(prices, addTax);
//        List<Double> pricesIncludingShipping =
//                map2(pricesIncludingTax, addShipping);
//        System.out.println(pricesIncludingShipping);
//        Function<Double, Double> composedMap = addTax.compose(addShipping);
//        List<Double> pricesWithTaxAndShipping = map2(prices, composedMap);
//        List<Double> list_ = map3(intList, x -> (double) x);
//        System.out.println(map2(list_, composedMap));
//
//        Effect<Double> printWith2decimals = x -> {
//            System.out.printf("%.2f", x);
//            System.out.println();
//        };
////
////        forEach(pricesIncludingShipping, printWith2decimals);
//
//        Function<Executable, Function<Executable, Executable>> compose =
//                x -> y -> () -> {
//                    x.exec();
//                    y.exec();
//                };
//        Executable program = foldLeft(pricesIncludingShipping, () -> {
//                },
//                e -> d -> compose.apply(e).apply(() -> printWith2decimals.apply(d)));
//        program.exec();
//
        List<Number> lsInt = range(0, 100);
        System.out.println(lsInt);

        List<Integer> list = list(1, 2, 3, 4, 5);
        System.out.println(foldRight(list, "0", x -> y -> addSI(x, y)));

//
//        List<Integer> lsInt2 = range2(0, 150);
//        System.out.println(lsInt);
    }


}
