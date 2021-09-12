package com.shashank.cricboard.model;

public class WinningMargin {
    public WinningMargin(Integer runs, Integer wickets) {
        this.runs = runs;
        this.wickets = wickets;
    }

    Integer runs;
    Integer wickets;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" by ");
        if (runs == null) stringBuilder.append(wickets).append(" wickets.");
        else stringBuilder.append(runs).append(" runs.");
        return stringBuilder.toString();
    }
}
