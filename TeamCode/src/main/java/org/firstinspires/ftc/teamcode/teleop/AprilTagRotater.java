package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SubSystems.subAprilTagDetection;
import org.firstinspires.ftc.teamcode.SubSystems.subDrive;


@TeleOp(name="April Test TeleOp")
public class AprilTagRotater extends LinearOpMode {

    double X_Power;
    double Y_Power;
    double Rotation;
    double slowModeHeld;
    double angle;

    boolean fieldCentric = true;
    boolean headingLock = false;
    boolean aprilTagLock = false;

    boolean fieldCentricTrigger;
    boolean fieldCentricTriggerLast;

    boolean headingLockTrigger;
    boolean headingLockTriggerLast;

    public static double targetAngle = (5 * Math.PI) / 4;

    private subAprilTagDetection aprilTagTesting;

    subDrive drive = null;


    @Override
    public void runOpMode() throws InterruptedException {

        drive = new subDrive(hardwareMap);
        aprilTagTesting = new subAprilTagDetection(hardwareMap, telemetry);

        waitForStart();
        if (isStopRequested()) return;
        while (opModeIsActive()) {


            X_Power = gamepad1.left_stick_x;
            Y_Power = gamepad1.left_stick_y;
            Rotation = gamepad1.right_stick_x;
            slowModeHeld = gamepad1.a ? .4 : 1;

            Double offsetX = aprilTagTesting.getOffsetX();

            fieldCentricTriggerLast = fieldCentricTrigger;
            fieldCentricTrigger = gamepad1.b;

            headingLockTriggerLast = headingLockTrigger;
            headingLockTrigger = gamepad1.x;

            if (gamepad1.back && gamepad1.options){
                drive.recalibrate();
            }

            if (headingLockTrigger && !headingLockTriggerLast) {
                headingLock = !headingLock;
            }

            if (Math.abs(gamepad1.right_stick_x) > 0.2) {
                headingLock = false;
                aprilTagLock = false;
            }

            if (gamepad1.right_bumper && !aprilTagLock) {
                aprilTagLock = true;
            } else if (gamepad1.right_bumper && aprilTagLock) {
                aprilTagLock = false;
            }

            if (aprilTagLock) {
                Rotation = aprilTagTesting.getRotationCorrection();
            }

            if (headingLock) {
                angle = -drive.getImu() + Math.PI - targetAngle;

                if (angle >= Math.PI){
                    angle -= 2*Math.PI;
                }
                if (angle <= -Math.PI){
                    angle += 2*Math.PI;
                }
                drive.To(X_Power, Y_Power, angle, slowModeHeld, true);
            } else {
                drive.To(X_Power, -Y_Power, -0.7 * Rotation, slowModeHeld, fieldCentric);
            }


            telemetry.addData("Heading Lock", headingLock);
            telemetry.addData("IMU: ", drive.getImu());
            telemetry.update();
        }
    }
}