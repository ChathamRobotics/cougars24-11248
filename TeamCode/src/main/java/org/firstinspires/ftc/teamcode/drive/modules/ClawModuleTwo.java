package org.firstinspires.ftc.teamcode.drive.modules;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ClawModuleTwo {

    private final Servo clawtwo;
    public ClawModuleTwo(HardwareMap hwMap) {
        clawtwo = hwMap.get(Servo.class, "clawtwo");
        clawtwo.setDirection(Servo.Direction.REVERSE);
        clawtwo.scaleRange(0.35, 0.75);
    }

    /**
     * Set the state of the claw
     * @param state How open or closed the claw is (0-1), 0 is open, 1 is closed
     */
    public void setState(float state) {
        clawtwo.setPosition(state);
    }
}
