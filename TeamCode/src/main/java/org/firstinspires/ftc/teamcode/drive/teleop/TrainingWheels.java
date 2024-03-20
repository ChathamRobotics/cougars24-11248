package org.firstinspires.ftc.teamcode.drive.teleop;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.opmode.ModuleIntegrator;

import java.util.Arrays;

@TeleOp(name = "Training Wheels")
public class TrainingWheels extends LinearOpMode {
    float SPEED = 1;

    TeleopDrive robot;

    @Override
    public void runOpMode() {
        robot = new TeleopDrive(hardwareMap, Arrays.asList(ModuleIntegrator.Module.CLAWTWO, ModuleIntegrator.Module.WINCHSYSTEMTWO,ModuleIntegrator.Module.SLIDEPIVOT, ModuleIntegrator.Module.PIVOT, ModuleIntegrator.Module.DRAWERSLIDE, ModuleIntegrator.Module.CLAW, ModuleIntegrator.Module.GUN, ModuleIntegrator.Module.PIVOTTWO, ModuleIntegrator.Module.WINCHSYSTEM, ModuleIntegrator.Module.WINCHLIFTONE, ModuleIntegrator.Module.WINCHLIFTTWO), SPEED);
        robot.claw.setState(0);
        robot.clawtwo.setState(0);
        robot.slidePivot.goToPos(0.2f);
        robot.pivottwo.setState(0.4f);
        waitForStart();

        while (opModeIsActive()) {

            float forward = 0;

            float strafe = 0;

            float turn = 0;

            float fm = 1;

            float sm = 1;

            float tm = 1;

            if(Math.abs(gamepad1.left_stick_y) >= 0.3) { forward = gamepad1.left_stick_y*0.6f; } else { forward = 0; }

            if(Math.abs(gamepad1.left_stick_x) >= 0.1) { strafe = gamepad1.left_stick_x*0.6f; } else { strafe = 0; }

            if(Math.abs(gamepad1.right_stick_x) >= 0.1) { turn = gamepad1.right_stick_x*0.6f; } else { turn = 0; }

            if(gamepad1.left_stick_button) {
                fm = 0.4f;
                sm = 0.4f;
                tm = 0.4f;
            } else {
                fm = 1;
            sm = 1;
            tm = 1;
            }

            robot.slidePivot.move(gamepad2.right_stick_y, false);
            robot.drawerSlide.moveSlide((gamepad2.left_stick_y)*0.6f, false);
            robot.move(forward*fm, strafe*sm, turn*tm);

            if (gamepad2.square) robot.claw.setState(0); // close
            if (gamepad2.cross) robot.claw.setState(1); // open
            if (gamepad2.triangle) robot.clawtwo.setState(0); // close
            if (gamepad2.circle) robot.clawtwo.setState(1); // open
            if (gamepad2.dpad_up) {robot.pivot.setState(0.9f); robot.pivottwo.setState(0.9f);} //score
            if (gamepad2.dpad_down) {robot.pivot.setState(0.6f); robot.pivottwo.setState(0.6f);} // pickup
            if (gamepad2.dpad_right) {robot.pivot.setState(0.8f); robot.pivottwo.setState(0.8f);} //
            if (gamepad2.dpad_left) {robot.pivot.setState(0.4f); robot.pivottwo.setState(0.4f);} //

            if (gamepad2.left_bumper) robot.gun.setState(1);
            if (gamepad2.right_bumper) robot.gun.setState(0);




            Pose2d poseEstimate = robot.getPoseEstimate();
            telemetry.addData("x", poseEstimate.getX());
            telemetry.addData("y", poseEstimate.getY());
            telemetry.addData("heading", poseEstimate.getHeading());
            telemetry.addData("RotationPos", robot.slidePivot.clawLift.get(0).getCurrentPosition());
            telemetry.addData("RotationPow", robot.slidePivot.clawLift.get(0).getPower());
            telemetry.addData("LinearslidePos", robot.drawerSlide.clawRot.get(0).getCurrentPosition());
            telemetry.addData("LinearslidePow", robot.drawerSlide.clawRot.get(0).getPower());
            telemetry.addData(robot.motors.get(0).getDeviceName(), robot.motors.get(0).getCurrentPosition());
            telemetry.addData(robot.motors.get(1).getDeviceName(), robot.motors.get(1).getCurrentPosition());
            telemetry.addData(robot.motors.get(2).getDeviceName(), robot.motors.get(2).getCurrentPosition());
            telemetry.addData(robot.motors.get(3).getDeviceName(), robot.motors.get(3).getCurrentPosition());
            telemetry.addData("slideJoystick", -gamepad2.right_stick_y * 0.5f);
            telemetry.update();
        }
    }
}