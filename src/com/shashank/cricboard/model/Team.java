package com.shashank.cricboard.model;

public class Team {
    private String name;

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    private String symbol;

    public Team(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }
}
