package io.kojan.runit.api.matcher;

import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.hamcrest.Description;

import io.kojan.javadeptools.rpm.RpmInfo;

class PackageNameMatcher extends AbstractPackageMatcher {
    private final Pattern pattern;
    private final Predicate<String> predicate;

    public PackageNameMatcher(String regex) {
        pattern = Pattern.compile(regex);
        predicate = pattern.asMatchPredicate();
    }

    @Override
    protected boolean matchesSafely(RpmInfo rpm) {
        return predicate.test(rpm.getName());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("RPM name matching pattern \"");
        description.appendValue(pattern);
        description.appendText("\"");
    }
}
