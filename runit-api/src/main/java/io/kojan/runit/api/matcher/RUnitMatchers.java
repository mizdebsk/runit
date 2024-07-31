package io.kojan.runit.api.matcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import io.kojan.javadeptools.rpm.RpmInfo;

/**
 * A static class providing factory methods for creating Hamcrest
 * {@link Matcher}s related to RPM packages.
 * 
 * @author Mikolaj Izdebski
 */
public class RUnitMatchers {
    private RUnitMatchers() {
    }

    /**
     * Creates a Hamcrest {@link Matcher} that matches source RPM packages.
     * 
     * @return matcher for source RPMs
     */
    public static Matcher<RpmInfo> sourceRPM() {
        return new TypeSafeMatcher<>() {
            @Override
            protected boolean matchesSafely(RpmInfo rpm) {
                return rpm.isSourcePackage();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("source RPM");
            }

            @Override
            protected void describeMismatchSafely(RpmInfo rpm, Description description) {
                description.appendText("was binary RPM ").appendValue(rpm);
            }
        };
    }
}
