package frc.team3465.omegafactor2017;

/**
 * Created by FRC 3465 on 1/16/2017.
 */
public final class Preconditions {
    public static <T> T checkNotNull(T obj) {
        if (obj == null) throw new NullPointerException();
        return obj;
    }

    public static double clip(double x, double min, double max) {
        return Math.min(Math.max(x, min), max);
    }
}