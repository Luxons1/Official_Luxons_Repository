/*
   Mother Code for Team #10588 THE LUXONS, of Marquette High School Robotics.
   All liscences, terms and lists will be found here.

   **CopyRight-LUXONS 2019**, USES PUBLIC DOMAIN CODE FROM GITHUB LLC, from other libraries.
   Authors: Sameer Iyer, and Sushen Kolakaleti

   !!WARNING!! NO EDITS ARE TO BE MADE, BY ANYONE!!! WITHOUT THE CONSENT OF SAMEER IYER OR SUSHEN KOLAKALETI.

   CLASSES UNDER LISCENCE:

   - Autonomous:
   LuxonsAutonomousRobot.java
   Crater.java
   TMA.java
   TMA_C.java
   Autonomous_Crater.java
   Autonomous_TMA.java

   - TeleOp:
   LuxonsRobot.java
   LuxonsTeleOpMode.java
   LuxonsTeleOpModeNew.java
   TeleOpMode.java

   Last Edit By: Sushen Kolakaleti on 1/31/2019
 */

package org.firstinspires.ftc.teamcode.Autonomous;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
//import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
//import org.firstinspires.ftc.teamcode.Autonomous.Gold;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;

public class LuxonsAutonomousRobot {
    //Wheels
    public DcMotor leftFrontMotor;
    public DcMotor leftBackMotor;
    public DcMotor rightFrontMotor;
    public DcMotor rightBackMotor;

    //Other Motors
    public DcMotor liftMotor1;
    public DcMotor liftMotor2;
    public DcMotor clawMotor;
    public DcMotor basketMotor;

    //Servos
    public Servo tmdServo;
    public CRServo clawServo;

    //Sensors
    public ModernRoboticsI2cColorSensor colorSensor;

    //Extra Doubles
    public final double gearRatio = 1.0;
    public final double wheelRadius = 2.0;
    public final double TicksperRev = 900.0;
    public final double LiftTicks = 1440.0;
    public final double lift = 3.5;
    public final double basketLift = 1.3;

    public double powerForward[] = {0.4, 0.4};
    public double powerLift[] = {0.4, 0.4};
    public double powerClaw = 0.5;
    public double powerBasket = 0.4;

    public double ratio = (144.0/10.5);

    public double pos = 0.5;

    public double cubeX;
    public int cubePos = 0;
    public boolean goAhead = false;
    public int targetColorNumber = 8;

    //Extra Integers
    public boolean cubeSensed = false;

    //Hardware Object
    HardwareMap hardwareMap;

    //Constructor
    public LuxonsAutonomousRobot(){

    }

    public Gold detector = new Gold();

    //init loop
    public void init(HardwareMap hwMap) {
        // Save reference to Hardware map
        hardwareMap = hwMap;

        leftFrontMotor = hardwareMap.dcMotor.get("drive_left_front");
        leftBackMotor = hardwareMap.dcMotor.get("drive_left_back");
        rightFrontMotor = hardwareMap.dcMotor.get("drive_right_front");
        rightBackMotor = hardwareMap.dcMotor.get("drive_right_back");

        //Motors
        clawMotor= hardwareMap.dcMotor.get("claw_motor");
        basketMotor = hardwareMap.dcMotor.get("basket_motor");
        liftMotor1 = hardwareMap.dcMotor.get("lift_motor1");
        liftMotor2 = hardwareMap.dcMotor.get("lift_motor2");

        //Servos
        tmdServo = hardwareMap.servo.get("tmd_servo");
        clawServo = hardwareMap.crservo.get("intake_servo1");

        //Sensor
        //colorSensor = hardwareMap.get(ModernRoboticsI2cColorSensor.class, "color_sensor");

        //Detector
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance()); // Initialize it with the app context and camera
        detector.useDefaults(); // Set detector to use default settings

        // Optional tuning
        detector.alignSize = 100; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
        detector.alignPosOffset = 0; // How far from center frame to offset this alignment zone.
        detector.downscale = 0.4; // How much to downscale the input frames

        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA
        //detector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring
        detector.maxAreaScorer.weight = 0.005; //

        detector.ratioScorer.weight = 5; //
        detector.ratioScorer.perfectRatio = 1.0; // Ratio adjustment */

        detector.setAlignSettings(25, 25);

        detector.enable();

