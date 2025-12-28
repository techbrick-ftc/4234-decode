package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.robotcore.hardware.Gamepad;

public class SubButtonTranslation {

    double drive_X;
    double drive_Y;
    double drive_Z;
    boolean targeting = false;
    boolean shooting = false;

    public void getInputs(Gamepad gamepad1) {
        drive_X = gamepad1.left_stick_x;
        drive_Y = gamepad1.left_stick_y;
        drive_Z = gamepad1.right_stick_x;

        
    }
}