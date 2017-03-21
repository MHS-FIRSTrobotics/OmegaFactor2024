package frc.team3465.omegafactor2017.hardware;

import edu.wpi.first.wpilibj.AnalogInput;
import trikita.log.Log;

/**
 * Created by FRC 3465 on 3/18/2017.
 */
public class HrlvMaxSonarEz {
    private final AnalogInput input;

    public HrlvMaxSonarEz(AnalogInput input) {
        this.input = input;
        input.setAverageBits(20);
        input.setOversampleBits(20);
        //input.initAccumulator();
    }

    public double getDistanceMm() {
        double averageVoltage = input.getVoltage();
        //double voltageIn = 5 / 1024d;
        double mmDistance = 5 * averageVoltage / (4.959 / 1024d);
        //Log.i("Distance: " + mmDistance + "; " + averageVoltage);
        return mmDistance;
    }

    public double getDistanceCm() {
        return getDistanceMm() / 10;
    }

    public double getDistanceIn() {
        return getDistanceCm() / 2.54;
    }
}
