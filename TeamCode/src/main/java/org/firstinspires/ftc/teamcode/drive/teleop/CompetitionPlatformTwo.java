package org.firstinspires.ftc.teamcode.drive.teleop;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.opmode.ModuleIntegrator;

import java.util.Arrays;

@TeleOp(name = "Competition Platform Two")
public class CompetitionPlatformTwo extends LinearOpMode {
    // "My Pizza Crystalized" - Chip

        float SPEED = 0.75f;
        TeleopDrive robot;

        @Override
        public void runOpMode() {
            robot = new TeleopDrive(hardwareMap, Arrays.asList(ModuleIntegrator.Module.CLAWTWO, ModuleIntegrator.Module.ROTATION, ModuleIntegrator.Module.PIVOT, ModuleIntegrator.Module.LINEAR_SLIDE, ModuleIntegrator.Module.CLAW, ModuleIntegrator.Module.GUN, ModuleIntegrator.Module.PIVOTTWO), SPEED);

            waitForStart();

            while (opModeIsActive()) {
                robot.linearSlide.move(gamepad2.right_stick_y * 0.5f, false);
                robot.rotation.movePivot(gamepad2.left_stick_y * 0.5f, false);
                if (gamepad2.left_bumper) robot.claw.setState(0.8f);
                if (gamepad2.left_trigger != 0) robot.clawtwo.setState(0.8f);
                if (gamepad2.right_bumper) robot.claw.setState(1);
                if (gamepad2.right_trigger != 0) robot.clawtwo.setState(1);
                if (gamepad2.dpad_up) {robot.pivot.setState(1); robot.pivottwo.setState(1);}
                if (gamepad2.dpad_down) {robot.pivot.setState(0.8f); robot.pivottwo.setState(0.8f);}
                if (gamepad2.circle) robot.gun.setState(1);
                if (gamepad2.triangle) robot.gun.setState(0);

                robot.move(gamepad1.left_stick_y, gamepad1.right_trigger - gamepad1.left_trigger + gamepad1.left_stick_x, gamepad1.right_stick_x);
                if (gamepad1.left_bumper) robot.setSpeed(0.2f);
                if (gamepad1.right_bumper) robot.setSpeed(1);
                if (!gamepad1.left_bumper && !gamepad1.right_bumper) robot.setSpeed(0.75f);

                Pose2d poseEstimate = robot.getPoseEstimate();
                telemetry.addData("x", poseEstimate.getX());
                telemetry.addData("y", poseEstimate.getY());
                telemetry.addData("heading", poseEstimate.getHeading());
                telemetry.addData("slide1Pos", robot.linearSlide.clawLift.get(0).getCurrentPosition());
                telemetry.addData("slide1Pow", robot.linearSlide.clawLift.get(0).getPower());
                telemetry.addData("rot1Pos", robot.rotation.clawRot.get(0).getCurrentPosition());
                telemetry.addData("rot1Pow", robot.rotation.clawRot.get(0).getPower());
                telemetry.addData(robot.motors.get(0).getDeviceName(), robot.motors.get(0).getCurrentPosition());
                telemetry.addData(robot.motors.get(1).getDeviceName(), robot.motors.get(1).getCurrentPosition());
                telemetry.addData(robot.motors.get(2).getDeviceName(), robot.motors.get(2).getCurrentPosition());
                telemetry.addData(robot.motors.get(3).getDeviceName(), robot.motors.get(3).getCurrentPosition());
                telemetry.addData("slideJoystick", -gamepad2.right_stick_y * 0.5f);
                telemetry.update();
            }
        }
    }


