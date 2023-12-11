package org.firstinspires.ftc.teamcode.drive.opmode;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

/**
 * 11248 ON TOP
 *
 * Elite gaming is occuring here
 *
 * This is the entire robot
 *
 * capitalization rules - pascal case (variables): first letter lowercase, first letter of every word after is uppercase
 * classes: first letter of each word is uppercase
 * constants - upper snake case - all capitalized with underscores inbetween
 */
@TeleOp(group = "drive")
public class CompetitionPlatform extends LinearOpMode {

    DcMotorEx linearSlide = null;
    DcMotorEx rpOne = null;
    DcMotorEx rptwo = null;
    DcMotorEx intakemotor = null;
    Servo claw = null;
    Servo intakeclaw = null;
    Servo pivot = null;

    //All Variables
    public static final int CLAW_LIFT_MAX = 2620;
    // 933.3333324 approx 1 full rot or 31.4159265359 mm of string
    // 29.7089226808 ticks per mm
    // slide extends 30mm or 891 ish ticks

    public static final int INTAKE_LIFT_MAX = 378;
    // 382.9406 is the exact value for 1/2 rot

    private float Motorpower = 0.4f;
    private final ElapsedTime runtime = new ElapsedTime();
    private double lastPowerChangeTime;
    private float linearslidepower = 1;
    private double intakemotorpower = 1;

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        // intense motor and servo mapping
        // don't touch
        linearSlide = hardwareMap.get(DcMotorEx.class, "linearslide");
        rpOne = hardwareMap.get(DcMotorEx.class, "rpone");
        rptwo = hardwareMap.get(DcMotorEx.class, "rptwo");
        intakemotor = hardwareMap.get(DcMotorEx.class, "intakemotor");

        claw = hardwareMap.get(Servo.class, "claw");
        intakeclaw = hardwareMap.get(Servo.class, "intakeclaw");
        pivot = hardwareMap.get(Servo.class, "pivot");

        linearSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rpOne.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rptwo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intakemotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //motor direction
        linearSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        rpOne.setDirection(DcMotorSimple.Direction.FORWARD);
        rptwo.setDirection(DcMotorSimple.Direction.FORWARD);
        intakemotor.setDirection(DcMotorSimple.Direction.FORWARD);

        linearSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rpOne.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rptwo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        intakemotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        //auton fixer thingy
        linearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rpOne.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rptwo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakemotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        linearSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rpOne.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rptwo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intakemotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //POWER

        linearSlide.setPower(0);
        rpOne.setPower(0);
        rptwo.setPower(0);
        intakemotor.setPower(0);



        intakeclaw.scaleRange(0.02, 0.21);


