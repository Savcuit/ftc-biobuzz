package org.firstinspires.ftc.teamcode;
 
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
 
@TeleOp(name = "MecanumDrive", group = "Competition")
public class MecanumDrive extends LinearOpMode {
 
    private DcMotor frontLeft, frontRight, backLeft, backRight, intake;
    private Servo servo;
 
    private double speed = 0.6;
    private boolean lastL1, lastR1;
 
    @Override
    public void runOpMode() {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        intake = hardwareMap.get(DcMotor.class, "intake");
        servo = hardwareMap.get(Servo.class, "servo");
 
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
 
        waitForStart();
 
        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;
 
            double fl = y + x + rx;
            double fr = y - x - rx;
            double bl = y - x + rx;
            double br = y + x - rx;
 
            double max = Math.max(1.0, Math.max(
                Math.max(Math.abs(fl), Math.abs(fr)),
                Math.max(Math.abs(bl), Math.abs(br))
            ));
 
            frontLeft.setPower(fl / max * speed);
            frontRight.setPower(fr / max * speed);
            backLeft.setPower(bl / max * speed);
            backRight.setPower(br / max * speed);
 
            if (gamepad1.left_bumper && !lastL1) {
                speed = Range.clip(speed - 0.1, 0.2, 1.0);
            }

            if (gamepad1.right_bumper && !lastR1) {
                speed = Range.clip(speed + 0.1, 0.2, 1.0);
            }
 
            lastL1 = gamepad1.left_bumper;
            lastR1 = gamepad1.right_bumper;
 
            if (gamepad1.right_trigger > 0.1) {
                intake.setPower(gamepad1.right_trigger);
            } else if (gamepad1.left_trigger > 0.1) {
                intake.setPower(-gamepad1.left_trigger);
            } else {
                intake.setPower(0);
            }
 
            if (gamepad1.a) {
                servo.setPosition(0.2);
            } else if (gamepad1.b) {
                servo.setPosition(0.8);
            }
 
            telemetry.addData("Speed", "%.0f%%", speed * 100);
            telemetry.update();
        }
    }
}