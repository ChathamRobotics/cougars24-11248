package org.firstinspires.ftc.teamcode.drive.modules;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DrawerSlideModule {

    private static final int MAX_LIFT_ROT = 750;
    private static final int ROTATION_MOTORS = 1;
    public List<DcMotorEx> clawRot = new ArrayList<>();

    public DrawerSlideModule(HardwareMap hwMap) {
        List<DcMotorSimple.Direction> motorDirections = Arrays.asList(DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD, DcMotorSimple.Direction.REVERSE);

        for (int i = 1; i <= ROTATION_MOTORS; i++) {
            DcMotorEx slideMotor = hwMap.get(DcMotorEx.class, "clawRot" + i);
            slideMotor.setDirection(motorDirections.get(i - 1));
            clawRot.add(slideMotor);
        }

        clawRot.forEach(motor -> {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        });
    }

        /**
         * Moves the pivot motor with a power
         *
         * @param power Power to move the linear slide with
         * @param b
         */
        public void moveSlide (float power, boolean b){
            clawRot.forEach(motor -> {
                if (power > 0) {
                    if (clawRot.get(0).getCurrentPosition() < MAX_LIFT_ROT - 10) {
                        motor.setPower(power);
                        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    } else {
                        motor.setTargetPosition(MAX_LIFT_ROT);
                        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        motor.setPower(clawRot.get(0).getCurrentPosition() < MAX_LIFT_ROT ? 0.1 : -0.1);
                    }
                } else {
                    if (clawRot.get(0).getCurrentPosition() > 10) {
                        motor.setPower(power);
                        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    } else {
                        motor.setTargetPosition(0);
                        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        motor.setPower(clawRot.get(0).getCurrentPosition() < 0 ? 0.1 : -0.1);
                    }
                }
            });
        }

        /**
         * Sets the position of the linear slide
         * @param pos Position of linear slide to go to. 0-1: 0 = bottom, 1 = top
         */
        public void setSlidePos ( float pos){
            int position = (int) (pos * MAX_LIFT_ROT);
            clawRot.forEach(motor -> {
                motor.setTargetPosition(position);
                if (pos > clawRot.get(0).getCurrentPosition()) {
                    motor.setPower(.1 + (position - clawRot.get(0).getCurrentPosition()) / 650f);
                }
                if (pos < clawRot.get(0).getCurrentPosition()) {
                    motor.setPower(-.1 + (position - clawRot.get(0).getCurrentPosition()) / 650f);
                }
                motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            });
        }

        /**
         * Go to a position of the linear slide
         * @param pos Position of linear slide to go to. 0-1: 0 = bottom, 1 = top
         */
        public void goToSlidePos ( float pos){
            int position = (int) (pos * MAX_LIFT_ROT);
            while (Math.abs(position - clawRot.get(0).getCurrentPosition()) > 10) {
                setSlidePos(pos);
            }
        }
    }

