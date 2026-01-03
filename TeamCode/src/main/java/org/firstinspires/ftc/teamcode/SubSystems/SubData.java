package org.firstinspires.ftc.teamcode.SubSystems;

public class SubData {

    public static boolean isRedTeam;

    public void setTeam(boolean team) {
        isRedTeam = team;
    }

    public void changeTeam() {
        isRedTeam = !isRedTeam;
    }

    public boolean getTeam() {
        return isRedTeam;
    }

    public static void setAngle(double angle) {
        storedAngle = angle;
    }

    public static double getAngle() {
        return storedAngle;
    }




}
