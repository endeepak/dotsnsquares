package com.example.dotnsquares.domain;

import java.io.Serializable;

public class HumanParticipant implements Participant, Serializable {
    private String name;

    public HumanParticipant(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
