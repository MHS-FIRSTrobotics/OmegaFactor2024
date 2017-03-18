package frc.team3465.omegafactor2017.modes;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import frc.team3465.omegafactor2017.CameraServer2;
import frc.team3465.omegafactor2017.HardwareMap;
import frc.team3465.omegafactor2017.hardware.F130Gamepad;
import frc.team3465.omegafactor2017.util.SmartDashboard2;

import static java.lang.Thread.sleep;

/**
 * Created by David on 1/7/2017.
 */
public class TeleOp implements frc.team3465.omegafactor2017.TeleOp {
    private final F130Gamepad gamepad1;
    private final CameraServer2 cameraServer;
    //private final File teleOpSettings = new File("/home/lvuser/frc/teleop.settings");
    private long timestamp;

    //private static final String WINCH_POW = "WINCH_POW";
    //HashMap<String, String> settingsMap = new HashMap<>();
    private boolean lastTimeAWasPressed = false;
    private boolean holdWinch = false;
    private double maxRotateSpeed;
    private boolean autoCorrectStraight;
    private boolean enableCorrectionInvertionY;
    private boolean enableManuallyAdjustCorrectionConstant;
    private SmartDashboard2.SmartTelemetryForBoolean manualCourseCorrect;
    private SmartDashboard2.SmartTelemetryForBoolean enableCorrectInvert;
    private SmartDashboard2.SmartTelemetryForBoolean autoCorrect;
    private SmartDashboard2.SmartTelemetryForNumber correction_update;
    private SmartDashboard2.SmartTelemetryForBoolean enable_winch_hold;
    private SmartDashboard2.SmartTelemetryForBoolean led_e;
    private SmartDashboard2.SmartTelemetryForNumber rotate_speed;
    private SmartDashboard2.SmartTelemetryForNumber max_rotate_speed;
    private SmartDashboard2.SmartTelemetryForNumber led;
    private SmartDashboard2.SmartTelemetryForNumber teleop_lat_k;
    private SmartDashboard2.SmartTelemetryForNumber rotate_angle;
    private SmartDashboard2.SmartTelemetryForNumber distance_reading;
    private SmartDashboard2.SmartTelemetryForBoolean enable_launcher;
    private SmartDashboard2.SmartTelemetryForNumber launcher_speed;
    //private double ledPower = 1;

    public TeleOp(F130Gamepad gamepad1, CameraServer2 cameraServer) {
        this.gamepad1 = gamepad1;
        this.cameraServer = cameraServer;
    }

    @Override
    public void rearm() {
        maxRotateSpeed = 16.1;
        lastTimeAWasPressed = false;
        holdWinch = false;
        autoCorrectStraight = true;
        enableCorrectionInvertionY = false;
        enableManuallyAdjustCorrectionConstant = false;
      //  ledPower = 1;
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
        //String enable_auto_correct_tag = ;
        //String enable_manual_adj_course_correct_key = "MANUAL_ADJ_COURSE_CORRECT";
        //String enable_correction_invert_neg_y_key = "INVERT_Y_CORR";

        autoCorrect = SmartDashboard2.putBoolean("ENABLE_AUTO_CORRECT", autoCorrectStraight);
        enableCorrectInvert = SmartDashboard2.putBoolean("INVERT_Y_CORR", enableCorrectionInvertionY);
        manualCourseCorrect = SmartDashboard2.putBoolean("MANUAL_ADJ_COURSE_CORRECT", enableManuallyAdjustCorrectionConstant);
        correction_update = SmartDashboard2.putNumber("CORRECTION_UPDATE", 0);
        enable_winch_hold = SmartDashboard2.putBoolean("ENABLE_WINCH_HOLD", holdWinch);
        led_e = SmartDashboard2.putBoolean("LED_E", false);
        rotate_speed = SmartDashboard2.putNumber("ROTATE_SPEED", 0);
        max_rotate_speed = SmartDashboard2.putNumber("MAX_ROTATE_SPEED", maxRotateSpeed);
        led = SmartDashboard2.putNumber("LED", .5);
        teleop_lat_k = SmartDashboard2.putNumber("TELEOP_LAT", -1);
        rotate_angle = SmartDashboard2.putNumber("ROTATE_ANGLE", 0);
        distance_reading = SmartDashboard2.putNumber("IN_AWAY", -1);
        enable_launcher = SmartDashboard2.putBoolean("ENABLE_LAUNCHER", false);
        launcher_speed = SmartDashboard2.putNumber("LAUNCHER_SPEED", 1);
        //SmartDashboard.putNumber("LED", ledPower);
    }

