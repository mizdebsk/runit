package io.kojan.runit.api.matcher;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.hamcrest.Description;

import io.kojan.javadeptools.rpm.RpmDependency;
import io.kojan.javadeptools.rpm.RpmInfo;

class DependencyMatcher extends AbstractPackageMatcher {
    private final String kind;
    private final Function<RpmInfo, List<RpmDependency>> getter;
    private final Pattern pattern;
    private final Predicate<List<RpmDependency>> predicate;

    public DependencyMatcher(String kind, Function<RpmInfo, List<RpmDependency>> getter, String regex) {
        this.kind = kind;
        this.getter = getter;
        pattern = Pattern.compile(regex);
        Predicate<String> depPredicate = pattern.asMatchPredicate();
        predicate = deps -> deps.stream().map(RpmDependency::toString).anyMatch(depPredicate);
    }

    @Override
    protected boolean matchesSafely(RpmInfo rpm) {
        return predicate.test(getter.apply(rpm));
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("package that ");
        description.appendText(kind);
        description.appendText(" ");
        description.appendValue(pattern);
    }

    @Override
    protected void describeMismatchSafely(RpmInfo rpm, Description description) {
        super.describeMismatchSafely(rpm, description);
        description.appendText(" with the following ");
        description.appendText(kind);
        description.appendText(":");
        String separator = "\n" + " ".repeat(14) + kind + ": ";
        description.appendValueList(separator, separator, "", getter.apply(rpm));
    }
}
