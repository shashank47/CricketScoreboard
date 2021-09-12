package com.shashank.cricboard.model.wicket;

import com.shashank.cricboard.model.Player;

public class Runout extends Wicket {
    Player fielder;

    public Runout(Player batsman, Player fielder) {
        this.fielder = fielder;
        this.batsman = batsman;
    }

    @Override
    public String toString() {
        return "run out (" + fielder.getName() + ")";
    }
}
