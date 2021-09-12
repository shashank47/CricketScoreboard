package com.shashank.cricboard.model;

import com.shashank.cricboard.model.types.InningStatus;

import java.util.List;

import static com.shashank.cricboard.Constants.LINE_SEPARATOR;

public class Match {

    List<Team> teams;
    List<Inning> innings;
    MatchStatus status = MatchStatus.SCHEDULED;
    MatchResult result = null;
    int numOversPerInning;

    public Match(List<Team> teams, int numOversPerInning) {
        this.teams = teams;
        this.numOversPerInning = numOversPerInning;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void beginMatch(List<Inning> innings) {
        for (Inning inning : innings) {
            inning.setMaxNumOfOvers(numOversPerInning);
        }
        this.innings = innings;
        status = MatchStatus.IN_PROGRESS;
        innings.get(0).beginInning();
    }

    public String getScoreboard() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < innings.size(); i++) {
            stringBuilder.append(" Inning ").append(i + 1)
                    .append(LINE_SEPARATOR)
                    .append(innings.get(i).toString());
        }
        if (status == MatchStatus.COMPLETED) {
            stringBuilder.append(result);
        }
        return stringBuilder.toString();
    }

    public synchronized void processBall(Ball ball) {
        for (Inning inning : innings) {
            if (inning.status == InningStatus.IN_PROGRESS) {
                inning.processBall(ball);
                if (inning.status == InningStatus.COMPLETED) {
                    if (innings.indexOf(inning) == 0) {
                        innings.get(1).beginInning(inning.totalRuns);
                    } else {
                        endMatch();
                    }
                    return;
                }
            }
        }
    }

    private void endMatch() {
        status = MatchStatus.COMPLETED;
        Inning firstInning = innings.get(0);
        Inning secondInning = innings.get(1);
        if (firstInning.totalRuns > secondInning.totalRuns) {
            result = new MatchResult(firstInning.battingTeam,
                    new WinningMargin(firstInning.totalRuns - secondInning.totalRuns, null));
        } else {
            result = new MatchResult(secondInning.battingTeam,
                    new WinningMargin(null,
                            Math.toIntExact(secondInning.batsmanScores.size() - 1 - secondInning.getWickets())));
        }
    }
}
