package frc.team3465.omegafactor2017.util;

/**
 * Created by FRC 3465 on 3/17/2017.
 */
public class Cacheable<T> {
    T object;
    long setTime;
    final long expireTime;

    public Cacheable(T obj, long expireAfter) {
        this.object = obj;
        this.expireTime = (long) (expireAfter * 1E6);
        this.setTime = System.nanoTime();
    }

    public Cacheable(T obj) {
        this(obj, 500);
    }

    public Cacheable(Class<T> klazz) {
        this();
    }

    public Cacheable() {
        this(null, 500);
    }

    public T get() {
        if (!isValid())
            object = null;

        return object;
    }

    public void set(T object) {
        this.object = object;
        this.setTime = System.nanoTime();
    }

    public boolean isValid() {
        return object != null  && setTime + expireTime < System.nanoTime();
    }
}
