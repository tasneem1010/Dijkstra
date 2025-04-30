package com.example.graphs;

public class Dijkstra {
    /**
     * find the shortest path between two world capitals
     */

    TableEntry[] table; // dijkstra table
    Graph graph; // the graph of capitals and flights between them
    Vertex destination; // destination capital

    /**
     * Dijkstra constructor
     *
     * @param source      city to travel from
     * @param g           graph of capitals and flights
     * @param filterIndex filter based on (0=distance, 1=time, 2=cost)
     * @param destination city to travel to
     */
    public Dijkstra(Vertex source, Graph g, int filterIndex, Vertex destination) {
        this.graph = g;
        this.destination = destination;
        initializeTable(source);
        getDijkstra(filterIndex, destination);
    }

    /**
     * Set the default values of the table
     *
     * @param start source city /vertex
     */
    public void initializeTable(Vertex start) {
        table = new TableEntry[graph.vertices.length];
        for (int i = graph.vertexCount - 1; i >= 0; i--) {
            table[i] = new TableEntry(graph.vertices[i]);
        }
        for (TableEntry te : table) {
            // find the source vertex
            if (te.vertex.capital.equalsIgnoreCase(start.capital)) {
                te.weights[0] = 0; // distance
                te.weights[1] = 0; // time
                te.weights[2] = 0; // cost
            }
        }
    }

    /**
     * Set the Dijkstra table
     * 
     * @param filterIndex filter based on (0=distance, 1=time, 2=cost)
     * @param destination city/vertex to travel to
     */
    public void getDijkstra(int filterIndex, Vertex destination) {
        TableEntry tableEntry;

        while (true) {
            // find the smallest unknown vertex in the graph (this process can be optimized
            // using a priority queue but this method is used based on the requirements of
            // the assignment)
            tableEntry = findSmallestUnknown(filterIndex);
            // all vertices are known or destination is reached
            if (tableEntry == null || tableEntry.vertex.capital.equals(destination.capital)) {
                break;
            }
            tableEntry.known = true;
            EdgeNode edge = tableEntry.vertex.edgeList.head;
            while (edge != null) {
                TableEntry adjacent = findTableEntry(edge.destination);
                if (!(adjacent == null) && !adjacent.known) {
                    if (tableEntry.weights[filterIndex] + edge.weights[filterIndex] < adjacent.weights[filterIndex]) {
                        adjacent.update(
                                tableEntry.weights[0] + edge.weights[0],
                                tableEntry.weights[1] + edge.weights[1],
                                tableEntry.weights[2] + edge.weights[2],
                                tableEntry.vertex);

                    }
                }
                edge = edge.next;
            }
        }
    }

    /**
     * find the smallest unknown vertex in the graph
     *
     * @param filterIndex index of the weight to compare (0=distance, 1=time,
     *                    2=cost)
     * @return the table entry with the smallest weight
     */
    private TableEntry findSmallestUnknown(int filterIndex) {
        double min = Double.MAX_VALUE;
        TableEntry minimum = null;
        for (TableEntry entry : table) {
            double weight = entry.weights[filterIndex];
            if ((!entry.known) && weight < min) {
                minimum = entry;
                min = weight;
            }
        }
        return minimum;
    }

    /**
     * find the table entry with this vertex based on name
     *
     * @param vertex to find
     * @return the entry of found, null if not
     */
    public TableEntry findTableEntry(Vertex vertex) {
        for (TableEntry entry : table) {
            if (entry.vertex == null)
                continue;
            if (entry.vertex.capital.equalsIgnoreCase(vertex.capital)) {
                return entry;
            }
        }
        return null;
    }

    /**
     * get the path from the Dijkstra table
     *
     * @return stack of names in of path
     */
    public Stack getPath() {
        Stack path = new Stack();
        TableEntry destinationEntry = findTableEntry(destination);

        if (destinationEntry == null || destinationEntry.path == null) {
            return path;
        }
        while (destinationEntry != null && destinationEntry.path != null) {
            path.push(destinationEntry.vertex.capital);
            destinationEntry = findTableEntry(destinationEntry.path);
        }
        if (destinationEntry != null) {
            path.push(destinationEntry.vertex.capital);
        }
        return path;
    }
}
