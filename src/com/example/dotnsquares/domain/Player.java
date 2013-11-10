package com.example.dotnsquares.domain;

import java.io.Serializable;

public class Player implements Serializable {
    private final Participant participant;
    private final int color;

    public Player(Participant participant, int color) {
        this.participant = participant;
        this.color = color;
    }

    public String getName() {
        return participant.getName();
    }

    public int getColor() {
        return color;
    }
}
