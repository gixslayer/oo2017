package oo.assignment11;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class TimerController {
    private final Timer timer;
    private final Timeline timeline;
    private final TimerListener listener;

    public TimerController(TimerListener listener) {
        this.timer = new Timer();
        this.timeline = new Timeline(new KeyFrame(Duration.seconds(1), this::tickHandler));
        this.listener = listener;
    }

    public void start(int duration) {
        timer.set(duration);
        timeline.setCycleCount(duration);
        timeline.play();

        listener.onStarted();
        listener.onProgressChanged(1.0);
    }

    public void stop() {
        timeline.stop();

        listener.onStopped();
        listener.onProgressChanged(0.0);
    }

    public void quit() {
        System.exit(0);
    }

    private void tickHandler(ActionEvent event) {
        timer.tick();

        listener.onProgressChanged(timer.getTimeLeftFraction());

        if(timer.hasCompleted()) {
            listener.onCompleted();
        }
    }
}
