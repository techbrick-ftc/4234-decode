package org.firstinspires.ftc.teamcode.SubSystems;


import com.qualcomm.robotcore.hardware.DcMotorEx;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class subFlywheel {

    DcMotorEx flyWheel;

    public subFlywheel(HardwareMap hardwareMap) {

        flyWheel = hardwareMap.get(DcMotorEx.class, "frontRight");

        flyWheel.setDirection(DcMotorSimple.Direction.FORWARD);

        flyWheel.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);


    }

    public void setFlyWheel(double targetFlywheelRPM, double outputGearRatio) { //TODO: Test

        // Set for 6000 RPM motor, may need to be changed?
        final double ticksPerRevolution = 21;

        // Gearing = output teeth / input teeth. Set, then change motor speed to directly set flywheel RPM.
        double targetRPM = targetFlywheelRPM * outputGearRatio;

        // Clamp targetRPM to (max: rpm of motor, min: 0)
        if (targetRPM > 6000) {
            targetRPM = 6000;
        } else if (targetRPM < 0) {
            targetRPM = 0;
        }

        flyWheel.setVelocity(targetRPM / 60 * ticksPerRevolution);

    }

}
