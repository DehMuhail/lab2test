package com.laba2;

import generated.Orangery;

import java.util.Comparator;

public class FlowerComparator implements Comparator<Orangery.Flower> {
    @Override
    public int compare(Orangery.Flower f1, Orangery.Flower f2) {
        return f1.getName().compareTo(f2.getName());
    }
}
