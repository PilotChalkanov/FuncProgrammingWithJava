package data_structures;


import recursion.TailCall;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static recursion.TailCall.ret;
import static recursion.TailCall.sus;

public abstract class LList<A> {
    public abstract A head();

    public abstract LList<A> tail();

    public abstract boolean isEmpty();

    public abstract LList<A> cons(A el);

    public abstract LList<A> setHead(A el);

    public abstract String toString();

    public abstract LList<A> drop(int idx);

    public abstract LList<A> dropWhile(Function<A, Boolean> f);

    public abstract LList<A> init();

    public abstract LList<A> reverse();

    public abstract int size();

    public abstract <B> B foldLeft(B identity, Function<B, Function<A, B>> f);

    public abstract <B> B foldRight(B identity, Function<A, Function<B, B>> f);

    public abstract <B> LList<B> map(Function<A, B> f);

    public abstract LList<A> filter(Function<A, Boolean> p);

    public abstract <B> LList<B> flatMap(Function<A, LList<B>> f);


    public static <A> LList<A> concat(LList<A> list1, LList<A> list2) {
        return list1.isEmpty()
                ? list2
                : list2.isEmpty()
                ? list1
                : new Cons<>(list1.head(), concat(list1.tail(), list2));
    }

    // singleton instance representing empty llist
    @SuppressWarnings("rawtypes")
    public static final LList NIL = new Nil();

    private static class Nil<A> extends LList<A> {
        private Nil() {
        }

        public A head() {
            throw new IllegalStateException("head called on empty Llist");
        }

        public LList<A> tail() {
            throw new IllegalStateException("tail called on empty Llist");
        }

        public boolean isEmpty() {
            return true;
        }

        public LList<A> cons(A el) {
            return new Cons<>(el, this);
        }

        public LList<A> setHead(A el) {
            throw new IllegalStateException("setHead called on empty list");
        }

        public LList<A> drop(int idx) {
            return this;
        }

        public LList<A> dropWhile(Function<A, Boolean> f) {
            return this;
        }

        public LList<A> init() {
            throw new IllegalStateException("init called on empty list");
        }

        public LList<A> reverse() {
            return this;
        }

        public int size() {
            return 0;
        }

        public <B> B foldLeft(B identity, Function<B, Function<A, B>> f) {
            throw new IllegalStateException("foldLeft called on empty list");
        }

        public <B> B foldRight(B identity, Function<A, Function<B, B>> f) {
            return null;
        }

        public <B> LList<B> map(Function<A, B> f) {
            return NIL;
        }

        public LList<A> filter(Function<A, Boolean> p) {
            return NIL;
        }

        public <B> LList<B> flatMap(Function<A, LList<B>> f) {
            return NIL;
        }

        public String toString() {
            return "[NIL]";
        }

    }

    private static class Cons<A> extends LList<A> {

        private final A head;
        private final LList<A> tail;

        private Cons(A head, LList<A> tail) {
            this.head = head;
            this.tail = tail;
        }

        public A head() {
            return head;
        }

        public LList<A> tail() {
            return tail;
        }

        public LList<A> cons(A el) {
            return new Cons<>(el, this);
        }

        public LList<A> setHead(A el) {
            return new Cons<>(el, tail());
        }

        public String toString() {
            return String.format("[%sNIL]", _toString(this, new StringBuilder()).eval());
        }

        private TailCall<StringBuilder> _toString(LList<A> ls, StringBuilder acc) {
            return ls.isEmpty() ?
                    ret(acc) :
                    sus(() -> _toString(ls.tail(), acc.append(ls.head() + ", ")));

        }

        public LList<A> drop(int idx) {
            return idx <= 0 ?
                    this :
                    _drop(idx, this).eval();
        }

        private TailCall<LList<A>> _drop(int idx, LList<A> ls) {
            return (ls instanceof LList.Nil<A>) ?
                    ret(new Nil<>()) :
                    (idx <= 0) ?
                            ret(new Cons<>(ls.head(), ls.tail())) :
                            sus(() -> _drop(idx - 1, ls.tail()));
        }

        public LList<A> dropWhile(Function<A, Boolean> f) {
            return _dropWhile(f, this).eval();
        }

        private TailCall<LList<A>> _dropWhile(Function<A, Boolean> f, LList<A> ls) {
            return (!ls.isEmpty()) && f.apply(ls.head()) ?
                    sus(() -> _dropWhile(f, ls.tail())) :
                    ret(ls);
        }

        public LList<A> init() {
            return this.reverse().drop(1).reverse();
        }

        public int size() {
            return foldRight(0, ignore -> y -> y + 1);
        }

        public <B> B foldLeft(B identity, Function<B, Function<A, B>> f) {
            return _foldLeft(this, identity, f).eval();
        }

