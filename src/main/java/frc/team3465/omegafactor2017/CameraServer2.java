package frc.team3465.omegafactor2017;

import edu.wpi.cscore.*;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3465.omegafactor2017.cv.GripPipeline;
import org.opencv.core.Mat;
import trikita.log.Log;

/**
 * This is a demo program showing the use of the CameraServer class.
 * With start automatic capture, there is no opportunity to process the image.
 * Look at the IntermediateVision sample for how to process the image before sending it to the FRC PC Dashboard.
 */
public class CameraServer2 {
    public static final int IMAGE_WIDTH = 240;
    private static final int IMAGE_HEIGHT = 320;
    final UsbCamera cam0;
    edu.wpi.first.wpilibj.CameraServer server;
    private CameraImageProcessor processor;
    private Thread processingThread;
    private int runAttempt = 1;

    public CameraServer2() {
        server = edu.wpi.first.wpilibj.CameraServer.getInstance();
        cam0 = server.startAutomaticCapture("cam0", 0);
        cam0.setWhiteBalanceAuto();
        cam0.setResolution(IMAGE_WIDTH, IMAGE_HEIGHT);
        cam0.setFPS(24);
        //processor = new CameraImageProcessor(server);
        //processingThread = processor.start();


        //the camera name (ex "cam0") can be found through the roborio web interface
    }

    public void notifyToUseCv(boolean enableCv) {
        processor.enableAugment(enableCv);
    }

    private void restartImageProcessor() {
        ++runAttempt;
        processor = null;
        processor = new CameraImageProcessor(server);
        processingThread = processor.start();
    }

    private class CameraImageProcessor implements Runnable {
        private final CameraServer server;
        private final GripPipeline cvPipeline;
        private volatile boolean processCv = false;
        private Thread runningThread;
        private boolean crashed = false;

        CameraImageProcessor(CameraServer server) {
            this.server = server;
            cvPipeline = new GripPipeline();
        }

        @Override
        public void run() {
            try {
                safeRun();
            } catch (Exception ex) {
                crashed = true;
                Log.e(ex.toString());
                ex.printStackTrace();
                Log.w("Camera Processing Attempt " + runAttempt + " failed. Possibly restarting...");
                restartImageProcessor();
            }
        }

        private void safeRun() {
            if (runningThread != Thread.currentThread() && (runningThread != null && runningThread.isAlive()) && !crashed) throw new IllegalStateException("Camera Server is already running");
            else runningThread = Thread.currentThread();
            Log.i("Camera thread starting...");
            // Get a CvSink. This will capture Mats from the camera
            CvSink cvSink = CameraServer.getInstance().getVideo();
            // Setup a CvSource. This will send images back to the Dashboard
            CvSource outputStream = CameraServer.getInstance().putVideo("Rectangle", IMAGE_WIDTH, IMAGE_HEIGHT);
            // Mats are very memory expensive. Lets reuse this Mat.
            Mat mat = new Mat();
            Log.i("Camera thread started!");
            for (VideoProperty property : outputStream.enumerateProperties()) {
                Log.i(property.getString());
            }
            Log.i("Camera stream: " + outputStream.getKind().name());
            SmartDashboard.putBoolean("CAMERA_CONN", outputStream.isConnected());
            //MjpegServer main = CameraServer.getInstance().addServer("MAIN");
            //CameraServer.getInstance().addServer(outputStream);
            long timestamp = System.nanoTime();
            while (!Thread.currentThread().isInterrupted()) {
                SmartDashboard.putBoolean("CAMERA_CONN", outputStream.isConnected());
                if (cvSink.grabFrame(mat) == 0) {
                    // Send the output the error.
                    outputStream.notifyError(cvSink.getError());
                    // skip the rest of the current iteration
                    continue;
                }

                //if (processCv) cvPipeline.process(mat);
                // Give the output stream a new image to display
                outputStream.putFrame(mat);
                SmartDashboard.putNumber("CAMERA_LAT", System.nanoTime() - timestamp);
                timestamp = System.nanoTime();
            }
            SmartDashboard.putNumber("CAMERA_LAT", -1);
            Log.e("Camera thread is stopped!");
        }

        void enableAugment(boolean enable) {
            processCv = enable;
        }

        public Thread start() {
            Thread processorThread = new Thread(this);
            processorThread.setName("CV Processor");
            processorThread.setDaemon(true);
            processorThread.setPriority(3);
            processorThread.start();

            return processorThread;
        }
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