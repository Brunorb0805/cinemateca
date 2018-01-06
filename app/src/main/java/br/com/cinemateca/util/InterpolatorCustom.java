package br.com.cinemateca.util;

/**
 * Created by brunorb on 05/01/2018.
 */

public class InterpolatorCustom implements android.view.animation.Interpolator
{
    private double mAmplitude = 1;
    private double mFrequency = 10;

    public InterpolatorCustom(double amplitude, double frequency) {
        mAmplitude = amplitude;
        mFrequency = frequency;
    }

    @Override
    public float getInterpolation(float time) {
        return (float) (-1 * Math.pow(Math.E, -time/ mAmplitude) * Math.cos(mFrequency * time) + 1);
    }
}