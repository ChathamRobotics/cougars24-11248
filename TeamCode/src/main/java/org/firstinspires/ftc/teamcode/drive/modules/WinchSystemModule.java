package org.firstinspires.ftc.teamcode.drive.modules;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WinchSystemModule {

    private static final int MAX_SLIDE_POS = 1000000;
    private static final int LIFT_MOTORS = 2;
    public List<DcMotorEx> clawLift = new ArrayList<>();

    public WinchSystemModule(HardwareMap hwMap) {
        List<DcMotorSimple.Direction> motorDirections = Arrays.asList(DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.REVERSE);

        for (int i = 1; i <= LIFT_MOTORS; i++) {
            DcMotorEx slideMotor = hwMap.get(DcMotorEx.class, "winchSystem" + i);
            slideMotor.setDirection(motorDirections.get(i - 1));
            clawLift.add(slideMotor);
        }

        clawLift.forEach(motor -> {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        });
    }

        /**
         * Moves the linear slide with a power
         *
         * @param power Power to move the linear slide with
         * @param b
         */
        public void move (float power, boolean b){
            clawLift.forEach(motor -> {
                if (power > 0) {
                    if (clawLift.get(0).getCurrentPosition() < MAX_SLIDE_POS - 10) {
                        motor.setPower(power);
                        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    } else {
                        motor.setTargetPosition(MAX_SLIDE_POS);
                        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        motor.setPower(clawLift.get(0).getCurrentPosition() < MAX_SLIDE_POS ? 0.1 : -0.1);
                    }
                } else {
                    if (clawLift.get(0).getCurrentPosition() > 10) {
                        motor.setPower(power);
                        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    } else {
                        motor.setTargetPosition(0);
                        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        motor.setPower(clawLift.get(0).getCurrentPosition() < 0 ? 0.1 : -0.1);
                    }
                }
            });
        }

        /**
         * Sets the position of the linear slide
         * @param pos Position of linear slide to go to. 0-1: 0 = bottom, 1 = top
         */
        public void setPos ( float pos){
            int position = (int) (pos * MAX_SLIDE_POS);
            clawLift.forEach(motor -> {
                motor.setTargetPosition(position);
                if (pos > clawLift.get(0).getCurrentPosition()) {
                    motor.setPower(.1 + (position - clawLift.get(0).getCurrentPosition()) / 200f);
                }
                if (pos < clawLift.get(0).getCurrentPosition()) {
                    motor.setPower(-.1 + (position - clawLift.get(0).getCurrentPosition()) / 200f);
                }
                motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            });
        }

        /**
         * Go to a position of the linear slide
         * @param pos Position of linear slide to go to. 0-1: 0 = bottom, 1 = top
         */
        public void goToPos ( float pos){
            int position = (int) (pos * MAX_SLIDE_POS);
            while (Math.abs(position - clawLift.get(0).getCurrentPosition()) > 10) {
                setPos(pos);
            }
        }
    }

