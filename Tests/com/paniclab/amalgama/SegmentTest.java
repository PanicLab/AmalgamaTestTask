package com.paniclab.amalgama;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by root on 04.07.2017.
 */
public class SegmentTest {

    @Test(expected = NullPointerException.class)
    public void createInstance_TakesNullArgOne_throwNullPointerException() throws Exception {
        Segment segment = Segment.newInstance(null, Point.valueOf(0));
    }

    @Test(expected = NullPointerException.class)
    public void createInstance_TakesNullArgTwo_throwNullPointerException() throws Exception {
        Segment segment = Segment.newInstance(Point.NEGATIVE_INFINITY, null);
    }

    @Test(expected = NullPointerException.class)
    public void createInstance_TakesNullBothArgs_throwNullPointerException() throws Exception {
        Segment segment = Segment.newInstance(null, null);
    }

    @Test(expected = WrongSegmentException.class)
    public void newInstance_takesAllArgsAsNegInfinity_throwWrongSegmentException() throws Exception {
        Point x = Point.NEGATIVE_INFINITY;
        Point y = Point.NEGATIVE_INFINITY;
        Segment segment = Segment.newInstance(x, y);
    }

    @Test(expected = WrongSegmentException.class)
    public void newInstance_takesAllArgsAsPosInfinity_throwWrongSegmentException() throws Exception {
        Point x = Point.POSITIVE_INFINITY;
        Point y = Point.POSITIVE_INFINITY;
        Segment segment = Segment.newInstance(x, y);
    }

    @Test
    public void newInstance_takesNegAndPosInfinities_exists() throws Exception {
        Point x = Point.POSITIVE_INFINITY;
        Point y = Point.NEGATIVE_INFINITY;
        Segment segment = Segment.newInstance(x, y);
        assertNotNull(segment);
    }

    @Test
    public void hasInfiniteLength_createdWithNegAndPosInfinitiesArgs_returnTrue() throws Exception {
        Point x = Point.POSITIVE_INFINITY;
        Point y = Point.NEGATIVE_INFINITY;
        Segment segment = Segment.newInstance(x, y);
        assertTrue(segment.hasInfiniteLength());
    }

    @Test
    public void hasInfiniteLength_createsWithNegInfinityAndRandomArg_returnFalse() throws Exception {
        Point x = Point.NEGATIVE_INFINITY;
        Point y = Point.valueOf(1);
        Segment segment = Segment.newInstance(x, y);
        assertFalse(segment.hasInfiniteLength());
    }

    @Test
    public void hasInfiniteLength_createsWithRandomArgAndPosInfinity_returnFalse() throws Exception {
        Point x = Point.valueOf("-145");
        Point y = Point.POSITIVE_INFINITY;
        Segment segment = Segment.newInstance(x, y);
        assertFalse(segment.hasInfiniteLength());
    }

    @Test
    public void hasZeroLength_takesRandomEqualsArgs_returnTrue() throws Exception {
        Point x = Point.valueOf("10");
        Point y = Point.valueOf(10);
        Segment segment = Segment.newInstance(x, y);
        assertTrue(segment.hasZeroLength());
    }

    @Test
    public void hasZeroLength_takesZeroBothArgs_returnTrue()throws Exception {
        Point x = Point.valueOf("0");
        Point y = Point.valueOf(BigDecimal.ZERO);
        Segment segment = Segment.newInstance(x, y);
        assertTrue(segment.hasZeroLength());
    }

    @Test
    public void hasZeroLength_takesRandomNotEqualsArgs_returnFalse() throws Exception {
        Point x = Point.valueOf("-15");
        Point y = Point.valueOf(4.5626);
        Segment segment = Segment.newInstance(x, y);
        assertFalse(segment.hasZeroLength());
    }
}