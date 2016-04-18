package com.mdorst;

public class SortedTree<E extends Comparable<? super E>> {

    class Balance {
        int left;
        int right;

        Balance() {
            left = right = 0;
        }
    }

    class Node {
        E data;
        Node left, right;
        int depth;
        Balance balance;

        Node() {
            depth = 0;
            balance = new Balance();
        }
    }

    private Node node;

    /**
     * Insert an element into the tree.
     * Throws NullPointerException if the specified element is null.
     * @param e The element to be inserted.
     */
    public boolean insert(E e) {
        return insert(node, e);
    }

    private boolean insert(Node n, E e) {
        if (n.depth == 0) {

        }
        if (e.compareTo(n.data) == 0)
            return false;
        if (e.compareTo(n.data) < 0)
            return insert(n.left, e);
        else
            return insert(n.right, e);
    }
}
