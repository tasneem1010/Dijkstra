package com.example.graphs;

public class EdgeNode{
    /**
     * edge list node
     */
    Vertex destination; // destination city
    double[] weights; // array of weights: [distance, time, cost]
    EdgeNode next; // the next node

    /**
     * EdgeNode constructor
     * @param destination destination city
     * @param time time of flight
     * @param cost cost of flight
     * @param distance  distance between cities in kms
     */
    public EdgeNode(Vertex destination, double time, double cost, double distance){
        this.destination = destination;
        this.weights = new double[3];
        this.weights[0] = distance;
        this.weights[1] = time;
        this.weights[2] = cost;
    }
}
