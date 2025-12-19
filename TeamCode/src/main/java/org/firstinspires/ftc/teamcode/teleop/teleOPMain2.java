package org.firstinspires.ftc.teamcode.teleop;

import org.firstinspires.ftc.teamcode.SubSystems.subDrive;
import org.firstinspires.ftc.teamcode.SubSystems.subFlywheel;
import org.firstinspires.ftc.teamcode.SubSystems.subIntake;
import org.firstinspires.ftc.teamcode.SubSystems.subAprilTagDetection;
import org.firstinspires.ftc.teamcode.SubSystems.subData;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Main TeleOP")


public class teleOPMain2 extends LinearOpMode {


    public static double defPow = 1;
    public static double slowPow = 0.4;
    boolean slowMode = false;
    public double drivePow = defPow;

    public boolean isRedTeam = false;
    public double colorTagID;

    public static double intakeAngle = 0.3 * Math.PI;
    public static double outtakeAngle = 0.7 * Math.PI;

    public static double gateRange = 1;

    public boolean headingLock;
    public boolean aprilTagLock = false;
    public static double headingAngle;
    public static double headingTargetAngle = 0;
    double targetAngleMultiplier = -1;

    public boolean fieldCentric = true;

    double xP;
    double yP;
    double rP;
    double kP = 0.7;

    /*
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
    */

    boolean artifactUpR = false;
    boolean artifactUpL = false;

    boolean flyWheelOn = false;

    static double reverseAllMultiplier = 1;

    subDrive drive = null;
    subFlywheel flywheel = null;
    subIntake intake = null;
    subAprilTagDetection subAprilTagDetection = null;
    subData subData = null;

    @Override
    public void runOpMode() throws InterruptedException {


        drive = new subDrive(hardwareMap);
        flywheel = new subFlywheel(hardwareMap);
        intake = new subIntake(hardwareMap);
        subAprilTagDetection = new subAprilTagDetection(hardwareMap, telemetry);
        subData = new subData();


        waitForStart();
        if (isStopRequested()) return;
        while (opModeIsActive()) {

            xP = gamepad1.left_stick_x;
            yP = -gamepad1.left_stick_y;
            rP = -gamepad1.right_stick_x;

            /*
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
            */
            if (gamepad1.bWasPressed()) {
                slowMode = !slowMode;
            }
            if (gamepad1.aWasPressed()) {
                headingTargetAngle = intakeAngle * targetAngleMultiplier;
                headingLock = true;
            }
            if (gamepad1.xWasPressed()) {
                /* headingTargetAngle = outtakeAngle * targetAngleMultiplier;
                headingLock = true; *
                 */
                headingLock = true;
                aprilTagLock = true;
            }
            if (Math.abs(rP) > 0.5) {
                headingLock = false;
                aprilTagLock = false;
            }
            if (gamepad1.leftStickButtonWasPressed() && gamepad1.rightStickButtonWasPressed()) {
                drive.recalibrate();
            }
            if (gamepad1.optionsWasPressed()) {
                isRedTeam = !isRedTeam;

                if (isRedTeam) {
                    targetAngleMultiplier = 1;
                    colorTagID = 20;
                } else {
                    targetAngleMultiplier = -1;
                    colorTagID = 24;
                }

            }


            if (!slowMode) {
                drivePow = defPow;
            } else {
                drivePow = slowPow;
            }


            if (!headingLock) {
                if (!aprilTagLock) {
                    drive.To(xP, yP, rP, drivePow, true);
                } else {
                    drive.To(xP, yP, subAprilTagDetection.getRotationCorrection(colorTagID) * kP, slowMode ? 0.7 : 1, fieldCentric);
                }
            } else {
                //headingAngle = -drive.getImu() + Math.PI + headingTargetAngle;
                headingAngle = headingTargetAngle - drive.getImu();
                if (headingAngle >= Math.PI) {
                    headingAngle -= 2 * Math.PI;
                }
                if (headingAngle <= -Math.PI) {
                    headingAngle += 2 * Math.PI;
                }

                drive.To(xP, yP, headingAngle * kP, drivePow, true);
            }
            if (gamepad1.dpadRightWasPressed()) {
                flywheel.setFlyWheel(0);
            } else if (gamepad1.dpadUpWasPressed() || gamepad2.dpadUpWasPressed()) {
                flywheel.setFlyWheel(5500);
            } else if (gamepad1.dpadLeftWasPressed() || gamepad2.dpadLeftWasPressed()) {
                flywheel.setFlyWheel(4700);
            } else if (gamepad1.dpadDownWasPressed() || gamepad2.dpadDownWasPressed()) {
                flywheel.setFlyWheel(4000);
            }

            if (gamepad1.left_trigger >= 0.2) {
                intake.Set(1, 1);
                intake.kicker(1);
            } else if (gamepad1.right_trigger >= 0.2) {
                intake.Set(-1, -1);
                intake.kicker(1);
            } else {
                intake.Set(0, 0);
                intake.kicker(0);
            }

            if (gamepad1.leftBumperWasPressed()) {
                if (artifactUpL) {
                    artifactUpL = false;
                } else {
                    artifactUpL = true;
                }
            }

            intake.artifactLifts(gamepad1.right_bumper ? .5 : 1, gamepad1.left_bumper ? .5 : .07);


            telemetry.addData("Slow Mode?", slowMode);
            telemetry.addData("Team", isRedTeam);
            telemetry.addData("imu", drive.getRawImu());
            telemetry.addData("target angle", headingTargetAngle);

            telemetry.update();


        }

    }

}
