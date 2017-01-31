//package frc.team3465.omegafactor2017;
//
//import org.opencv.
//
//import org.jetbrains.annotations.NotNull;
//
//import java.util.LinkedList;
//import java.util.List;
//
//
//public class CameraServer  extends AbstractExecutionThreadService {
//    private NIVision.Image frame;
//    private List<Integer> cameraList = new LinkedList<>();
//    private static Thread executionThread;
//
//    @Override
//    public void startUp() throws Exception {
//        frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
//
//        // the camera name (ex "cam0") can be found through the roborio web interface
//        try {
//            cameraList.add(NIVision.IMAQdxOpenCamera("cam0",
//                    NIVision.IMAQdxCameraControlMode.CameraControlModeController));
//        } catch (Exception ex) {
//            System.err.println("Error accessing cam0");
//            ex.printStackTrace();
//        }
//
//        try {
//            cameraList.add(NIVision.IMAQdxOpenCamera("cam1",
//                    NIVision.IMAQdxCameraControlMode.CameraControlModeController));
//        } catch (Exception ex) {
//            System.err.println("Error accessing cam1");
//            ex.printStackTrace();
//        }
//
//        for (Integer session : cameraList) {
//            NIVision.IMAQdxConfigureGrab(session);
//        }
//
//
//        Thread.currentThread().setPriority(4);
//    }
//
//    @Override
//    protected void run() throws Exception {
//        executionThread = Thread.currentThread();
//        for (Integer session :
//                cameraList) {
//            NIVision.IMAQdxStartAcquisition(session);
//        }
//
//        //NIVision.IMA
//
//        int session;
//        if (cameraList.size() > 0) {
//            session = cameraList.get(0);
//        } else {
//            System.err.println("No camera found! Quitting...");
//            return;
//        }
//        while (isRunning()) {
////            for (Integer session :
////                    cameraList) {
//            //int session = cameraList.get(0);
//            NIVision.IMAQdxGrab(session, frame, 1);
//            edu.wpi.first.wpilibj.CameraServer.getInstance().setImage(frame);
//            //}
//
//            //NetworkTable.
//
//            Thread.yield();
//        }
//    }
//
//    @NotNull
//    private NIVision.Point convertFloatPointToPoint(NIVision.PointFloat point) {
//        return new NIVision.Point((int)point.x, (int) point.y);
//    }
//
//    @Override
//    public void shutDown() {
//        executionThread = null;
//        for (Integer session :
//                cameraList) {
//            NIVision.IMAQdxStopAcquisition(session);
//        }
//    }
//
//    public static void requestPriorityChange(int level) {
//        if (executionThread != null) {
//            executionThread.setPriority(level);
//        }
//    }
//}