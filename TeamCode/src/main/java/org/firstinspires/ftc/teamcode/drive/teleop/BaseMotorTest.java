package org.firstinspires.ftc.teamcode.drive.teleop;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp(name = "BaseMotorTest")
public class BaseMotorTest extends LinearOpMode {

    private float Motorpower = 0.8f;
    @Override
    public void runOpMode() {

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (!isStopRequested()) {

            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y * Motorpower,
                            -(gamepad1.right_trigger - gamepad1.left_trigger + gamepad1.left_stick_x) * Motorpower * 1.2,
                            -gamepad1.right_stick_x * Motorpower
                    )
            );
            if (gamepad1.left_bumper) Motorpower = 0.2f;
            if (gamepad1.right_bumper) Motorpower = 1f;
            if (!gamepad1.left_bumper && !gamepad1.right_bumper) Motorpower = 0.75f;
        }
    }
}
