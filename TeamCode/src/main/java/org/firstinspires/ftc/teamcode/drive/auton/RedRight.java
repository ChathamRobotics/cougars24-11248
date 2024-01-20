package org.firstinspires.ftc.teamcode.drive.auton;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.constraints.MinVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TranslationalVelocityConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.Locations;
import org.firstinspires.ftc.teamcode.drive.opmode.ModuleIntegrator;
import org.firstinspires.ftc.teamcode.drive.teleop.TeleopDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import java.util.Arrays;

@Autonomous (name = "Red Right")
public class RedRight extends LinearOpMode {

    float SPEED = 0.6f;
    TeleopDrive robot;

    @Override
    public void runOpMode() {
        robot = new TeleopDrive(hardwareMap, Arrays.asList(ModuleIntegrator.Module.CLAWTWO, ModuleIntegrator.Module.DRAWERSLIDE, ModuleIntegrator.Module.PIVOT, ModuleIntegrator.Module.SLIDEPIVOT, ModuleIntegrator.Module.CLAW, ModuleIntegrator.Module.GUN, ModuleIntegrator.Module.PIVOTTWO, ModuleIntegrator.Module.WINCHSYSTEM, ModuleIntegrator.Module.WINCHLIFTONE, ModuleIntegrator.Module.WINCHLIFTTWO), SPEED);
        Pose2d startPos = Locations.redRight;

        robot.setPoseEstimate(startPos);

        TrajectorySequence forward = robot.trajectorySequenceBuilder(startPos)
                .splineToLinearHeading(Locations.backdropRed, Math.toRadians(-90))
                .strafeLeft(2)
                .back(11)
                .build();

        Trajectory backward = robot.trajectoryBuilder(forward.end())
                        .forward(11).build();

        robot.clawtwo.setState(0.8f);
        robot.claw.setState(0.8f);
        robot.slidePivot.goToPos(0.172f);
        robot.pivottwo.setState(0.4f);

        waitForStart();

        robot.slidePivot.setPos(0.2f);
        robot.pivottwo.setState(0.4f);
        robot.drawerSlide.setSlidePos(0.25f);
        robot.followTrajectorySequence(forward);
        robot.clawtwo.setState(1); // open
        robot.claw.setState(1);
        sleep(500);
        robot.followTrajectory(backward);
        robot.drawerSlide.setSlidePos(0);
        robot.pivottwo.setState(0.6f);
        robot.slidePivot.goToPos(0);

    }

}