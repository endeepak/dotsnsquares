package com.endeepak.dotsnsquares.domain;

import android.graphics.Point;

public class Circle {
    private final Point center;
    private final int radius;

    public Circle(Point center, int radius) {
        this.center = center;
        this.radius = radius;
    }

    public boolean contains(Point point) {
        return Math.pow(point.x - center.x, 2) + Math.pow(point.y - center.y, 2) <= Math.pow(radius, 2);
    }

    public static Circle with(Point center, int radius) {
        return new Circle(center, radius);
    }
}
