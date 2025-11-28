package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.teamcode.SubSystems.subDrive;


@TeleOp(name="intaketest")
public class intakeTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        CRServo intakeBootRow = hardwareMap.get(CRServo.class, "intakebootrow");



        waitForStart();
        if (isStopRequested()) return;
        while (opModeIsActive()) {

            intakeBootRow.setPower(gamepad1.left_trigger-gamepad1.right_trigger);








        }


    }


}
