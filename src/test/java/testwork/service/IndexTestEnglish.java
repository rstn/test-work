package testwork.service;


import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections4.Trie;
import org.junit.Before;
import org.junit.Test;

public class IndexTestEnglish {

    private static final String TEST_STRING = "!fd [addfgd] s-b,   fdgfd. \n  zzzD ojgdfgsd. fd";

    Trie<String, Set<Integer>> trie;

    @Before
    public void setup() throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader(TEST_STRING));
        FileIndex index = new FileIndex();
        index.createFromFile(reader, FileIndex.DEFAULT_LOCALE);
        trie = index.trie;
    }

    @Test
    public void testCreate() {
        assertNotNull(trie);
        assertEquals(6, trie.size());
    }

    @Test
    public void testGetIndexForWord() {
        assertEquals(new HashSet<>(asList(20)), trie.get("fdgfd"));
        assertEquals(new HashSet<>(asList(1, 44)), trie.get("fd"));
    }
}
