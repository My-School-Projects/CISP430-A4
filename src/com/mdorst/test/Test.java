package com.mdorst.test;

import com.mdorst.SortedTree;

public class Test {

    public static void main(String[] args) {
        TestRunner test = new TestRunner();
        SortedTree<Integer> tree = new SortedTree<>();
        test.lambda(() -> tree.insert(null))
        .describe("tree.insert(null)")
        .throwsException(NullPointerException.class);
        test.done();
    }
}
