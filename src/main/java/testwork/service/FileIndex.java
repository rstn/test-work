package testwork.service;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;

import testwork.exception.ResourceNotFoundException;
import testwork.index.LocaleIndex;

public class FileIndex implements LocaleIndex {

    public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

    protected Trie<String, Set<Integer>> trie = new PatriciaTrie<>();

    @Override
    public void create(String fileName) throws ResourceNotFoundException {
        create(fileName, DEFAULT_LOCALE);
    }

    @Override
    public void create(String fileName, Locale locale) throws ResourceNotFoundException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            createFromFile(reader, locale);
        } catch (IOException e) {
            throw new ResourceNotFoundException(e);
        }
    }

    public void createFromFile(BufferedReader reader, Locale locale) throws IOException {
        BreakIterator breakIterator = BreakIterator.getWordInstance(locale);
        String currentLine;
        Integer pos = 0;
        int lastIndex;
        int firstIndex;
        while ((currentLine = reader.readLine()) != null) {
            breakIterator.setText(currentLine);
            lastIndex = breakIterator.first();
            while (lastIndex != BreakIterator.DONE) {
                firstIndex = lastIndex;
                lastIndex = breakIterator.next();
                if (lastIndex != BreakIterator.DONE && Character.isLetterOrDigit(currentLine.charAt(firstIndex))) {
                    String currentWord = currentLine.substring(firstIndex, lastIndex);
                    putWordToTrie(currentWord, pos + firstIndex);
                }
            }
            pos += currentLine.length();
        }
    }

    public Set<Integer> findWordPositions(String word) {
        return trie.get(word);
    }

    private void putWordToTrie(String key, Integer pos) {
        if (isNotBlank(key)) {
            Set<Integer> index = trie.get(key);
            if (index == null) {
                trie.put(key,  new HashSet<>(asList(pos)));
            } else {
                index.add(pos);
                trie.put(key, index);
            }
        }
    }
}
