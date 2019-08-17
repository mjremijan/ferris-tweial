package org.ferris.tweial._junit.hamcrest;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan Remijan mjremijan@yahoo.com @mjremijan
 */
public class StartsWithMatcher extends BaseMatcher<String> {
    private String startsWith;
    
    public StartsWithMatcher(String startsWith) {
        this.startsWith = startsWith;
    }

    @Override
    public boolean matches(Object item) {
        return item != null && ((String) item).startsWith(startsWith);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(String.format("starting with \"%s\"", startsWith));
    }
}
