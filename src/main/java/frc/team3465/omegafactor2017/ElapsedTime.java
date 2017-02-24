package frc.team3465.omegafactor2017;

/**
 * Created by FRC 3465 on 2/23/2017.
 */
public class ElapsedTime {
    private final long startTime;

    public ElapsedTime() {
        startTime = System.nanoTime();
    }

    public double seconds() {
        return  (System.nanoTime() - startTime) / 1E9;
    }

    public double milliseconds() {
        return (System.nanoTime() - startTime) / 1E6;
    }
}
