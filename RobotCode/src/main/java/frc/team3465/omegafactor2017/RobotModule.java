package frc.team3465.omegafactor2017;

import edu.wpi.first.wpilibj.IterativeRobot;
import trikita.log.Log;

public class RobotModule extends IterativeRobot {
    private final static String TAG = "CoreRobot";

    @Override
    public void robotInit() {
        Log.level(Log.D);
        Log.i("Robot is starting...");
        //logger = new Logger("OmegaFactor2017", Logger.ATTR_DEFAULT);
        //TODO: Module Init
        Log.i("Robot is ready for action!");
    }

    @Override
    public void disabledInit() {
        Log.i("Disabling robot!");
        super.disabledInit();
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
        super.teleopInit();
    }

    @Override
    public void teleopPeriodic() {
        super.teleopPeriodic();
    }
}
