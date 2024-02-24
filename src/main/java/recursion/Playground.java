package recursion;

import java.math.BigInteger;

import static recursion.TailCall.ret;
import static recursion.TailCall.sus;

public class Playground {
    static int add(int x, int y) {
        return addRec(x, y).eval();
    }

    static TailCall<Integer> addRec(int x, int y) {
        return y == 0 ?
                ret(x) :
                sus(() -> addRec(x + 1, y - 1));

    }

    public static int fibonacci(int number) {
        if (number == 0 || number == 1) {
            return number;
        }
        return fibonacci(number - 1) + fibonacci(number - 2);
    }


    public static BigInteger fib(int x) {
        return fib_(BigInteger.ONE, BigInteger.ZERO, BigInteger.valueOf(x)).eval();
    }

    private static TailCall<BigInteger> fib_(BigInteger acc1, BigInteger acc2,
                                             BigInteger x) {
        if (x.equals(BigInteger.ZERO)) {
            return ret(BigInteger.ZERO);
        } else if (x.equals(BigInteger.ONE)) {
            return ret(acc1.add(acc2));
        } else {
            return sus(() -> fib_(acc2, acc1.add(acc2), x.subtract(BigInteger.ONE)));
        }
    }

    public static long fibonacciIterative(int n) {
        if (n <= 1) {
            return n;
        }

        long fibNMinus2 = 0;
        long fibNMinus1 = 1;
        long fibN = 0;

        for (int i = 2; i <= n; i++) {
            fibN = fibNMinus1 + fibNMinus2;
            fibNMinus2 = fibNMinus1;
            fibNMinus1 = fibN;
        }

        return fibN;
    }

    public static void main(String[] args) {
        System.out.println(add(4, 500));

        long startTime = System.nanoTime();
        System.out.println(fib(5000));
        long endTime = System.nanoTime();

        // Calculate elapsed time in milliseconds
        long elapsedTimeInMilliseconds = (endTime - startTime) / 1_000_000;

        System.out.println("Elapsed Time: " + elapsedTimeInMilliseconds + " ms");

        long startTime2 = System.nanoTime();
        System.out.println(fibonacciIterative(5000));
        long endTime2 = System.nanoTime();

        // Calculate elapsed time in milliseconds
        long elapsedTimeInMilliseconds2 = (endTime2 - startTime2) / 1_000_000;

        System.out.println("Elapsed Time: " + elapsedTimeInMilliseconds2 + " ms");
    }


}
