package frc.team3465.omegafactor2017;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import frc.team3465.omegafactor2017.hardware.F130Gamepad;
import trikita.log.Log;

public class RobotModule extends IterativeRobot {
    private final static String TAG = "CoreRobot";
    private TeleOp teleOpCode;
    private CameraServer2 cameraServer;
    private final F130Gamepad gamepad1 = new F130Gamepad(new Joystick(0));

    @Override
    public void robotInit() {
        Log.level(Log.D);
        Log.i("Robot is starting...");
        HardwareMap.init();
        //logger = new Logger("OmegaFactor2017", Logger.ATTR_DEFAULT);
        teleOpCode = new frc.team3465.omegafactor2017.modes.TeleOp(gamepad1);
        cameraServer = new CameraServer2();
        Log.i("Robot is ready for action!");
    }



    @Override
    public void disabledInit() {
        Log.i("Disabling robot!");
        //super.disabledInit();
    }

    @Override
    public void autonomousInit() {
        super.autonomousInit();
    }

    @Override
    public void autonomousPeriodic() {
        super.autonomousPeriodic();
    }

    @Override
    public void teleopInit() {
        Log.i("Starting teleop...");
        teleOpCode.rearm();
        teleOpCode.start();
    }

    @Override
    public void teleopPeriodic() {
        teleOpCode.loop();
    }
}
