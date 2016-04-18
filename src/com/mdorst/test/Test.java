package com.mdorst.test;

import com.mdorst.SortedTree;

import java.lang.reflect.Method;

public class Test {
    private static TestRunner test;
    private static SortedTree<Integer> tree;

    public static void main(String[] args) {
        test = new TestRunner();
        tree = new SortedTree<>();
        shouldThrow(method(tree, "insert", Comparable.class), null, NullPointerException.class);
        test.done();
    }

    private static Method method(Object o, String methodName, Class<?> param) {
        Class<?> c = o.getClass();
        try {
            return c.getMethod(methodName, param);
        } catch (NoSuchMethodException e) {
            test.fail("No such method: " + methodName + " - for class: " + c.getName());
            return null;
        }
    }

    private static void shouldThrow(Method m, Object o, Class<? extends Throwable> exceptionType) {
        try {
            if (m == null) return; // Method not found, error already handled
            m.invoke(tree, o);
            test.fail(m + " does not throw " + exceptionType.getName());
        } catch (Throwable e) {
            if (exceptionType.isInstance(e)) {
                test.pass(m + " throws " + exceptionType.getName());
            }
        }
    }
}
