package oo.assignment11;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class TimerApplication extends Application {
    private static final Background RED_BACKGROUND;
    private static final Background WHITE_BACKGROUND;

    static {
        RED_BACKGROUND = new Background(new BackgroundFill(Color.RED, null, null));
        WHITE_BACKGROUND = new Background(new BackgroundFill(Color.WHITE, null, null));
    }

    private final TimerController controller;
    private final GridPane grid;
    private final ProgressBar timeLeftBar;
    private final TextField timeField;
    private final Button startButton;
    private final Button stopButton;
    private final Button quitButton;

    public TimerApplication() {
        this.controller = new TimerController();
        this.grid = new GridPane();
        this.timeLeftBar = new ProgressBar();
        this.timeField = new TextField();
        this.startButton = new Button();
        this.stopButton = new Button();
        this.quitButton = new Button();

        this.controller.setOnCompleted(() -> grid.setBackground(RED_BACKGROUND));
        this.controller.setOnStarted(() -> grid.setBackground(WHITE_BACKGROUND));
        this.controller.setOnStopped(() -> grid.setBackground(WHITE_BACKGROUND));
        this.controller.setOnProgressChanged(timeLeftBar::setProgress);
        this.quitButton.setOnAction(event -> controller.quit());
        this.stopButton.setOnAction(event -> controller.stop());
        this.startButton.setOnAction(event -> ifPresentOrElse(getDuration(),
                controller::start,
                this::showInvalidDurationDialog));
    }

    @Override
    public void start(Stage stage) {
        ColumnConstraints constraint = new ColumnConstraints();

        constraint.setPercentWidth(25.0);
        startButton.setText("Start");
        stopButton.setText("Stop");
        quitButton.setText("Quit");
        timeField.setPrefWidth(Double.MAX_VALUE);
        timeLeftBar.setPrefWidth(Double.MAX_VALUE);
        timeLeftBar.setProgress(0.0);

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

    private Optional<Integer> getDuration() {
        return parseDuration(timeField.getText());
    }

    private Optional<Integer> parseDuration(String text) {
        try {
            int duration = Integer.parseInt(text);

            return duration >= 0 ? Optional.of(duration) : Optional.empty();
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
    }

    private void showInvalidDurationDialog() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid duration");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a non-negative number.");

        alert.showAndWait();
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private static <T> void ifPresentOrElse(Optional<T> value, Consumer<T> ifPresent, Action orElse) {
        // NOTE: This function was added to Optional<T> in Java 9, but I'll just create an ugly
        // backport for Java 8.
        if(value.isPresent()) {
            ifPresent.accept(value.get());
        } else {
            orElse.perform();
        }
    }
}
