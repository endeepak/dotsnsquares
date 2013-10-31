package com.example.dotnsquares;

import android.graphics.Point;

public class TouchSwipe {
    private final Point startingPoint;
    private final Point endPoint;
    private final Direction direction;

    public TouchSwipe(Point startingPoint, Point endPoint, Direction direction) {
        this.startingPoint = startingPoint;
        this.endPoint = endPoint;
        this.direction = direction;
    }

    public static TouchSwipe create(Point startingPoint, Point endPoint) {
        Direction direction = getDirection(startingPoint, endPoint);
        return new TouchSwipe(startingPoint, endPoint, direction);
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
        return direction.type == DirectionType.Horizontal;
    }

    public boolean isVertical() {
        return direction.type == DirectionType.Vertical;
    }

    enum Direction {
        LeftToRight(DirectionType.Horizontal),
        RightToLeft(DirectionType.Horizontal),
        TopToBottom(DirectionType.Vertical),
        BottomToTop(DirectionType.Vertical);
        private DirectionType type;

        Direction(DirectionType type) {
            this.type = type;
        }
    }

    enum DirectionType {
        Horizontal,
        Vertical
    }
}
