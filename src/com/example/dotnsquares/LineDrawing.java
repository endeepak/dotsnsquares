package com.example.dotnsquares;

import android.graphics.Point;

public class LineDrawing {
    private Point startingDot;
    private Point endDot;
    private Point currentPoint;

    public void startFrom(Point startingDot) {
        this.startingDot = startingDot;
        this.currentPoint = startingDot;
    }

    public boolean isCompleted() {
        return startingDot != null && endDot != null && startingDot != endDot;
    }

    public void moveTo(Point currentPoint) {
        this.currentPoint = currentPoint;
    }

    public void endAt(Point endDot) {
        this.endDot = endDot;
    }

    public void reset() {
        startingDot = null;
        endDot = null;
        currentPoint = null;
    }

    public Point getStartingDot() {
        return startingDot;
    }

    public Point getCurrentPoint() {
        return currentPoint;
    }

    public Point getEndDot() {
        return endDot;
    }

    public boolean isStarted() {
        return startingDot != null;
    }
}
