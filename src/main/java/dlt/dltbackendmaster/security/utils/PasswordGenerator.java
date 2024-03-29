package dlt.dltbackendmaster.security.utils;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @author https://mkyong.com/java/java-password-generator-example/
 *
 */
public class PasswordGenerator
{
    private static final String CHAR_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPERCASE = CHAR_LOWERCASE.toUpperCase();
    private static final String DIGIT = "0123456789";
    private static final String OTHER_PUNCTUATION = "!@#&()–:;',?/*";
    // FIXME: Allow all these characters... currenty is giving CORS error
    // private static final String OTHER_PUNCTUATION = "!@#&()–[{}]:;',?/*";
    private static final String OTHER_SYMBOL = "~$^+=<>";
    private static final String OTHER_SPECIAL = OTHER_PUNCTUATION + OTHER_SYMBOL;
    private static final int PASSWORD_LENGTH = 8;
    private static final String PASSWORD_ALLOW = CHAR_LOWERCASE + CHAR_UPPERCASE + DIGIT + OTHER_SPECIAL;
    private static SecureRandom random = new SecureRandom();

    public static String generateStrongPassword() {
        StringBuilder result = new StringBuilder(PASSWORD_LENGTH);
        String strLowerCase = generateRandomString(CHAR_LOWERCASE, 2);
        result.append(strLowerCase);
        String strUppercaseCase = generateRandomString(CHAR_UPPERCASE, 1);
        result.append(strUppercaseCase);
        String strDigit = generateRandomString(DIGIT, 2);
        result.append(strDigit);
        String strSpecialChar = generateRandomString(OTHER_SPECIAL, 1);
        result.append(strSpecialChar);
        String strOther = generateRandomString(PASSWORD_ALLOW, PASSWORD_LENGTH - 6);
        result.append(strOther);
        String password = result.toString();
        return shuffleString(password);
    }

    // generate a random char[], based on `input`
    private static String generateRandomString(String input, int size) {
        if (input == null || input.length() <= 0)
            throw new IllegalArgumentException("Invalid input.");
        if (size < 1)
            throw new IllegalArgumentException("Invalid size.");
        StringBuilder result = new StringBuilder(size);

        for (int i = 0; i < size; i++) {
            // produce a random order
            int index = random.nextInt(input.length());
            result.append(input.charAt(index));
        }
        return result.toString();
    }

    // for final password, make it more random
    public static String shuffleString(String input) {
        List<String> result = Arrays.asList(input.split(""));
        Collections.shuffle(result);
        // java 8
        return result.stream().collect(Collectors.joining());
    }
}
