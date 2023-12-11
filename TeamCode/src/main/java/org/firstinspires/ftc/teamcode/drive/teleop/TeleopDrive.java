package org.firstinspires.ftc.teamcode.drive.teleop;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.drive.opmode.ModuleIntegrator;

import java.util.List;

public class TeleopDrive extends ModuleIntegrator {

    public float speed = 0.5f;

    public TeleopDrive(HardwareMap hwMap, List<Module> modules, float setSpeed) {
        super(hwMap, modules);
        speed = setSpeed;
    }

    public void move(float forward, float strafe, float turn) {
        setWeightedDrivePower(new Pose2d(forward * speed, strafe * -speed, turn * -speed));
        update();
    }
    public void setSpeed(float setSpeed) {
        speed = setSpeed;
    }
}
