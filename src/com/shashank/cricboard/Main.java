package com.shashank.cricboard;

import com.shashank.cricboard.model.Ball;
import com.shashank.cricboard.model.Match;
import com.shashank.cricboard.model.Player;
import com.shashank.cricboard.model.RunsFromBall;
import com.shashank.cricboard.model.Team;
import com.shashank.cricboard.model.types.Extras;
import com.shashank.cricboard.model.wicket.CatchOut;
import com.shashank.cricboard.model.wicket.Lbw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String... s) {
        Team india = new Team("INDIA", "IND");
        Team england = new Team("ENGLAND", "ENG");

        Player dhoni = new Player(1, "MS Dhoni", india);
        Player kohli = new Player(2, "Virat Kohli", india);
        Player rSharma = new Player(3, "Rohit Sharma", india);
        final List<Player> indianPlayers = new ArrayList<>();
        indianPlayers.add(dhoni);
        indianPlayers.add(kohli);
        indianPlayers.add(rSharma);
        Player stokes = new Player(4, "Ben Stokes", england);
        Player morgan = new Player(5, "Eoin Morgan", england);
        Player buttler = new Player(6, "Jos Buttler", england);
        final List<Player> engPlayers = new ArrayList<>();
        engPlayers.add(stokes);
        engPlayers.add(morgan);
        engPlayers.add(buttler);
        final Map<Team, List<Player>> players = new HashMap<>();
        players.put(india, indianPlayers);
        players.put(england, engPlayers);

        CricketScoreboardService cricService = new CricketScoreboardService();
        Match match = cricService.initializeMatch(india, england, 2);
        cricService.beginMatch(match, players);
        cricService.processBall(match, Ball.builder().baller(stokes).runs(new RunsFromBall(2)).build());
        cricService.processBall(match, Ball.builder().baller(stokes).runs(new RunsFromBall(1)).build());
        cricService.processBall(match, Ball.builder().baller(stokes).runs(new RunsFromBall(1)).build());
        cricService.processBall(match, Ball.builder().baller(stokes).runs(new RunsFromBall(true,false)).build());
        cricService.processBall(match, Ball.builder().baller(stokes).runs(new RunsFromBall(false,true)).build());
        cricService.processBall(match, Ball.builder().baller(stokes)
                .extras(Collections.singletonList(Extras.NOBALL)).build());
        cricService.processBall(match, Ball.builder().baller(stokes).runs(new RunsFromBall(2)).build());

        cricService.processBall(match, Ball.builder().baller(morgan).runs(new RunsFromBall(2)).build());
        cricService.processBall(match, Ball.builder().baller(morgan).wicket(new CatchOut(morgan, stokes)).build());
        cricService.processBall(match, Ball.builder().baller(morgan).wicket(new Lbw(morgan)).build());

        cricService.processBall(match, Ball.builder().baller(rSharma).runs(new RunsFromBall(2)).build());
        cricService.processBall(match, Ball.builder().baller(rSharma).runs(new RunsFromBall(false,true)).build());
        cricService.processBall(match, Ball.builder().baller(rSharma).runs(new RunsFromBall(false,true)).build());
        cricService.processBall(match, Ball.builder().baller(rSharma).runs(new RunsFromBall(false,true)).build());
        System.out.println(match.getScoreboard());
    }
}
