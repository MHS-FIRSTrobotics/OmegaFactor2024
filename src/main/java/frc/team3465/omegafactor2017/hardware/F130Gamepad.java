package frc.team3465.omegafactor2017.hardware;

import edu.wpi.first.wpilibj.Joystick;
import org.jetbrains.annotations.NotNull;

import static frc.team3465.omegafactor2017.util.Preconditions.checkNotNull;

/**
 * Created by FIRST on 1/19/2016.
 */
public class F130Gamepad {
    private final Joystick joystick;

    public F130Gamepad(@NotNull final Joystick joystick) {
        this.joystick = checkNotNull(joystick);
    }

    public double leftX() {
        return -joystick.getRawAxis(0);
    }

    public double leftY() {
        return -joystick.getRawAxis(1);
    }

    public double leftTrigger() {
        return joystick.getRawAxis(2);
    }

    public double rightTrigger() {
        return joystick.getRawAxis(3);
    }

    public double rightX() {
        return -joystick.getRawAxis(4);
    }

    public double rightY() {
        return -joystick.getRawAxis(5);
    }

    public boolean a() {
        return joystick.getRawButton(1);
    }

    public boolean b() {
        return joystick.getRawButton(2);
    }

    public boolean x() {
        return joystick.getRawButton(3);
    }

    public boolean y() {
        return joystick.getRawButton(4);
    }

    public boolean backLeft() {
        return joystick.getRawButton(5);
    }

    public boolean backRight() {
        return joystick.getRawButton(6);
    }

    public boolean back() {
        return joystick.getRawButton(7);
    }

    public boolean dpadUp() {
        return joystick.getPOV() == 90;
    }

    public boolean dpadDown() {
        return joystick.getPOV() == 270;
    }

    public boolean dpadLeft() {
        return joystick.getPOV() == 180;
    }

    public boolean dpadRight() {
        return joystick.getPOV() == 0;
    }

    public boolean start() {
        return joystick.getRawButton(8);
    }

    public boolean leftJoystickButton() {
        return joystick.getRawButton(9);
    }

    public boolean rightJoystickButton() {
        return joystick.getRawButton(10);
    }

    public void leftRumble(int value) {
        joystick.setRumble(Joystick.RumbleType.kLeftRumble, value);
    }
}
