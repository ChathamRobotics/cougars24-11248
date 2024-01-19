package org.firstinspires.ftc.teamcode.drive.modules;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class WinchServoModuleOne {

    private final Servo claw;


    public WinchServoModuleOne(HardwareMap hwMap) {
        claw = hwMap.get(Servo.class, "winchservoone");
        claw.scaleRange(0, 1);
        claw.setDirection(Servo.Direction.REVERSE);
    }

    /**
     * Set the state of the claw
     * @param state How open or closed the claw is (0-1), 0 is open, 1 is closed
     */
    public void setState(float state) {
        claw.setPosition(state);
    }
}
