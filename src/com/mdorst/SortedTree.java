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
        /**
         * Null is not allowed. Throw an error if e is null.
         */
        if (e == null) {
            throw new NullPointerException();
        }
        /**
         * Call the actual recursive insert method
         */
        return insert(node, e);
    }

    /**
     * This method recursively searches the tree until it finds
     * the correct place to insert the element {@code e}.
     * @param n The current node being searched
     * @param e The element to be inserted
     * @return {@code true} if the element was successfully inserted.
     */
    private boolean insert(Node n, E e) {
        /**
         * If depth = 0, insert the element and return up the stack.
         */
        if (n.depth == 0) {
            n.data = e;
            n.left = new Node();
            n.right = new Node();
            return true;
        } else {
            /**
             * If the element is already in the tree, return false.
             */
            if (e.compareTo(n.data) == 0)
                return false;
            /**
             * If e < n.data, insert on the left subtree.
             * If e > n.data, insert on the right subtree.
             * If the insertion succeeds, modify this node's balance.
             * If the insertion fails, return false.
             */
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
