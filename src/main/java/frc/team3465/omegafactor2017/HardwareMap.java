package frc.team3465.omegafactor2017;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.*;
import trikita.log.Log;

/**
 * Created by FRC 3465 on 1/16/2017.
 */
public class HardwareMap {
    public static final VictorSP leftFront = new VictorSP(0);
    public static final VictorSP LeftBack = new VictorSP(1);
    public static final VictorSP RightFront = new VictorSP(2);
    public static final VictorSP RightBack = new VictorSP(3);
    public static final CANTalon Winch = new CANTalon(1);
    public static AHRS navx;

    public static void init() {
        RightBack.setInverted(true);
        RightFront.setInverted(true);

        //PIDController pidController = new PIDController()
        try {
            if (navx != null) return;
            navx = new AHRS(SPI.Port.kMXP);
            navx.setAngleAdjustment(navx.getAngle());
        } catch (RuntimeException ex) {
            Log.e("NavX encountered error: " + ex.getMessage());
        }
    }

    public static void drive(double fwbw, double strafe, double turn) {
        leftFront.set(fwbw - strafe - turn);
        RightFront.set(fwbw + strafe + turn);
        RightBack.set(fwbw - strafe + turn);
        LeftBack.set(fwbw + strafe - turn);
    }}
