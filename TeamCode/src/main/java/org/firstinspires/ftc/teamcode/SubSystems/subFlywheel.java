package org.firstinspires.ftc.teamcode.SubSystems;


import com.qualcomm.robotcore.hardware.DcMotorEx;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class subFlywheel {

    DcMotorEx flyWheelPrimary;
    DcMotorEx flyWheelSecondary;

    public subFlywheel(HardwareMap hardwareMap) {

        flyWheelPrimary = hardwareMap.get(DcMotorEx.class, "frontRight");
        flyWheelSecondary = hardwareMap.get(DcMotorEx.class, "frontLeft");

        flyWheelPrimary.setDirection(DcMotorSimple.Direction.FORWARD);
        flyWheelSecondary.setDirection(DcMotorSimple.Direction.REVERSE);

        flyWheelPrimary.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        flyWheelSecondary.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);



    }



}
