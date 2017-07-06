package com.paniclab.amalgama;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Сергей on 06.07.2017.
 */
public class UtilTest {
    @Test
    public void getSuperpositionOf_takesTwoInfiniteIntrvls_returnIntrvlEqualsToBoth() throws Exception {
        Point x1 = Point.NEGATIVE_INFINITY;
        Point y1 = Point.POSITIVE_INFINITY;
        Interval intervalOne = Interval.newInstance(x1, y1);

        Point x2 = Point.NEGATIVE_INFINITY;
        Point y2 = Point.POSITIVE_INFINITY;
        Interval intervalTwo = Interval.newInstance(x2, y2);

        Interval result = Util.getSuperpositionOf(intervalOne, intervalTwo);

        assertEquals(intervalOne, result);
        assertEquals(intervalTwo, result);
    }

    @Test
    public void getSuperpositionOf_takesInfiniteAndNegHalfIntrvls_returnIntrvlEqualsToHlfIntrvl() throws Exception {
        Point x1 = Point.NEGATIVE_INFINITY;
        Point y1 = Point.POSITIVE_INFINITY;
        Interval intervalOne = Interval.newInstance(x1, y1);

        Point x2 = Point.NEGATIVE_INFINITY;
        Point y2 = Point.valueOf(10);
        Interval intervalTwo = Interval.newInstance(x2, y2);

        Interval result = Util.getSuperpositionOf(intervalOne, intervalTwo);
        assertEquals(result, intervalTwo);
    }

    @Test
    public void getSuperpositionOf_takesInfiniteAndPosHalfIntrvls_returnIntrvlEqualsToHlfIntrvl() throws Exception {
        Point x1 = Point.NEGATIVE_INFINITY;
        Point y1 = Point.POSITIVE_INFINITY;
        Interval intervalOne = Interval.newInstance(x1, y1);

        Point x2 = Point.POSITIVE_INFINITY;
        Point y2 = Point.valueOf(-10);
        Interval intervalTwo = Interval.newInstance(x2, y2);

        Interval result = Util.getSuperpositionOf(intervalOne, intervalTwo);
        assertEquals(result, intervalTwo);
    }

    @Test
    public void getSuperpositionOf_takesTwoRandomIntrvlsThatHasSuperpos_returnIntrvl() throws Exception {
        Point x1 = Point.valueOf(100);
        Point y1 = Point.valueOf(140);
        Interval intervalOne = Interval.newInstance(x1, y1);

        Point x2 = Point.valueOf(121);
        Point y2 = Point.valueOf(190);
        Interval intervalTwo = Interval.newInstance(x2, y2);

        Interval expected = Interval.newInstance(Point.valueOf(121), Point.valueOf(140));
        Interval result = Util.getSuperpositionOf(intervalOne, intervalTwo);

        assertEquals(expected, result);
    }

    @Test
    public void getSuperpositionOf_takesTwoRandomIntrvlsThatHasNoSuperpos_returnNull() throws Exception {
        Point x1 = Point.valueOf(100);
        Point y1 = Point.valueOf(140);
        Interval intervalOne = Interval.newInstance(x1, y1);

        Point x2 = Point.valueOf(141);
        Point y2 = Point.valueOf(190);
        Interval intervalTwo = Interval.newInstance(x2, y2);

        Interval result = Util.getSuperpositionOf(intervalOne, intervalTwo);
        assertNull(result);
    }

    @Test
    public void getSuperpositionOf_takesRandomAndZeroLengthIntrvlsThatHasSuperpos_returnZrLgthIntvl() throws Exception {
        Point x1 = Point.valueOf(100);
        Point y1 = Point.valueOf(140);
        Interval intervalOne = Interval.newInstance(x1, y1);

        Point x2 = Point.valueOf(131);
        Point y2 = Point.valueOf(131);
        Interval intervalTwo = Interval.newInstance(x2, y2);

        Interval expected = Interval.newInstance(Point.valueOf(131), Point.valueOf(131));
        Interval result = Util.getSuperpositionOf(intervalOne, intervalTwo);
    }

    @Test
    public void getSuperpositionOf_takesRandomAndZeroLengthIntrvlsThatHasNoSuperpos_returnNull() throws Exception {
        Point x1 = Point.valueOf(100);
        Point y1 = Point.valueOf(140);
        Interval intervalOne = Interval.newInstance(x1, y1);

        Point x2 = Point.valueOf(190);
        Point y2 = Point.valueOf(190);
        Interval intervalTwo = Interval.newInstance(x2, y2);

        Interval result = Util.getSuperpositionOf(intervalOne, intervalTwo);
        assertNull(result);
    }

    @Test
    public void getSuperpositionOf_takesTwoZeroLengthIntrvlsThatHasSuperpos_returnIntrvlEqualToBoth() throws Exception {
        Point x1 = Point.ZERO;
        Point y1 = Point.ZERO;
        Interval intervalOne = Interval.newInstance(x1, y1);

        Point x2 = Point.ZERO;
        Point y2 = Point.ZERO;
        Interval intervalTwo = Interval.newInstance(x2, y2);

        Interval expected = Interval.newInstance(Point.ZERO, Point.ZERO);
        Interval result = Util.getSuperpositionOf(intervalOne, intervalTwo);

        assertEquals(expected, result);
        assertTrue(intervalOne.equals(result));
        assertTrue(intervalTwo.equals(result));
    }

    @Test
    public void getSuperpositionOf_takesTwoZeroLengthIntrvlsThatHasNoSuperpos_returnNull() throws Exception {
        Point x1 = Point.ZERO;
        Point y1 = Point.ZERO;
        Interval intervalOne = Interval.newInstance(x1, y1);

        Point x2 = Point.valueOf(100);
        Point y2 = Point.valueOf(100);
        Interval intervalTwo = Interval.newInstance(x2, y2);

        Interval result = Util.getSuperpositionOf(intervalOne, intervalTwo);
        assertNull(result);
    }
}