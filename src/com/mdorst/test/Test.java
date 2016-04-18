package com.mdorst.test;

import com.mdorst.SortedTree;

import java.lang.reflect.Method;

public class Test {
    private static TestRunner test;
    private static SortedTree<Integer> tree;

    public static void main(String[] args) {
        test = new TestRunner();
        tree = new SortedTree<>();
        shouldThrow(tree, "insert", null, Comparable.class, NullPointerException.class);
        test.done();
    }

    /**
     * Tests that a method throws a specific exception.
     * @param receiver The object that the method must be called on
     * @param methodName The name of the method to be called
     * @param paramClass The class of the parameter to be passed to the method
     * @param param The parameter to be passed to the method
     * @param exceptionType The type of exception that the method is expected to throw
     */
    private static void shouldThrow(Object receiver, String methodName, Object param,
                                    Class<?> paramClass,
                                    Class<? extends Throwable> exceptionType) {
        Class<?> c = receiver.getClass();
        Method m;
        try {
            m = c.getMethod(methodName, paramClass);
        } catch (NoSuchMethodException e) {
            test.fail("No such method: " + methodName + " - for class: " + c.getName());
            return;
        }
        try {
            m.invoke(tree, param);
            return;
        } catch (Throwable e) {
            if (exceptionType.isInstance(e)) {
                test.pass(m + " throws " + exceptionType.getName());
            }
        }
        test.fail(m + " does not throw " + exceptionType.getName());
    }
}
