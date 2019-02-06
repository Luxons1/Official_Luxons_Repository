package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="AutonomousTMA")
@Disabled
public class Autonomous_TMA extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    //Declares Motor Objects
    private DcMotor leftFrontMotor;
    private DcMotor leftBackMotor;
    private DcMotor rightFrontMotor;
    private DcMotor rightBackMotor;
    private DcMotor liftMotor1;
    private DcMotor liftMotor2;

    private Servo tmdServo;

    //Declares Motor Function Arrays
    private final double gearRatio = 1.0;
    private final double wheelRadius = 2.0;
    private final double TicksperRev = 900.0;
    private double powerForward[] = {0.4, 0.4};
    private double powerTurnLeft[] = {-0.4, 0.4};
    private double powerTurnRight[] = {0.4, -0.4};

    private double pos = 0.5;

    //Ratio:
    private double ratio = (144.0/10.5);

    //OpMode Variables:
    private double lower = 2.0;
    private  double toArea = (5.0*ratio);
    private  double toCrater = (7.3*ratio);

    //Runs the OpMode
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftFrontMotor = hardwareMap.dcMotor.get("drive_left_front");
        rightFrontMotor = hardwareMap.dcMotor.get("drive_right_front");
        leftBackMotor = hardwareMap.dcMotor.get("drive_left_back");
        rightBackMotor = hardwareMap.dcMotor.get("drive_right_back");

        liftMotor1 = hardwareMap.dcMotor.get("lift_motor1");
        liftMotor2 = hardwareMap.dcMotor.get("lift_motor2");

        tmdServo = hardwareMap.servo.get("tmd_servo");
        tmdServo.setPosition(pos);

        leftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBackMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        liftMotor1.setDirection(DcMotorSimple.Direction.REVERSE);
        liftMotor2.setDirection(DcMotorSimple.Direction.FORWARD);

        waitForStart();
        runtime.reset();

        //Runs OpMode:
        /* lowerRobot(lower);
        idle();
        sleep(5000);
        finishRun();
        idle();

        leftTurn(1.0);
        idle();
        sleep(2000);
        finishRun();
        idle();

        run(initialRun);
        idle();
        sleep(2000);
        finishRun();
        idle();

        rightTurn(1.0);
        idle();
        sleep(2000);
        finishRun();
        idle(); */

        run(toArea);
        idle();
        sleep(5000);
        finishRun();
        idle();

        drop_tm();
        idle();
        sleep(2000);
        finishRun();
        idle();

        leftTurn(0.5);
        idle();
        sleep(3000);
        finishRun();
        idle();

        back(toCrater);
        idle();
        sleep(8000);
        finishRun();
        idle();

        finishRun();
        idle();
        sleep(5000);
    }

    public void run(double distance) {
        int ticks = (int) ((distance / (2 * Math.PI * wheelRadius) * gearRatio) * TicksperRev);

        leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() + ticks);
        leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition() + ticks);
        rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() + ticks);
        rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() + ticks);

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFrontMotor.setPower(powerForward[0]);
        leftBackMotor.setPower(powerForward[1]);
        rightFrontMotor.setPower(powerForward[0]);
        rightBackMotor.setPower(powerForward[1]);
    }

    public void back(double distance) {
        int ticks = (int) ((distance / (2 * Math.PI * wheelRadius) * gearRatio) * TicksperRev);

        leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() - ticks);
        leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition() - ticks);
        rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() - ticks);
        rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition()- ticks);

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

    public void leftTurn(double distanceInRotations) {
        int partsRotationsLeft = (int)(distanceInRotations*TicksperRev);
        int partsRotationsRight = (int)(distanceInRotations*TicksperRev);

        leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() - partsRotationsLeft);
        leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition() - partsRotationsLeft);
        rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() + partsRotationsRight);
        rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() + partsRotationsRight);

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFrontMotor.setPower(powerForward[0]);
        leftBackMotor.setPower(powerForward[0]);
        rightFrontMotor.setPower(powerForward[1]);
        rightBackMotor.setPower(powerForward[1]);
    }

    public void rightTurn(double distanceInRotations) {
        int partsRotationsLeft = (int)(distanceInRotations*TicksperRev);
        int partsRotationsRight = (int)(distanceInRotations*TicksperRev);

        leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() + partsRotationsLeft);
        leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition() + partsRotationsLeft);
        rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() - partsRotationsRight);
        rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() - partsRotationsRight);

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFrontMotor.setPower(powerForward[1]);
        leftBackMotor.setPower(powerForward[1]);
        rightFrontMotor.setPower(powerForward[0]);
        rightBackMotor.setPower(powerForward[0]);
    }

    public void lowerRobot(double distanceInRotations) {
        int partsRotations = (int)(TicksperRev*distanceInRotations);

        liftMotor1.setTargetPosition(liftMotor1.getCurrentPosition() + partsRotations);
        liftMotor2.setTargetPosition(liftMotor2.getCurrentPosition() + partsRotations);

        liftMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        liftMotor1.setPower(powerForward[0]);
        liftMotor2.setPower(powerForward[1]);
    }

    public void drop_tm() {
        tmdServo.setPosition(pos - 0.5);
    }
}
