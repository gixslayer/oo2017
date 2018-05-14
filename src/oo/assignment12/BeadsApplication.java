package oo.assignment12;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class BeadsApplication extends Application {
    private static final Color START_COLOR = Color.RED;
    private static final Color END_COLOR = Color.BLUE;
    private static final double RADIUS = 10;
    private static final double WIDTH = 800;
    private static final double HEIGHT = 600;

    private final Pane pane;
    private final Line line;
    private final List<Circle> circles;

    public BeadsApplication() {
        this.pane = new Pane();
        this.line = new Line();
        this.circles = new ArrayList<>();
        this.pane.setOnMouseClicked(this::onMouseClicked);
        this.pane.setOnMouseMoved(this::onMouseMoved);
        this.pane.getChildren().add(line);
    }

    @Override
    public void start(Stage stage) {
        addBead();
        updateBeads(WIDTH / 2, HEIGHT / 2);

        stage.setTitle("Beads application");
        stage.setScene(new Scene(pane, WIDTH, HEIGHT));
        stage.show();
    }

    private void onMouseMoved(MouseEvent mouseEvent) {
        updateBeads(mouseEvent.getSceneX(), mouseEvent.getSceneY());
    }

    private void onMouseClicked(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == MouseButton.PRIMARY) {
            addBead();
        } else if (circles.size() > 1) {
            removeBead();
        }

        updateBeads(mouseEvent.getSceneX(), mouseEvent.getSceneY());
    }

    private void updateBeads(double x, double y) {
        line.setEndX(x);
        line.setEndY(y);

        double numBeads = circles.size();
        double dx = x / numBeads;
        double dy = y / numBeads;

        for(int i = 1; i <= numBeads; ++i) {
            Circle circle = circles.get(i - 1);
            circle.setCenterX(dx * i);
            circle.setCenterY(dy * i);
            circle.setFill(START_COLOR.interpolate(END_COLOR, i / numBeads));
        }
    }

    private void addBead() {
        Circle circle = new Circle(0, 0, RADIUS);
        circle.setFill(END_COLOR);

        circles.add(circle);
        pane.getChildren().add(circle);
    }

    private void removeBead() {
        pane.getChildren().remove(circles.remove(circles.size() - 1));
    }

    public static void main(String[] args) {
        launch(args);
    }

}
