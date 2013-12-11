/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package com.example.dotsnsquares.domain;

public class BotPlayer implements Player {
    private final LineSelectionStrategy lineSelectionStrategy = new BruteForceLineSelectionStrategy();
    private String name;
    private int color;

    public BotPlayer(String name, int color) {
        this.name = name;
        this.color = color;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public void play(String token, MoveDecidedEventListener moveDecidedEventListener, BoardState boardState) {
        moveDecidedEventListener.onMoveDecided(new MoveDecidedEvent(token, this, lineSelectionStrategy.getLine(boardState)));
    }
}