        private <B> TailCall<B> _foldLeft(LList<A> ls, B identity, Function<B, Function<A, B>> f) {
            return ls.isEmpty()
                    ? ret(identity)
                    : _foldLeft(ls.tail(), f.apply(identity).apply(ls.head()), f);

        }

        public LList<A> reverse() {
            return _reverse(this, list()).eval();
        }

        public TailCall<LList<A>> _reverse(LList<A> ls, LList<A> acc) {
            return ls.isEmpty()
                    ? ret(acc)
                    : sus(() -> _reverse(ls.tail(), acc.cons(ls.head())));
        }

        static public <A> LList<A> reverseViaFoldLeft(LList<A> ls) {
            return ls.foldLeft(list(), x -> x::cons);
        }

        public static <A, B> B foldRightViaFoldLeft(B acc, LList<A> ls, Function<A, Function<B, B>> f) {
            return ls.reverse().foldLeft(acc, x -> y -> f.apply(y).apply(x));
        }

        public static <A> LList<A> concatViaFold(LList<A> ls, LList<A> ol) {
            return ls.foldRight(ol, x -> y -> new Cons<>(x, y));
        }

        public static <A> LList<A> setHead(LList<A> list, A h) {
            return list.setHead(h);
        }


        public boolean isEmpty() {
            return false;
        }

        public <B> B foldRight(B acc, Function<A, Function<B, B>> f) {
            return _foldRight(this, acc, f).eval();

        }

        public <B> LList<B> map(Function<A, B> f) {
            return foldRight(list(), x -> y -> y.cons(f.apply(x)));
        }

        public LList<A> filter(Function<A, Boolean> p) {
            return foldRight(list(), x -> y -> p.apply(x) ? y.cons(x) : y);
        }

        public <B> LList<B> flatMap(Function<A, LList<B>> f) {

            return foldRight(list(), x -> y -> concat(y, (f.apply(x))));
        }

        public static <A, B> B foldRight(LList<A> ls, B acc, Function<A, Function<B, B>> f) {
            return ls.foldRight(acc, f);
        }

        private <B> TailCall<B> _foldRight(LList<A> ls, B acc, Function<A, Function<B, B>> f) {
            return ls.isEmpty()
                    ? ret(acc)
                    : _foldRight(ls.tail(), f.apply(ls.head()).apply(acc), f);
        }

        public static Integer sum(LList<Integer> ls) {
            return ls.foldLeft(0, x -> y -> x + y);
        }

        public static Double product(LList<Double> ls) {
            return ls.foldLeft(1.0, x -> y -> x * y);
        }

        public static LList<Integer> multiplyByThree(LList<Integer> ls) {
            return ls.foldLeft(list(), (LList<Integer> x) -> (Integer y) -> x.cons(y));
        }

        public static LList<String> doubleToString(LList<Double> ls) {
            return ls.foldRight(list(), x -> y -> y.cons(x.toString()));
        }

        public static <A> Integer lengthViaFoldLeft(LList<A> list) {
            return list.foldLeft(0, x -> ignore -> x + 1);
        }

        public static <A> LList<A> flatten(LList<LList<A>> ls) {
            return ls.foldRight(list(), (LList<A> x) -> (LList<A> y) -> Cons.concat(x, y));
        }


        @SuppressWarnings("unchecked")
        public static <A> LList<A> list() {
            return NIL;
        }

        @SafeVarargs
        public static <A> LList<A> list(A... a) {
            LList<A> n = list();
            for (int i = a.length - 1; i >= 0; i--) {
                n = new Cons<>(a[i], n);
            }
            return n;
        }


    }

    public static void main(String[] args) {
        LList<Integer> ls = Cons.list(1, 2, 3, 4);
        ls.cons(0);
        ls.setHead(1000);
        System.out.println(ls.toString());
        ls = ls.cons(0).drop(1);
        System.out.println(ls.toString());
        ls = ls.dropWhile(x -> x < 4);
        System.out.println(ls.toString());
        LList<Integer> ls1 = Cons.list();
        LList<Integer> ls2 = Cons.list(9, 10, 11, 12);
        LList<Integer> ls3 = Cons.concat(ls2, ls1);
        System.out.println(ls3.reverse());
        System.out.println(ls3.init());
        System.out.println(ls3.size());

        System.out.println(Cons.lengthViaFoldLeft(ls3));
        System.out.println(Cons.reverseViaFoldLeft(ls2));
        LList<LList<Integer>> nestedList = Cons.list();
        nestedList = nestedList.cons(Cons.list(1, 2, 3));
        nestedList = nestedList.cons(Cons.list(4, 5, 6));
        System.out.println(nestedList);
        System.out.println(Cons.flatten(nestedList));
        System.out.println(nestedList.filter(x -> x.head() == 1));
    }

}

