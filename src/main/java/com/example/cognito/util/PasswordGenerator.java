package com.example.cognito.util;


import java.security.SecureRandom;

public class PasswordGenerator {

    private static SecureRandom random = new SecureRandom();

    /** different dictionaries used */
    private static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMERIC = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*_=+-/";

    /**
     * Method will generate random string based on the parameters
     *
     * @param len
     *            the length of the random string
     * @param dic
     *            the dictionary used to generate the password
     * @return the random password
     */
    public static String generatePassword(int len, String dic) {
        String result = "";
        for (int i = 0; i < len; i++) {
            int index = random.nextInt(dic.length());
            result += dic.charAt(index);
        }
        return result;
    }

    public static String generatePassword() {
        String dic = ALPHA_CAPS + ALPHA + SPECIAL_CHARS + NUMERIC ;
        int len = 15;
        String result = "";
        for (int i = 0; i < len; i++) {
            int index = random.nextInt(dic.length());
            result += dic.charAt(index);
        }
        return result;
    }

    public static String generatePasswordWithGuideline() {
        String dic = ALPHA_CAPS + ALPHA + SPECIAL_CHARS + NUMERIC ;
        int len = 15;
        String result = "" + ALPHA_CAPS.charAt(random.nextInt(ALPHA_CAPS.length())) +
                ALPHA.charAt(random.nextInt(ALPHA.length())) +
                SPECIAL_CHARS.charAt(random.nextInt(SPECIAL_CHARS.length())) +
                NUMERIC.charAt(random.nextInt(NUMERIC.length()));

        for (int i = 0; i < len-4; i++) {
            int index = random.nextInt(dic.length());
            result += dic.charAt(index);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("Password Generator Examples");
        System.out.println();

        int len = 10;
        System.out.println("Alphanumeric password, length " + len + " chars: ");
        String password = generatePassword(len, ALPHA_CAPS + ALPHA);
        System.out.println(password);
        System.out.println();

        len = 20;
        System.out.println("Alphanumeric + special password, length " + len + " chars: ");
        password = generatePassword(len, ALPHA_CAPS + ALPHA + SPECIAL_CHARS);
        System.out.println(password);
        System.out.println();

        len = 15;
        System.out.println("Alphanumeric + numeric + special password, length " + len + " chars: ");
        password = generatePassword(len, ALPHA_CAPS + ALPHA + SPECIAL_CHARS + NUMERIC);
        System.out.println(password);
        System.out.println();
    }
}