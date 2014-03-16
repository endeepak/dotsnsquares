package com.endeepak.dotsnsquares.domain;

public enum Opponent {
    HumanOnSameDevice("human", false),
    EasyBot("bot (easy)", true),
    NormalBot("bot (normal)", true),
    HardBot("bot (hard)", true),
    ProBot("bot (pro)", true);

    private String title;
    private boolean bot;

    Opponent(String title, boolean isBot) {
        this.title = title;
        bot = isBot;
    }

    @Override
    public String toString() {
        return title;
    }

    public boolean isBot() {
        return bot;
    }
}
