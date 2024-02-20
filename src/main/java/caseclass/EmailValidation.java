package caseclass;

import java.util.function.Function;
import static caseclass.Result.*;
import static caseclass.Case.*;
import java.util.regex.Pattern;

public class EmailValidation {

    static Pattern emailPattern =
            Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");

    static Function<String, Result<String>> emailChecker = s -> match(
            mcase(() -> success(s)), //default case
            mcase(() -> s == null, () -> failure("email must not be null")),
            mcase(() -> s.isEmpty(), () -> failure("email must not be empty")),
            mcase(() -> !emailPattern.matcher(s).matches(), () -> failure("email " + s + " is invalid"))
    );


    private static void logError(String s) {
        System.err.println("Error message logged: " + s);
    }

    private static void sendVerificationMail(String s) {
        System.out.println("Mail sent to " + s);
    }

    public static void main(String[] args) {
        emailChecker.apply("this.is@my.email").bind(sucess, failure);
    }

    static Effect<String> sucess = EmailValidation::sendVerificationMail;
    static Effect<String> failure = EmailValidation::logError;

}
