package frc.team3465.omegafactor2017;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3465.omegafactor2017.hardware.F130Gamepad;
import frc.team3465.omegafactor2017.modes.AutoDriveForward;
import frc.team3465.omegafactor2017.modes.AutoNothing;
import trikita.log.Log;

public class RobotModule extends IterativeRobot {
    private final static String TAG = "CoreRobot";
    private TeleOp teleOpCode;
    private CameraServer2 cameraServer;
    private final F130Gamepad gamepad1 = new F130Gamepad(new Joystick(0));
    private final AutoMode[] autoMode;
    private SendableChooser<AutoMode> sendableChooser;
    private AutoMode selectedAuto;

    public RobotModule() {
        AutoMode[] temp = { new AutoNothing(), new AutoDriveForward() };
        autoMode = temp;
    }

    @Override
    public void robotInit() {
        Log.level(Log.D);
        Log.i("Robot is starting...");
        HardwareMap.init();
        //logger = new Logger("OmegaFactor2017", Logger.ATTR_DEFAULT);
        cameraServer = new CameraServer2();
        teleOpCode = new frc.team3465.omegafactor2017.modes.TeleOp(gamepad1, cameraServer);
        //UsbCamera usbCamera = CameraServer.getInstance().startAutomaticCapture();
        //Log.i(CameraServer.getInstance().getServer().getKind());
        sendableChooser = new SendableChooser<>();
        sendableChooser.addDefault("!!! Do Nothing !!!", autoMode[0]);
        for (int i = 1; i < autoMode.length; i++) {
            sendableChooser.addDefault(autoMode[i].name(), autoMode[i]);
        }
        SmartDashboard.putData("Auto Chooser", sendableChooser);

        Log.i("Robot is ready for action!");
    }
    @Override
    public void disabledInit() {
        Log.i("Disabling robot!");
        SmartDashboard.putNumber(frc.team3465.omegafactor2017.modes.TeleOp.teleop_lat, -1);
        //super.disabledInit();
    }

    @Override
    public void robotPeriodic() {
        //SendableChooser chooser = new SendableChooser(
    }

    @Override
    public void autonomousInit() {
        selectedAuto = sendableChooser.getSelected();
        selectedAuto.rearm();
        selectedAuto.start();
    }

    @Override
    public void autonomousPeriodic() {
        if (selectedAuto != null) selectedAuto.loop();
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
