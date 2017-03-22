package frc.team3465.omegafactor2017.modes;

import frc.team3465.omegafactor2017.AutoMode;
import frc.team3465.omegafactor2017.util.ElapsedTime;
import frc.team3465.omegafactor2017.HardwareMap;
import trikita.log.Log;

/**
 * Created by FRC 3465 on 2/15/2017.
 */
public class AutoDriveForward implements AutoMode {
    ElapsedTime time;
    private double maxRotateSpeed = 16.1;
    private boolean finished =  false;

    @Override
    public void rearm() {
        finished = false;
    }

    @Override
    public void start() {
        //Log.i("S")
        time = new ElapsedTime();
    }

    @Override
    public void loop() {
        if (!finished) {
            finished = time.seconds() < 10 && Math.abs(HardwareMap.distanceSensor.getDistanceCm() - 30) < .5d;
            HardwareMap.drive(.2, 0, pidSourceRotationCorrection());
        } else {
            HardwareMap.drive(0, 0, 0);
        }


    }

    private double pidSourceRotationCorrection() {
        double correction = 0;
        if (HardwareMap.navx != null) {
            //SmartDashboard.putNumber("ROTATE_ANGLE", HardwareMap.navx.getAngle());
            //rotate_angle.put(HardwareMap.navx.getAngle());
            double rate = HardwareMap.navx.getRate();
            // SmartDashboard.putNumber("ROTATE_SPEED", rate);
            //rotate_speed.put(rate);

            //enableManuallyAdjustCorrectionConstant = SmartDashboard.getBoolean(enable_manual_adj_course_correct_key, false);
            boolean enableManuallyAdjustCorrectionConstant =  true;
//            if (!enableManuallyAdjustCorrectionConstant) {
//                //SmartDashboard.putNumber("MAX_ROTATE_SPEED", maxRotateSpeed);
//                max_rotate_speed.put(maxRotateSpeed);
//            }
            //maxRotateSpeed = SmartDashboard.getNumber("MAX_ROTATE_SPEED", maxRotateSpeed);
            //max_rotate_speed.getNumber();

            correction = HardwareMap.navx.getRate() / maxRotateSpeed;
        }
        return correction;
    }

    @Override
    public String name() { return "Drive Forward"; }

}
