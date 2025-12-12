package org.firstinspires.ftc.teamcode.teleop;

import org.firstinspires.ftc.teamcode.SubSystems.subDrive;
import org.firstinspires.ftc.teamcode.SubSystems.subFlywheel;
import org.firstinspires.ftc.teamcode.SubSystems.subIntake;
import org.firstinspires.ftc.teamcode.SubSystems.subAprilTagDetection;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="11/30")


public class teleOPMain2 extends LinearOpMode {


    public static double defPow = 1;
    public static double slowPow = 0.4;
    boolean slowMode = false;
    public double drivePow = defPow;

    public boolean isRedTeam = false;

    public static double intakeAngle = 0.3 * Math.PI;
    public static double outtakeAngle = 0.7 * Math.PI;

    public static double gateRange = 1;

    public boolean headingLock;
    public static double headingAngle;
    public static double headingTargetAngle = 0;
    double targetAngleMultiplier = -1;

    public boolean fieldCentric = true;

    double xP;
    double yP;
    double rP;
    double kP = 0.7;

    boolean slowModeT = false;
    boolean slowModeTL;
    boolean intakeT = false;
    boolean intakeTL;
    boolean aimT;
    boolean aimTL;
    boolean manualT;
    boolean manualTL;
    boolean resetimuT;
    boolean resetimuTL;
    boolean teamChangeT;
    boolean teamChangeTL;

    boolean flyWheelOn = false;



    subDrive drive = null;
    subFlywheel flywheel = null;
    subIntake intake = null;
    subAprilTagDetection subAprilTagDetection = null;


    @Override
    public void runOpMode() throws InterruptedException {


        drive = new subDrive(hardwareMap);
        flywheel = new subFlywheel(hardwareMap);
        intake = new subIntake(hardwareMap);
        subAprilTagDetection = new subAprilTagDetection(hardwareMap, telemetry);


        waitForStart();
        if (isStopRequested()) return;
        while (opModeIsActive()) {

            xP =  gamepad1.left_stick_x;
            yP = -gamepad1.left_stick_y;
            rP =  gamepad1.right_stick_x;


            slowModeTL = slowModeT;
            intakeTL = intakeT;
            aimTL = aimT;
            manualTL = manualT;
            resetimuTL = resetimuT;
            teamChangeTL = teamChangeT;

            slowModeT = gamepad1.start;
            intakeT = gamepad1.a;
            aimT = gamepad1.b;
            manualT = gamepad1.y;
            resetimuT = (gamepad1.left_stick_button && gamepad1.right_stick_button);
            teamChangeT = gamepad1.dpad_left;

            if (slowModeT && !slowModeTL) {
                slowMode = !slowMode;
            }
            if (intakeT && !intakeTL) {
                //headingTargetAngle = Math.PI + intakeAngle * targetAngleMultiplier;
                headingTargetAngle = intakeAngle * targetAngleMultiplier;
                headingLock = true;
            }
            if (aimT && !aimTL) {
                //headingTargetAngle = Math.PI + outtakeAngle * targetAngleMultiplier;
                headingTargetAngle = outtakeAngle * targetAngleMultiplier;
                headingLock = true;
            }
            if (manualT && !manualTL || rP > 0.5) {
                headingLock = false;
            }
            if (resetimuT && !resetimuTL) {
                drive.recalibrate();
            }
            if (teamChangeT && !teamChangeTL) {
                isRedTeam = !isRedTeam;

                if (isRedTeam) {
                    targetAngleMultiplier = 1;
                } else {
                    targetAngleMultiplier = -1;
                }

            }



            if (!slowMode) {
                drivePow = defPow;
            } else {
                drivePow = slowPow;
            }



            if (!headingLock) {
                drive.To(xP, yP, rP, drivePow, true);
            } else {
                //headingAngle = -drive.getImu() + Math.PI + headingTargetAngle;
                headingAngle = headingTargetAngle - drive.getImu();
                if (headingAngle >= Math.PI){
                    headingAngle -= 2 * Math.PI;
                }
                if (headingAngle <= -Math.PI){
                    headingAngle += 2 * Math.PI;
                }

                drive.To(xP, yP, headingAngle * kP, drivePow, true);
            }

            if (gamepad1.dpadUpWasPressed() || gamepad2.dpadUpWasPressed()) {
                if (flyWheelOn) {
                    flywheel.setFlyWheel(0);
                    flyWheelOn = false;
                } else {
                    flywheel.setFlyWheel(5500);
                    flyWheelOn = true;
                }
            } else if (gamepad1.dpadLeftWasPressed() || gamepad1.dpadRightWasPressed() || gamepad2.dpadLeftWasPressed() || gamepad2.dpadRightWasPressed()) {
                if (flyWheelOn) {
                    flywheel.setFlyWheel(0);
                    flyWheelOn = false;
                } else {
                    flywheel.setFlyWheel(4700);
                    flyWheelOn = true;
                }
            } else if (gamepad1.dpadDownWasPressed() || gamepad2.dpadDownWasPressed()) {
                if (flyWheelOn) {
                    flywheel.setFlyWheel(0);
                    flyWheelOn = false;
                } else {
                    flywheel.setFlyWheel(4000);
                    flyWheelOn = true;
                }
            }


            intake.Set(1,1, gamepad1.right_trigger - gamepad1.left_trigger);
            intake.artifactLifts(gamepad1.left_bumper ? 1 : 0, gamepad1.right_bumper ? 1 : 0);


            telemetry.addData("Slow Mode?", slowMode);
            telemetry.addData("Team", isRedTeam);
            telemetry.addData("imu", drive.getRawImu());
            telemetry.addData("target angle", headingTargetAngle);

            telemetry.update();


        }

    }

}
