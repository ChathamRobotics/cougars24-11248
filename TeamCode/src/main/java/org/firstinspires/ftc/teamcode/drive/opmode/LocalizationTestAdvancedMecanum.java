package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

/**
 * This is a simple teleop routine for testing localization. Drive the robot around like a normal
 * teleop routine and make sure the robot's estimated pose matches the robot's actual pose (slight
 * errors are not out of the ordinary, especially with sudden drive motions). The goal of this
 * exercise is to ascertain whether the localizer has been configured properly (note: the pure
 * encoder localizer heading may be significantly off if the track width has not been tuned).
 */
@TeleOp(group = "drive")
public class LocalizationTestAdvancedMecanum extends LinearOpMode {

    private float Motorpower = 0.4f;

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (!isStopRequested()) {
            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y * Motorpower,
                            -gamepad1.left_stick_x * Motorpower,
                            -gamepad1.right_stick_x * Motorpower
                    )
            );

            drive.update();

            // Quick Adjust Power
            if (gamepad1.y) {
                Motorpower = 0.8f;
            } else if (gamepad1.a) {
                Motorpower = 0.4f;
            }
            if (gamepad1.left_bumper) {
                Motorpower = 0.9f;

            } else if (gamepad1.right_bumper) {
                Motorpower = 0.2f;

            } else if (!gamepad1.right_bumper && !gamepad1.left_bumper) {
                Motorpower = 0.4f;

            }

            Pose2d poseEstimate = drive.getPoseEstimate();
            telemetry.addData("x", poseEstimate.getX());
            telemetry.addData("y", poseEstimate.getY());
            telemetry.addData("heading", poseEstimate.getHeading());
            telemetry.update();

        }
    }
}
