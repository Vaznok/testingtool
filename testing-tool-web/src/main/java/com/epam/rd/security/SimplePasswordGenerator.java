package com.epam.rd.security;

import java.util.Random;

public class SimplePasswordGenerator implements PasswordGenerator {

    private static final char[] SYMBOLS =
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    private final Random random;
    private final char[] password;

    public SimplePasswordGenerator(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("length < 1: " + length);
        }
        password = new char[length];
        random = new Random();
    }

    @Override
    public String generatePassword() {
        for (int idx = 0; idx < password.length; idx++) {
            password[idx] = SYMBOLS[random.nextInt(SYMBOLS.length)];
        }
        return new String(password);
    }

}
