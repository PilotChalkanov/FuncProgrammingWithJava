package caseclass;
import data_structures.Tuple;
import java.util.function.Supplier;

public class Case<T> extends Tuple<Supplier<Boolean>, Supplier<Result<T>>> {

    private Case(Supplier<Boolean> conditionalSupplier, Supplier<Result<T>>resultSupplier){
        super(conditionalSupplier, resultSupplier);
    }
    private static class DefaultCase<T> extends Case<T> {
        boolean condition;

        DefaultCase(Supplier<Boolean> conditionalSupplier, Supplier<Result<T>> resultSupplier){
            super(conditionalSupplier, resultSupplier);

        }
    }
    public static <T> Case<T> mcase(Supplier<Boolean> condition,
                                    Supplier<Result<T>> result){
        return new Case(condition, result);
    }

    public static <T> DefaultCase<T> mcase(Supplier<Result<T>> result){
        return new DefaultCase<>(() -> true, result);
    }
    @SafeVarargs
    public static <T> Result<T> match(DefaultCase<T> defaultCase,
                                      Case<T>... matchers){
        for(Case<T> aCase: matchers)
            if (aCase._1.get()) return aCase._2.get();
        return defaultCase._2.get();
    }
}
