package org.firstinspires.ftc.teamcode.drive.auton;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.constraints.MinVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.ProfileAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TranslationalVelocityConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.drive.Locations;
import org.firstinspires.ftc.teamcode.drive.opmode.ModuleIntegrator;
import org.firstinspires.ftc.teamcode.drive.teleop.TeleopDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.Arrays;
import java.util.List;

@Autonomous (name = "Blue Left Full Auton Center Park (BOARDSIDE)")
public class BlueLeftCenterPark extends LinearOpMode {

    // TFOD_MODEL_ASSET points to a model file stored in the project Asset location,
    // this is only used for Android Studio when using models in Assets.
    private static final String TFOD_MODEL_ASSET = "model_20240208_105447.tflite";
    // Define the labels recognized in the model for TFOD (must be in training order!)
    private static final String[] LABELS = {
            "BlueElement"
    };

    private TfodProcessor tfod;
    private VisionPortal visionPortal;

    private TeleopDrive robot;

    private ElapsedTime runtime = new ElapsedTime();

    float SPEED = 0.6f;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData(">", "Robot Initializing...");
        telemetry.update();
        initTfod();

        robot = new TeleopDrive(hardwareMap, Arrays.asList(ModuleIntegrator.Module.CLAWTWO, ModuleIntegrator.Module.DRAWERSLIDE, ModuleIntegrator.Module.PIVOT, ModuleIntegrator.Module.SLIDEPIVOT, ModuleIntegrator.Module.CLAW, ModuleIntegrator.Module.GUN, ModuleIntegrator.Module.PIVOTTWO, ModuleIntegrator.Module.WINCHSYSTEM, ModuleIntegrator.Module.WINCHLIFTONE, ModuleIntegrator.Module.WINCHLIFTTWO), SPEED);

        //PRELOAD PURPLE LEFT

        robot.clawtwo.setState(0);
        robot.claw.setState(0); //close ^
        robot.slidePivot.setPos(0.2f);
        robot.pivot.setState(0.4f);
        robot.pivottwo.setState(0.4f);

        Pose2d startPos = Locations.blueLeft;

        robot.setPoseEstimate(startPos);


        Trajectory forward = robot.trajectoryBuilder(startPos)
                .forward(18, new TranslationalVelocityConstraint(20), new ProfileAccelerationConstraint(20))
                .build();

        Trajectory toLeft = robot.trajectoryBuilder(forward.end())
                .lineToLinearHeading(new Pose2d(12.5, 33, Math.toRadians(180)), new TranslationalVelocityConstraint(15),new ProfileAccelerationConstraint(20))
                .build();

        Trajectory toCenter = robot.trajectoryBuilder(forward.end())
                .lineToLinearHeading(new Pose2d(15, 35.2, Math.toRadians(90) ), new TranslationalVelocityConstraint(15), new ProfileAccelerationConstraint(20))
                .build();

        Trajectory toRight = robot.trajectoryBuilder(forward.end())
                .lineToLinearHeading(new Pose2d(16, 28, Math.toRadians(0)), new TranslationalVelocityConstraint(15), new ProfileAccelerationConstraint(20))
                .build();

        TrajectorySequence toBackdropLeft = robot.trajectorySequenceBuilder(toLeft.end())
                .setVelConstraint(new MinVelocityConstraint(Arrays.asList(new TranslationalVelocityConstraint(15))))
                .forward(0.2)
                .strafeRight(8)
                .splineToLinearHeading(Locations.backdropBlue, Math.toRadians(180))
                .strafeRight(4)
                .back(8)
                .build();

        TrajectorySequence toBackdropCenter = robot.trajectorySequenceBuilder(toCenter.end())
                .setVelConstraint(new MinVelocityConstraint(Arrays.asList(new TranslationalVelocityConstraint(15))))
                .forward(5)
                .splineToLinearHeading(Locations.backdropBlue, Math.toRadians(180))
                .strafeLeft(4)
                .back(8)
                .build();

        TrajectorySequence toBackdropRight = robot.trajectorySequenceBuilder(toRight.end())
                .setVelConstraint(new MinVelocityConstraint(Arrays.asList(new TranslationalVelocityConstraint(15))))
                .forward(5)
                .splineToLinearHeading(Locations.backdropBlue, Math.toRadians(180))
                .back(8)
                .strafeLeft(3)
                .build();


        telemetry.addData(">", "Robot Initialized!");
        telemetry.addData(">", "Press start to start");
        telemetry.update();


        while (opModeInInit()) {
            tfod.getRecognitions();
            sleep(50);
        }

        runtime.reset();
        robot.setPoseEstimate(Locations.blueLeft);

        Recognition propRecognition = null;

