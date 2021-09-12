package com.shashank.cricboard.model;

public class MatchResult {
    Team winner;
    WinningMargin winningMargin;

    public MatchResult(Team winner, WinningMargin winningMargin) {
        this.winner = winner;
        this.winningMargin = winningMargin;
    }

    @Override
    public String toString() {
        return winner.getName() + " won by " + winningMargin;
    }
}
