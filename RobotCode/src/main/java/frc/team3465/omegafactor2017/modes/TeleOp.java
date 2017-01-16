package frc.team3465.omegafactor2017.modes;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;
import frc.team3465.omegafactor2017.Mode;
import frc.team3465.omegafactor2017.hardware.F130Gamepad;

/**
 * Created by David on 1/7/2017.
 */
public class TeleOp implements frc.team3465.omegafactor2017.TeleOp {
    private final F130Gamepad gamepad1;
    private double fwbw;
    private double strafe;
    private double turn;
    private VictorSP LeftFront;
    private VictorSP LeftBack;
    private Talon RightBack;
    private Talon RightFront;



    public TeleOp(F130Gamepad gamepad1) {
        this.gamepad1 = gamepad1;

    }

    @Override
    public void rearm() {

    }

    @Override
    public void start() {



    }

    @Override
    public void loop() {




    }
}
