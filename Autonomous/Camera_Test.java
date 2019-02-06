package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Camera")
public class Camera_Test extends LinearOpMode {
    LuxonsAutonomousRobot autoRobot = new LuxonsAutonomousRobot();

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        autoRobot.init(hardwareMap);

        waitForStart();
        runtime.reset();

        //Run OpMode:
        autoRobot.goldAlign();

        telemetry.addData("Cube Position: ", autoRobot.cubePos);
        telemetry.addLine();
        telemetry.addData("CubeX: ", autoRobot.cubeX);
        telemetry.update();
        sleep(10000);
    }
}
