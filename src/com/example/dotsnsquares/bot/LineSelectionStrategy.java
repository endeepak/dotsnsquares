package com.example.dotsnsquares.bot;

import com.example.dotsnsquares.domain.BoardState;
import com.example.dotsnsquares.domain.Line;

public interface LineSelectionStrategy {
    Line getLine(BoardState boardState);
}
