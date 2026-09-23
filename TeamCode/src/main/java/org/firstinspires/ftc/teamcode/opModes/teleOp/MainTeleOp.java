package org.firstinspires.ftc.teamcode.opModes.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.utils.Rates;

@TeleOp(name="Annay", group="TeleOp")
public class MainTeleOp extends OpMode {

    public Drive drive;
    Gamepad currentGamepad1;
    Gamepad previousGamepad1;

    private Intake intake;

    private Rates moveRate;
    private Rates turnRate;

    @Override
    public void init() {
        drive = new Drive(hardwareMap);
        currentGamepad1 = new Gamepad();
        previousGamepad1 = new Gamepad();
        intake = new Intake(hardwareMap);

        /*
        The center rate of moveRate higher than that of turn rate, because the robot movement is
        acceptable with smaller stick movements.
        */

        moveRate = new Rates(0.7, 1.0, 0.4, 0.01);
        turnRate = new Rates(0.375, 1.0, 0.4, 0.01);

    }

    @Override
    public void loop() {
        previousGamepad1.copy(currentGamepad1);
        currentGamepad1.copy(gamepad1);

        if ( currentGamepad1.right_bumper && !previousGamepad1.right_bumper){
            intake.intakeSpin();
        }

        if ( !currentGamepad1.right_bumper && previousGamepad1.right_bumper){
            intake.intakeStop();
        }


        drive.drive(-currentGamepad1.left_stick_y, currentGamepad1.left_stick_x, currentGamepad1.right_stick_x);

        drive.update();
        intake.update();
    }

}
