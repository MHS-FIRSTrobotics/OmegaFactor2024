package frc.team3465.omegafactor2017.modes;

import frc.team3465.omegafactor2017.AutoMode;
import frc.team3465.omegafactor2017.ElapsedTime;
import frc.team3465.omegafactor2017.HardwareMap;
import frc.team3465.omegafactor2017.Mode;

/**
 * Created by FRC 3465 on 2/15/2017.
 */
public class AutoDriveForward implements AutoMode {
    ElapsedTime time;

    @Override
    public void rearm() {

    }

    @Override
    public void start() {
        time = new ElapsedTime();
    }

    @Override
    public void loop() {
        if (time.seconds() < 5) {
            HardwareMap.LeftBack.set(1);
            HardwareMap.leftFront.set(1);
            HardwareMap.RightBack.set(1);
            HardwareMap.RightFront.set(1);
        } else {
            HardwareMap.LeftBack.set(0);
            HardwareMap.leftFront.set(0);
            HardwareMap.RightBack.set(0);
            HardwareMap.RightFront.set(0);
        }
    }

    @Override
    public String name() {
        return "Drive Forward";
    }
}
