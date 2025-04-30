package com.example.graphs;
public class List {
    /**
     * linked list implementation
     */

    int count; // number of nodes in list
    EdgeNode head; // list head
    EdgeNode tail; // list tail

    /**
     * add edge node to the end of the list
     * @param EdgeNode node to add
     */
    public void add(EdgeNode EdgeNode) {
        if (count == 0) {
            head = EdgeNode;
        } else {
            tail.next = EdgeNode;
        }
        tail = EdgeNode;
        count++;
    }

    /**
     * print list to console
     */
    public void printList(){
        EdgeNode EdgeNode = head;
        while (EdgeNode!=null){
            System.out.println("\t" + (EdgeNode).destination);
            EdgeNode = EdgeNode.next;
        }
    }
}
