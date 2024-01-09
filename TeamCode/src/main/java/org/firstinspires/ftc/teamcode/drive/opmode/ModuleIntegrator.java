package org.firstinspires.ftc.teamcode.drive.opmode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.modules.ClawLiftModule;
import org.firstinspires.ftc.teamcode.drive.modules.ClawModule;
import org.firstinspires.ftc.teamcode.drive.modules.ClawModuleTwo;
import org.firstinspires.ftc.teamcode.drive.modules.ClawPivotModule;
import org.firstinspires.ftc.teamcode.drive.modules.ClawPivotModuleTwo;
import org.firstinspires.ftc.teamcode.drive.modules.LiftRotationModule;
import org.firstinspires.ftc.teamcode.drive.modules.PlaneGunModule;

import java.util.List;

public class ModuleIntegrator extends SampleMecanumDrive {

    public enum Module {
        CLAW,
        CLAWTWO,
        PIVOT,
        PIVOTTWO,
        LINEAR_SLIDE,
        ROTATION,

        GUN,
    }

    public ClawModule claw;
    public ClawModuleTwo clawtwo;
    public ClawPivotModule pivot;
    public ClawLiftModule linearSlide;

    public ClawPivotModuleTwo pivottwo;

    public PlaneGunModule gun;

    public LiftRotationModule rotation;


    public ModuleIntegrator(HardwareMap hwMap, List<Module> modules) {
        super(hwMap);
        if (modules.contains(Module.CLAW)) {
            claw = new ClawModule(hwMap);
        }
        if (modules.contains(Module.PIVOT)) {
            pivot = new ClawPivotModule(hwMap);
        }
        if (modules.contains(Module.LINEAR_SLIDE)) {
            linearSlide = new ClawLiftModule(hwMap);
        }
        if (modules.contains(Module.PIVOTTWO)) {
            pivottwo = new ClawPivotModuleTwo(hwMap);
        }
        if (modules.contains(Module.ROTATION)) {
            rotation = new LiftRotationModule(hwMap);
        }
        if (modules.contains(Module.GUN)) {
            gun = new PlaneGunModule(hwMap);
        }

        if (modules.contains(Module.CLAWTWO)) {
            clawtwo = new ClawModuleTwo(hwMap);
        }
    }
}
