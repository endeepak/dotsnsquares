package com.endeepak.dotsnsquares.domain;

import java.io.Serializable;

public interface Player extends Serializable {
    String getName();
    int getColor();
    void play(BoardState boardState);
    void stop();
}
