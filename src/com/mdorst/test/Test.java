package com.mdorst.test;

import com.mdorst.SortedTree;

public class Test {

    public static void main(String[] args) {
        TestRunner test = new TestRunner();
        SortedTree<Integer> tree = new SortedTree<>();
        test.shouldThrow(tree, "insert", null, Comparable.class, NullPointerException.class);
        test.done();
    }
}
