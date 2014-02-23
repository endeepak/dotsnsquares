package com.endeepak.dotsnsquares.domain;

import android.graphics.Point;

public class LinePath {
    private final Point startingPoint;
    private final Point endPoint;
    private final Direction direction;

    public LinePath(Point startingPoint, Point endPoint, Direction direction) {
        this.startingPoint = startingPoint;
        this.endPoint = endPoint;
        this.direction = direction;
    }

    public static LinePath create(Point startingPoint, Point endPoint) {
        int dx = endPoint.x - startingPoint.x;
        int dy = endPoint.y - startingPoint.y;
        return new LinePath(startingPoint, endPoint, getDirection(dx, dy));
    }

    private static Direction getDirection(int dx, int dy) {
        if(Math.abs(dx) > Math.abs(dy)) {
            return dx > 0 ? Direction.LeftToRight : Direction.RightToLeft;
        } else {
            return dy > 0 ? Direction.TopToBottom : Direction.BottomToTop;
        }
    }

    public boolean isHorizontal() {
        return direction.orientationType == OrientationType.Horizontal;
    }

    public boolean isVertical() {
        return direction.orientationType == OrientationType.Vertical;
    }

    public Direction getDirection() {
        return direction;
    }

    public DirectionType getDirectionType() {
        return direction.directionType;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public Point getPointBasedOnDirection(float x, float y) {
        float xBasedOnDirection = this.isHorizontal() ? x : startingPoint.x;
        float yBasedOnDirection = this.isVertical() ? y : startingPoint.y;
        return new Point((int) xBasedOnDirection, (int) yBasedOnDirection);
    }

    enum Direction {
        LeftToRight(OrientationType.Horizontal, DirectionType.Forward),
        RightToLeft(OrientationType.Horizontal, DirectionType.Backward),
        TopToBottom(OrientationType.Vertical, DirectionType.Forward),
        BottomToTop(OrientationType.Vertical, DirectionType.Backward);
        private final OrientationType orientationType;
        private final DirectionType directionType;

        Direction(OrientationType orientationType, DirectionType directionType) {
            this.orientationType = orientationType;
            this.directionType = directionType;
        }
    }

    enum OrientationType {
        Horizontal,
        Vertical
    }

    enum DirectionType {
        Forward,
        Backward
    }
}
