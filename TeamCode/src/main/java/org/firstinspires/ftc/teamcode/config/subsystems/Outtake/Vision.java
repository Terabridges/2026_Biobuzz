package org.firstinspires.ftc.teamcode.config.subsystems.Outtake;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.config.subsystems.Subsystem;
import org.psilynx.psikit.core.Logger;

public class Vision implements Subsystem {
    //----------Hardware----------
    private Limelight3A limelight;

    //----------Software----------
    public LLResult latest;
    public int currentPipline = 0;

    //-------Constructor-------
    public Vision(HardwareMap map) {}


    //-------Methods---------
    private void limelightInit() {
        limelight.setPollRateHz(100);
        limelight.pipelineSwitch(currentPipline);
        limelight.start();
    }

    private void limelightUpdate() {
        latest = limelight.getLatestResult();
    }
    public void Relocalizer(int detectedId) {
        
        switch (detectedId) {

        }
    }


    //---------------- Interface Methods ----------------
    @Override
    public void toInit(){
        limelightInit();
    }

    @Override
    public void update(){
        limelightUpdate();
    }

    @Override
    public void logPsiKitData() {

    }

}
