package org.firstinspires.ftc.teamcode.drive.modules;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ClawPivotModuleTwo {
    private Servo clawPivotTwo;
    public ClawPivotModuleTwo(HardwareMap hwMap) {
        clawPivotTwo = hwMap.get(Servo.class, "clawpivottwo");
        clawPivotTwo.scaleRange(0, 1);
        clawPivotTwo.setDirection(Servo.Direction.FORWARD);
    }

    /**
     * Set the state of the arm
     * @param state How far up or down the arm is (0-1) 0 = inside, 1 = outside
     */
    public void setState(float state) {clawPivotTwo.setPosition(state);}
}
