package frc.team3465.omegafactor2017;

import jaci.openrio.toast.lib.log.Logger;
import jaci.openrio.toast.lib.module.IterativeModule;

public class RobotModule extends IterativeModule {
    public static Logger logger;

    @Override
    public String getModuleName() {
        return "OmegaFactor2017";
    }

    @Override
    public String getModuleVersion() {
        return "0.0.1";
    }

    @Override
    public void robotInit() {
        logger = new Logger("OmegaFactor2017", Logger.ATTR_DEFAULT);
        //TODO: Module Init
    }

    @Override
    public void autonomousInit() {
        super.autonomousInit();
    }

    @Override
    public void teleopInit() {
        super.teleopInit();
    }

    @Override
    public void autonomousPeriodic() {
        super.autonomousPeriodic();
    }

    @Override
    public void teleopPeriodic() {
        super.teleopPeriodic();
    }
}
