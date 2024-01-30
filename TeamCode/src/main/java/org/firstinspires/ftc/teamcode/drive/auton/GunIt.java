package org.firstinspires.ftc.teamcode.drive.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.opmode.ModuleIntegrator;
import org.firstinspires.ftc.teamcode.drive.teleop.TeleopDrive;

import java.util.Arrays;

@Autonomous(name = "GunIt")
public class GunIt extends LinearOpMode {

    float SPEED = 0.5f;
    TeleopDrive robot;

    ElapsedTime runtime = new ElapsedTime(0);

    @Override
    public void runOpMode() {
        robot = new TeleopDrive(hardwareMap, Arrays.asList(ModuleIntegrator.Module.CLAWTWO, ModuleIntegrator.Module.PIVOT, ModuleIntegrator.Module.CLAW), SPEED);

        waitForStart();

            runtime.reset();
            while (runtime.seconds() < 1.75)
                robot.move(-1, 0, 0);
        }
    }