        while(runtime.seconds() < 5) {
            List<Recognition> currentRecognitions = tfod.getRecognitions();
            telemetry.addData("# Objects Detected", currentRecognitions.size());

            if (currentRecognitions.size() > 0) {
                propRecognition = currentRecognitions.get(0);
            }

            // Step through the list of recognitions and display info for each one.
            for (Recognition recognition : currentRecognitions) {
                double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
                double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;

                telemetry.addData(""," ");
                telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
                telemetry.addData("- Position", "%.0f / %.0f", x, y);
                telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
            }   // end for() loop

            // Push telemetry to the Driver Station.
            telemetry.update();

            // Save CPU resources; can resume streaming when needed.
            if (gamepad1.dpad_down) {
                visionPortal.stopStreaming();
            } else if (gamepad1.dpad_up) {
                visionPortal.resumeStreaming();
            }

            // Share the CPU.
            sleep(20);
        }
        visionPortal.close();

        int direction = 0;

        if (propRecognition == null) {
            // right
            direction = 3;
        } else {
            double x = (propRecognition.getLeft() + propRecognition.getRight()) / 2;
            double y = (propRecognition.getTop()  + propRecognition.getBottom()) / 2;

            if (y < 330 && x > 320 && x < 600) {
                // center
                direction = 2;
            } else {
                // left
                direction = 1;
            }
        }
        telemetry.addLine("Going forward...");
        telemetry.update();
        robot.pivot.setState(0.6f);
        robot.pivottwo.setState(0.6f);
        robot.followTrajectory(forward);
        robot.slidePivot.setPos(0.03f);
        telemetry.addLine("Went forward");
        telemetry.addLine("Turning" + direction);
        telemetry.update();
        switch (direction) {
            case 1:
                robot.followTrajectory(toLeft);
                break;
            case 2:
                robot.followTrajectory(toCenter);
                break;
            case 3:
                robot.followTrajectory(toRight);
                break;
        }
        telemetry.addLine("Placing pixel...");
        telemetry.update();
        robot.pivot.setState(0.6f);
        robot.pivottwo.setState(0.6f);
        robot.claw.setState(1);
        sleep(750);
        //robot.pivot.setState(0.8f);
        //robot.pivottwo.setState(0.8f);
        //sleep(500);
        robot.slidePivot.setPos(0.168f);
        robot.drawerSlide.setSlidePos(0.5f);
        robot.pivot.setState(0.4f);
        robot.pivottwo.setState(0.4f);
        robot.claw.setState(0);
        sleep(500);

        switch (direction) {
            case 1:
                robot.followTrajectorySequence(toBackdropLeft);
                telemetry.addLine("Left");
                telemetry.update();
                break;
            case 2:
                robot.followTrajectorySequence(toBackdropCenter);
                telemetry.addLine("Center");
                telemetry.update();
                break;
            case 3:
                robot.followTrajectorySequence(toBackdropRight);
                telemetry.addLine("Right");
                telemetry.update();
                break;
        }
        robot.clawtwo.setState(1); // open
        sleep(2000);
        robot.drawerSlide.setSlidePos(0);
        robot.claw.setState(0);
        robot.clawtwo.setState(0);
        robot.pivot.setState(0.9f);
        robot.pivottwo.setState(0.9f);

        Trajectory backward = robot.trajectoryBuilder(robot.getPoseEstimate())
                .lineToConstantHeading(new Vector2d(48,10)).build();

        robot.followTrajectory(backward);
        sleep(500);
        robot.slidePivot.goToPos(0);

        double timeStart = runtime.milliseconds();
        while (timeStart + 1000 > runtime.milliseconds()) {
            robot.drawerSlide.moveSlide(-0.3f, true);
        }

    }

    /**
     * Initialize the TensorFlow Object Detection processor.
     */
    private void initTfod() {

        // Create the TensorFlow processor by using a builder.
        tfod = new TfodProcessor.Builder()

                // With the following lines commented out, the default TfodProcessor Builder
                // will load the default model for the season. To define a custom model to load,
                // choose one of the following:
                //   Use setModelAssetName() if the custom TF Model is built in as an asset (AS only).
                //   Use setModelFileName() if you have downloaded a custom team model to the Robot Controller.
                .setModelAssetName(TFOD_MODEL_ASSET)

                // The following default settings are available to un-comment and edit as needed to
                // set parameters for custom models.
                .setModelLabels(LABELS)
                //.setIsModelTensorFlow2(true)
                //.setIsModelQuantized(true)
                //.setModelInputSize(300)
                //.setModelAspectRatio(16.0 / 9.0)

                .build();

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera (webcam).
        builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));

        // Choose a camera resolution. Not all cameras support all resolutions.
        //builder.setCameraResolution(new Size(640, 480));

        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
        //builder.enableLiveView(true);

        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
        //builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

        // Choose whether or not LiveView stops if no processors are enabled.
        // If set "true", monitor shows solid orange screen if no processors enabled.
        // If set "false", monitor shows camera view without annotations.
        //builder.setAutoStopLiveView(false);

        // Set and enable the processor.
        builder.addProcessor(tfod);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        // Set confidence threshold for TFOD recognitions, at any time.
        //tfod.setMinResultConfidence(0.75f);

        // Disable or re-enable the TFOD processor at any time.
        //visionPortal.setProcessorEnabled(tfod, true);

    }   // end method initTfod()
}