        //Servo Set
        tmdServo.setPosition(-0.1);

        //Encoders
        leftFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rightFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rightBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        clawMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        clawMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        basketMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        basketMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        liftMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        liftMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Directions
        leftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotor.Direction.FORWARD);
        rightBackMotor.setDirection(DcMotor.Direction.FORWARD);

        clawMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        basketMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        liftMotor1.setDirection(DcMotorSimple.Direction.REVERSE);
        liftMotor2.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    //Autonomous Functions
    public void runRobot(String type, double distance) {
        int ticks = (int)((distance / (2 * Math.PI * wheelRadius) * gearRatio) * TicksperRev);

        if (type.equalsIgnoreCase("front")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() + ticks);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition() + ticks);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() + ticks);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() + ticks);
        }

        else if (type.equalsIgnoreCase("back")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() - ticks);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition() - ticks);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() - ticks);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() - ticks);
        }

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFrontMotor.setPower(powerForward[0]);
        leftBackMotor.setPower(powerForward[1]);
        rightFrontMotor.setPower(powerForward[0]);
        rightBackMotor.setPower(powerForward[1]);
    }

    public void runRobotRotations(String type, double distanceInRotations) {
        int ticks = (int)(distanceInRotations*TicksperRev);

        if (type.equalsIgnoreCase("front")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() + ticks);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition() + ticks);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() + ticks);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() + ticks);
        }

        else if (type.equalsIgnoreCase("back")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() - ticks);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition() - ticks);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() - ticks);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() - ticks);
        }

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFrontMotor.setPower(powerForward[0]);
        leftBackMotor.setPower(powerForward[1]);
        rightFrontMotor.setPower(powerForward[0]);
        rightBackMotor.setPower(powerForward[1]);
    }

    public void finishRun() {
        leftFrontMotor.setPower(0);
        leftBackMotor.setPower(0);
        rightFrontMotor.setPower(0);
        rightBackMotor.setPower(0);

        liftMotor1.setPower(0);
        liftMotor2.setPower(0);

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        liftMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void turnRobot(String type, double distanceInRotations) {
        int partsRotationsLeft = (int)(distanceInRotations*TicksperRev);
        int partsRotationsRight = (int)(distanceInRotations*TicksperRev);

        if (type.equalsIgnoreCase("left")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() - partsRotationsLeft);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition() - partsRotationsLeft);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() + partsRotationsRight);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() + partsRotationsRight);
        }

        else if (type.equalsIgnoreCase("right")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() + partsRotationsLeft);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition() + partsRotationsLeft);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() - partsRotationsRight);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() - partsRotationsRight);
        }

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFrontMotor.setPower(powerForward[0]);
        leftBackMotor.setPower(powerForward[0]);
        rightFrontMotor.setPower(powerForward[1]);
        rightBackMotor.setPower(powerForward[1]);
    }


    public void shiftRobot(String type, double distanceInRotations) {
        int partsRotationsLeft = (int)(distanceInRotations*TicksperRev);
        int partsRotationsRight = (int)(distanceInRotations*TicksperRev);

        if (type.equalsIgnoreCase("left")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() - partsRotationsLeft);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition() + partsRotationsLeft);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() + partsRotationsRight);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() - partsRotationsRight);
        }

        else if (type.equalsIgnoreCase("right")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() + partsRotationsLeft);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition() - partsRotationsLeft);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() - partsRotationsRight);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() + partsRotationsRight);
        }

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFrontMotor.setPower(powerForward[0]);
        leftBackMotor.setPower(powerForward[1]);
        rightFrontMotor.setPower(powerForward[0]);
        rightBackMotor.setPower(powerForward[1]);
    }

    public void diagRobot(String type, double distanceInRotations) {
        int partsRotationsLeft = (int)(distanceInRotations*TicksperRev);
        int partsRotationsRight = (int)(distanceInRotations*TicksperRev);

        double LF = 0, LB = 0, RF = 0, RB = 0;

        if (type.equalsIgnoreCase("left and up")) {
            LF = 0.0;
            RF = 0.4;
            RB = 0.0;
            LB = 0.4;
            rightFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() + partsRotationsRight);
            leftBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() + partsRotationsLeft);
        }

        else if (type.equalsIgnoreCase("right and up")) {
            LF = 0.4;
            RF = 0.0;
            RB = 0.4;
            LB = 0.0;
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() + partsRotationsLeft);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() + partsRotationsRight);
        }

        else if (type.equalsIgnoreCase("left and down")) {
            LF = 0.4;
            RF = 0.0;
            RB = 0.4;
            LB = 0.0;
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() - partsRotationsLeft);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() - partsRotationsRight);
        }

        else if (type.equalsIgnoreCase("right and down")) {
            LF = 0.0;
            RF = 0.4;
            RB = 0.0;
            LB = 0.4;
            rightFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() - partsRotationsRight);
            leftBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() - partsRotationsLeft);
        }

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFrontMotor.setPower(LF);
        leftBackMotor.setPower(LB);
        rightFrontMotor.setPower(RF);
        rightBackMotor.setPower(RB);
    }

    public void liftRobot(String type) {
        int partsRotations = (int)(LiftTicks*lift);

        if (type.equalsIgnoreCase("up")) {
            liftMotor1.setTargetPosition(liftMotor1.getCurrentPosition() - partsRotations);
            liftMotor2.setTargetPosition(liftMotor2.getCurrentPosition() - partsRotations);
        }

        else if (type.equalsIgnoreCase("down")) {
            liftMotor1.setTargetPosition(liftMotor1.getCurrentPosition() + partsRotations);
            liftMotor2.setTargetPosition(liftMotor2.getCurrentPosition() + partsRotations);
        }

        liftMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        liftMotor1.setPower(powerLift[0]);
        liftMotor2.setPower(powerLift[1]);
    }

     //Senses minerals using phone, from the lander:
     public void senseCubePhone() {
       //detector.enable();
       sleep(1000);

       detector.isFound();
       detector.getXPosition();
       detector.getAligned();

       cubeX = detector.goldXPos;

       if (detector.aligned == true) {
         goAhead = true;
       }

       else if (detector.aligned = false && detector.found == true) {
         goAhead = false;
         cubeSensed = true;

         if (cubeX < 250) {
           cubePos = 3;
         }

         else if (cubeX > 250) {
           cubePos = 2;
         }

         else {
           cubePos = 2;
         }
       }

       else if (detector.aligned == false && detector.found == false) {
         cubeSensed = false;
         goAhead = false;
         cubeX = 0;
         cubePos = 1;
       }

       //detector.disable();
    }

    //Senses minerals using the phone, individually:
    public void goldAlign() {
      //detector.enable();
      sleep(1000);

      detector.isFound();
      detector.getXPosition();
      detector.getAligned();

      cubeX = detector.goldXPos;

      if (detector.found == true) {
        cubeSensed = true;
        goAhead = true;
      }

      else if (detector.found == false) {
        cubeSensed = false;
        goAhead = false;
      }

      //detector.disable();
    }

     //Senses minerals using the color sensor, individually:
     public void senseCubeSensor() {
      colorSensor.enableLed(true);
      sleep(1000);

      if (colorSensor.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER) == targetColorNumber) {
        cubeSensed = true;
        goAhead = true;
      }

      else if (colorSensor.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER) != targetColorNumber) {
        cubeSensed = false;
        goAhead = false;
      }

      colorSensor.enableLed(false);
    }

    public void extendClaw(String type, double distanceInRotations) {
        int distanceInTicks = (int)(distanceInRotations*TicksperRev);

        if (type.equalsIgnoreCase("out")) {
            clawMotor.setTargetPosition(clawMotor.getCurrentPosition() + distanceInTicks);
        }

        else if (type.equalsIgnoreCase("in")) {
            clawMotor.setTargetPosition(clawMotor.getCurrentPosition() - distanceInTicks);
        }

        clawMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        clawMotor.setPower(powerClaw);
    }

    public void liftBasket(String type) {
      int distanceInTicks = (int)(basketLift*TicksperRev);

      if (type.equalsIgnoreCase("up")) {
        basketMotor.setTargetPosition(basketMotor.getCurrentPosition() - distanceInTicks);
      }

      else if (type.equalsIgnoreCase("down")) {
        basketMotor.setTargetPosition(basketMotor.getCurrentPosition() + distanceInTicks);
      }

      basketMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

      basketMotor.setPower(powerBasket);
    }

    public void drop_tm() {
      clawServo.setPower(0.4);
    }

    public void drop_tm_old() {
      tmdServo.setPosition(0);
    }

    public final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}