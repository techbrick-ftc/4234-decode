package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class SubLift {
    DcMotorEx liftRight;
    DcMotorEx liftLeft;

    public SubLift(HardwareMap hardwareMap) {
        liftRight = hardwareMap.get(DcMotorEx.class, "LiftRight");
        liftLeft = hardwareMap.get(DcMotorEx.class, "LiftLeft");

        liftRight.setDirection(DcMotorSimple.Direction.FORWARD);
        liftLeft.setDirection(DcMotorSimple.Direction.FORWARD);

        liftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


    }
}
