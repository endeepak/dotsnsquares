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
package com.endeepak.dotsnsquares.domain;

import java.io.Serializable;

public interface Player extends Serializable {
    String getName();

    int getColor();

    void play(String token, MoveDecidedEventListener moveDecidedEventListener, BoardState boardState);

    public interface MoveDecidedEventListener {
        void onMoveDecided(MoveDecidedEvent moveDecidedEvent);
    }

    public static class MoveDecidedEvent {
        private final String token;
        private final Player player;
        private final Line line;

        public MoveDecidedEvent(String token, Player player, Line line) {
            this.token = token;
            this.player = player;
            this.line = line;
        }

        public String getToken() {
            return token;
        }

        public Player getPlayer() {
            return player;
        }

        public Line getLine() {
            return line;
        }
    }
}
