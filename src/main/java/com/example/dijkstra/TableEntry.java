package com.example.dijkstra;

public class TableEntry {
    /**
     * One row of the dijkstra table
     */
    Vertex vertex;
    boolean known;
    double[] weights; // Array of weights: [distance, time, cost]
    Vertex path;

    public TableEntry(Vertex vertex) {
        this.vertex = vertex;
        this.known = false;
        this.weights = new double[3];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = Double.MAX_VALUE;
        }
        this.path = null;
    }

    /**
     * Update row data
     * @param distance between source and destination
     * @param time of flight
     * @param cost of flight
     * @param path previous vertex
     */
    public void update(double distance, double time, double cost, Vertex path) {
        this.weights[0] = distance;
        this.weights[1] = time;
        this.weights[2] = cost;
        this.path = path;
    }

    /**
     * Get weight based on filter index
     * @param filterIndex index of the weight to get (0=distance, 1=time, 2=cost)
     * @return the requested weight
     */
    public double getWeight(int filterIndex) {
        return weights[filterIndex];
    }
}
