package com.endeepak.dotsnsquares.bot;

public class LineSelectionStrategyFactory {
    public static LineSelectionStrategy easy() {
        CompleteSquareAndDecideLineSelectionStrategy completeSquareAndRandomLineSelectionStrategy = new CompleteSquareAndDecideLineSelectionStrategy(new RandomLineSelectionStrategy());
        return new RandomStrategySelectorStrategy(completeSquareAndRandomLineSelectionStrategy, new BruteForceLineSelectionStrategy());
    }

    public static LineSelectionStrategy normal() {
        return new CompleteSquareAndDecideLineSelectionStrategy(new AvoidGivingCompletableLineSelectionStrategy());
    }
}
