package com.sitescope.util;

public class WordCounter {

    public static int countWords(String text) {
        if (text == null || text.isBlank()) {
            return 0;
        }

        String[] words = text.trim().split("\\s+");
        int count = 0;
        for (String word : words) {
            if (word.matches(".*[a-zA-Z0-9].*")) {
                count++;
            }
        }
        return count;
    }
}
