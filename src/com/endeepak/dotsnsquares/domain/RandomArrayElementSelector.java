package com.endeepak.dotsnsquares.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

public class RandomArrayElementSelector implements Serializable {
    private transient final Random random = new Random();

    public <T> T getNext(List<T> elements) {
        int size = elements.size();
        int index = size <= 1 ? 0 : random.nextInt(size - 1);
        return elements.get(index);
    }
}
