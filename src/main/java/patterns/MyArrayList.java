package patterns;

import chaining.IConsumer;

public class MyArrayList {
    Object [] elements = new Object[5];

    public MyArrayList(Object[] elements) {
        this.elements = elements;
    }
    public void forEach(IConsumer<Object> action){
        for (Object element : elements) {
            action.consume(element);
        }
    }
}
