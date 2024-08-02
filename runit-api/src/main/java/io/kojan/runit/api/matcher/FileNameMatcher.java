package io.kojan.runit.api.matcher;

import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import io.kojan.javadeptools.rpm.RpmFile;

class FileNameMatcher extends TypeSafeMatcher<RpmFile> {
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

    @Override
    protected void describeMismatchSafely(RpmFile file, Description description) {
        description.appendText("RPM file ");
        description.appendValue(file);
    }
}
