package com.example.dijkstra;

import java.io.File;

import java.util.Scanner;

import static java.lang.Math.*;


public class Graph {
    /**
     * representing a graph
     */
    Vertex[] vertices; //array of vertices /cities
    int vertexCount; // number of vertices

    /**
     * Graph constructor
     * @param file file to read containing world capitals,their coordinates and flight info between them.
     */
    public Graph(File file) {
        vertexCount = 0;
        constructFromFile(file);
    }

    /**
     * construct graph from given file
     * @param file containing graph details with the following formatting:
     *              list the number of vertices and edges, then list the vertices (index followed by its x and y coordinates),
     *              then list the edges (pairs of vertices, price, and time)
     */
    public void constructFromFile(File file) {
        try (Scanner input = new Scanner(file)) {
            String[] counts = input.nextLine().split(",");
            int numberOfVertices = Integer.parseInt(counts[0]);
            vertices = new Vertex[numberOfVertices];
            while (input.hasNext() && numberOfVertices>0) { //read all vertices until edges start
                String line = input.nextLine();
                String[] verData = line.split(",");
                if (verData.length != 3) {
                    System.out.println("vertex data is incorrectly formatted, skipping...");
                } else {
                    addVertex(verData[0], Double.parseDouble(verData[1]), Double.parseDouble(verData[2])); //insert vertex to graph
                }
                numberOfVertices--;
            }
            int numOfEdges = Integer.parseInt(counts[1]);
            while (input.hasNext() && numOfEdges>0) {
                String line = input.nextLine();
                String[] data = line.split(",");
                if (data.length != 4) {
                    System.out.println("edge data is incorrectly formatted, skipping line...");
                }else {
                addEdge(data[0], data[1], Double.parseDouble(data[3]), Double.parseDouble(data[2]));}
                numOfEdges--;
            }
        } catch (Exception e) {
            throw  new RuntimeException();
        }
    }

    /**
     * add edge to graph
     * @param source source city
     * @param destination destination city
     * @param time flight time
     * @param cost flight cost
     */
    public void addEdge(String source, String destination, double time, double cost) {
        Vertex vertex = getVertex(source);
        if (vertex== null) {
            System.out.println("invalid edge");
            return;
        }
        Vertex vertex1 = getVertex(destination);
        if (vertex1 == null){
            System.out.println("destination doesn't exist");
            return;
        }

        //to get the distance between 2 points on a sphere we use the haversine formula:
        double x1 = toRadians(vertex.latitude);
        double x2 = toRadians(vertex1.latitude);
        double y1 = toRadians(vertex.longitude);
        double y2 = toRadians(vertex1.longitude);
        double earthRadius = 6378; //in kms
        double distance = 2*earthRadius*
                asin(
                    sqrt(
                        hav(x2-x1) + cos(x1) * cos(x2) * hav(y2-y1)
                    )
                );
        vertex.addEdge(vertex1, time, cost,distance);
    }

    /**
     * @param theta angle
     * @return sin half the angle squared
     */
    public double hav(double theta){
        return Math.pow(Math.sin((theta)/2),2);
    }
    /**
     * print graph to console
     */
    public void printGraph() {
        for (int i = 0; i < vertexCount; i++) {
            System.out.println(vertices[i].capital);
            vertices[i].edgeList.printList();
        }
    }

    /**
     * add vertex to graph
     * @param capital city name
     * @param lat city latitude
     * @param lon city longitude
     */
    public void addVertex(String capital, double lat, double lon) {
        if (getVertex(capital) != null) return;
        vertices[vertexCount++] = new Vertex(capital, lat, lon);
    }

    /**
     * find vertex by name
     * @param capital of the capital
     * @return the found vertex ,null if not found
     */
    public Vertex getVertex(String capital) {
        Vertex vertex = null;
        for (int i = 0; i < vertexCount; i++) {
            if (vertices[i].capital.equalsIgnoreCase(capital)) {
                vertex = vertices[i];
            }
        }
        return vertex;
    }
}
