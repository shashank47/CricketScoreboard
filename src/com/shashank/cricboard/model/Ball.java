package com.shashank.cricboard.model;

import com.shashank.cricboard.model.types.Extras;
import com.shashank.cricboard.model.wicket.Wicket;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public class Ball {
    Player baller;
    Player batsman;
    RunsFromBall runs;
    @Builder.Default
    List<Extras> extras = new ArrayList<>();
    Wicket wicket;
}
