package com.mdorst.test.function;

/**
 * A functional interface which facilitates anonymous lambda declarations.
 * This class is used by {@code lambda().test()} pattern of {@code TestRunner}.
 */
public interface Lambda {
    Comparable<?> call();
}
