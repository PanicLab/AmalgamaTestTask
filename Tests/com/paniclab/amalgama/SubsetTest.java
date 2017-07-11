package com.paniclab.amalgama;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by root on 11.07.2017.
 */
public class SubsetTest {

    @Test
    public void creation_builderIsEmpty_returnSubsetEmpty() throws Exception {
        Subset subset = Subset.builder().create();
        assertTrue(subset == Subset.EMPTY);
    }

    @Test
    public void creation() throws Exception {
        Point x1 = Point.NEGATIVE_INFINITY;
        Point y1 = Point.valueOf(-100);
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(-50);
        Point y2 = Point.valueOf(10);
        Interval intervalTwo = Interval.between(x2, y2);

        Point x3 = Point.valueOf(400);
        Point y3 = Point.POSITIVE_INFINITY;
        Interval intervalThree = Interval.between(x3, y3);

        Subset subset = Subset.builder()
                                .addInterval(intervalOne)
                                .addInterval(intervalTwo)
                                .addInterval(intervalThree)
                                .create();
        System.out.println(subset.toString());
    }
}