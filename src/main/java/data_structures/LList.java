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

}

