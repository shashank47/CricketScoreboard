package com.shashank.cricboard;

import com.shashank.cricboard.model.Ball;
import com.shashank.cricboard.model.Inning;
import com.shashank.cricboard.model.Match;
import com.shashank.cricboard.model.Player;
import com.shashank.cricboard.model.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CricketScoreboardService {

    public Match initializeMatch(Team team1, Team team2, int numOvers) {
        final List<Team> teams = new ArrayList<>();
        teams.add(team1);
        teams.add(team2);
        return new Match(teams, numOvers);
    }

    public void beginMatch(Match match, Map<Team, List<Player>> players) {
        Team tossWinner = doToss(match);
        // We assume toss winner bats.
        Inning firstInning = new Inning(tossWinner, players.get(tossWinner));
        Team tossLooser = match.getTeams().get(0) != tossWinner ? match.getTeams().get(0) : match.getTeams().get(1);
        Inning secondInning = new Inning(tossLooser, players.get(tossLooser));
        List<Inning> innings = new ArrayList<>();
        innings.add(firstInning);
        innings.add(secondInning);
        match.beginMatch(innings);
    }

    private Team doToss(Match match) {
        Random random = new Random();
        return match.getTeams().get(random.nextInt(1));
    }

    public void processBall(Match match, Ball ball) {
        synchronized (match) {
            match.processBall(ball);
        }
    }
}
