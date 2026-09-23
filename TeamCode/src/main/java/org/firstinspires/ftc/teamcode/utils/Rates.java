package org.firstinspires.ftc.teamcode.utils;

public class Rates {

    private double centerRate;
    private double maxRate;
    private double expo;
    private double deadzone;

    public Rates(double centerRate, double maxRate, double expo, double deadzone) {
        this.centerRate = centerRate;
        this.maxRate = maxRate;
        this.expo = expo;
        this.deadzone = deadzone;
    }

    /**
     * apply Deadzone prevents robot twitches while the joystick is moving without intent.
     * @param stickPosition
     * @return
     */
    private double applyDeadzone(double stickPosition) {
        if (stickPosition < deadzone) {
            return 0.0;
        }

        return (stickPosition - deadzone) / (1 - deadzone);
    }

    /**
     *
     * @param stickPosition A parameter read from a controller that should be in the domain {0..1}
     * @return A power value representative of what we want to hand to a Drive train / motors
     */
    public double apply(double stickPosition) {
        stickPosition = applyDeadzone(stickPosition);
        double actualRatesExpoFactor =
                stickPosition * (Math.pow(stickPosition, 5) * expo + stickPosition * (1-expo));
        return (centerRate * stickPosition) +
                ((maxRate - centerRate) * actualRatesExpoFactor);
    }

}
