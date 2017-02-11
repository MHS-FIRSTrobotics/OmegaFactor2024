package frc.team3465.omegafactor2017;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;

/**
 * Created by FRC 3465 on 1/16/2017.
 */
public class HardwareMap {
    public static final VictorSP leftFront = new VictorSP(0);
    public static final VictorSP LeftBack = new VictorSP(1);
    public static final VictorSP RightFront = new VictorSP(2);
    public static final VictorSP RightBack = new VictorSP(3);
    public static final CANTalon Winch = new CANTalon(1);

    public static void init() {
        RightBack.setInverted(true);
        RightFront.setInverted(true);
    }
}
