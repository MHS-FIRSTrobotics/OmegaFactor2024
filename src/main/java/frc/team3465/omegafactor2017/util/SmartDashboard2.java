package frc.team3465.omegafactor2017.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.HashMap;

/**
 * Created by FRC 3465 on 3/17/2017.
 */
public final class SmartDashboard2 {
    private final static HashMap<String, SmartTelemetry> registar = new HashMap<>();

    public static SmartTelemetryForNumber putNumber(String key, double number) {
        if (!registar.containsKey(key)) registar.put(key, new SmartTelemetryForNumber(key, number));

        return (SmartTelemetryForNumber) registar.get(key);
    }

    public static SmartTelemetryForBoolean putBoolean(String key, boolean value) {
        if (!registar.containsKey(key)) registar.put(key, new SmartTelemetryForBoolean(key, value));

        return (SmartTelemetryForBoolean) registar.get(key);
    }

    public static SmartTelemetryForString putString(String key, String value) {
        if (!registar.containsKey(key)) registar.put(key, new SmartTelemetryForString(key, value));

        return (SmartTelemetryForString) registar.get(key);
    }


    private static class SmartTelemetry {
        private final String key;

        private SmartTelemetry(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }

//        public T get() {
//            switch (type) {
//                case BOOLEAN:
//                    return (T) (Boolean) SmartDashboard.getBoolean(key, (boolean) defaultValue);
//                case NUMBER:
//                    return (T) (Double) SmartDashboard.getNumber(key, (double) defaultValue);
//                case STRING:
//                    return (T) SmartDashboard.getString(key, (String) defaultValue);
//                default:
//                    throw new IllegalStateException("Can't generically get type " + defaultValue.getClass().toString());
//            }
//        }
    }

    public static final class SmartTelemetryForString extends SmartTelemetry {
        private final String defaultValue;

        private SmartTelemetryForString(String key, String defaultValue) {
            super(key);
            this.defaultValue = defaultValue;
            put(defaultValue);
        }

        public String getString() {
            return getString(defaultValue);
        }

        public String getString(String defaultValue) {
            return SmartDashboard.getString(super.key, defaultValue);
        }

        public void put(String value) {
            SmartDashboard.putString(getKey(), value);
        }
    }

    public static final class SmartTelemetryForNumber extends SmartTelemetry {
        private final double defaultValue;

        private SmartTelemetryForNumber(String key, double defaultValue) {
            super(key);
            this.defaultValue = defaultValue;
            put(defaultValue);
        }

        public double getNumber() {
            return getNumber(defaultValue);
        }

        public double getNumber(double defaultValue) {
            return SmartDashboard.getNumber(super.key, defaultValue);
        }

        public void put(double number) {
            SmartDashboard.putNumber(getKey(), number);
        }
    }

    public static final class SmartTelemetryForBoolean extends SmartTelemetry {
        private final boolean defaultValue;

        private SmartTelemetryForBoolean(String key, boolean defaultValue) {
            super(key);
            this.defaultValue = defaultValue;
            put(defaultValue);
        }

        public boolean getBoolean() {
            return getBoolean(defaultValue);
        }

        public boolean getBoolean(boolean defaultValue) {
            return SmartDashboard.getBoolean(super.key, defaultValue);
        }

        public void put(boolean value) {
            SmartDashboard.putBoolean(getKey(), value);
        }
    }
}
