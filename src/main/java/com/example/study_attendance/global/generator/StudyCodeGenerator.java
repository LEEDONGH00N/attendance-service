package com.example.study_attendance.global.generator;

import java.util.Random;

public class StudyCodeGenerator {

    private static final String BASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LENGTH = 6;
    private static final Random random = new Random();

    public static String generateCode() {
        StringBuilder sb = new StringBuilder(LENGTH);

        for (int i = 0; i < LENGTH; i++) {
            int idx = random.nextInt(BASE.length());
            sb.append(BASE.charAt(idx));
        }

        return sb.toString();
    }
}