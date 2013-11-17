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

import java.io.Serializable;
import java.util.ArrayList;

public class BoardSize implements Serializable {
    private int size;

    public BoardSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return String.format("%s x %s", size, size);
    }

    public static ArrayList<BoardSize> fromSizes(int... sizes) {
        ArrayList<BoardSize> options = new ArrayList<BoardSize>();
        for (int size : sizes) {
            options.add(new BoardSize(size));
        }
        return options;
    }

    public int getSize() {
        return size;
    }
}