        //DRIVEEE

        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (!isStopRequested()) {

            telemetry.addData("pos", linearSlide.getCurrentPosition());
            telemetry.addData("pivotpos", pivot.getPosition());
            telemetry.addData("clawpos", claw.getPosition());

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
            // Linearslide reset encoders
            if (gamepad2.triangle) { // AKA Y
                linearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                linearSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }

            // Claw
            if (gamepad2.left_bumper) {
                // catch
                claw.setPosition(0.75);
            } else if (gamepad2.right_bumper) {
                // release
                claw.setPosition(0.44);
            }
            //pivot
            if (gamepad2.square) {
                // in
                pivot.setPosition(0.7765);
            } else if (gamepad2.circle) {
                // out
                pivot.setPosition(0.325);
            }

            // Intakeclaw
            if (gamepad2.left_trigger > 0) {
                // close
                intakeclaw.setPosition(0);
            } else if (gamepad2.right_trigger > 0) {
                // open
                intakeclaw.setPosition(1);
            }


// Intake Motor Code
// 765.8812 ticks per revolution NEED TO CHANGE POSITION
            if (gamepad2.right_stick_y != 0) {
                if (gamepad2.right_stick_y * -1 > 0) {
                    intakemotor.getCurrentPosition();
                    intakemotor.setTargetPosition(INTAKE_LIFT_MAX);
                    intakemotor.setPower(intakemotorpower * 0.4);
                    intakemotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }

                if (gamepad2.right_stick_y * -1 < 0) {
                    intakemotor.getCurrentPosition();
                    intakemotor.setTargetPosition(0);
                    intakemotor.setPower(intakemotorpower * 0.4);
                    intakemotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
            } else {
                intakemotor.setPower(0);
            }

// DESPARATELY NEEDS REVISION - WILL FIX AFTER THIS WEEKEND
            // linear slide
            if ((gamepad2.left_stick_y * linearslidepower * -1 > 0 && linearSlide.getCurrentPosition() < CLAW_LIFT_MAX - 50)
                    || (gamepad2.left_stick_y * linearslidepower * -1 > 0 && linearSlide.getCurrentPosition() < 100)
                    || (!(linearSlide.getCurrentPosition() <= 100) && gamepad2.left_stick_y != 0 && !(linearSlide.getCurrentPosition() >= CLAW_LIFT_MAX - 50)))
            {

                if (gamepad2.left_stick_y * -1 < 0) {
                    linearSlide.setPower(gamepad2.left_stick_y * linearslidepower * -1);
                    telemetry.addData("currently moving", "if1");

                } else if (gamepad2.left_stick_y != 0) {
                    linearSlide.setPower(gamepad2.left_stick_y * linearslidepower * -1);
                    linearSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    linearSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

                    telemetry.addData("currently moving", "clawLift (if2)");
                    telemetry.addData("clawLift Power", linearSlide.getPower());

                } else {
                    linearSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

                    telemetry.addData("currently moving", "if3");
                }
            }

             else if(linearSlide.getCurrentPosition() >= 100 && linearSlide.getCurrentPosition() <= CLAW_LIFT_MAX - 50) {
                linearSlide.setPower(0);
            } else if (linearSlide.getCurrentPosition() < 100) {
                 if (linearSlide.getCurrentPosition() < 0) {
                    linearSlide.setPower(linearslidepower * 0.1);
                    telemetry.addData("currently moving", "if4");
                }
                linearSlide.setTargetPosition(0);
                linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
        else if(linearSlide.getCurrentPosition() > CLAW_LIFT_MAX - 50)
            {
                if (linearSlide.getCurrentPosition() > CLAW_LIFT_MAX) {
                    linearSlide.setPower(linearslidepower * 0.1);
                    telemetry.addData("currently moving", "if6");
                    linearSlide.setTargetPosition(CLAW_LIFT_MAX);
                    linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                } else {
                    if (gamepad2.left_stick_y * -1 < 0) {
                        linearSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        linearSlide.setPower(gamepad2.left_stick_y * linearslidepower * -1);
                        telemetry.addData("currently moving", "reverseSpool");
                    }
                }
            }

            Pose2d poseEstimate = drive.getPoseEstimate();
            telemetry.addData("x", poseEstimate.getX());
            telemetry.addData("y", poseEstimate.getY());
            telemetry.addData("heading", poseEstimate.getHeading());
            telemetry.update();

        }
    }
};

/**   //Linearslide, primitive edition, backup in worst case scenario
 if (gamepad2.left_stick_y * -1 > 0 && linearslide.getCurrentPosition() < CLAW_LIFT_MAX - 25)
 {
 linearslide.setPower(gamepad2.left_stick_y * -1);
 linearslide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
 linearslide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

 telemetry.addData("currently moving", "clawLift");
 telemetry.addData("clawLift Power", linearslide.getPower());

 } else
 { linearslide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); }

 if (gamepad2.left_stick_y * -1 < 0 && linearslide.getCurrentPosition() > 25)
 {
 linearslide.setPower(gamepad2.left_stick_y * -1);
 linearslide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
 linearslide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

 telemetry.addData("currently moving", "clawLift");
 telemetry.addData("clawLift Power", linearslide.getPower());
 }

 if (gamepad2.left_stick_y == 0)
 {  linearslide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
 linearslide.setPower(0);}
 **/

