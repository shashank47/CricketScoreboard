package com.shashank.cricboard.model;

import com.shashank.cricboard.model.types.BatsmanStatus;
import com.shashank.cricboard.model.wicket.Wicket;

public class BatsmanScore {
    public Player getPlayer() {
        return player;
    }

    Player player;
    int runs = 0;
    int balls = 0;
    int fours = 0;
    int sixes = 0;
    BatsmanStatus status = BatsmanStatus.YET_TO_PLAY;

    public void setWicket(Wicket wicket) {
        this.wicket = wicket;
        status = BatsmanStatus.OUT;
    }

    Wicket wicket = null;

    public BatsmanScore(Player player) {
        this.player = player;
    }

    public void startBatting() {
        status = BatsmanStatus.PLAYING;
    }

    public void appendRuns(RunsFromBall runsFromBall) {
        if (runsFromBall.four) {
            runs += 4;
            fours++;
        } else if (runsFromBall.six) {
            runs += 6;
            sixes++;
        } else runs += runsFromBall.runningRuns;
    }
}
