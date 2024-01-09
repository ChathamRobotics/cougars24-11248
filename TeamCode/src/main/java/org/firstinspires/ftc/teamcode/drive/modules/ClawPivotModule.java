package org.firstinspires.ftc.teamcode.drive.modules;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ClawPivotModule {
    private Servo clawPivot;
    public ClawPivotModule(HardwareMap hwMap) {
        clawPivot = hwMap.get(Servo.class, "clawpivot");
        clawPivot.scaleRange(0, 1);
        clawPivot.setDirection(Servo.Direction.REVERSE);
    }

    /**
     * Set the state of the arm
     * @param state How far up or down the arm is (0-1) 0 = inside, 1 = outside
     */
    public void setState(float state) {clawPivot.setPosition(state);}
}
