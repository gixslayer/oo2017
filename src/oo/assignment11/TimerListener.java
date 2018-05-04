package oo.assignment11;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public interface TimerListener {
    void onStarted();
    void onStopped();
    void onCompleted();
    void onProgressChanged(double remainder);
}
