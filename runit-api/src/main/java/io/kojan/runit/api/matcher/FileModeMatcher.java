package io.kojan.runit.api.matcher;

import org.hamcrest.Description;

import io.kojan.javadeptools.rpm.RpmFile;

class FileModeMatcher extends AbstractFileMatcher {
    private final String mode;

    public FileModeMatcher(String mode) {
        this.mode = mode;
    }

    private String modeOf(RpmFile file) {
        return Integer.toOctalString(file.getMode() & 07777);
    }

    @Override
    protected boolean matchesSafely(RpmFile file) {
        return modeOf(file).equals(mode);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("RPM file with mode");
        description.appendValue(mode);
    }

    @Override
    protected void describeMismatchSafely(RpmFile file, Description description) {
        super.describeMismatchSafely(file, description);
        description.appendText(" wit mode ");
        description.appendValue(modeOf(file));
    }
}
