package com.mdorst.test.function;

/**
 * A functional interface which facilitates anonymous function declarations.
 * This class is used by {@code function().return()} pattern of {@code TestRunner}.
 */
public interface Lambda {
    Comparable<?> call();
}
