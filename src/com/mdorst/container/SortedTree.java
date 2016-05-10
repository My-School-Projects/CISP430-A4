package com.mdorst.container;

import com.mdorst.functional.Process;

public class SortedTree<E extends Comparable<? super E>> {

    private class Node {
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
     * If this element already exist in the tree, insertion will fail, returning {@code false}.
     * Throws NullPointerException if the specified element is null.
     * @param e The element to be inserted.
     * @return {@code true} if the element was successfully inserted.
     */
    public boolean insert(E e) {
        // Null is not allowed. Throw an error if e is null.
        if (e == null) {
            throw new NullPointerException();
        }
        // Call the actual recursive insert method
        return insert(node, e);
    }

    /**
     * Recursively searches the tree until it finds the correct place to insert the element {@code e}.
     * @param n The current node being searched
     * @param e The element to be inserted
     * @return {@code true} if the element was successfully inserted.
     */
    private boolean insert(Node n, E e) {
        // If n.data is null, insert the element and return up the stack.
        if (n.data == null) {
            n.data = e;
            n.left = new Node();
            n.right = new Node();
            return true;
        } else {
            // If the element is already in the tree, return false.
            if (e.compareTo(n.data) == 0)
                return false;
            // If e < n.data, insert on the left subtree.
            // If e > n.data, insert on the right subtree.
            if (e.compareTo(n.data) < 0) {
                return insert(n.left, e);
            }
            else {
                return insert(n.right, e);
            }
        }
    }
    /**
     * Delete an element from the tree.
     * If the element does not exist in the tree, deletion will fail, returning {@code false}.
     * Throws NullPointerException if the specified element is null.
     * @param e The element to be deleted.
     * @return {@code true} if the element was successfully deleted.
     */
    public boolean delete(E e) {
        // Null is not allowed. Throw an error if e is null.
        if (e == null) {
            throw new NullPointerException();
        }
        return delete(node, e);
    }

    /**
     * Recursively searches the tree until it finds the correct element to delete.
     * If the element is a leaf node, that node will be deleted directly.
     * If the element is a branch node, one of its children will be promoted to take its place.
     * If the element is not found, deletion will fail, returning {@code false}.
     * @param n The current node being searched
     * @param e The element to be deleted
     * @return {@code true} if the element was successfully deleted.
     */
    private boolean delete(Node n, E e) {
        if (n.data == null) return false;
        if (e.compareTo(n.data) == 0) {
            //
            // Found it - delete it and return true
            //
            if (n.left.data != null) {
                // node has a child on the left
                // promote the immediate predecessor
                promotePredecessor(n);
            } else
            if (n.right.data != null) {
                // node has a child on the right
                // promote the immediate successor
                promoteSuccessor(n);
            } else {
                // node is at the leaf level
                // delete it
                n.data = null;
            }
            return true;
        }
        if (e.compareTo(n.data) < 0) {
            // e < n.data - traverse left
            return delete(n.left, e);
        } else {
            // e > n.data - traverse right
            return delete(n.right, e);
        }
    }

    /**
     * Promotes the immediate successor of {@code n}.
     * If the immediate successor has its own successor(s),
     * their immediate successors will be promoted recursively.
     * @param n The node to be replaced by its immediate successor.
     */
    private void promoteSuccessor(Node n) {
        if (n.right.data == null) {
            // n has no successor - n is the leaf node
            // delete n.data
            n.data = null;
        } else {
            // n has a successor - promote its direct successor
            n.data = leftMost(n.right).data;
            // promote the successor(s) of its successor
            promoteSuccessor(leftMost(n.right));
        }
    }

    /**
     * Promotes the immediate predecessor of {@code n}.
     * If the immediate predecessor has its own predecessor(s),
     * their immediate predecessors will be promoted recursively.
     * @param n The node to be replaced by its immediate predecessor.
     */
    private void promotePredecessor(Node n) {
        if (n.left.data == null) {
            // n has no predecessor - n is the leaf node
            // delete n.data
            n.data = null;
        } else {
            // n has a predecessor - promote its direct predecessor
            n.data = rightMost(n.left).data;
            // promote the predecessor(s) of its predecessor
            promotePredecessor(rightMost(n.left));
        }
    }

    /**
     * Does a depth first descent right, starting on {@code n}, and returns the right most descendant.
     * @param n The node who's right most descendant is to be returned.
     * @return The right most descendant of {@code n}.
     */
    private Node rightMost(Node n) {
        // This node has no right subtree. Return this node.
        if (n.right.data == null) return n;
        // This node has a right subtree. Continue the descent.
        return rightMost(n.right);
    }

    /**
     * Does a depth first descent left, starting on {@code n}, and returns the left most descendant.
     * @param n The node who's left most descendant is to be returned.
     * @return The left most descendant of {@code n}.
     */
    private Node leftMost(Node n) {
        // This node has no left subtree. Return this node.
        if (n.left.data == null) return n;
        // This node has a left subtree. Continue the descent.
        return leftMost(n.left);
    }
}
