package me.jduv.java.util;

/**
 * Simple string utility class.
 */
public final class Strings {
    private final static String EMPTY_STRING = "";

    /**
     * Checks to see if the target string is null or empty.
     * 
     * @param s
     *            The string to check.
     * @returns True if the target string is null or empty, false otherwise.
     */
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    /**
     * Checks to see if the target string is null or whitespace.
     * 
     * @param s
     *            The string to check.
     * @return True if the target string is null or whitespace, false otherwise.
     */
    public static boolean isNullOrWhitespace(String s) {
        return s == null || s.trim().isEmpty();
    }

    /**
     * Trims the target string only if it isn't null.
     * 
     * @param s
     *            The string to trim.
     * @return The trimmed string or null.
     */
    public static String safeTrim(String s) {
        return s != null ? s.trim() : s;
    }

    /**
     * Returns an empty string.
     * 
     * @return An empty string.
     */
    public static String empty() {
        return EMPTY_STRING;
    }
}
