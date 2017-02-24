package frc.team3465.omegafactor2017;

import edu.wpi.cscore.*;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3465.omegafactor2017.cv.GripPipeline;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import trikita.log.Log;

/**
 * This is a demo program showing the use of the CameraServer class.
 * With start automatic capture, there is no opportunity to process the image.
 * Look at the IntermediateVision sample for how to process the image before sending it to the FRC PC Dashboard.
 */
public class CameraServer2 {
    public static final int IMAGE_WIDTH = 640;
    private static final int IMAGE_HEIGHT = 480;
    private static final int FPS = 60;
    private static final double CAMERA_DELAY = 1 / FPS;
    final UsbCamera cam0;
    edu.wpi.first.wpilibj.CameraServer server;
    private CameraImageProcessor processor;
    private Thread processingThread;
    private int runAttempt = 1;
    private final static CvEnabler enabler = new CvEnabler();

    public CameraServer2() {
        server = edu.wpi.first.wpilibj.CameraServer.getInstance();
        //server.startAutomaticCapture();
        cam0 = server.startAutomaticCapture("cam0", "/dev/video0");
        cam0.setWhiteBalanceAuto();
        cam0.setResolution(IMAGE_WIDTH, IMAGE_HEIGHT);
        cam0.setFPS(24);
        processor = new CameraImageProcessor(server, enabler, cam0);
        processingThread = processor.start();


        //the camera name (ex "cam0") can be found through the roborio web interface
    }

    public void notifyToUseCv(boolean enableCv) {
        enabler.setEnabled(enableCv);
    }

    private void restartImageProcessor() {
        ++runAttempt;
        processor = null;
        processor = new CameraImageProcessor(server, enabler, cam0);
        processingThread = processor.start();
    }

    public void stop() {
        processingThread.interrupt();
    }

    private class CameraImageProcessor implements Runnable {
        private final CameraServer server;
        private final GripPipeline cvPipeline;
        private final UsbCamera camera;
        private volatile boolean processCv = false;
        private Thread runningThread;
        private boolean crashed = false;
        private final CvEnabler enabler;

        CameraImageProcessor(CameraServer server, CvEnabler enabler, UsbCamera cam0) {
            this.server = server;
            cvPipeline = new GripPipeline();
            this.enabler = enabler;
            this.camera = cam0;
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
//            if (runningThread != Thread.currentThread() && (runningThread != null && runningThread.isAlive()) && !crashed) throw new IllegalStateException("Camera Server is already running");
//            else runningThread = Thread.currentThread();
//            Log.i("Camera thread starting...");
//            // Get a CvSink. This will capture Mats from the camera
//            CvSink cvSink = CameraServer.getInstance().getVideo(camera);
//            // Setup a CvSource. This will send images back to the Dashboard
//            CvSource outputStream = CameraServer.getInstance().putVideo("CV_cam0", IMAGE_WIDTH, IMAGE_HEIGHT);
//            // Mats are very memory expensive. Lets reuse this Mat.
//            Mat mat = new Mat();
//            Log.i("Camera thread started!");
//            //for (VideoProperty property : outputStream.enumerateProperties()) {
//            //    Log.i(property.getString());
//            //}
//            Log.i("Camera stream: " + outputStream.getKind().name());
//            //SmartDashboard.putBoolean("CAMERA_CONN", outputStream.isConnected());
//            //MjpegServer main = CameraServer.getInstance().addServer("MAIN");
//            //CameraServer.getInstance().addServer(outputStream);
//            long timestamp = System.nanoTime();
//            while (!Thread.currentThread().isInterrupted()) {
//                //SmartDashboard.putBoolean("CAMERA_CONN", outputStream.isConnected());
//                if (cvSink.grabFrame(mat) == 0) {
//                    // Send the output the error.
//                    String error = cvSink.getError();
//
//                    outputStream.notifyError(error);
//                    System.err.println(error);
//                    // skip the rest of the current iteration
//                    continue;
//                }
//
//
//                if (enabler.isEnabled()) {
//                    cvPipeline.process(mat);
//                }
//
//                Imgproc.rectangle(mat, new Point(100, 100), new Point(400, 400),
//                        new Scalar(255, 255, 255), 5);
//
//                SmartDashboard.putBoolean("USING_CV", enabler.isEnabled());
//                // Give the output stream a new image to display
//                outputStream.setFPS(24);
//                outputStream.putFrame(mat);
//                long delta = System.nanoTime() - timestamp;
//                SmartDashboard.putNumber("CAMERA_LAT", delta / 1e9);
//                timestamp = System.nanoTime();
//                double sleep = CAMERA_DELAY - delta;
//                if (sleep > 0) {
//                    try {
//                        Thread.sleep((long) (sleep / 1e6), (int) (sleep - sleep / 1e6));
//                    } catch (InterruptedException e) {
//                        Log.i("Interrupted stream...");
//                        Thread.currentThread().interrupt();
//                        return;
//                    }
//                }
//            }
//            SmartDashboard.putNumber("CAMERA_LAT", -1);
//            Log.e("Camera thread is stopped!");
            // !------------------- NEW CODE -------------------- !
            // Set the resolution
            int width = 320;
            int height = 240;
            camera.setResolution(width, height);

            // Get a CvSink. This will capture Mats from the camera
            CvSink cvSink = CameraServer.getInstance().getVideo();
            // Setup a CvSource. This will send images back to the Dashboard
            CvSource outputStream = CameraServer.getInstance().putVideo("CV_cam0", width, height);

            // Mats are very memory expensive. Lets reuse this Mat.
            Mat mat = new Mat();

            // This cannot be 'true'. The program will never exit if it is. This
            // lets the robot stop this thread when restarting robot code or
            // deploying.
            while (!Thread.interrupted()) {
                // Tell the CvSink to grab a frame from the camera and put it
                // in the source mat.  If there is an error notify the output.
                if (cvSink.grabFrame(mat) == 0) {
                    // Send the output the error.
                    outputStream.notifyError(cvSink.getError());
                    // skip the rest of the current iteration
                    continue;
                }
                int channels = mat.channels();

                if (enabler.isEnabled()) {
                    cvPipeline.process(mat);
//                    mat.reshape(channels);
//                    Log.i("Num of orig channels: " + channels);
//                    Log.i("Num of curr channels: " + mat.channels());
                    outputStream.putFrame(cvPipeline.hsvThresholdOutput());
                } else {
                    outputStream.putFrame(mat);
                }

                // Put a rectangle on the image
                //Imgproc.rectangle(mat, new Point(100, 100), new Point(400, 400),
                //        new Scalar(255, 255, 255), 5);
                // Give the output stream a new image to display

                try {
                    Thread.sleep((long) CAMERA_DELAY * 1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
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