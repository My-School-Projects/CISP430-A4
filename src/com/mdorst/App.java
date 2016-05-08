package com.mdorst;

import com.mdorst.util.DataInput;

public class App {
    public static void main(String[] args) {
        SortedTree<String> dictionary = new SortedTree<>();
        DataInput file = new DataInput("data/dictionary.txt");
        /**
         * Read contents of data/dictionary.txt into AVL tree
         */
        while (file.hasNext()) {
            dictionary.insert(file.getLine());
        }
        System.out.println("Dictionary in ascending order:");
        System.out.println("==============================");
        dictionary.traverseAscending(System.out::println);
        System.out.println("\nDictionary in descending order:");
        System.out.println("===============================");
        dictionary.traverseDescending(System.out::println);
    }
}
