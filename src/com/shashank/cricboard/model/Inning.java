package com.shashank.cricboard.model;

import com.shashank.cricboard.model.types.BatsmanStatus;
import com.shashank.cricboard.model.types.Extras;
import com.shashank.cricboard.model.types.InningStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.shashank.cricboard.Constants.LINE_SEPARATOR;

public class Inning {
    final Team battingTeam;
    final List<BatsmanScore> batsmanScores = new ArrayList<>();
    Player onStrike = null;
    Player otherEnd = null;
    Integer numberOfBalls = null;
    List<Extras> extras = new ArrayList<>();
    Integer totalRuns = null;
    Integer targetRuns = null;
    InningStatus status = InningStatus.YET_TO_PLAY;
    final HashMap<Player, BatsmanScore> playerScoreMapping = new HashMap<>();

    public void setMaxNumOfOvers(Integer maxNumOfOvers) {
        this.maxNumOfOvers = maxNumOfOvers;
    }

    Integer maxNumOfOvers = null;

    public Inning(Team battingTeam, List<Player> players) {
        this.battingTeam = battingTeam;
        for (Player player : players) {
            BatsmanScore batsmanScore = new BatsmanScore(player);
            batsmanScores.add(batsmanScore);
            playerScoreMapping.put(player, batsmanScore);
        }
    }

    public void beginInning() {
        onStrike = batsmanScores.get(0).player;
        otherEnd = batsmanScores.get(1).player;
        batsmanScores.get(0).startBatting();
        batsmanScores.get(1).startBatting();
        numberOfBalls = 0;
        totalRuns = 0;
        status = InningStatus.IN_PROGRESS;
    }

    public void beginInning(int target) {
        beginInning();
        targetRuns = target;
    }

    public synchronized void processBall(Ball ball) {
        ball.batsman = onStrike;
        RunsFromBall runs = ball.runs;
        if (runs != null) {
            playerScoreMapping.get(onStrike).appendRuns(runs);
            if (runs.runningRuns != null && runs.runningRuns % 2 != 0) swapStrike();
            appendRuns(runs);
        }
        if (!ball.extras.isEmpty()) {
            this.extras.addAll(ball.extras);
            this.totalRuns += ball.extras.size();
        }
        if (ball.wicket != null) {
            if (ball.wicket.batsman == null) ball.wicket.batsman = onStrike;
            playerScoreMapping.get(ball.wicket.batsman).setWicket(ball.wicket);
            Optional<BatsmanScore> nextBatsmanOptional = batsmanScores.stream()
                    .filter(it -> it.status == BatsmanStatus.YET_TO_PLAY).findFirst();
            if (nextBatsmanOptional.isPresent()) {
                BatsmanScore nextBatsman = nextBatsmanOptional.get();
                nextBatsman.status = BatsmanStatus.PLAYING;
                if (ball.wicket.batsman == onStrike) {
                    onStrike = nextBatsman.player;
                } else otherEnd = nextBatsman.player;
            } else {
                endInning();
            }
        }
        if (ball.extras.isEmpty()) {
            incrementBalls();
        }
        if (targetRuns != null && targetRuns <= totalRuns) endInning();
    }

    private void appendRuns(RunsFromBall runs) {
        if (runs.four) totalRuns += 4;
        else if (runs.six) totalRuns += 6;
        else if (runs.runningRuns != null) totalRuns += runs.runningRuns;
    }

    private void incrementBalls() {
        numberOfBalls++;
        playerScoreMapping.get(onStrike).balls++;
        if (numberOfBalls % 6 == 0) {
            if (numberOfBalls / 6 == maxNumOfOvers) endInning();
            else swapStrike();
        }
    }

    private void swapStrike() {
        Player temp = onStrike;
        onStrike = otherEnd;
        otherEnd = temp;
    }

    private void endInning() {
        status = InningStatus.COMPLETED;
    }

    private String getRunsString(BatsmanScore batsmanScore) {
        return "  " + batsmanScore.runs + " " + batsmanScore.balls + " " + batsmanScore.fours + " " + batsmanScore.sixes;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Batting Team : ").append(battingTeam.getName()).append(LINE_SEPARATOR);
        for (BatsmanScore batsmanScore : batsmanScores) {
            stringBuilder.append(batsmanScore.player.getName());
            switch (batsmanScore.status) {
                case YET_TO_PLAY:
                    stringBuilder.append(" YET TO PLAY");
                    break;
                case OUT:
                    stringBuilder.append(getRunsString(batsmanScore));
                    stringBuilder.append(LINE_SEPARATOR).append(batsmanScore.wicket);
                    break;
                case PLAYING:
                    stringBuilder.append("*");
                    stringBuilder.append(getRunsString(batsmanScore));
                    break;
                case RETIRED_HURT:
                    stringBuilder.append(getRunsString(batsmanScore));
                    stringBuilder.append(LINE_SEPARATOR).append(" RETIRED HURT");
                default:
            }
            stringBuilder.append(LINE_SEPARATOR);
        }
        if (numberOfBalls != null) {
            stringBuilder.append("EXTRAS : ").append(extras.size());
            if (!extras.isEmpty()) {
                stringBuilder.append("(");
                long wides = extras.stream().filter(it -> it == Extras.WIDE).count();
                if (wides > 0) stringBuilder.append("W ").append(wides).append(" ");
                long noballs = extras.stream().filter(it -> it == Extras.NOBALL).count();
                if (noballs > 0) stringBuilder.append("NB ").append(noballs).append(" ");
                long byes = extras.stream().filter(it -> it == Extras.BYE).count();
                if (byes > 0) stringBuilder.append("B ").append(noballs).append(" ");
                stringBuilder.append(")");
            }
            stringBuilder.append(LINE_SEPARATOR);
            stringBuilder.append("Total runs ").append(totalRuns)
                    .append("(")
                    .append(getWickets()).append(" wickets, ")
                    .append(getOvers(numberOfBalls)).append(" overs")
                    .append(")");
            stringBuilder.append(LINE_SEPARATOR);
        }
        stringBuilder.append(LINE_SEPARATOR);
        return stringBuilder.toString();
    }

    private String getOvers(Integer numberOfBalls) {
        return numberOfBalls / 6 + "." + numberOfBalls % 6;
    }

    public long getWickets() {
        return batsmanScores.stream().filter(it -> it.wicket != null).count();
    }
}
