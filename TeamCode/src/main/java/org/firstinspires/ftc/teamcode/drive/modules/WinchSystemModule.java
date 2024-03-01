package org.firstinspires.ftc.teamcode.drive.modules;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WinchSystemModule {
    private static final int LIFT_MOTORS = 1;
    public List<DcMotorEx> clawLift = new ArrayList<>();

    public WinchSystemModule(HardwareMap hwMap) {
        List<DcMotorSimple.Direction> motorDirections = Arrays.asList(DcMotorSimple.Direction.FORWARD, DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.REVERSE);

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
                        motor.setPower(power);
            });
        }
    }

