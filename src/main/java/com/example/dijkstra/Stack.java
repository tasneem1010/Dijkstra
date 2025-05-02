package com.example.dijkstra;

public class Stack {
    /**
     * stack implementation
     */
    StackNode top; // top of stack
    int count; // count of nodes in stack

    /**
     * push node to top of stack
     * @param id name of the capital to push
     */
    public void push(String id) {
        if (id == null) return;
        StackNode node = new StackNode(id);
        if (top == null) {
            top = node;
        } else {
            StackNode temp = top;
            top = node;
            top.next = temp;
        }
        count++;
    }

    /**
     * @return the name at the top of stack
     */

    public String pop() {
        if (top == null) return null;
        StackNode temp = top;
        top = top.next;
        count--;
        return temp.id;
    }

    /**
     * @return true if the top is null
     */
    public boolean isEmpty() {
        return top == null;
    }

    /**
     * print contents of stack to console
     */
    public void printStack() {
        if (isEmpty()) {
            System.out.println("Stack is empty");
            return;
        }
        StackNode node = top;
        while (node.next != null) {
            System.out.print(node.id + " --> ");
            node = node.next;
        }
        System.out.print(node.id);
    }

    /**
     * @return string representation of the stack
     */
    public String toString() {
        StringBuilder b = new StringBuilder();
        if (isEmpty()) {
            return ("No Path Found.");
        } else {
            StackNode node = top;
            while (node.next != null) {
                b.append(node.id).append(" --> ");
                node = node.next;
            }
            b.append(node.id).append(".");
        }
        return b.toString();
    }
}
