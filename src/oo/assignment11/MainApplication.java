package oo.assignment11;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class MainApplication extends Application {
    private static final Background RED_BACKGROUND;
    private static final Background WHITE_BACKGROUND;

    private GridPane grid;
    private ProgressBar timeLeftBar;
    private TextField timeField;
    private Timeline timeline;
    private int timeRemaining;
    private double totalTime;

    static {
        RED_BACKGROUND = new Background(new BackgroundFill(Color.RED, null, null));
        WHITE_BACKGROUND = new Background(new BackgroundFill(Color.WHITE, null, null));
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Time flies");
        stage.setResizable(false);

        grid = new GridPane();
        timeLeftBar = new ProgressBar();
        timeField = new TextField();
        Button startButton = new Button();
        Button stopButton = new Button();
        Button quitButton = new Button();
        ColumnConstraints constraint = new ColumnConstraints();

        startButton.setText("Start");
        stopButton.setText("Stop");
        quitButton.setText("Quit");
        timeField.setPrefWidth(Double.MAX_VALUE);
        timeLeftBar.setPrefWidth(Double.MAX_VALUE);
        timeLeftBar.setProgress(0.0);
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), this::tickHandler));
        timeline.setCycleCount(Timeline.INDEFINITE);

        quitButton.setOnAction(event -> System.exit(0));
        startButton.setOnAction(this::onStartButton);
        stopButton.setOnAction(this::onStopButton);

        grid.setHgap(10.0);
        grid.setVgap(10.0);
        grid.setPadding(new Insets(10, 10, 10, 10));
        constraint.setPercentWidth(25.0);
        grid.getColumnConstraints().addAll(constraint, constraint, constraint, constraint);

        grid.add(timeLeftBar, 0, 0, 4, 1);
        grid.add(timeField, 0, 1, 3, 1);
        grid.add(startButton, 3, 1);
        grid.add(stopButton, 0, 2);
        grid.add(quitButton, 3, 2);

        stage.setScene(new Scene(grid, 250, 110));
        stage.show();
    }

    private void onStartButton(Event event) {
        timeline.play();
        grid.setBackground(WHITE_BACKGROUND);
        totalTime = timeRemaining = Integer.parseInt(timeField.getText());
        timeLeftBar.setProgress(1.0);
    }

    private void onStopButton(Event event) {
        timeline.stop();
        grid.setBackground(WHITE_BACKGROUND);
        timeLeftBar.setProgress(0.0);
    }

    private void tickHandler(ActionEvent event) {
        timeLeftBar.setProgress(timeRemaining / totalTime);

        if(timeRemaining-- <= 0) {
            grid.setBackground(RED_BACKGROUND);
        }
    }
}
