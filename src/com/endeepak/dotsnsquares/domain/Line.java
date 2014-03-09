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

    @Override
    public String toString() {
        return startingDotPosition + "->" + endDotPosition ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Line line = (Line) o;

        if (endDotPosition != null ? !endDotPosition.equals(line.endDotPosition) : line.endDotPosition != null)
            return false;
        if (startingDotPosition != null ? !startingDotPosition.equals(line.startingDotPosition) : line.startingDotPosition != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = startingDotPosition != null ? startingDotPosition.hashCode() : 0;
        result = 31 * result + (endDotPosition != null ? endDotPosition.hashCode() : 0);
        return result;
    }
}
