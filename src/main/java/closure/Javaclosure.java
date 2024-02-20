package closure;

public class Javaclosure {
    public static void main(String[] args) {
        int val = 111;

        Task lambda = () -> {
//            val = 13414; // This will throw an error
            System.out.println(val);
            System.out.println("Task Completed");
        };

        printValue(lambda);
    }

    private static void printValue(Task lambda) {
        lambda.doTask();
    }

}
