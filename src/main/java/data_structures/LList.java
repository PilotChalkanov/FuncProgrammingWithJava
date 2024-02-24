package data_structures;

public abstract class LList<A> {
    public A head;
    public LList<A> tail;

    public abstract boolean isEmpty();

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

}

