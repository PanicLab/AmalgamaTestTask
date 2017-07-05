package com.paniclab.amalgama;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by root on 04.07.2017.
 */
public class IntervalTest {

    @Test(expected = NullPointerException.class)
    public void createInstance_TakesNullArgOne_throwNullPointerException() throws Exception {
        Interval interval = Interval.newInstance(null, Point.valueOf(0));
    }

    @Test(expected = NullPointerException.class)
    public void createInstance_TakesNullArgTwo_throwNullPointerException() throws Exception {
        Interval interval = Interval.newInstance(Point.NEGATIVE_INFINITY, null);
    }

    @Test(expected = NullPointerException.class)
    public void createInstance_TakesNullBothArgs_throwNullPointerException() throws Exception {
        Interval interval = Interval.newInstance(null, null);
    }

    @Test(expected = IntervalException.class)
    public void newInstance_takesAllArgsAsNegInfinity_throwWrongSegmentException() throws Exception {
        Point x = Point.NEGATIVE_INFINITY;
        Point y = Point.NEGATIVE_INFINITY;
        Interval interval = Interval.newInstance(x, y);
    }

    @Test(expected = IntervalException.class)
    public void newInstance_takesAllArgsAsPosInfinity_throwWrongSegmentException() throws Exception {
        Point x = Point.POSITIVE_INFINITY;
        Point y = Point.POSITIVE_INFINITY;
        Interval interval = Interval.newInstance(x, y);
    }

    @Test
    public void newInstance_takesNegAndPosInfinities_exists() throws Exception {
        Point x = Point.POSITIVE_INFINITY;
        Point y = Point.NEGATIVE_INFINITY;
        Interval interval = Interval.newInstance(x, y);
        assertNotNull(interval);
    }

    @Test
    public void hasInfiniteLength_createdWithNegAndPosInfinitiesArgs_returnTrue() throws Exception {
        Point x = Point.POSITIVE_INFINITY;
        Point y = Point.NEGATIVE_INFINITY;
        Interval interval = Interval.newInstance(x, y);
        assertTrue(interval.hasInfiniteLength());
    }

    @Test
    public void hasInfiniteLength_createsWithNegInfinityAndRandomArg_returnFalse() throws Exception {
        Point x = Point.NEGATIVE_INFINITY;
        Point y = Point.valueOf(1);
        Interval interval = Interval.newInstance(x, y);
        assertFalse(interval.hasInfiniteLength());
    }

    @Test
    public void hasInfiniteLength_createsWithRandomArgAndPosInfinity_returnFalse() throws Exception {
        Point x = Point.valueOf("-145");
        Point y = Point.POSITIVE_INFINITY;
        Interval interval = Interval.newInstance(x, y);
        assertFalse(interval.hasInfiniteLength());
    }

    @Test
    public void hasZeroLength_takesRandomEqualsArgs_returnTrue() throws Exception {
        Point x = Point.valueOf("10");
        Point y = Point.valueOf(10);
        Interval interval = Interval.newInstance(x, y);
        assertTrue(interval.hasZeroLength());
    }

    @Test
    public void hasZeroLength_takesZeroBothArgs_returnTrue()throws Exception {
        Point x = Point.valueOf("0");
        Point y = Point.valueOf(BigDecimal.ZERO);
        Interval interval = Interval.newInstance(x, y);
        assertTrue(interval.hasZeroLength());
    }

    @Test
    public void hasZeroLength_takesRandomNotEqualsArgs_returnFalse() throws Exception {
        Point x = Point.valueOf("-15");
        Point y = Point.valueOf(4.5626);
        Interval interval = Interval.newInstance(x, y);
        assertFalse(interval.hasZeroLength());
    }
}