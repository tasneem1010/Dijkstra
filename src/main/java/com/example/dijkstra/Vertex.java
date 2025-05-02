package com.example.dijkstra;

import javafx.scene.shape.Circle;

public class Vertex{
    /**
     * Vertex representing a city (capital)
     */
    String capital; // Vertex ID
    double longitude; // y
    double latitude; // x
    List edgeList; // List of all adjacent vertices and the weight of the edge
    Circle circle;

    public Vertex(String name,double latitude,double longitude){
        this.capital = name;
        this.latitude = latitude;
        this.longitude = longitude;
        edgeList = new List();
        this.circle = new Circle(3);
    }

    public Vertex() {
    }

    /**
     * add edge to edge list
     * @param destination
     * @param time
     * @param cost
     * @param distance
     */
    public void addEdge(Vertex destination,double time,double cost,double distance){
        edgeList.add(new EdgeNode(destination,time,cost,distance));
    }

    @Override
    public String toString() {
        return capital;
    }
}
