package com.example.dotnsquares;

import android.graphics.Point;

public class LinearPath {
    private final Point startingPoint;
    private final Point endPoint;
    private final Direction direction;

    public LinearPath(Point startingPoint, Point endPoint, Direction direction) {
        this.startingPoint = startingPoint;
        this.endPoint = endPoint;
        this.direction = direction;
    }

    public static LinearPath create(Point startingPoint, Point endPoint) {
        Direction direction = getDirection(startingPoint, endPoint);
        return new LinearPath(startingPoint, endPoint, direction);
    }

    private static Direction getDirection(Point startingPoint, Point endPoint) {
        int dx = endPoint.x - startingPoint.x;
        int dy = endPoint.y - startingPoint.y;

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

    public DirectionType getDirectionType() {
        return direction.directionType;
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
