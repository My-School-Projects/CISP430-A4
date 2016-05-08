package com.mdorst;

public class SortedTree<E extends Comparable<? super E>> {

    class Node {
        E data;
        Node left, right;
    }

    private Node node;

    public SortedTree() {
        node = new Node();
    }

    public void traverseAscending(Process<E> process) {
        traverseAscending(node, process);
    }

    private void traverseAscending(Node n, Process<E> process) {
        if (n.data == null) return;
        traverseAscending(n.left, process);
        process.call(n.data);
        traverseAscending(n.right, process);
    }

    public void traverseDescending(Process<E> process) {
        traverseDescending(node, process);
    }

    public void traverseDescending(Node n, Process<E> process) {
        if (n.data == null) return;
        traverseDescending(n.right, process);
        process.call(n.data);
        traverseDescending(n.left, process);
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
         * If n.data is null, insert the element and return up the stack.
         */
        if (n.data == null) {
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
             */
            if (e.compareTo(n.data) < 0) {
                return insert(n.left, e);
            }
            else {
                return insert(n.right, e);
            }
        }
    }
}
