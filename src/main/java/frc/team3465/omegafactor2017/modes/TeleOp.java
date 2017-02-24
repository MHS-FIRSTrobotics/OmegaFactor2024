package frc.team3465.omegafactor2017.modes;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3465.omegafactor2017.CameraServer2;
import frc.team3465.omegafactor2017.HardwareMap;
import frc.team3465.omegafactor2017.hardware.F130Gamepad;

import java.io.*;
import java.util.HashMap;

import static java.lang.Thread.sleep;

/**
 * Created by David on 1/7/2017.
 */
public class TeleOp implements frc.team3465.omegafactor2017.TeleOp {
    private final F130Gamepad gamepad1;
    private final CameraServer2 cameraServer;
    private final File teleOpSettings = new File("/home/lvuser/frc/teleop.settings");
    private final String enable_auto_correct_tag = "ENABLE_AUTO_CORRECT";
    private long timestamp;
    public static final String teleop_lat = "TELEOP_LAT";
    private static final String WINCH_POW = "WINCH_POW";
    HashMap<String, String> settingsMap = new HashMap<>();
    private boolean lastTimeAWasPressed = false;
    private boolean holdWinch = false;
    private double maxRotateSpeed;
    private boolean autoCorrectStraight;
    private boolean enableCorrectionInvertionY;
    private String enable_correction_invert_neg_y_key = "INVERT_Y_CORR";

    public TeleOp(F130Gamepad gamepad1, CameraServer2 cameraServer) {
        this.gamepad1 = gamepad1;
        this.cameraServer = cameraServer;
    }

    @Override
    public void rearm() {
        maxRotateSpeed = 9.7;
        lastTimeAWasPressed = false;
        holdWinch = false;
        autoCorrectStraight = true;
        enableCorrectionInvertionY = true;
    }

    @Override
    public void start() {
        timestamp = System.nanoTime();
//        if (teleOpSettings.exists()) {
//            try {
//                BufferedInputStream stream = new BufferedInputStream(new FileInputStream(teleOpSettings));
//                int readByte = stream.read();
//                while (readByte != -1) {
//                    String chars = "";
//                    String value = "";
//                    boolean inValueMode = true;
//                    boolean inCommentMode = false;
//                    if (Character.isAlphabetic(readByte) && inValueMode && !inCommentMode) {
//                        chars += readByte;
//                    } else if (readByte == '=' && inValueMode && !inCommentMode) {
//                        inValueMode = false;
//                    } else if (readByte == '#' && inValueMode) {
//                        inCommentMode = true;
//                    } else {
//                        if (readByte == '\n') {
//                            if (!inCommentMode) settingsMap.put(chars, value);
//                        } else {
//                            if (!inCommentMode) value += readByte;
//                        }
//                    }
//                    readByte = stream.read();
//                }
//            } catch (FileNotFoundException e) {
//                Log.i("TeleOp Settings failed to open: " + e.getMessage());
//            } catch (IOException e) {
//                Log.i("TeleOp Settings failed to reas: " + e.getMessage());
//            }
//        }
        //SmartDashboard.putNumber(WINCH_POW, .7);
        SmartDashboard.putBoolean(enable_auto_correct_tag, autoCorrectStraight);
        SmartDashboard.putBoolean(enable_correction_invert_neg_y_key, enableCorrectionInvertionY);
    }

    public void loop() {
        double pwr = gamepad1.backRight() ? 1 : .5;
        boolean enableCv = gamepad1.backLeft();
        pwr = enableCv ? .2 : pwr;
        cameraServer.notifyToUseCv(enableCv);

        double correction = 0;
        if (HardwareMap.navx != null) {
            SmartDashboard.putNumber("ROTATE_ANGLE", HardwareMap.navx.getAngle());
            double rate = HardwareMap.navx.getRate();
            SmartDashboard.putNumber("ROTATE_SPEED", rate);
            SmartDashboard.putNumber("MAX_ROTATE_SPEED", maxRotateSpeed);
            if (autoCorrectStraight) maxRotateSpeed = SmartDashboard.getNumber("MAX_ROTATE_SPEED", maxRotateSpeed);
            maxRotateSpeed = Math.max(Math.abs(rate), maxRotateSpeed);
            correction = HardwareMap.navx.getRate() / maxRotateSpeed;
        }

        double strafe = gamepad1.leftX() * pwr;
        double fwbw = gamepad1.leftY() * pwr;
        double turn = gamepad1.rightX() * pwr;

        SmartDashboard.putNumber("CORRECTION_UPDATE", correction);
        autoCorrectStraight = SmartDashboard.getBoolean(enable_auto_correct_tag, autoCorrectStraight);
        if (Math.abs(turn) < .05 && Math.abs(fwbw) > .1 && autoCorrectStraight) {
            if (enableCorrectionInvertionY) correction *= Math.signum(fwbw);
            turn += correction;
        }

        if (gamepad1.dpadUp()) {
            HardwareMap.Winch.set(1);
        } else if (gamepad1.dpadDown()) {
            HardwareMap.Winch.set(-1);
        } else if (gamepad1.a()) {
            //double number = SmartDashboard.getNumber(WINCH_POW, -.7);
            if (!lastTimeAWasPressed) holdWinch = !holdWinch;
            //Log.i("Winch is " + number);
        } else {
            HardwareMap.Winch.set(0);
        }

        lastTimeAWasPressed = gamepad1.a();

        SmartDashboard.putBoolean("ENABLE_WINCH_HOLD", holdWinch);

        //Log.d(fwbw);
// ++
        // + -
        // - +
        // - -

        if (holdWinch) {
            HardwareMap.Winch.set(0.1);
        }
        HardwareMap.drive(fwbw, strafe, turn);
        SmartDashboard.putNumber(teleop_lat, (System.nanoTime() - timestamp) / 1e9);
        timestamp = System.nanoTime();
        try {
            sleep(20);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}