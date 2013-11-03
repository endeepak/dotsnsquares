package com.example.dotnsquares.domain;

import android.graphics.Point;

public class LinePath {
    private final Point startingPoint;
    private final Point endPoint;
    private final int dx;
    private final int dy;
    private final Direction direction;

    public LinePath(Point startingPoint, Point endPoint, int dx, int dy, Direction direction) {
        this.startingPoint = startingPoint;
        this.endPoint = endPoint;
        this.dx = dx;
        this.dy = dy;
        this.direction = direction;
    }

    public static LinePath create(Point startingPoint, Point endPoint) {
        int dx = endPoint.x - startingPoint.x;
        int dy = endPoint.y - startingPoint.y;
        return new LinePath(startingPoint, endPoint, dx, dy, getDirection(dx, dy));
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

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public Direction getDirection() {
        return direction;
    }

    public DirectionType getDirectionType() {
        return direction.directionType;
    }

    public Point getPointBasedOnDirection(float x, float y) {
        float xBasedOnDirection;
        float yBasedOnDirection;
        if (this.isHorizontal()) {
            xBasedOnDirection = x;
            yBasedOnDirection = startingPoint.y;
        } else if (this.isVertical()) {
            xBasedOnDirection = startingPoint.x;
            yBasedOnDirection = y;
        } else {
            xBasedOnDirection = x;
            yBasedOnDirection = y;
        }
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
