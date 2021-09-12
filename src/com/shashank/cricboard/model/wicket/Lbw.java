package com.shashank.cricboard.model.wicket;

import com.shashank.cricboard.model.Player;

public class Lbw extends Wicket {
    Player bowler;

    public Lbw(Player bowler) {
        this.bowler = bowler;
    }

    @Override
    public String toString() {
        return "Lbw " +
                "b " + bowler.getName();
    }
}
