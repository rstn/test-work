package testwork.index;

import java.util.Locale;

import testwork.exception.ResourceNotFoundException;

public interface LocaleIndex extends Index {

    /**
     * Create index from source, use Locale for correct words dividing
     * @param resourceName
     * @param locale
     * @throws ResourceNotFoundException
     */
    void create(String resourceName, Locale locale) throws ResourceNotFoundException;
}
