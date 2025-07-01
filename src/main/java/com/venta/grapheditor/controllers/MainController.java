package com.venta.grapheditor.controllers;

import com.venta.grapheditor.model.Edge;
import com.venta.grapheditor.model.Vertex;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MainController {
    @FXML
    private Canvas drawingCanvas;

    @FXML
    private ToggleGroup toolsToggleGroup;

    private final List<Vertex> vertices = new ArrayList<>();
    private final List<Edge> edges = new ArrayList<>();

    private GraphicsContext gc;
    private String currentTool = "SELECT";
    private Vertex selectedVertex = null;

    @FXML
    public void initialize() {
        gc = drawingCanvas.getGraphicsContext2D();
        setupTools();
        redrawCanvas();
    }

    private void setupTools() {
        toolsToggleGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                currentTool = newVal.getUserData().toString();
            }
        });
    }

    @FXML
    private void handleCanvasClick(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();

        switch (currentTool) {
            case "ADD_VERTEX":
                addVertex(x, y);
                break;
            case "REMOVE_VERTEX":
                removeVertex(x, y);
                break;
            case "ADD_EDGE":
                handleAddEdge(x, y);
                break;
            case "REMOVE_EDGE":
                removeEdge(x, y);
                break;
        }
        redrawCanvas();
    }

    private void addVertex(double x, double y) {
        vertices.add(new Vertex(x, y));
    }

    private void removeVertex(double x, double y) {
        Vertex toRemove = findVertex(x, y);
        if (toRemove != null) {
            vertices.remove(toRemove);
            edges.removeIf(edge -> edge.from == toRemove || edge.to == toRemove);
        }
    }

    private void handleAddEdge(double x, double y) {
        Vertex vertex = findVertex(x, y);
        if (vertex == null) return;

        if (selectedVertex == null) {
            selectedVertex = vertex;
        } else {
            edges.add(new Edge(selectedVertex, vertex));
            selectedVertex = null;
        }
    }

    private void removeEdge(double x, double y) {
        Edge toRemove = findEdge(x, y);
        if (toRemove != null) {
            edges.remove(toRemove);
        }
    }

    private Vertex findVertex(double x, double y) {
        double radius = 10;
        for (Vertex v : vertices) {
            if (Math.sqrt(Math.pow(v.x - x, 2) + Math.pow(v.y - y, 2)) <= radius) {
                return v;
            }
        }
        return null;
    }

    private Edge findEdge(double x, double y) {
        for (Edge e : edges) {
            if (isPointOnLine(x, y, e.from.x, e.from.y, e.to.x, e.to.y, 5)) {
                return e;
            }
        }
        return null;
    }

    private boolean isPointOnLine(double px, double py, double x1, double y1,
                                  double x2, double y2, double tolerance) {
        double d1 = Math.sqrt(Math.pow(px - x1, 2) + Math.pow(py - y1, 2));
        double d2 = Math.sqrt(Math.pow(px - x2, 2) + Math.pow(py - y2, 2));
        double lineLen = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        return Math.abs(d1 + d2 - lineLen) < tolerance;
    }

    private void redrawCanvas() {
        gc.clearRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight());

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        for (Edge e : edges) {
            gc.strokeLine(e.from.x, e.from.y, e.to.x, e.to.y);
        }

        gc.setFill(Color.BLUE);
        for (Vertex v : vertices) {
            gc.fillOval(v.x - 5, v.y - 5, 10, 10);
        }

        if (selectedVertex != null) {
            gc.setFill(Color.RED);
            gc.fillOval(selectedVertex.x - 5, selectedVertex.y - 5, 10, 10);
        }
    }

    // Обработчики меню
    @FXML
    private void handleNew() {
        vertices.clear();
        edges.clear();
        selectedVertex = null;
        redrawCanvas();
    }

    @FXML
    private void handleOpen() { }

    @FXML
    private void handleSave() {  }

    @FXML
    private void handleExit() { System.exit(0); }
}