package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

//Optional Code::
@Autonomous(name="TMA: C")
@Disabled //Leave Disabled for now.
public class TMA_C extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    //Creates Robot Object
    LuxonsAutonomousRobot autoRobot = new LuxonsAutonomousRobot();

    //OpMode Variables:
    private double firstRun = (4.6*autoRobot.ratio);
    private  double toCrater = (7.6*autoRobot.ratio);

    //Runs the OpMode
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        autoRobot.init(hardwareMap);

        waitForStart();
        runtime.reset();

        //Runs OpMode:
        unhook();

        autoRobot.drop_tm();
        idle();
        sleep(2000);
        autoRobot.finishRun();
        idle();

        autoRobot.turnRobot("right", 3.5);
        idle();
        sleep(2000);
        autoRobot.finishRun();
        idle();

        autoRobot.runRobot("back", 12.0);
        idle();
        sleep(1000);
        autoRobot.finishRun();
        idle();

        autoRobot.runRobot("front", toCrater);
        idle();
        sleep(8000);
        autoRobot.finishRun();
        idle();

        autoRobot.finishRun();
        idle();
        sleep(1000);
    }

    public void unhook() {
        autoRobot.liftRobot("down");
        idle();
        sleep(5000);
        autoRobot.finishRun();
        idle();

        autoRobot.senseCubePhone();

        autoRobot.shiftRobot("right", 1.0);
        idle();
        sleep(2000);
        autoRobot.finishRun();
        idle();

        path();
    }

    public void path() {
        if (autoRobot.cubePos == 1) {
            autoRobot.runRobot("front", firstRun);
            sleep(5000);
            autoRobot.finishRun();
            idle();

            autoRobot.shiftRobot("right", 1.0);
            sleep(2000);
            autoRobot.finishRun();
            idle();
        }

        else if (autoRobot.cubePos == 2) {
            autoRobot.runRobot("front", firstRun);
            sleep(5000);
            autoRobot.finishRun();
            idle();

            autoRobot.shiftRobot("left", 1.0);
            sleep(2000);
            autoRobot.finishRun();
            idle();
        }

        else  if (autoRobot.cubePos == 3) {
            autoRobot.runRobot("front", firstRun);
            sleep(5000);
            autoRobot.finishRun();
            idle();

            autoRobot.shiftRobot("left", 3.0);
            sleep(2000);
            autoRobot.finishRun();
            idle();
        }

        else if (autoRobot.cubePos == 0 || autoRobot.cubeSensed == false) {
            backupPath();
        }
    }

    public void backupPath() {
        //autoRobot.senseCubeSensor();

        if (autoRobot.cubeSensed == false) {
            autoRobot.runRobot("front", firstRun);
            sleep(5000);
            autoRobot.finishRun();
            idle();

            autoRobot.shiftRobot("left", 1.0);
            sleep(2000);
            autoRobot.finishRun();
            idle();
        }

        else {

        }
    }
}