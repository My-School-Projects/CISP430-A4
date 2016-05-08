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

    /**
     * Traverse the tree in ascending order, calling {@code process} on each node.
     * @param process An object which implements the functional interface {@code Process}.
     */
    public void traverseAscending(Process<E> process) {
        traverseAscending(node, process);
    }

    /**
     * Recursive implementation of {@code traverseAscending}.
     * @param n The {@code Node} currently being traversed.
     * @param process An object which implements the functional interface {@code Process}.
     */
    private void traverseAscending(Node n, Process<E> process) {
        if (n.data == null) return;
        traverseAscending(n.left, process);
        process.call(n.data);
        traverseAscending(n.right, process);
    }

    /**
     * Traverse the tree in descending order, calling {@code process} on each node.
     * @param process An object which implements the functional interface {@code Process}.
     */
    public void traverseDescending(Process<E> process) {
        traverseDescending(node, process);
    }

    /**
     * Recursive implementation of {@code traverseDescending}.
     * @param n The {@code Node} currently being traversed.
     * @param process An object which implements the functional interface {@code Process}.
     */
    private void traverseDescending(Node n, Process<E> process) {
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

    public boolean delete(E e) {
        return searchToDelete(node, e);
    }

    private boolean searchToDelete(Node n, E e) {
        if (n.data == null) return false;
        if (e.compareTo(n.data) == 0) {
            //
            // Found it - delete it and return true
            //
            if (n.left.data != null) {
                // node has a child on the left
                // promote the immediate predecessor
                n.data = promotePredecessor(n);
            } else
            if (n.right.data != null) {
                // node has a child on the right
                // promote the immediate successor
                n.data = promoteSuccessor(n);
            } else {
                // node is at the leaf level
                // delete it
                n.data = null;
            }
            return true;
        }
        if (e.compareTo(n.data) < 0) {
            // e < n.data - traverse left
            return searchToDelete(n.left, e);
        } else {
            // e > n.data - traverse right
            return searchToDelete(n.right, e);
        }
    }

    private E promoteSuccessor(Node n) {
        if (n.right.data == null) {
            E data = n.data;
            n.data = null;
            return data;
        }
        return promoteSuccessor(leftMost(n.right));
    }

    private E promotePredecessor(Node n) {
        if (n.left.data == null) {
            E data = n.data;
            n.data = null;
            return data;
        }
        return promotePredecessor(rightMost(n.left));
    }

    private Node rightMost(Node n) {
        if (n.right.data == null) return n;
        return rightMost(n.right);
    }

    private Node leftMost(Node n) {
        if (n.left.data == null) return n;
        return leftMost(n.left);
    }
}
