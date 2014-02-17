package com.endeepak.dotsnsquares.domain;

import java.io.Serializable;

public class Line implements Serializable {
    private final DotPosition startingDotPosition;
    private final DotPosition endDotPosition;

    public Line(DotPosition startingDotPosition, DotPosition endDotPosition) {
        this.startingDotPosition = startingDotPosition;
        this.endDotPosition = endDotPosition;
    }

    public DotPosition getStartingDotPosition() {
        return startingDotPosition;
    }

    public DotPosition getEndDotPosition() {
        return endDotPosition;
    }
}
