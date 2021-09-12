package com.shashank.cricboard.model;

public class RunsFromBall {
    Integer runningRuns;
    boolean four = false;
    boolean six = false;

    public RunsFromBall(boolean four, boolean six) {
        this.runningRuns = null;
        this.four = four;
        this.six = six;
    }

    public RunsFromBall(int runningRuns) {
        this.runningRuns = runningRuns;
    }
}
