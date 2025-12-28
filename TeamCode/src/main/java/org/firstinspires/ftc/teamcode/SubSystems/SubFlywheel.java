package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class SubFlywheel {
    
    DcMotorEx flyWheel;

    public SubFlywheel(HardwareMap hardwareMap) {
        flyWheel = hardwareMap.get(DcMotorEx.class, "flywheel1");
        flyWheel.setDirection(DcMotorSimple.Direction.REVERSE);
        flyWheel.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
    }

    public void setFlyWheelRPM(double targetFlywheelRPM) { //TODO: Test

        // Set for 6000 RPM motor, may need to be changed?
        final double ticksPerRevolution = 21;

        // Gearing = output teeth / input teeth. Set, then change motor speed to directly set flywheel RPM.
        double targetRPM = targetFlywheelRPM;

        // Clamp targetRPM to (max: rpm of motor, min: 0)
        if (targetRPM > 6000) {
            targetRPM = 6000;
        } else if (targetRPM < 0) {
            targetRPM = 0;
        }

        flyWheel.setVelocity(targetRPM / 60 * ticksPerRevolution);
    }

}
