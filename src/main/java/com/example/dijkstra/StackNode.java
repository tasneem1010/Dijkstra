package com.example.graphs;

public class StackNode {
    /**
     * stack node
     */

    StackNode next; // next node
    String id; // name of the node

    /**
     * StackNode constructor
     * @param id name of node
     */
    public StackNode(String id) {
        this.id = id;
    }
}
