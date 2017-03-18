package frc.team3465.omegafactor2017.modes;

import frc.team3465.omegafactor2017.*;
import frc.team3465.omegafactor2017.hardware.F130Gamepad;

/**
 * Created by FRC 3465 on 3/2/2017.
 */
public class TestTeleOp implements frc.team3465.omegafactor2017.TeleOp {

    private final F130Gamepad gamepad1;

    public TestTeleOp(F130Gamepad gamepad1, CameraServer2 cameraServer) {
        this.gamepad1 = gamepad1;
        //this.cameraServer = cameraServer;
    }

    @Override
    public void rearm() {

    }

    @Override
    public void start() {
        HardwareMap.leftFront.setExpiration(30);
        HardwareMap.LeftBack.setExpiration(30);
        HardwareMap.RightBack.setExpiration(30);
        HardwareMap.RightFront.setExpiration(30);
    }

    @Override
    public void loop() {
        double pow = Math.hypot(gamepad1.leftX(), gamepad1.leftY()) * Math.signum(gamepad1.leftY());
        if (gamepad1.a()) {
            HardwareMap.leftFront.set(pow);
        }

        if (gamepad1.b()) {
            HardwareMap.RightFront.set(pow);
        }

        if (gamepad1.x()) {
            HardwareMap.RightBack.set(pow);
        }

        if (gamepad1.y()) {
            HardwareMap.LeftBack.set(pow);
        }
    }
}
