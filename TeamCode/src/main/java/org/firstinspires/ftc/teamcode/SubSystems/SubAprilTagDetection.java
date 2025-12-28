package org.firstinspires.ftc.teamcode.SubSystems;

import static org.firstinspires.ftc.teamcode.SubSystems.SubData.isRedTeam;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class SubAprilTagDetection {
    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTag;
    public Telemetry telemetry; //TODO: Check if private or public
    private double imageWidth;
    private double tagID;

    public SubAprilTagDetection(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        //note which tag to identify based on team
        if (isRedTeam) {
            tagID = 20;
        } else {
            tagID = 24;
        }

        //set up the camera output and april tag processing
        imageWidth = 640;
        aprilTag = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagOutline(true)
                // .setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES) //TODO: See if needs changing to radians?
                .setOutputUnits(DistanceUnit.INCH, AngleUnit.RADIANS)
                .build();

        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class,
                        "Webcam 1"))
                .addProcessor(aprilTag)
                .setCameraResolution(new Size(640, 480))
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .build();

    }
    //get the x offset
    public Double getOffsetX(double tagID) {
        List <AprilTagDetection> detections = aprilTag.getDetections();
        if (detections.isEmpty()) {
            return 0.0;
        }

        for(AprilTagDetection detection : detections) {
            if (detection.id == tagID) {
                return detection.ftcPose.bearing;
            }
        }

        return 0.0;

//        AprilTagDetection tag = detections.get(0);
//        return tag.center.x - (imageWidth / 2.0);
    }

    //converts the offset into rotation correction value
    public double getRotationCorrection(double colorTagID) {
        double offset = getOffsetX(colorTagID);

        if (offset == 0) {
            return 0.0;
        }

        // double kP = 0.003; //TODO: See if needs increasing, esp if changing from degrees to radians
        double kP = 0.17;
        double rotation = kP * offset;

        return Math.max(-1, Math.min(1, rotation));
    }

    public void stop() {
        visionPortal.close();
    }

}
