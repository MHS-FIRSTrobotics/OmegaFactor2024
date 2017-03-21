package frc.team3465.omegafactor2017.util;

import trikita.log.Log;

import java.util.Objects;

/**
 * Created by FRC 3465 on 3/17/2017.
 */
public class DoubleCacheable {
    double object;
    long setTime;
    final long expireTime;

    public DoubleCacheable(double obj, long expireAfter) {
        this.object = obj;
        this.expireTime = (long) (expireAfter * 1E6);
        this.setTime = System.nanoTime();
    }

    public DoubleCacheable(double obj) {
        this(obj, 500);
    }

    public DoubleCacheable() {
        this(Double.NaN, 500);
    }

    public double get() {
        if (!isValid())
            object = Double.NaN;

        return object;
    }

    public double safeGet(double fail) {
        return get() == Double.NaN ? fail : object;
    }

    public void set(double object) {
        this.object = object;
        this.setTime = System.nanoTime();
        //Log.i("Setting obj: " + object);
    }

    public boolean isValid() {
        boolean b = !Double.isNaN(object); // && (setTime + expireTime) < System.nanoTime();
        //Log.i("isValid():" + b + " obj: " + object);

        return b;
    }
}
