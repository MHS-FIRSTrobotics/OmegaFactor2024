package frc.team3465.omegafactor2017;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;

/**
 * This is a demo program showing the use of the CameraServer class.
 * With start automatic capture, there is no opportunity to process the image.
 * Look at the IntermediateVision sample for how to process the image before sending it to the FRC PC Dashboard.
 */
public class CameraServer2 {
    final UsbCamera cam0;
    edu.wpi.first.wpilibj.CameraServer server;

    public CameraServer2() {
        server = edu.wpi.first.wpilibj.CameraServer.getInstance();
        cam0 = server.startAutomaticCapture("cam0", 0);
        cam0.setWhiteBalanceAuto();
        cam0.setResolution(240, 320);
        cam0.setFPS(24);


        //the camera name (ex "cam0") can be found through the roborio web interface
    }

//    /**
//     * start up automatic capture you should see the video stream from the
//     * webcam in your FRC PC Dashboard.
//     */
//    public void operatorControl() {
//        while (isOperatorControl() && isEnabled()) {
//            /** robot code here! **/
//            Timer.delay(0.005);		// wait for a motor update time
//        }
//    }

}