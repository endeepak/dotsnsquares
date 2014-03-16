package com.endeepak.dotsnsquares.domain;

public enum PlayerTurn {
    Player1First("Player 1") {
        @Override
        public int getPlayerIndex() {
            return 0;
        }
    },
    Player2First("Player 2") {
        @Override
        public int getPlayerIndex() {
            return 1;
        }
    },
    Random("Random Player") {
        @Override
        public int getPlayerIndex() {
            return new java.util.Random().nextInt(2);
        }
    };

    private String title;

    PlayerTurn(String title) {
        this.title = title;
    }

    abstract public int getPlayerIndex();

    @Override
    public String toString() {
        return title;
    }
}

