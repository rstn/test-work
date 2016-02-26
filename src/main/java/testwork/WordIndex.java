package testwork;

import java.util.Locale;
import java.util.Set;

import testwork.exception.ResourceNotFoundException;
import testwork.index.LocaleIndex;
import testwork.service.FileIndex;

public class WordIndex {

    private LocaleIndex fileLocaleIndex = new FileIndex();

    public Set<Integer> getIndexesForWord(String word) {
        return fileLocaleIndex.findWordPositions(word);
    }

    public void readIndex(String filename, Locale locale) throws ResourceNotFoundException {
        fileLocaleIndex.create(filename, locale);
    }

    public void readIndex(String filename) throws ResourceNotFoundException {
        fileLocaleIndex.create(filename);
    }

}
