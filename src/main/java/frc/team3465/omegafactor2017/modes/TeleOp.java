package frc.team3465.omegafactor2017.modes;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3465.omegafactor2017.CameraServer2;
import frc.team3465.omegafactor2017.HardwareMap;
import frc.team3465.omegafactor2017.Mode;
import frc.team3465.omegafactor2017.hardware.F130Gamepad;
import trikita.log.Log;

/**
 * Created by David on 1/7/2017.
 */
public class TeleOp implements frc.team3465.omegafactor2017.TeleOp {
    private final F130Gamepad gamepad1;
    private double fwbw;
    private double strafe;
    private double turn;
    private double pwr;
    private long timestamp;

    public TeleOp(F130Gamepad gamepad1) {
        this.gamepad1 = gamepad1;

    }

    @Override
    public void rearm() {

    }

    @Override
    public void start() {
        timestamp = System.nanoTime();

    }

    public void loop() {
        pwr = gamepad1.backRight() ? 1 : .5;
        boolean enableCv = gamepad1.rightTrigger() > .5;
        pwr = enableCv ? .2 : pwr;
       // CameraServer2.notifyToUseCv(enableCv);

        strafe = gamepad1.leftX() * pwr;
        fwbw = gamepad1.leftY() * pwr;
        turn = gamepad1.rightX() * pwr;

        if (gamepad1.dpadUp()) {
            HardwareMap.Winch.set(1);
        } else if (gamepad1.dpadDown()) {
            HardwareMap.Winch.set(-1);
        } else {
            HardwareMap.Winch.set(0);
        }
        //Log.d(fwbw);
// ++
        // + -
        // - +
        // - -

        HardwareMap.leftFront.set(fwbw - strafe - turn);
        HardwareMap.RightFront.set(fwbw + strafe + turn);
        HardwareMap.RightBack.set(fwbw - strafe + turn);
        HardwareMap.LeftBack.set(fwbw + strafe - turn);
        SmartDashboard.putNumber("TELEOP_LAT", System.nanoTime() - timestamp);
        timestamp = System.nanoTime();
    }
}