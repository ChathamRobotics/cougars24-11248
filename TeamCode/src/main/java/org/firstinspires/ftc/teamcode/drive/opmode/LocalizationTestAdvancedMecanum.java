package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

/**
 * 11248 ON TOP
 *
 * Elite gaming is occuring here
 *
 * This is the entire drive base
 */
@TeleOp(group = "drive")
public class LocalizationTestAdvancedMecanum extends LinearOpMode {

    private float Motorpower = 0.4f;
    private final ElapsedTime runtime = new ElapsedTime();
    private double lastPowerChangeTime;


    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (!isStopRequested()) {

            // This is the main driving mechanism
            // if this breaks it's so joever.
            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y * Motorpower,
                            -(gamepad1.right_trigger - gamepad1.left_trigger + gamepad1.left_stick_x) * Motorpower * 1.2,
                            -gamepad1.right_stick_x * Motorpower
                    )
            );
            // Ramp up & Ramp Down
            if (runtime.time() > lastPowerChangeTime + 0.5) {
                if (gamepad1.dpad_down) {
                    Motorpower = Math.max(0, Motorpower - 0.1f);
                    lastPowerChangeTime = runtime.time();
                } else if (gamepad1.dpad_up) {
                    Motorpower = Math.min(1, Motorpower + 0.1f);
                    lastPowerChangeTime = runtime.time();
                }
            }
                drive.update();

                // Boost & Slow Modes
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

