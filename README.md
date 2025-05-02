# Dijkstra's Algorithm Visualization

A JavaFX-based application that visualizes Dijkstra's shortest path algorithm on a world map, allowing users to find optimal routes between world capitals based on different criteria (distance, time, or cost).

## Features

- Interactive world map interface with city markers
- Visual representation of flight paths between capitals
- Three optimization criteria:
  - Distance (kilometers)
  - Time (minutes)
  - Cost (dollars)
- Animated plane movement along the calculated path
- Detailed path information display
- User-friendly dropdown selection for source and destination cities

## Project Structure

```
src/main/java/com/example/dijkstra/
├── Dijkstra.java        # Core algorithm implementation
├── MainScreen.java      # JavaFX GUI implementation
├── Graph.java           # Graph data structure
├── Vertex.java          # Vertex (city) representation
├── EdgeNode.java        # Edge (flight) representation
├── TableEntry.java      # Dijkstra's algorithm table entry
├── Stack.java           # Stack implementation for path tracking
├── StackNode.java       # Stack node implementation
├── List.java            # List implementation
├── Dot.java             # Visual representation of cities on map
└── Driver.java          # Application entry point
```

## Requirements

- Java 8 or higher
- JavaFX
- Maven

## Building and Running

1. Clone the repository:
```bash
git clone [repository-url]
cd Dijkstra
```

2. Build the project using Maven:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn javafx:run
```

## Usage

1. Select a source city from the "Source" dropdown
2. Select a destination city from the "Destination" dropdown
3. Choose your optimization criteria (Distance/Time/Cost)
4. Click "Run" to calculate and visualize the shortest path
5. View the results:
   - Path details in the text area
   - Total distance in kilometers
   - Total cost in dollars
   - Total time in minutes
   - Visual path on the map with animated plane movement

## Implementation Details

The application implements Dijkstra's algorithm to find the shortest path between two world capitals. The algorithm considers three different weights for each edge:
- Distance (in kilometers)
- Time (in minutes)
- Cost (in dollars)

The visualization includes:
- Interactive city markers on a world map
- Animated plane movement along the calculated path
- Dotted lines representing flight paths
- Color-coded source (red) and destination (blue) cities

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the LICENSE file for details. 