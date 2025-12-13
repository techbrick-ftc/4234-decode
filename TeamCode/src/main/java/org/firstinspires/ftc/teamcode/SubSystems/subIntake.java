package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class subIntake {

    CRServo sortingGate;
    CRServo transferBootWheels;
    DcMotorEx intakeRow;
    Servo leftLift;
    Servo rightLift;

    public double gatePosition;
    public subIntake (HardwareMap hardwareMap) {

        intakeRow = hardwareMap.get(DcMotorEx.class, "intakeRow");
        intakeRow.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeRow.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        transferBootWheels = hardwareMap.get(CRServo.class, "transfer");
        transferBootWheels.setDirection(DcMotorSimple.Direction.REVERSE);

        sortingGate = hardwareMap.get(CRServo.class, "gate");

    }

    public void Set (double intakeRowPower, double Gate) {

        intakeRow.setPower(intakeRowPower);
        sortingGate.setPower(Gate);

    }

    public void kicker(double transforPow) {
        transferBootWheels.setPower(transforPow);
    }

    public void artifactLifts (double left, double right) {

        rightLift.setPosition(right);
        leftLift.setPosition(left);

    }

}
