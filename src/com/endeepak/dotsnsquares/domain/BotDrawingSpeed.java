package com.endeepak.dotsnsquares.domain;

import java.util.ArrayList;

public enum BotDrawingSpeed {
    Slow(2000),
    Normal(1000),
    Fast(500),
    Bolt(1);

    private int animationTime;

    BotDrawingSpeed(int animationTime) {
        this.animationTime = animationTime;
    }

    public int getAnimationTime() {
        return animationTime;
    }

}
