package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.follower.ManualDrive;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.Hardware.RobotHardware;

@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")
public class TeleOp extends CommandOpMode{
    private final RobotHardware robot = RobotHardware.getInstance();

    public static GamepadEx gamepadEx;
    public static GamepadEx gamepadEx2;
    double loopTime;

    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset(); //// == super.reset(), without it the code breaks, this resets the command scheduler
        gamepadEx = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        robot.init(hardwareMap, false);
        //robot.follower.setPose(RobotHardware.lastAutoPose); // if you want auto handoff --- face asta deja in robot hardware da il las in  caz ca nu mere if-ul
    }

    @Override
    public void run() {
        CommandScheduler.getInstance().run(); //// == super.reset(), without it the code breaks, this activates the command scheduler
        //robot.follower.manual(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x); // this is manual teleop drive with default robot centric (no auto braking)
        ManualDrive.driveOrHold(
                robot.follower,
                -gamepad1.left_stick_y,
                gamepad1.left_stick_x,
                gamepad1.right_stick_x
        );

        double loop = System.nanoTime();
        //*telemetry
        telemetry.addData("follower pose", robot.follower.pose());
        telemetry.addData("x:", robot.follower.pose().x());
        telemetry.addData("y:", robot.follower.pose().y());
        telemetry.addData("heading:", robot.follower.pose().y());
        telemetry.addData("hz ", 1000000000 / (loop - loopTime));

        loopTime = loop;
        telemetry.update();
        robot.clearBulkCache();
        robot.follower.update();
    }
}
