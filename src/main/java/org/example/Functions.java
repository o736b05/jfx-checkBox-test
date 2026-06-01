package org.example;
import java.util.Random;
public class Functions {
    private static class MyException extends RuntimeException {
        public MyException(String message) {
            super(message);
        }
    }

    public static void MaybeGetException() {
        Random random = new Random();
        if (random.nextBoolean()) {
            throw new MyException("Специально сгенерированная ошибка");
        }
    }
}