    public void loop() {
        double pwr = checkForDriveModeChange();
        double correction = pidSourceRotationCorrection();
        updateTelemetry(correction);
        handleWinchMovement();
        handleShootingCommands();
        driveRobot(pwr, correction);
        updatePerformanceCharacteristics();

    }

    private void updatePerformanceCharacteristics() {
        //SmartDashboard.putNumber(teleop_lat, (System.nanoTime() - timestamp) / 1e6);
        teleop_lat_k.put((System.nanoTime() - timestamp) / 1E6);
        timestamp = System.nanoTime();
        try {
            sleep(20);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void driveRobot(double pwr, double correction) {
        double strafe = gamepad1.leftX() * pwr;
        double fwbw = gamepad1.leftY() * pwr;
        double turn = gamepad1.rightX() * pwr;

        if (Math.abs(turn) < .05 && Math.abs(fwbw) > .1) {
            if (enableCorrectionInvertionY) correction *= -Math.signum(fwbw);
            turn += correction;
        }

        HardwareMap.drive(fwbw, strafe, turn);
    }

    private void handleShootingCommands() {
        if (gamepad1.b()) HardwareMap.ballShooter.set(launcher_speed.getNumber());
        else HardwareMap.ballShooter.set(0);
    }

    private void updateTelemetry(double correction) {
//        SmartDashboard.putNumber("CORRECTION_UPDATE", correction);
//        SmartDashboard.putBoolean("ENABLE_WINCH_HOLD", holdWinch);
        correction_update.put(correction);
        enable_winch_hold.put(holdWinch);
        enableManuallyAdjustCorrectionConstant = enableCorrectInvert.getBoolean();
        //enableManuallyAdjustCorrectionConstant = SmartDashboard.getBoolean(enable_correction_invert_neg_y_key, enableManuallyAdjustCorrectionConstant);
        //autoCorrectStraight = SmartDashboard.getBoolean(enable_auto_correct_tag, autoCorrectStraight);
        autoCorrectStraight = autoCorrect.getBoolean(autoCorrectStraight);
        //ledPower = SmartDashboard.getNumber("LED", ledPower);
        //HardwareMap.ledDriver.set(ledPower);

        //if (SmartDashboard.getBoolean("LED_E", false) || CameraServer2.overrideLed) {
        if (led_e.getBoolean(false) || CameraServer2.overrideLed) {
            //HardwareMap.ledDriver.set(SmartDashboard.getNumber("LED", 0));
            HardwareMap.ledDriver.set(led.getNumber(0));
        } else {
            HardwareMap.ledDriver.disable();
        }

        distance_reading.put(HardwareMap.distanceSensor.getDistanceIn());
    }

    private void handleWinchMovement() {
        if (gamepad1.a() && !lastTimeAWasPressed) holdWinch = !holdWinch;

        if (gamepad1.dpadUp()) {
            HardwareMap.Winch.set(1);
        } else if (gamepad1.dpadDown()) {
            HardwareMap.Winch.set(-1);
        } else {
            HardwareMap.Winch.set(holdWinch ? .1 : 0);
        }

        lastTimeAWasPressed = gamepad1.a();
    }

    private double checkForDriveModeChange() {
        double pwr = gamepad1.backRight() ? 1 : .5;
        boolean enableCv = gamepad1.backLeft();
        pwr = enableCv ? .2 : pwr;
        cameraServer.notifyToUseCv(enableCv);
        return pwr;
    }

    private double pidSourceRotationCorrection() {
        double correction = 0;
        if (HardwareMap.navx != null) {
            //SmartDashboard.putNumber("ROTATE_ANGLE", HardwareMap.navx.getAngle());
            rotate_angle.put(HardwareMap.navx.getAngle());
            double rate = HardwareMap.navx.getRate();
            // SmartDashboard.putNumber("ROTATE_SPEED", rate);
            rotate_speed.put(rate);

            //enableManuallyAdjustCorrectionConstant = SmartDashboard.getBoolean(enable_manual_adj_course_correct_key, false);
            enableManuallyAdjustCorrectionConstant =  manualCourseCorrect.getBoolean();
            if (!enableManuallyAdjustCorrectionConstant) {
                //SmartDashboard.putNumber("MAX_ROTATE_SPEED", maxRotateSpeed);
                max_rotate_speed.put(maxRotateSpeed);
            }
            if (autoCorrectStraight) {
                if (enableManuallyAdjustCorrectionConstant) {
                    //maxRotateSpeed = SmartDashboard.getNumber("MAX_ROTATE_SPEED", maxRotateSpeed);
                    max_rotate_speed.getNumber();
                } else {
                    maxRotateSpeed = Math.max(Math.abs(rate), maxRotateSpeed);
                }

                correction = HardwareMap.navx.getRate() / maxRotateSpeed;
            }
        }
        return correction;
    }

}