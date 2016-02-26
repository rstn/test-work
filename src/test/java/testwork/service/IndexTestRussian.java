package testwork.service;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.collections4.Trie;
import org.junit.Before;
import org.junit.Test;

public class IndexTestRussian {

    private static final String TEST_STRING = "!ав [ывапыва] ф-шг,   яавпвапвй. \n  Ёёявыапы ззапрап. ав";

    private static final Locale RUSSIAN_LOCALE = new Locale("ru", "RU");

    Trie<String, Set<Integer>> trie;

    @Before
    public void setup() throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader(TEST_STRING));
        FileIndex index = new FileIndex();
        index.createFromFile(reader, RUSSIAN_LOCALE);
        trie = index.trie;
    }

    @Test
    public void testCreate() {
        assertNotNull(trie);
        assertEquals(6, trie.size());
    }

    @Test
    public void testGetIndexForWord() {
        assertEquals(new HashSet<>(asList(44)), trie.get("ззапрап"));
        assertEquals(new HashSet<>(asList(1, 53)), trie.get("ав"));
    }
}
