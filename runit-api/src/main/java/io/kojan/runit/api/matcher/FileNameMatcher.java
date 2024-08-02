package io.kojan.runit.api.matcher;

import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.hamcrest.Description;

import io.kojan.javadeptools.rpm.RpmFile;

class FileNameMatcher extends AbstractFileMatcher {
    private final Pattern pattern;
    private final Predicate<String> predicate;

    public FileNameMatcher(String regex) {
        pattern = Pattern.compile(regex);
        predicate = pattern.asMatchPredicate();
    }

    @Override
    protected boolean matchesSafely(RpmFile file) {
        return predicate.test(file.getName());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("RPM file name matching pattern \"");
        description.appendValue(pattern);
        description.appendText("\"");
    }
}
