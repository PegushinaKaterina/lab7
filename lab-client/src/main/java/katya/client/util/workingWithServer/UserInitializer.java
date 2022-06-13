package katya.client.util.workingWithServer;

import katya.common.util.CheckBoolean;
import katya.common.util.Validator;

import java.util.*;

public final class UserInitializer {
    private UserInitializer() {
    }

    public static boolean acceptAnswer(Scanner scanner) throws IllegalArgumentException {
        boolean answer = true;
        try {
            String stringAnswer = scanner.nextLine().trim().toLowerCase(Locale.ROOT);
            answer = new Validator<Boolean>(stringAnswer)
                    .withCheckingNull(false)
                    .withCheckingFunction(CheckBoolean::checkBoolean, "Ответ должен быть да/нет")
                    .getValue();
        } catch (NoSuchElementException e) {
            System.out.println("Введен недопустимый символ");
            System.exit(1);
        }
        return answer;
    }

    public static boolean askForRegistration(String question, Scanner scanner) {
        System.out.println(question);
        boolean isRunning = true;
        boolean answer = true;
        while (isRunning) {
            try {
                answer = acceptAnswer(scanner);
                isRunning = false;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return answer;
    }

    public static List<String> getUser(Scanner scanner) {
        String login = null;
        boolean isRunning = true;
        while (isRunning) {
            try {
                login = inputLogin(scanner);
                isRunning = false;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        String password = null;
        isRunning = true;
        while (isRunning) {
            try {
                password = inputPassword(scanner);
                isRunning = false;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        List<String> user = new LinkedList<>();
        user.add(login);
        user.add(password);
        return user;
    }

    private static String inputLogin(Scanner scanner) throws IllegalArgumentException {
        String login = null;
        try {
            System.out.println("Введите имя пользователя, "
                    + "(Минимальная длина 5 символов)");
            login = scanner.nextLine().trim();

            login = new Validator<String>(login)
                    .withCheckingNull(false)
                    .withCheckingPredicate((arg) -> ((String) arg).length() >= 5,
                            "Имя пользователя должно содержать не менее 5 символов")
                    .getValue();

        } catch (NoSuchElementException e) {
            System.out.println("Введен недопустимый символ");
            System.exit(1);
        }
        return login;
    }

    private static String inputPassword(Scanner scanner) throws IllegalArgumentException {
        String password = null;
        try {
            System.out.println("Введите пароль, "
                    + "(Минимальная длина 5 символов)");
            password = scanner.nextLine().trim();
            password = new Validator<String>(password)
                    .withCheckingNull(false)
                    .withCheckingPredicate((arg) -> ((String) arg).length() >= 5,
                            "Пароль должен содержать не менее 5 символов")
                    .getValue();
        } catch (NoSuchElementException e) {
            System.out.println("Введен недопустимый символ");
            System.exit(1);
        }
        return password;
    }
}
