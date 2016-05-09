package com.mdorst.functional;

/**
 * A specialization of {@code Process<String>} which will print anything passed to {@code call()}.
 */
public class Printer implements Process<String> {
    public void call(String s) {
        System.out.println(s);
    }
}
