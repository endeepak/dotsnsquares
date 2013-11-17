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

import android.graphics.Color;

import java.io.Serializable;

public class GameOptions implements Serializable {
    public static final String DEFAULT_PLAYER1_NAME = "Player 1";
    public static final String DEFAULT_PLAYER2_NAME = "Player 2";
    public static final int DEFAULT_PLAYER1_COLOR = Color.parseColor("#D7E6B1");
    public static final int DEFAULT_PLAYER2_COLOR = Color.parseColor("#0AC9B0");
    private BoardSize boardSize;
    private Player player1;
    private Player player2;

    public GameOptions(BoardSize boardSize) {
        this.boardSize = boardSize;
        this.player1 = new Player(new HumanParticipant(DEFAULT_PLAYER1_NAME), DEFAULT_PLAYER1_COLOR);
        this.player2 = new Player(new HumanParticipant(DEFAULT_PLAYER2_NAME), DEFAULT_PLAYER2_COLOR);
    }

    public void setBoardSize(BoardSize boardSize) {
        this.boardSize = boardSize;
    }

    public BoardSize getBoardSize() {
        return boardSize;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getPlayer2() {
        return player2;
    }
}
