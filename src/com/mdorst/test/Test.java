package com.mdorst.test;

import com.mdorst.SortedTree;

public class Test {
    private static TestRunner test;
    private static SortedTree<Integer> tree;

    public static void main(String[] args) {
        test = new TestRunner();
        tree = new SortedTree<>();
        test.shouldThrow(tree, "insert", null, Comparable.class, NullPointerException.class);
        test.done();
    }
}
