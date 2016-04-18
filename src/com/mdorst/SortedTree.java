package com.mdorst;

public class SortedTree<E extends Comparable<? super E>> {

    class Node {
        E data;
        Node left, right;
        int depth;
        int balance;

        Node() {
            depth = balance = 0;
        }
    }

    private Node node;

    public SortedTree() {
        node = new Node();
    }

    /**
     * Insert an element into the tree.
     * If this element already exist in the tree, insertion will fail,
     * returning {@code false}.
     * Throws NullPointerException if the specified element is null.
     * @param e The element to be inserted.
     * @return {@code true} if the element was successfully inserted.
     */
    public boolean insert(E e) {
        return insert(node, e);
    }

    private boolean insert(Node n, E e) {
        if (n.depth == 0) {
            n.data = e;
            n.left = new Node();
            n.right = new Node();
        } else {
            // if the element is already in the tree, return false
            if (e.compareTo(n.data) == 0)
                return false;
            if (e.compareTo(n.data) < 0) {
                if (insert(n.left, e)) {
                    n.balance--;
                } else return false;
            }
            else {
                if (insert(n.right, e)) {
                    n.balance++;
                } else return false;
            }
        }
        return true;
    }
}
