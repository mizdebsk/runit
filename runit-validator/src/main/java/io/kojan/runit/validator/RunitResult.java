package io.kojan.runit.validator;

import java.util.ArrayDeque;
import java.util.Deque;

import org.fedoraproject.javapackages.validator.spi.Decorated;
import org.fedoraproject.javapackages.validator.spi.LogEntry;
import org.fedoraproject.javapackages.validator.spi.LogEvent;
import org.fedoraproject.javapackages.validator.spi.Result;
import org.fedoraproject.javapackages.validator.spi.ResultBuilder;
import org.fedoraproject.javapackages.validator.spi.TestResult;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.TestExecutionResult.Status;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;

import io.kojan.javadeptools.rpm.RpmPackage;
import io.kojan.runit.api.GlobalContext;
import io.kojan.runit.api.Mismatch;
import io.kojan.runit.engine.TestCase;

public class RunitResult implements TestExecutionListener {

    private final ResultBuilder rb = new ResultBuilder();
    private final Deque<TestIdentifier> stack = new ArrayDeque<>();
    private final Deque<Boolean> stack2 = new ArrayDeque<>();

    public RunitResult(TestCase test, GlobalContext context) {
        rb.addLog(LogEntry.debug("Running test case {0}", Decorated.struct(test.getDisplayName())));
        for (RpmPackage rpmPackage : context.getRpmPackages()) {
            rb.addLog(LogEntry.debug("Context RPM {0}", Decorated.rpm(rpmPackage)));
        }
    }

    @Override
    public void testPlanExecutionStarted(TestPlan testPlan) {
    }

    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
    }

    @Override
    public void dynamicTestRegistered(TestIdentifier testIdentifier) {
    }

    @Override
    public void executionSkipped(TestIdentifier testIdentifier, String reason) {
    }

    @Override
    public void executionStarted(TestIdentifier id) {
        stack.push(id);
        stack2.push(false);
    }

    @Override
    public void executionFinished(TestIdentifier id, TestExecutionResult r) {

        stack.pop();
        boolean hasChildren = stack2.pop();
        if (stack2.isEmpty()) {
            return;
        }
        stack2.pop();
        stack2.push(true);
        if (hasChildren && r.getStatus().equals(Status.SUCCESSFUL)) {
            return;
        }
        if (stack2.size() < 2 && r.getStatus().equals(Status.SUCCESSFUL)) {
            return;
        }

        TestResult result = switch (r.getStatus()) {
        case SUCCESSFUL -> TestResult.pass;
        case ABORTED -> TestResult.skip;
        default -> {
            if (r.getThrowable().isPresent() && r.getThrowable().get() instanceof AssertionError) {
                yield TestResult.fail;
            }
            if (hasChildren) {
                yield TestResult.warn;
            }
            yield TestResult.error;
        }
        };

        rb.mergeResult(result);

        LogEvent event = switch (result) {
        case skip -> LogEvent.skip;
        case pass -> LogEvent.pass;
        case info -> LogEvent.info;
        case warn -> LogEvent.warn;
        case fail -> LogEvent.fail;
        case error -> LogEvent.error;
        };
        LogEntryBuilder log = new LogEntryBuilder(event);

        String dn = id.getDisplayName();
        if (dn.startsWith("[")) {
            dn = stack.peek().getDisplayName() + " " + dn;
        }
        log.append(dn);

        if (r.getThrowable().isPresent()) {
            log.append(" ");
            Throwable t = r.getThrowable().get();
            if (t instanceof Mismatch e) {
                Decorated NL = Decorated.plain(System.lineSeparator());
                log.append(e.getReason());
                log.append(NL);
                log.append("Expected: ");
                DecoratedDescription expDescr = new DecoratedDescription(log, Decorated::expected);
                expDescr.appendDescriptionOf(e.getMatcher());
                log.append(NL);
                log.append(" but: ");
                DecoratedDescription actDescr = new DecoratedDescription(log, Decorated::actual);
                e.getMatcher().describeMismatch(e.getValue(), actDescr);
            } else {
                log.append(Decorated.plain(t.toString()));
            }
        }

        rb.addLog(log.build());
    }

    public Result finish() {
        rb.addLog(LogEntry.debug("Overal test result is {0}", Decorated.actual(rb.getResult())));
        return rb.build();
    }

}
