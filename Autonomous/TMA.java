package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="TMA")
public class TMA extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    //Creates Robot Object
    LuxonsAutonomousRobot autoRobot = new LuxonsAutonomousRobot();

    //OpMode Variables:
    private double toDepot = (2.9*autoRobot.ratio);
    private double firstRun = (1.0*autoRobot.ratio);
    private double runAhead = (2.0*autoRobot.ratio);
    private double toCrater2 = (3.3*autoRobot.ratio);

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

        autoRobot.extendClaw("out", 2.5);
        idle();
        sleep(2000);
        autoRobot.finishRun();
        idle();

        autoRobot.drop_tm();
        idle();
        sleep(1000);
        autoRobot.finishRun();
        idle();

        autoRobot.extendClaw("in", 3.0);
        idle();
        sleep(2000);
        autoRobot.finishRun();
        idle();

        autoRobot.finishRun();
        idle();
        sleep(1000);

        telemetry.addData("Cube Position: ", autoRobot.cubePos);
        telemetry.addLine();
        sleep(5000);
    }

    public void unhook() {
        autoRobot.liftRobot("down");
        idle();
        sleep(4000);
        autoRobot.finishRun();
        idle();

        /* autoRobot.diagRobot("right and up", 0.81);
        idle();
        sleep(1000);
        autoRobot.finishRun();
        idle(); */

        autoRobot.runRobotRotations("front", 0.15);
        idle();
        sleep(1000);
        autoRobot.finishRun();
        idle();

        autoRobot.shiftRobot("right", 0.6);
        idle();
        sleep(1000);
        autoRobot.finishRun();
        idle();

        autoRobot.runRobot("front", firstRun);
        idle();
        sleep(1000);
        autoRobot.finishRun();
        idle();

        path();
    }

    public void path() {
        autoRobot.goldAlign();

        if (autoRobot.goAhead == true) {
            autoRobot.shiftRobot("left", 0.5);
            idle();
            sleep(1000);
            autoRobot.finishRun();
            idle();

            autoRobot.runRobot("front", runAhead);
            idle();
            sleep(4000);
            autoRobot.finishRun();
            idle();
        }

        else if (autoRobot.goAhead == false) {
            autoRobot.shiftRobot("right", 1.8);
            idle();
            sleep(2000);
            autoRobot.finishRun();
            idle();

            autoRobot.goldAlign();

            if (autoRobot.goAhead == true) {
                autoRobot.shiftRobot("left", 0.5);
                idle();
                sleep(1000);
                autoRobot.finishRun();
                idle();

                autoRobot.runRobot("front", toDepot);
                idle();
                sleep(3000);
                autoRobot.finishRun();
                idle();

                autoRobot.turnRobot("left", 1.0);
                idle();
                sleep(1000);
                autoRobot.finishRun();
                idle();
            }

            else if (autoRobot.goAhead == false) {
          /* autoRobot.shiftRobot("left", 2.5);
          idle();
          sleep(3000);
          autoRobot.finishRun();
          idle();

          autoRobot.shiftRobot("left", 3.5);
          idle();
          sleep(5000);
          autoRobot.finishRun();
          idle();

          autoRobot.runRobot("front", toCrater);
          idle();
          sleep(5000);
          autoRobot.finishRun();
          idle(); */

                autoRobot.runRobotRotations("front", 0.4);
                idle();
                sleep(1000);
                autoRobot.finishRun();
                idle();

                autoRobot.turnRobot("left", 2.2);
                idle();
                sleep(2000);
                autoRobot.finishRun();
                idle();

                autoRobot.runRobot("front", toCrater2);
                idle();
                sleep(3000);
                autoRobot.finishRun();
                idle();

                autoRobot.turnRobot("right", 2.2);
                idle();
                sleep(2000);
                autoRobot.finishRun();
                idle();

                autoRobot.runRobot("front", toDepot);
                idle();
                sleep(2000);
                autoRobot.finishRun();
                idle();

                autoRobot.turnRobot("right", 1.0);
                idle();
                sleep(1000);
                autoRobot.finishRun();
                idle();
            }
        }
    }
}
