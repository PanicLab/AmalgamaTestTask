package com.paniclab.amalgama;


import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Created by Сергей on 14.07.2017.
 */
public class FinalTest {

    static final Point NEGATIVE_INFINITY = Point.NEGATIVE_INFINITY;
    static final Point POSITIVE_INFINITY = Point.POSITIVE_INFINITY;



    @Test
    public void finalPackageTest() throws Exception {

        Superposition resolver = Superposition.builder()
                                                .add(getSubset_1())
                                                .add(getSubset_2())
                                                .add(getSubset_3())
                                                .add(getSubset_4())
                                                .add(getSubset_5())
                                                .build();

        assertEquals(getExpected(), resolver.resolve());
        assertEquals(getExpected().getIntervalList(), resolver.resolve().getIntervalList());
    }

    static Subset getSubset_1() {
        Point p12 = Point.valueOf(-2000);
        Interval interval1 = Interval.between(NEGATIVE_INFINITY, p12);

        Interval interval2 = Interval.between(0, 2000);
        Interval interval3 = Interval.between(5000, 8000);
        Interval interval4 = Interval.between(25000, 30000);
        Interval interval5 = Interval.between(50000, 200000);

        return Subset.builder().add(interval1)
                                .add(interval2)
                                .add(interval3)
                                .add(interval4)
                                .add(interval5)
                                .create();
    }

    static Subset getSubset_2() {
        Interval interval1 = Interval.between(-20000, -4000);
        Interval interval2 = Interval.between(-1000, 0);
        Interval interval3 = Interval.between(500, 800);
        Interval interval4 = Interval.between(2500, 3000);

        return Subset.builder().add(interval1)
                .add(interval2)
                .add(interval3)
                .add(interval4)
                .create();
    }

    static Subset getSubset_3() {
        Interval interval1 = Interval.between(NEGATIVE_INFINITY, Point.valueOf(-8000));
        Interval interval2 = Interval.between(-1000, 2000);
        Interval interval3 = Interval.between(2900, 7000);

        return Subset.builder().add(interval1)
                                    .add(interval2)
                                    .add(interval3)
                                    .create();
    }

    static Subset getSubset_4() {
        Interval interval1 = Interval.between(-30000, 750);
        Interval interval2 = Interval.between(7000, 28000);

        return Subset.builder().add(interval1)
                .add(interval2)
                .create();
    }

    static Subset getSubset_5() {
        Interval interval1 = Interval.between(-10000, 10000);
        Interval interval2 = Interval.between(Point.valueOf(25000), POSITIVE_INFINITY);

        return Subset.builder().add(interval1)
                .add(interval2)
                .create();
    }

    static Subset getExpected() {
        Interval interval1 = Interval.between(-10000, -8000);
        Interval interval2 = Interval.between(0, 0);
        Interval interval3 = Interval.between(500, 750);

        return Subset.builder().add(interval1)
                .add(interval2)
                .add(interval3)
                .create();
    }
}
