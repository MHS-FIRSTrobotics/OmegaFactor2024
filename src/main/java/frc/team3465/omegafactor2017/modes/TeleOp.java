package frc.team3465.omegafactor2017.modes;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;
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

    public TeleOp(F130Gamepad gamepad1) {
        this.gamepad1 = gamepad1;

    }

    @Override
    public void rearm() {

    }

    @Override
    public void start() {


    }

    public void loop() {
        if( gamepad1.backRight()){
           pwr = 1.0;
        }
        else{
            pwr = .5;
        }


        strafe = gamepad1.leftX() * pwr;
        fwbw = gamepad1.leftY() * pwr;
        turn = gamepad1.rightX() * pwr;
        //Log.d(fwbw);
// ++
        // + -
        // - +
        // - -

        HardwareMap.leftFront.set(fwbw - strafe - turn);
        HardwareMap.RightFront.set(fwbw + strafe + turn);
        HardwareMap.RightBack.set(fwbw - strafe + turn);
        HardwareMap.LeftBack.set(fwbw + strafe - turn);
    }
}