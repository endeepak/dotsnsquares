package com.endeepak.dotsnsquares.domain;

import android.graphics.Point;

public class LineDrawing {
    private Dot startingDot;
    private Dot endDot;
    private Point currentPoint;

    public void startFrom(Dot startingDot) {
        this.startingDot = startingDot;
        this.currentPoint = startingDot == null ? null : startingDot.getPoint();
    }

    public boolean isCompleted() {
        return startingDot != null && endDot != null && startingDot != endDot;
    }

    public void moveTo(Point currentPoint) {
        this.currentPoint = currentPoint;
    }

    public void endAt(Dot endDot) {
        this.endDot = endDot;
    }

    public void reset() {
        startingDot = null;
        endDot = null;
        currentPoint = null;
    }

    public Dot getStartingDot() {
        return startingDot;
    }

    public Point getCurrentPoint() {
        return currentPoint;
    }

    public Dot getEndDot() {
        return endDot;
    }

    public boolean isStarted() {
        return startingDot != null;
    }
}
