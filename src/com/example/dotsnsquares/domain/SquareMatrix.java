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

public class SquareMatrix {
    private int size;
    private int[][] horizontalLineIndexes;
    private int[][] verticalLineIndexes;
    private Line[] lines;

    public SquareMatrix(int size) {
        this.size = size;
        this.horizontalLineIndexes = new int[size + 1][size];
        this.verticalLineIndexes = new int[size][size + 1];
        this.lines =  new Line[2 * size * (size + 1)];
        for (int row = 0; row <= size ; row++) {
            for (int column = 0; column < size ; column ++) {
                int horizontalLineIndex = getHorizontalLineIndex(row, column);
                horizontalLineIndexes[row][column] = horizontalLineIndex;
                lines[horizontalLineIndex] = new Line(new DotPosition(row, column), new DotPosition(row, column +1));
            }
        }
        for (int column = 0; column <= size ; column++) {
            for (int row = 0; row < size ; row ++) {
                int verticalLineIndex = getVerticalLineIndex(row, column);
                verticalLineIndexes[row][column] = verticalLineIndex;
                lines[verticalLineIndex] = new Line(new DotPosition(row, column), new DotPosition(row +1, column));
            }
        }
    }

    public int getVerticalLineIndex(int row, int column) {
        return size + (row * (2 * size + 1)) + column;
    }

    public int getHorizontalLineIndex(int row, int column) {
        return row * (2 * size + 1) + column;
    }

    public Line getLine(int lineIndex) {
        return lines[lineIndex];
    }
}
