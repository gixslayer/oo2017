package oo.assignment11;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.util.function.Consumer;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class TimerController {
    private final Timer timer;
    private final Timeline timeline;
    private Consumer<Double> progressChangedListener;
    private Action startedListener;
    private Action stoppedListener;
    private Action completedListener;

    public TimerController() {
        this.timer = new Timer();
        this.timeline = new Timeline(new KeyFrame(Duration.seconds(1), this::tickHandler));
        this.progressChangedListener = value -> {};
        this.startedListener = () -> {};
        this.stoppedListener = () -> {};
        this.completedListener = () -> {};
    }

    public void start(int duration) {
        assert duration >= 0;

        timer.set(duration);
        timeline.setCycleCount(duration);
        timeline.playFromStart();

        startedListener.perform();
        progressChangedListener.accept(1.0);
    }

    public void stop() {
        timeline.stop();

        stoppedListener.perform();
        progressChangedListener.accept(0.0);
    }

    public void quit() {
        System.exit(0);
    }

    public void setOnProgressChanged(Consumer<Double> consumer) {
        assert consumer != null;

        progressChangedListener = consumer;
    }

    public void setOnStarted(Action action) {
        assert action != null;

        startedListener = action;
    }

    public void setOnStopped(Action action) {
        assert action != null;

        stoppedListener = action;
    }

    public void setOnCompleted(Action action) {
        assert action != null;

        completedListener = action;
    }

    private void tickHandler(ActionEvent event) {
        timer.tick();

        progressChangedListener.accept(timer.getTimeLeftFraction());

        if(timer.hasCompleted()) {
            completedListener.perform();
        }
    }
}
