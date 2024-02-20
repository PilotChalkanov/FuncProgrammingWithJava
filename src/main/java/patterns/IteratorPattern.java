package patterns;

public class IteratorPattern {
    public static void main(String[] args) {
        MyArrayList list = new MyArrayList(new Object[]{1, 2, 3, 4, 5});
        list.forEach(System.out::println);
    }
}
