package com.shashank.cricboard.model.wicket;

import com.shashank.cricboard.model.Player;

public class CatchOut extends Wicket {
    Player bowler;
    Player catcher;

    public CatchOut(Player bowler, Player catcher) {
        this.bowler = bowler;
        this.catcher = catcher;
    }

    @Override
    public String toString() {
        return "Lbw " +
                "c " + catcher.getName() +
                " b " + bowler.getName();
    }
}
