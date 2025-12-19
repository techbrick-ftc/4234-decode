package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotorEx;

@Disabled
@TeleOp(name="Demonstration")
public class Demo extends LinearOpMode {

    private DcMotorEx test;

    double speedMultiplier = 0.3;
    boolean preventWallCrashes = true;


    @Override
    public void runOpMode() throws InterruptedException {

        DcMotorEx test = (DcMotorEx) hardwareMap.dcMotor.get("test");
        DcMotorEx frontLeftMotor = (DcMotorEx) hardwareMap.dcMotor.get("frontLeft");
        DcMotorEx backLeftMotor = (DcMotorEx) hardwareMap.dcMotor.get("backLeft");
        DcMotorEx frontRightMotor = (DcMotorEx) hardwareMap.dcMotor.get("frontRight");
        DcMotorEx backRightMotor = (DcMotorEx) hardwareMap.dcMotor.get("backRight");





        waitForStart();
        if (isStopRequested()) return;
        while (opModeIsActive()) {










        }






    }
}
