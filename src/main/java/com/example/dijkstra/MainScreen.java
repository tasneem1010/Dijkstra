package com.example.graphs;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.animation.PathTransition;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URISyntaxException;

public class MainScreen {
    /**
     * show application main screen
     */
    Dot[] dots;
    private Dot selectedSource;
    private Dot selectedTarget;
    private ComboBox<Vertex> sources;
    private ComboBox<Vertex> destinations;
    private Line[] lines;
    private ImageView[] planes;
    private Thread[] threads;

    public void show(Stage stage) throws URISyntaxException {

        File file = new File(getClass().getResource("Data.txt").toURI());

        Graph g = new Graph(file); //read graph from file

        //drop down grid pane
        Label s = new Label("Source: ");
        sources = new ComboBox<>();
        sources.getItems().addAll(g.vertices);
        Label d = new Label("Destination: ");
        destinations = new ComboBox<>();
        destinations.getItems().addAll(g.vertices);
        Label f = new Label("Filter: ");
        ComboBox<String> filters = new ComboBox<>();
        filters.getItems().addAll("Distance","Time","Cost"); // 0=Distance, 1=Time, 2=Cost
        filters.setValue("Distance"); // Default to Distance
        GridPane dropDowns = new GridPane();
        dropDowns.getStyleClass().add("grid");
        dropDowns.addRow(0, s, sources);
        dropDowns.addRow(1, d, destinations);
        dropDowns.addRow(3, f, filters);

        //run button
        Button run = new Button("Run");

        //results grid
        Label p = new Label("Path:");
        TextArea path = new TextArea();
        path.setWrapText(true);
        path.setEditable(false);
        Label d1 = new Label("Distance:");
        TextField distance = new TextField();
        distance.setEditable(false);
        Label c = new Label("Cost:");
        TextField cost = new TextField();
        cost.setEditable(false);
        Label t = new Label("Time:");
        TextField time = new TextField();
        time.setEditable(false);
        GridPane results = new GridPane();
        results.getStyleClass().add("grid");
        results.addRow(0, p, path);
        results.addRow(1, d1, distance);
        results.addRow(2, c, cost);
        results.addRow(3, t, time);
        VBox sideBar = new VBox(dropDowns, run, results);
        sideBar.setSpacing(30);
        sideBar.getStyleClass().add("pane");
        //the image
        //fixme
        // map doesn't have city borders
        Image image0 = new Image(getClass().getResource("world_map_Equirectangular_projection.jpg").toString());
        ImageView imageView = new ImageView(image0);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(stage.getWidth() * 0.83);

        Pane image = new Pane(imageView);
        image.getStyleClass().add("pane0");

        // add dots (pins) of all vertices to map
        dots = new Dot[g.vertices.length];
        for (int i = 0; i < dots.length; i++) {
            if (g.vertices[i] == null) continue;
            dots[i] = placeNode(image, g.vertices[i], imageView, "black");
            dots[i].setLabelOffset(9, 0);
        }
        //combo box listener
        sources.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != oldValue) {
                setSelection(newValue == null ? null : newValue.capital, true);
            }
        });
        destinations.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != oldValue) {
                setSelection(newValue == null ? null : newValue.capital, false);
            }
        });
        run.setOnAction(e -> {
            if (filters.getValue() == null) {
                return;
            }
            int filter = filters.getValue().equals("Distance") ? 0 : 
                          (filters.getValue().equals("Time") ? 1 : 2);
            Vertex source = sources.getValue();
            Vertex destination = destinations.getValue();

            if (source == null || destination == null) {
                return;
            }

            Dijkstra dijkstra = new Dijkstra(source, g, filter, destination);
            Stack stack = dijkstra.getPath();

            path.setText(stack.toString());
            TableEntry te = dijkstra.findTableEntry(destination);
            time.setText(String.format("%.2f min", stack.isEmpty() ? 0 : te.weights[1]));
            cost.setText(String.format("%.2f$",  stack.isEmpty() ? 0 : te.weights[2]));
            distance.setText(String.format("%.2f km",  stack.isEmpty() ? 0 : te.weights[0]));

            // remove lines and planes from previous run
            if (lines != null)
                for (Line line : lines)
                    image.getChildren().remove(line);
            if (planes != null)
                for (ImageView plane : planes)
                    image.getChildren().remove(plane);

            if (threads != null)
                for (Thread thread : threads)
                    if(thread != null) thread.interrupt();

            lines = new Line[stack.count];
            planes = new ImageView[stack.count];
            threads = new Thread[stack.count];



            // create visual path
            if (!stack.isEmpty()) {
                String src = stack.pop();
                int i = 0;
                while (!stack.isEmpty()) {
                    String dest = stack.pop();
                    Dot so = findDot(src);
                    Dot des = findDot(dest);
                    Line line = new Line();
                    line.setStartX(so.x);
                    line.setStartY(so.y);
                    line.setEndX(des.x);
                    line.setEndY(des.y);
                    line.getStrokeDashArray().addAll(2d); //dotted line
                    image.getChildren().add(line);
                    movePlane(so.x, so.y, des.x, des.y, image, i);
                    src = dest;
                    lines[i++] = line;
                }
            }


        });

        HBox main = new HBox(image, sideBar);
        main.getStyleClass().add("pane");
        Label title = new Label("Dijkstra's algorithm");
        title.getStyleClass().add("title");
        VBox withTitle = new VBox(title, main);
        withTitle.setSpacing(20);
        withTitle.getStyleClass().add("pane");
        Scene scene = new Scene(withTitle, 1440, 875);
        scene.getStylesheets().add(getClass().getResource("style.css").toString());
        stage.setScene(scene);

    }

    /**
     * handle selected node
     * @param name name of the selected city
     * @param isSource true if it's the source city
     */
    private void setSelection(String name, boolean isSource) {
        Dot selection = isSource ? selectedSource : selectedTarget;

        if (selection != null)
            selection.circle.setFill(Paint.valueOf("black"));
        selection = findDot(name);
        if (isSource) {
            selectedSource = selection;
            sources.setValue(selection == null ? null : selection.vertex);
        } else {
            selectedTarget = selection;
            destinations.setValue(selection == null ? null : selection.vertex);
        }
        if (selection != null)
            selection.circle.setFill(Paint.valueOf(isSource ? "red" : "blue"));
    }

    /**
     * place a dot on vertex location
     * @param image
     * @param node
     * @param imageView
     * @param color
     * @return placed dot
     */
    public Dot placeNode(Pane image, Vertex node, ImageView imageView, String color) {
        Dot dot = new Dot(node, color, imageView.getBoundsInParent().getWidth(), imageView.getBoundsInParent().getHeight());

        image.getChildren().add(dot.circle);
        image.getChildren().add(dot.name);

        dot.circle.setOnMouseClicked(e -> {
            if (selectedSource == null) {
                // if no source, select source
                setSelection(dot.name.getText(), true);
            } else {
                // if there is source, select target
                if (selectedTarget == null)
                    setSelection(dot.name.getText(), false);
                    // if there is already target, re-select source, set destination to none
                else {
                    setSelection(dot.name.getText(), true);
                    setSelection(null, false);
                }
            }

        });
        return dot;
    }

    public Dot findDot(String name) {
        if (dots == null) return null;

        if (name == null) return null;

        for (Dot dot :
                dots) {
            if (dot.name.getText().equals(name)) {
                return dot;
            }
        }
        return null;
    }
    /**
     * move a plane from source to destination coordinates
     * @param sourceX
     * @param sourceY
     * @param desX
     * @param desY
     * @param pane
     * @param i
     */
    public void movePlane(double sourceX, double sourceY, double desX, double desY, Pane pane, int i) {
        Path path = new Path();
        MoveTo moveTo = new MoveTo(sourceX, sourceY);
        LineTo lineTo = new LineTo(desX, desY);
        path.getElements().add(moveTo);
        path.getElements().add(lineTo);
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(2000));
        Image image = new Image(getClass().getResource("plane3.png").toString());
        ImageView plane = new ImageView(image);

        Thread t = new Thread(() -> {
            try {
                Thread.sleep(2000L * i);

                Platform.runLater(()-> {

                    plane.setPreserveRatio(true);
                    plane.setFitWidth(24);
                    plane.setRotate(getAngle(sourceX, sourceY, desX, desY) + 45);
                    pane.getChildren().add(plane);
                    pathTransition.setNode(plane);

                    pathTransition.setOnFinished(e -> {
                        pane.getChildren().remove(plane);
                    });
                    pathTransition.setPath(path);
                    planes[i] = plane;
                    pathTransition.play();
                });


            } catch(InterruptedException v) {
                pane.getChildren().remove(plane);

            }
        });
        threads[i] = t;
        t.start();



    }

    /**
     * calculate angle of rotation for the plane
     * @param sourceX
     * @param sourceY
     * @param destX
     * @param destY
     * @return calculated angle
     */
    private double getAngle(double sourceX, double sourceY, double destX, double destY) {
        double deltaX = destX - sourceX;
        double deltaY = destY - sourceY;
        double angleRad = Math.atan2(deltaY, deltaX);
        double angleDeg = Math.toDegrees(angleRad);
        return (angleDeg + 360) % 360;
    }


}
