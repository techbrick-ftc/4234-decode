package org.firstinspires.ftc.teamcode.teleop;

import org.firstinspires.ftc.teamcode.SubSystems.subDrive;
import org.firstinspires.ftc.teamcode.SubSystems.subFlywheel;
import org.firstinspires.ftc.teamcode.SubSystems.subIntake;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="[Current] 4234 Main TeleOP")
public class teleOPMain extends LinearOpMode {

    //Chassis
    double State = 1;
    // 0 - Disabled
    // 1 - Driver controlled
    // 2 - Intaking, Heading Held
    // 3 - Shooting, Heading Held

    double angle;
    public double teamColor;
    public double targetAngle;
    double headingkP = 0.4;

    // Declare Subsystems
    subDrive drive = null;
    subFlywheel flywheel = null;


    @Override
    public void runOpMode() throws InterruptedException {


        // Init Subsystems
        drive = new subDrive(hardwareMap);
        flywheel = new subFlywheel(hardwareMap);


        // Drive variables and controls
        double X_Power;
        double Y_Power;
        double Rotation;

        boolean slowMode = false;
        boolean slowModeToggle = false;
        boolean slowModeToggleLast = false;

        boolean fieldCentric = true;
        boolean fieldCentricToggle = false;
        boolean fieldCentricToggleLast = false;

        teamColor = 0;


        waitForStart();
        if (isStopRequested()) return;
        while (opModeIsActive()) {


            // Drive inputs
            X_Power = gamepad1.left_stick_x;
            Y_Power = -gamepad1.left_stick_y;
            Rotation = gamepad1.right_stick_x;

            if (gamepad1.x) {
                State = 1;
            } else if (gamepad1.a) {
                State = 2;
            }


            // Triggers and booleans
            fieldCentricToggle = (gamepad1.left_stick_button && gamepad1.right_stick_button);
            if (fieldCentricToggle && !fieldCentricToggleLast) {
                fieldCentric = !fieldCentric;
                if (fieldCentric) {
                    drive.recalibrate(); // Recalibrates IMU when field centric enabled
                }
            }
            fieldCentricToggleLast = fieldCentricToggle;

            slowModeToggle = (gamepad1.left_bumper);
            if (slowModeToggle && !slowModeToggleLast) {
                slowMode = !slowMode;
            }
            slowModeToggleLast = slowModeToggle;

            if (gamepad2.back && gamepad2.x) {
                teamColor = -1;
            } else if (gamepad2.back && gamepad2.b) {
                teamColor = 1;
            }


            //region Drive
            if (State == 0) {

                drive.To(0, 0, 0, 0, fieldCentric);

            } else if (State == 1) {

                drive.To(X_Power, Y_Power, Rotation, slowMode ? 0.7 : 1, fieldCentric);

            } else if (State == 2) {

                // Calculate target angle based on team color
                targetAngle = Math.PI - teamColor * 0.3 * Math.PI;
                angle = -drive.getImu() + Math.PI - targetAngle;



                // Offset for more efficient rotation
                if (angle >= Math.PI){
                    angle -= 2*Math.PI;
                }
                if (angle <= -Math.PI){
                    angle += 2*Math.PI;
                }

                //TODO: Look at faster / more precise method of rotation
                drive.To(X_Power, Y_Power, angle * headingkP, slowMode ? 0.7 : 1, fieldCentric);

            }
            //endregion

            //region Flywheel
            flywheel.setFlyWheel(3000 * gamepad1.right_trigger, 2);


            // Telemetry
            telemetry.addData("Team (-1 = blue, 1 = red)", teamColor);
            telemetry.addData("Rotation", drive.getRawImu());
            telemetry.addData("Field Centric?", fieldCentric);
            telemetry.addData("Slow Mode", slowMode);

            if (!gamepad1.square) { // Use to suppress updates to tele data
                telemetry.update();
            }



        }

    }

}
