package com.shashank.cricboard.model;

public class Player {
    private int id;

    public String getName() {
        return name;
    }

    private String name;
    private Team team;

    public Player(int id, String name, Team team) {
        this.id = id;
        this.name = name;
        this.team = team;
    }
}
