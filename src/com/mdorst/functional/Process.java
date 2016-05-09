package com.mdorst.functional;

/**
 * Represents a generic process.
 * @param <T> The type of data to be processed.
 */
public interface Process<T> {
    void call(T t);
}
