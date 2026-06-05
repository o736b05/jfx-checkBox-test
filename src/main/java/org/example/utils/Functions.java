package org.example.utils;
import java.util.Random;
public class Functions {
    public static class MyException extends RuntimeException {
        public MyException(String message) {
            super(message);
        }
    }

    public static void MaybeGetException() {
        System.out.println(String.format("генерируем bool"));
        Random random = new Random();
        if (random.nextBoolean()) {
            throw new MyException("Специально сгенерированная ошибка");
        }
    }
}
