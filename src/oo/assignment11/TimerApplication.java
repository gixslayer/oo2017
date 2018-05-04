package oo.assignment11;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class TimerApplication extends Application implements TimerListener {
    private static final Background RED_BACKGROUND;
    private static final Background WHITE_BACKGROUND;

    static {
        RED_BACKGROUND = new Background(new BackgroundFill(Color.RED, null, null));
        WHITE_BACKGROUND = new Background(new BackgroundFill(Color.WHITE, null, null));
    }

    private final GridPane grid;
    private final ProgressBar timeLeftBar;
    private final TextField timeField;
    private final TimerController controller;

    public TimerApplication() {
        this.grid = new GridPane();
        this.timeLeftBar = new ProgressBar();
        this.timeField = new TextField();
        this.controller = new TimerController(this);
    }

    @Override
    public void start(Stage stage) {
        // Should probably do this using fxml & SceneBuilder, but cba setting that up for this simple application.
        Button startButton = new Button();
        Button stopButton = new Button();
        Button quitButton = new Button();
        ColumnConstraints constraint = new ColumnConstraints();

        startButton.setText("Start");
        stopButton.setText("Stop");
        quitButton.setText("Quit");
        constraint.setPercentWidth(25.0);
        timeField.setPrefWidth(Double.MAX_VALUE);
        timeLeftBar.setPrefWidth(Double.MAX_VALUE);
        timeLeftBar.setProgress(0.0);

        quitButton.setOnAction(event -> controller.quit());
        startButton.setOnAction(event -> controller.start(Integer.parseInt(timeField.getText())));
        stopButton.setOnAction(event -> controller.stop());

        grid.setHgap(10.0);
        grid.setVgap(10.0);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.getColumnConstraints().addAll(constraint, constraint, constraint, constraint);

        grid.add(timeLeftBar, 0, 0, 4, 1);
        grid.add(timeField, 0, 1, 3, 1);
        grid.add(startButton, 3, 1);
        grid.add(stopButton, 0, 2);
        grid.add(quitButton, 3, 2);

        stage.setTitle("Time flies");
        stage.setResizable(false);
        stage.setScene(new Scene(grid, 250, 110));
        stage.show();
    }

    @Override
    public void onStarted() {
        grid.setBackground(WHITE_BACKGROUND);
    }

    @Override
    public void onStopped() {
        grid.setBackground(WHITE_BACKGROUND);
    }

    @Override
    public void onCompleted() {
        grid.setBackground(RED_BACKGROUND);
    }

    @Override
    public void onProgressChanged(double remainder) {
        timeLeftBar.setProgress(remainder);
    }
}
