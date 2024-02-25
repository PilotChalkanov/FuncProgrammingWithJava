package data_structures;


import recursion.TailCall;

import static recursion.TailCall.ret;
import static recursion.TailCall.sus;

public abstract class LList<A> {
    public abstract A head();

    public abstract LList<A> tail();

    public abstract boolean isEmpty();

    public abstract LList<A> cons(A el);

    public abstract LList<A> setHead(A el);

    public abstract String toString();

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
                    sus(() -> _toString(ls.tail(), acc.append(ls.head())));

        }

        public static <A> LList<A> setHead(LList<A> list, A h) {
            return list.setHead(h);
        }


        public boolean isEmpty() {
            return false;
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
    }

}

