package com.example.dotsnsquares.bot;

import com.example.dotsnsquares.domain.BoardState;
import com.example.dotsnsquares.domain.Line;

import java.io.Serializable;

public interface LineSelectionStrategy extends Serializable {
    Line getLine(BoardState boardState);
}
