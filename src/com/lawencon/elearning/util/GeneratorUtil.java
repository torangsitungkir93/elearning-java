package com.lawencon.elearning.util;

import java.util.Random;

public class GeneratorUtil {
	private final static int CODE_LENGTH = 5;
	private final static String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		
	public static String generateRandomCode() {
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            final int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }
}
