package com.endeepak.dotsnsquares.bot;

import com.endeepak.dotsnsquares.domain.BoardState;
import com.endeepak.dotsnsquares.domain.Line;

import java.io.Serializable;

public interface LineSelectionStrategy extends Serializable {
    Line getLine(BoardState boardState);
}
