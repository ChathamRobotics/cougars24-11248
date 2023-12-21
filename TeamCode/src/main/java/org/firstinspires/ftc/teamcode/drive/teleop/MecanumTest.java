package org.firstinspires.ftc.teamcode.drive.teleop;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.opmode.ModuleIntegrator;

import java.util.Arrays;

@TeleOp(name = "MecanumTest")
public class MecanumTest extends LinearOpMode {

        float SPEED = 0.75f;
        TeleopDrive robot;

        @Override
        public void runOpMode() {
            robot = new TeleopDrive(hardwareMap, Arrays.asList(), SPEED);

            waitForStart();

            while (opModeIsActive()) {

                robot.move(gamepad1.left_stick_y, gamepad1.right_trigger - gamepad1.left_trigger, gamepad1.right_stick_x);
                if (gamepad1.left_bumper) robot.setSpeed(0.2f);
                if (gamepad1.right_bumper) robot.setSpeed(1);
                if (!gamepad1.left_bumper && !gamepad1.right_bumper) robot.setSpeed(0.75f);

                Pose2d poseEstimate = robot.getPoseEstimate();

            }
        }
    }


