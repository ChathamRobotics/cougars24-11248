package org.firstinspires.ftc.teamcode.drive.teleop;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.opmode.ModuleIntegrator;

import java.util.Arrays;

@TeleOp(name = "Big Papi Balling Testosterone")
public class BigPapiBallingTestosterone extends LinearOpMode {
    // "My Pizza Crystalized" - Chip

        float SPEED = 1f;
        TeleopDrive robot;

        @Override
        public void runOpMode() {
            robot = new TeleopDrive(hardwareMap, Arrays.asList(ModuleIntegrator.Module.CLAWTWO, ModuleIntegrator.Module.ROTATION, ModuleIntegrator.Module.PIVOT, ModuleIntegrator.Module.LINEAR_SLIDE, ModuleIntegrator.Module.CLAW, ModuleIntegrator.Module.GUN, ModuleIntegrator.Module.PIVOTTWO, ModuleIntegrator.Module.WINCHSYSTEM, ModuleIntegrator.Module.WINCHLIFTONE, ModuleIntegrator.Module.WINCHLIFTTWO), SPEED);
            //blue 5, green 3, purple 8, orange 7, red 1
            // red blue purple green orange repeat
            // (35)*(24)
            //840

            robot.linearSlide.goToPos(0.172f);
            robot.pivottwo.setState(0.4f);

            waitForStart();

            while (opModeIsActive()) {
                //before a full patch, keep in mind that linearslide is the rotater and rotation is the linear slide
                robot.linearSlide.move(-gamepad2.left_stick_y * 1, false);
                robot.rotation.movePivot(-gamepad2.right_stick_y * 0.5f, false);

                if (gamepad2.left_bumper) robot.claw.setState(0.8f);
                if (gamepad2.left_trigger != 0) robot.claw.setState(1);
                if (gamepad2.right_bumper) robot.clawtwo.setState(0.8f); // close
                if (gamepad2.right_trigger != 0) robot.clawtwo.setState(1); // open
                if (gamepad2.dpad_up) {robot.pivot.setState(1); robot.pivottwo.setState(1);}
                if (gamepad2.dpad_down) {robot.pivot.setState(0.6f); robot.pivottwo.setState(0.6f);}
                if (gamepad2.dpad_right) {robot.pivot.setState(0.9f); robot.pivottwo.setState(0.9f);}
                if (gamepad2.dpad_left) {robot.pivot.setState(0.4f); robot.pivottwo.setState(0.4f);}
                if (gamepad2.square) {robot.linearSlide.setPos(0.2f);}
                if (gamepad2.circle) robot.gun.setState(1);
                if (gamepad2.triangle) robot.gun.setState(0);

                robot.move(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
                robot.winchSystem.move(gamepad1.right_trigger - gamepad1.left_trigger, false);

                if (gamepad1.left_bumper) robot.setSpeed(0.2f);
                if (gamepad1.right_bumper) robot.setSpeed(1);
                if (!gamepad1.left_bumper && !gamepad1.right_bumper) robot.setSpeed(0.75f);
                if (gamepad1.dpad_up) {robot.winchLiftOne.setState(0.5f); }
                if (gamepad1.dpad_up) {robot.winchLiftTwo.setState(0.5f);}
                if (gamepad1.dpad_down) {robot.winchLiftOne.setState(0.15f); }
                if (gamepad1.dpad_down) {robot.winchLiftTwo.setState(0.15f); }


                Pose2d poseEstimate = robot.getPoseEstimate();
                telemetry.addData("x", poseEstimate.getX());
                telemetry.addData("y", poseEstimate.getY());
                telemetry.addData("heading", poseEstimate.getHeading());
                telemetry.addData("RotationPos", robot.linearSlide.clawLift.get(0).getCurrentPosition());
                telemetry.addData("RotationPow", robot.linearSlide.clawLift.get(0).getPower());
                telemetry.addData("LinearslidePos", robot.rotation.clawRot.get(0).getCurrentPosition());
                telemetry.addData("LinearslidePow", robot.rotation.clawRot.get(0).getPower());
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