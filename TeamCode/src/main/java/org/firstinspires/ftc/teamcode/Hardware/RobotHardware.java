package org.firstinspires.ftc.teamcode.Hardware;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import java.util.List;

@Config
public class RobotHardware {
    private static RobotHardware instance = null;
    public DcMotorEx rightRear, leftRear, leftFront, rightFront;
    List<LynxModule> allHubs;

    //*location related
    public Follower follower;
    public GoBildaPinpointDriver pinpoint;
    public boolean isAuto;
    public static Pose lastAutoPose = new Pose(72, 7.5, Math.toRadians(90)); //modificam daca trebe, isi ia overwrite de la auto daca e cazul, daca nu ramane pe defaultul asta



    //*units
    public static AngleUnit angleUnit = AngleUnit.RADIANS;
    public static DistanceUnit distanceUnit = DistanceUnit.INCH;

    //*intake
    public MotorEx IntakeMotor;

    //*pid
    /*public com.seattlesolvers.solverslib.controller.PIDFController turretController;
    public static double P = 0.5, secondP = 1, I = 0, D = 0.05, F = 1.16;
    public static double velocityP = 0.0052, velocityI =0, velocityD = 0, velocityF = 0.00052;*/


    //*alliance
    public enum AllianceColor{
        BLUE,
        RED
    }
    /*public enum StartingPose{
        CLOSE,
        FAR
    }*/




    //*custom methods
    public static RobotHardware getInstance() {
        if(instance == null){
            instance = new RobotHardware();
        }
        return instance;
    }
    public void clearBulkCache() {
        for (LynxModule hub : allHubs) {
            hub.clearBulkCache();
        }
    }


    public void init(HardwareMap hardwareMap, boolean isAuto){
        this.isAuto = isAuto;
        allHubs = hardwareMap.getAll(LynxModule.class);

        IntakeMotor = new MotorEx(hardwareMap,"IntakeMotor");
        IntakeMotor.setRunMode(Motor.RunMode.RawPower);


        follower = Constants.createFollower(hardwareMap);

        if(!isAuto) {
            for (LynxModule module : allHubs) {
                module.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
            }
            follower.setPose(lastAutoPose); //TODO update with startpose logic if lastAutoPose is empty
        }

    }
}
