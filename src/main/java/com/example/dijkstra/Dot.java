package com.example.graphs;

import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Dot {
    /**
     * pin on map and the label of the city name
     */
    Circle circle; // pin on the map
    Vertex vertex; // the city
    Label name; // city name
    double x; // x coordinates
    double y; // y coordinates

    /**
     * Dot constructor
     * @param vertex the city
     * @param color color of circle
     * @param canvasWidth map width
     * @param canvasHeight map height
     */
    public Dot(Vertex vertex,String color,double canvasWidth , double canvasHeight) {
        this.vertex = vertex;
        this.circle = new Circle(4);
        this.circle.setFill(Paint.valueOf(color));
        double[] pos = coordsToPixels(canvasWidth, canvasHeight, vertex.latitude, vertex.longitude);
        circle.setTranslateX(pos[0]);
        circle.setTranslateY(pos[1]);
        name = new Label(vertex.capital);
        name.getStyleClass().add("small");
        name.setTranslateX(pos[0]);
        name.setTranslateY(pos[1]);
        name.setRotate(7);
        name.setMouseTransparent(true);
    }
    /**
     * Set Label offset
     * @param xOffset offset of the x-axis
     * @param yOffset offset of the y-axis
     */
    public void setLabelOffset(double xOffset, double yOffset) {
        name.setTranslateX(name.getTranslateX() + xOffset);
        name.setTranslateY(name.getTranslateY() + yOffset);

    }

    /**
     * Calculate the pixel coordinates from map coordinates
     * @param canvasWidth map width
     * @param canvasHeight map height
     * @param latitude city latitude
     * @param longitude city longitude
     * @return double array of coordinates on the map
     */
    public double[] coordsToPixels(double canvasWidth , double canvasHeight, double latitude, double longitude){

         x = (longitude * canvasWidth / 360.0) + canvasWidth/2;
         y = canvasHeight/2 - (latitude * canvasHeight / 180.0) ;

        return new double[]{x,y};
    }
}
