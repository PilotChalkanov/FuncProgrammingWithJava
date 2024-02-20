package chaining;

import java.util.Objects;

@FunctionalInterface
public interface IConsumer <T>{
    void consume(T t);

// Implementing our own chaining method
    default IConsumer<T> thanAccept(IConsumer<T> next){

        Objects.requireNonNull(next);

        return (T t) -> {
            this.consume(t);
            next.consume(t);
        };
    }
}
