package frc.team3465.omegafactor2017.modes;

import frc.team3465.omegafactor2017.AutoMode;
import frc.team3465.omegafactor2017.CameraServer2;
import frc.team3465.omegafactor2017.HardwareMap;

/**
 * Created by FRC 3465 on 2/15/2017.
 */
public class AutoCameraLineUp implements AutoMode {
    private final CameraServer2 cameraServer;
    private final int centerPointOfImage = CameraServer2.IMAGE_WIDTH / 2;

    public AutoCameraLineUp(CameraServer2 cameraServer) {
        this.cameraServer = cameraServer;
    }

    @Override
    public void rearm() {

    }

    @Override
    public void start() {
        cameraServer.notifyToUseCv(true);
    }

    @Override
    public void loop() {
        HardwareMap.drive(0, 0 , (CameraServer2.centerX() - centerPointOfImage) * (1 / (double) CameraServer2.IMAGE_WIDTH));
    }

    @Override
    public String name() {
        return "Camera Lineup";
    }
}
