package org.firstinspires.ftc.teamcode.drive.modules;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class WinchServoModuleTwo {

    private final Servo clawtwo;


    public WinchServoModuleTwo(HardwareMap hwMap) {
        clawtwo = hwMap.get(Servo.class, "winchservotwo");
        clawtwo.scaleRange(0, 1);
        clawtwo.setDirection(Servo.Direction.FORWARD);
    }

    /**
     * Set the state of the claw
     * @param state How open or closed the claw is (0-1), 0 is open, 1 is closed
     */
    public void setState(float state) {
        clawtwo.setPosition(state);
    }
}
