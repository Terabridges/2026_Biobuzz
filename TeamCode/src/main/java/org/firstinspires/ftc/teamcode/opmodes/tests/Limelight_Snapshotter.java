package org.firstinspires.ftc.teamcode.opmodes.tests;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.io.OutputStream;
import java.net.Socket;

public class Limelight_Snapshotter {

    private Limelight3A limelight;
    private boolean lastAState = false;
    private int snapshotCount = 0;

    // Make sure laptop ip is correct through going to command prompt and typing 'ipconfig'
    private static final String LAPTOP_IP = "192.168.43.9";
    private static final int PORT = 6000;

    private Socket socket;
    private OutputStream out;
    private String connectionStatus = "Disconnected"; // New status tracker

    public void init(HardwareMap hwmap, Telemetry telemetry) {
        limelight = hwmap.get(Limelight3A.class, "limelight");
        limelight.start();
        limelight.pipelineSwitch(0);
        telemetry.addData("Status:", "Initialized. Waiting for Start...");
    }

    public void start() {
        connectionStatus = "Attempting to connect to " + LAPTOP_IP + "...";

        new Thread(() -> {
            try {
                socket = new Socket(LAPTOP_IP, PORT);
                out = socket.getOutputStream();
                connectionStatus = "CONNECTED to Laptop!";
            } catch (Exception e) {
                // If it fails, print the exact reason to the Driver Station
                connectionStatus = "FAILED: " + e.getMessage();
            }
        }).start();
    }

    public void loop(Gamepad gpad, Telemetry telemetry) {
        boolean currentAState = gpad.a;

        if (currentAState && !lastAState) {
            snapshotCount++;
            String snapName = "dataset_snap_" + snapshotCount;

            limelight.captureSnapshot(snapName);

            new Thread(() -> {
                try {
                    if (out != null) {
                        out.write(("SNAP:" + snapName + "\n").getBytes());
                        out.flush();
                    }
                } catch (Exception e) {
                    connectionStatus = "SEND ERROR: " + e.getMessage();
                }
            }).start();
        }

        lastAState = currentAState;

        // Show our new status on the Driver Station
        telemetry.addData("Network:", connectionStatus);
        telemetry.addData("Snapshots Saved:", snapshotCount);
        telemetry.update();
    }

    public void stop() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (Exception e) {
            System.out.println("An error occurred...");
        }
    }
}