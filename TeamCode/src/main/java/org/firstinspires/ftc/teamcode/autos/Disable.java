
// This OpMode disables the robot for 27 seconds


package org.firstinspires.ftc.teamcode.autos;


// IMPORT STATEMENTS

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name="[Any] Disable")
public class Disable extends LinearOpMode
{



    // DECLARATIONS

    static final int sleepForSeconds = 27;



    public void runOpMode() throws InterruptedException
    {



        while (opModeIsActive() && !isStopRequested())
        {

            sleep(sleepForSeconds * 1000);

        }

    }
}
