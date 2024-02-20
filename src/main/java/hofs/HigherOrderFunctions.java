package hofs;

public class HigherOrderFunctions {
    public static void main(String[] args) {
        IFactory<Integer> messageLength =  createFactory(() -> "Hello, World!", String::length);
        System.out.println(messageLength.create());
    }

    public static <T, R> IFactory<R> createFactory(IProducer<T> producer, IConfigurator<T, R> configurator) {
        return () -> {
            T instance = producer.produce();
            return configurator.configure(instance);
        };
    }
}
