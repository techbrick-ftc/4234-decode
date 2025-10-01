
// This code will be used for controlling the RPM of a flywheel. Assumes 1:1 GoBilda 6000 RPM motor.


package org.firstinspires.ftc.teamcode;


// IMPORT STATEMENTS

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp(name="RPM Controller")
public class rpmControl extends LinearOpMode
{



    // DECLARATIONS

    private DcMotorEx flywheelPrimary;
    private DcMotorEx flywheelSecondary; //Unused


    double targetRPM = 2000;
    static final double maximumRPM = 6000;
    static final double minimumRPM = 0;

    static final double ticksPerRevolution = 21;

    static final double changeTargetRpmInterval = 500;
    double targetVelocity;

    boolean increaseRPM = false;
    boolean increaseRPMLast;
    boolean decreaseRPM = false;
    boolean decreaseRPMLast;



    public void runOpMode() throws InterruptedException
    {



        // RUN ON INITIALISATION

        DcMotorEx flywheelPrimary = (DcMotorEx) hardwareMap.dcMotor.get("flywheel1");
        DcMotorEx flywheelSecondary = (DcMotorEx) hardwareMap.dcMotor.get("flywheel2");

        flywheelPrimary.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        flywheelSecondary.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        flywheelPrimary.setDirection(DcMotorSimple.Direction.FORWARD);
        flywheelSecondary.setDirection(DcMotorSimple.Direction.REVERSE);


        // LOOPING CODE
        waitForStart();
        while (opModeIsActive() && !isStopRequested())
        {



            increaseRPMLast = increaseRPM;
            increaseRPM = gamepad1.dpad_up;

            decreaseRPMLast = decreaseRPM;
            decreaseRPM = gamepad1.dpad_down;


            if (increaseRPM && !increaseRPMLast) {
                targetRPM += (changeTargetRpmInterval);
            }

            if (decreaseRPM && !decreaseRPMLast) {
                targetRPM -= (changeTargetRpmInterval);
            }


            if (targetRPM > maximumRPM)
            {
                targetRPM = maximumRPM;
            }

            else if (targetRPM < minimumRPM) {
                targetRPM = minimumRPM;
            }



            targetVelocity = (targetRPM / 60) * ticksPerRevolution;
            flywheelPrimary.setVelocity(-targetVelocity);
            flywheelSecondary.setVelocity(targetVelocity);



            // TELEMETRY

            telemetry.addData("Target RPM", targetRPM);
            telemetry.addData("Target Velocity", targetVelocity);
            telemetry.addData("Recorded Velocity", flywheelPrimary.getVelocity());

            telemetry.update();


        }
    }
}
