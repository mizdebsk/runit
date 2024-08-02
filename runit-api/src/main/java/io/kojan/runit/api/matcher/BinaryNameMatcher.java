package io.kojan.runit.api.matcher;

import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import io.kojan.javadeptools.rpm.RpmInfo;

class BinaryNameMatcher extends TypeSafeMatcher<RpmInfo> {
    private final Pattern pattern;
    private final Predicate<String> predicate;

    public BinaryNameMatcher(String regex) {
        pattern = Pattern.compile(regex);
        predicate = pattern.asMatchPredicate();
    }

    @Override
    protected boolean matchesSafely(RpmInfo rpm) {
        return !rpm.isSourcePackage() && predicate.test(rpm.getName());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("binary RPM name matching pattern \"");
        description.appendValue(pattern);
        description.appendText("\"");
    }

    @Override
    protected void describeMismatchSafely(RpmInfo rpm, Description description) {
        if (rpm.isSourcePackage()) {
            description.appendText("source RPM ");
            description.appendValue(rpm);
        } else {
            description.appendText("RPM ");
            description.appendValue(rpm);
        }
    }
}
