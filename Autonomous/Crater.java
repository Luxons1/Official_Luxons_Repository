package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Crater")
@Disabled
public class Crater extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    //Creates Robot Object
    LuxonsAutonomousRobot autoRobot = new LuxonsAutonomousRobot();

    //OpMode Variables:
    private double toCrater = (2.4*autoRobot.ratio);
    private double firstRun = (1.0*autoRobot.ratio);
    private double runAhead = (4.0*autoRobot.ratio);

    //Runs the OpMode
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        autoRobot.init(hardwareMap);

        waitForStart();
        runtime.reset();

        //Run OpMode:
        unhook();

        autoRobot.runRobot("front", toCrater);
        idle();
        sleep(4000);
        autoRobot.finishRun();
        idle();

        autoRobot.finishRun();
        idle();
        sleep(1000);

        telemetry.addData("Cube Position: ", autoRobot.cubePos);
        telemetry.addLine();
        telemetry.addData("CubeX: ", autoRobot.cubeX);
        telemetry.update();
        sleep(10000);
    }

    public void unhook() {
        autoRobot.liftRobot("down");
        idle();
        sleep(4000);
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
        if (autoRobot.goAhead == true) {
            autoRobot.runRobot("front", runAhead);
            sleep(5000);
            autoRobot.finishRun();
            idle();
        }

        else if (autoRobot.goAhead == false && autoRobot.cubePos == 1) {
            autoRobot.runRobot("front", firstRun);
            sleep(5000);
            autoRobot.finishRun();
            idle();

            autoRobot.shiftRobot("right", 1.5);
            sleep(2000);
            autoRobot.finishRun();
            idle();
        }

        else if (autoRobot.goAhead == false && autoRobot.cubePos == 2) {
            autoRobot.runRobot("front", firstRun);
            sleep(5000);
            autoRobot.finishRun();
            idle();

            autoRobot.shiftRobot("left", 1.5);
            sleep(2000);
            autoRobot.finishRun();
            idle();
        }

        else  if (autoRobot.goAhead == false && autoRobot.cubePos == 3) {
            autoRobot.runRobot("front", firstRun);
            sleep(5000);
            autoRobot.finishRun();
            idle();

            autoRobot.shiftRobot("left", 5.0);
            sleep(2000);
            autoRobot.finishRun();
            idle();
        }

        else {
            autoRobot.runRobot("front", firstRun);
            sleep(5000);
            autoRobot.finishRun();
            idle();

            autoRobot.shiftRobot("left", 1.5);
            sleep(2000);
            autoRobot.finishRun();
            idle();
        }
    }
}