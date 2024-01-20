package org.firstinspires.ftc.teamcode.drive.opmode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.modules.ClawModule;
import org.firstinspires.ftc.teamcode.drive.modules.ClawModuleTwo;
import org.firstinspires.ftc.teamcode.drive.modules.ClawPivotModule;
import org.firstinspires.ftc.teamcode.drive.modules.ClawPivotModuleTwo;
import org.firstinspires.ftc.teamcode.drive.modules.DrawerSlideModule;
import org.firstinspires.ftc.teamcode.drive.modules.PlaneGunModule;
import org.firstinspires.ftc.teamcode.drive.modules.SlidePivotModule;
import org.firstinspires.ftc.teamcode.drive.modules.WinchServoModuleOne;
import org.firstinspires.ftc.teamcode.drive.modules.WinchServoModuleTwo;
import org.firstinspires.ftc.teamcode.drive.modules.WinchSystemModule;

import java.util.List;

public class ModuleIntegrator extends SampleMecanumDrive {

    public enum Module {
        CLAW,
        CLAWTWO,
        PIVOT,
        PIVOTTWO,
        DRAWERSLIDE,
        SLIDEPIVOT,
        GUN,
        WINCHLIFTONE,
        WINCHLIFTTWO,
        WINCHSYSTEM,
    }

    public ClawModule claw;
    public ClawModuleTwo clawtwo;
    public ClawPivotModule pivot;
    public SlidePivotModule slidePivot;
    public WinchServoModuleOne winchLiftOne;

    public WinchServoModuleTwo winchLiftTwo;
    public WinchSystemModule winchSystem;
    public ClawPivotModuleTwo pivottwo;
    public PlaneGunModule gun;
    public DrawerSlideModule drawerSlide;

    public ModuleIntegrator(HardwareMap hwMap, List<Module> modules) {
        super(hwMap);
        if (modules.contains(Module.CLAW)) {
            claw = new ClawModule(hwMap);
        }
        if (modules.contains(Module.PIVOT)) {
            pivot = new ClawPivotModule(hwMap);
        }
        if (modules.contains(Module.DRAWERSLIDE)) {
            drawerSlide = new DrawerSlideModule(hwMap);
        }
        if (modules.contains(Module.WINCHLIFTONE)) {
            winchLiftOne = new WinchServoModuleOne(hwMap);
        }
        if (modules.contains(Module.WINCHLIFTTWO)) {
            winchLiftTwo = new WinchServoModuleTwo(hwMap);
        }
        if (modules.contains(Module.WINCHSYSTEM)) {
            winchSystem = new WinchSystemModule(hwMap);
        }
        if (modules.contains(Module.PIVOTTWO)) {
            pivottwo = new ClawPivotModuleTwo(hwMap);
        }
        if (modules.contains(Module.SLIDEPIVOT)) {
            slidePivot = new SlidePivotModule(hwMap);
        }
        if (modules.contains(Module.GUN)) {
            gun = new PlaneGunModule(hwMap);
        }
        if (modules.contains(Module.CLAWTWO)) {
            clawtwo = new ClawModuleTwo(hwMap);
        }
    }
}
