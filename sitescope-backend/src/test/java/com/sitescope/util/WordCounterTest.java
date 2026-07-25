package com.sitescope.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordCounterTest {

    @Test
    void testCountWordsWithNormalText() {
        assertEquals(4, WordCounter.countWords("The quick brown fox"));
    }

    @Test
    void testCountWordsWithExtraWhitespace() {
        assertEquals(3, WordCounter.countWords("   Spring   Boot   Framework  "));
    }

    @Test
    void testCountWordsWithNullOrBlank() {
        assertEquals(0, WordCounter.countWords(null));
        assertEquals(0, WordCounter.countWords("   "));
    }

    @Test
    void testCountWordsWithPunctuationAndSpecialChars() {
        assertEquals(5, WordCounter.countWords("Hello, world! SiteScope analyzer #1."));
    }
}
