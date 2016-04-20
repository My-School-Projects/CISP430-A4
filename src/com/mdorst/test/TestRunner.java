package com.mdorst.test;

import com.mdorst.test.function.ReturnTest;
import com.mdorst.test.function.Lambda;

import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * This class serves as a simple testing framework.
 * <p>
 * Calls to {@code assert*} and {@code log} are tracked, and the
 * associated messages are displayed on call to {@code done},
 * depending on the verbosity settings.
 */
public class TestRunner {
    private List<String> log;
    private PrintStream stream;
    private int passCount;
    private int failCount;
    /**
     * If {@code false}, only failed test expectations will be
     * displayed. If {@code true}, all expectations and log messages
     * will be displayed.
     * Default: {@code false}
     */
    public boolean verbose = false;

    private class Color {
        public static final String RED = "\u001B[31m";
        public static final String GREEN = "\u001B[32m";
        public static final String RESET = "\u001B[0m";
    }

    public void expect(boolean assertion, String expectation) {
        if (assertion) {
            pass(expectation);
        } else {
            fail(expectation);
        }
    }

    public void pass(String result) {
        stream.print(Color.GREEN + "." + Color.RESET);
        if (verbose)
            log.add(Color.GREEN + result + Color.RESET);
        passCount++;
    }

    public void fail(String failure) {
        stream.print(Color.RED + "F" + Color.RESET);
        log.add(Color.RED + failure + Color.RESET);
        failCount++;
    }

    public void assertTrue(boolean assertion, String expression) {
        if (assertion) {
            expect(true, expression + " == true");
        } else {
            expect(false, expression + " != true");
        }
    }

    public void assertFalse(boolean assertion, String expression) {
        if (!assertion) {
            expect(true, expression + " == false");
        } else {
            expect(false, expression + " != false");
        }
    }

    public void assertEqual(Object o1, Object o2, String expression) {
        if (Objects.equals(o1, o2)) {
            expect(true, expression + " == " + o2);
        } else {
            expect(false, expression + " != " + o2 +
                    ", " + expression + " == " + o1);
        }
    }

    /**
     * Test a code segment safely, with all exceptions caught and handled as test failures.
     * This method expects a lambda expression which contains the code to be tested.
     * This method is curried, and returns a {@code ReturnTest}.
     * The user is expected to call {@code returns} on this object, passing in the value
     * expected by the test.
     * Ex:
     * {@code
     *      function(() -> 3 + 2).returns(5);
     * }
     * @param f The code segment, usually a lambda, which returns the value to be tested.
     * @return The curried function to be called with the expected result.
     */
    public ReturnTest function(Lambda f) {
        return (obj) -> {
            Object result;
            try {
                result = f.call();
            } catch (Throwable e) {
                fail(e.getMessage() + " was thrown.");
                return;
            }
            if (obj != null && obj.equals(result))
                pass(result + " == " + obj);
            else
                fail(result + " != " + obj);
        };
    }

    /**
     * Tests that a method throws a specific exception.
     * @param receiver The object that the method must be called on
     * @param methodName The name of the method to be called
     * @param paramClass The class of the parameter to be passed to the method
     * @param param The parameter to be passed to the method
     * @param exceptionType The type of exception that the method is expected to throw
     */
    public void shouldThrow(Object receiver, String methodName, Object param,
                                    Class<?> paramClass,
                                    Class<? extends Throwable> exceptionType) {
        Class<?> c = receiver.getClass();
        Method m;
        try {
            m = c.getMethod(methodName, paramClass);
        } catch (NoSuchMethodException e) {
            fail("No such method: " + methodName + " - for class: " + c.getName());
            return;
        }
        try {
            m.invoke(receiver, param);
            return;
        } catch (Throwable e) {
            if (exceptionType.isInstance(e.getCause())) {
                pass(m + " throws " + exceptionType.getName());
                return;
            }
        }
        fail(m + " does not throw " + exceptionType.getName());
    }

    public void log(String message) {
        if (verbose) log.add(message);
    }

    /**
     * Prints "n passed, n failed", and displays any failed test expectations.
     * Displays passed test expectations and log messages if {@code verbose == true}
     */
    public void done() {
        stream.println("\n" + Color.GREEN + passCount + " passed, " +
                (failCount > 0 ? Color.RED : Color.GREEN) +
                failCount + " failed." + Color.RESET);
        log.forEach(stream::println);
    }

    public TestRunner(PrintStream ps) {
        stream = ps;
        log = new LinkedList<>();
    }

    public TestRunner() {
        this(System.out);
    }
}
