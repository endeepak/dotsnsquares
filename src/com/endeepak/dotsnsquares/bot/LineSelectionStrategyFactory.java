package com.endeepak.dotsnsquares.bot;

import com.endeepak.dotsnsquares.bot.LineSelectionStep.AvoidOpponentSquareCompletion;
import com.endeepak.dotsnsquares.bot.LineSelectionStep.FirstAvailableLineSelection;
import com.endeepak.dotsnsquares.bot.LineSelectionStep.ProvideLineFromMinimumSquaresBlock;
import com.endeepak.dotsnsquares.bot.LineSelectionStep.RandomLineSelection;
import com.endeepak.dotsnsquares.bot.LineSelectionStep.TrySquareCompletion;

public class LineSelectionStrategyFactory {

    public static LineSelectionStrategy easy() {
        return new RandomStrategySelectorStrategy(new StepWiseLineSelectionStrategy(new TrySquareCompletion(), new RandomLineSelection()), new FirstAvailableLineSelection());
    }

    public static LineSelectionStrategy normal() {
        return new StepWiseLineSelectionStrategy(new TrySquareCompletion(), new AvoidOpponentSquareCompletion(), new RandomLineSelection());
    }

    public static LineSelectionStrategy hard() {
        return new StepWiseLineSelectionStrategy(new TrySquareCompletion(), new AvoidOpponentSquareCompletion(), new ProvideLineFromMinimumSquaresBlock());
    }
}
