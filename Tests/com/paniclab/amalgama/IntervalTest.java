package com.paniclab.amalgama;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by root on 04.07.2017.
 */
public class IntervalTest {

    @Test(expected = NullPointerException.class)
    public void createInstance_TakesNullArgOne_throwNullPointerException() throws Exception {
        Interval interval = Interval.between(null, Point.valueOf(0));
    }

    @Test(expected = NullPointerException.class)
    public void createInstance_TakesNullArgTwo_throwNullPointerException() throws Exception {
        Interval interval = Interval.between(Point.NEGATIVE_INFINITY, null);
    }

    @Test(expected = NullPointerException.class)
    public void createInstance_TakesNullBothArgs_throwNullPointerException() throws Exception {
        Interval interval = Interval.between(null, null);
    }

    @Test(expected = IntervalException.class)
    public void newInstance_takesAllArgsAsNegInfinity_throwIntervalException() throws Exception {
        Point x = Point.NEGATIVE_INFINITY;
        Point y = Point.NEGATIVE_INFINITY;
        Interval interval = Interval.between(x, y);
    }

    @Test(expected = IntervalException.class)
    public void newInstance_takesAllArgsAsPosInfinity_throwIntervalException() throws Exception {
        Point x = Point.POSITIVE_INFINITY;
        Point y = Point.POSITIVE_INFINITY;
        Interval interval = Interval.between(x, y);
    }

    @Test
    public void newInstance_takesNegAndPosInfinities_exists() throws Exception {
        Point x = Point.POSITIVE_INFINITY;
        Point y = Point.NEGATIVE_INFINITY;
        Interval interval = Interval.between(x, y);
        assertNotNull(interval);
    }

    @Test
    public void hasInfiniteLength_createdWithNegAndPosInfinitiesArgs_returnTrue() throws Exception {
        Point x = Point.POSITIVE_INFINITY;
        Point y = Point.NEGATIVE_INFINITY;
        Interval interval = Interval.between(x, y);
        assertTrue(interval.hasInfiniteLength());
    }

    @Test
    public void hasInfiniteLength_createsWithNegInfinityAndRandomArg_returnFalse() throws Exception {
        Point x = Point.NEGATIVE_INFINITY;
        Point y = Point.valueOf(1);
        Interval interval = Interval.between(x, y);
        assertFalse(interval.hasInfiniteLength());
    }

    @Test
    public void hasInfiniteLength_createsWithRandomArgAndPosInfinity_returnFalse() throws Exception {
        Point x = Point.valueOf("-145");
        Point y = Point.POSITIVE_INFINITY;
        Interval interval = Interval.between(x, y);
        assertFalse(interval.hasInfiniteLength());
    }

    @Test
    public void hasZeroLength_takesRandomEqualsArgs_returnTrue() throws Exception {
        Point x = Point.valueOf("10");
        Point y = Point.valueOf(10);
        Interval interval = Interval.between(x, y);
        assertTrue(interval.hasZeroLength());
    }

    @Test
    public void hasZeroLength_takesZeroBothArgs_returnTrue()throws Exception {
        Point x = Point.valueOf("0");
        Point y = Point.valueOf(BigDecimal.ZERO);
        Interval interval = Interval.between(x, y);
        assertTrue(interval.hasZeroLength());
    }

    @Test
    public void hasZeroLength_takesRandomNotEqualsArgs_returnFalse() throws Exception {
        Point x = Point.valueOf("-15");
        Point y = Point.valueOf(4.5626);
        Interval interval = Interval.between(x, y);
        assertFalse(interval.hasZeroLength());
    }

    @Test
    public void isHalfInterval_takesPosInfinityAndRandomArg_returnTrue() throws Exception {
        Point x = Point.POSITIVE_INFINITY;
        Point y = Point.valueOf(-10);
        Interval interval = Interval.between(x, y);
        assertTrue(interval.isHalfInterval());
    }

    @Test
    public void isHalfInterval_takesNegInfinityAndRandomArg_returnTrue() throws Exception {
        Point x = Point.valueOf("5326");
        Point y = Point.NEGATIVE_INFINITY;
        Interval interval = Interval.between(x, y);
        assertTrue(interval.isHalfInterval());
    }

    @Test
    public void isHalfInterval_takesBothNotEqualsRandomArgs_returnFalse() throws Exception {
        Point x = Point.valueOf("4");
        Point y = Point.valueOf(6);
        Interval interval = Interval.between(x, y);
        assertFalse(interval.isHalfInterval());
    }

    @Test
    public void isHalfInterval_takesBothEqualsRandomArgs_returnFalse() throws Exception {
        Point x = Point.valueOf(BigDecimal.ZERO);
        Interval interval = Interval.between(x, x);
        assertFalse(interval.isHalfInterval());
    }

    @Test
    public void isHalfInterval_takesPosInfinityAndNegInfinity_returnFalse() throws Exception {
        Point x = Point.POSITIVE_INFINITY;
        Point y = Point.NEGATIVE_INFINITY;
        Interval interval = Interval.between(x, y);
        assertFalse(interval.isHalfInterval());
    }

    @Test
    public void isNegativeHalfInterval_takesPosInfinityAndRandomArg_returnFalse() throws Exception {
        Point x = Point.POSITIVE_INFINITY;
        Point y = Point.valueOf(11);
        Interval interval = Interval.between(x,y);
        //assertTrue(interval.isHalfInterval());

        assertFalse(interval.isNegativeHalfInterval());
    }

    @Test
    public void isNegativeHalfInterval_takesNegInfinityAndRandomArg_returnTrue() throws Exception {
        Point y = Point.NEGATIVE_INFINITY;
        Point x = Point.valueOf(0);
        Interval interval = Interval.between(x, y);
        //assertTrue(interval.isHalfInterval());

        assertTrue(interval.isNegativeHalfInterval());
    }

    @Test
    public void isPositiveHalfInterval_takesNegInfinityAndRandomArg_returnFalse() throws Exception {
        Point y = Point.NEGATIVE_INFINITY;
        Point x = Point.valueOf(0);
        Interval interval = Interval.between(x, y);
        //assertTrue(interval.isHalfInterval());

        assertFalse(interval.isPositiveHalfInterval());
    }

    @Test
    public void isPositiveHalfInterval_takesPosInfinityAndRandomArg_returnTrue() throws Exception {
        Point x = Point.POSITIVE_INFINITY;
        Point y = Point.valueOf(0);
        Interval interval = Interval.between(x, y);

        assertTrue(interval.isPositiveHalfInterval());
    }

    @Test
    public void equals_twoRandomIntervalsWithEqualLimits_returnTrue() throws Exception {
        Point x1 = Point.valueOf(-27);
        Point y1 = Point.valueOf(-1);
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(-1);
        Point y2 = Point.valueOf(-27);
        Interval intervalTwo = Interval.between(x2, y2);

        assertTrue(intervalOne.equals(intervalTwo));
    }

    @Test
    public void equals_twoRandomIntervalsWithNotEqualLimits_returnFalse() throws Exception {
        Point x1 = Point.valueOf(-27);
        Point y1 = Point.valueOf(-1);
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(-1);
        Point y2 = Point.valueOf(-28);
        Interval intervalTwo = Interval.between(x2, y2);

        assertFalse(intervalOne.equals(intervalTwo));
    }

    @Test
    public void equals_twoRandomZeroLengthIntervalsWithEqualLimits_returnTrue() throws Exception {
        Point x1 = Point.valueOf(-10);
        Point y1 = Point.valueOf(-10);
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(-10);
        Point y2 = Point.valueOf(-10);
        Interval intervalTwo = Interval.between(x2, y2);

        assertTrue(intervalOne.equals(intervalTwo));
    }

    @Test
    public void equals_twoInfiniteIntervals_returnTrue() throws Exception {
        Point x1 = Point.POSITIVE_INFINITY;
        Point y1 = Point.NEGATIVE_INFINITY;
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.NEGATIVE_INFINITY;
        Point y2 = Point.POSITIVE_INFINITY;
        Interval intervalTwo = Interval.between(x2, y2);

        assertTrue(intervalOne.equals(intervalTwo));
    }

    @Test
    public void equals_twoNegHalfIntervalsWithSameLimit_returnTrue() throws Exception {
        Point x1 = Point.NEGATIVE_INFINITY;
        Point y1 = Point.valueOf(14);
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(14);
        Point y2 = Point.NEGATIVE_INFINITY;
        Interval intervalTwo = Interval.between(x2, y2);

        assertTrue(intervalOne.equals(intervalTwo));
    }

    @Test
    public void equals_twoNegHalfIntervalsWithNotEqualLimit_returnFalse() throws Exception {
        Point x1 = Point.NEGATIVE_INFINITY;
        Point y1 = Point.valueOf(1);
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(14);
        Point y2 = Point.NEGATIVE_INFINITY;
        Interval intervalTwo = Interval.between(x2, y2);

        assertFalse(intervalOne.equals(intervalTwo));
    }

    @Test
    public void equals_twoPosHalfIntervalsWithSameLimit_returnTrue() throws Exception {
        Point x1 = Point.POSITIVE_INFINITY;
        Point y1 = Point.valueOf(14);
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(14);
        Point y2 = Point.POSITIVE_INFINITY;
        Interval intervalTwo = Interval.between(x2, y2);
    }

    @Test
    public void equals_twoPosHalfIntervalsWithNotEqualLimit_returnFalse() throws Exception {
        Point x1 = Point.POSITIVE_INFINITY;
        Point y1 = Point.valueOf(-14);
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(14);
        Point y2 = Point.POSITIVE_INFINITY;
        Interval intervalTwo = Interval.between(x2, y2);

        assertFalse(intervalOne.equals(intervalTwo));
    }

    @Test
    public void equals_negAndPosHalfIntervalsWithCommonBorder_returnFalse() throws Exception {
        Point x1 = Point.POSITIVE_INFINITY;
        Point y1 = Point.valueOf(14);
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(14);
        Point y2 = Point.NEGATIVE_INFINITY;
        Interval intervalTwo = Interval.between(x2, y2);

        assertFalse(intervalOne.equals(intervalTwo));
    }

    @Test
    public void equals_isReflexive() throws Exception {
        Point x1 = Point.POSITIVE_INFINITY;
        Point y1 = Point.valueOf(14);
        Interval interval = Interval.between(x1, y1);
        assertTrue(interval.equals(interval));
    }

    @Test
    public void equals_isSymmetric() throws Exception {
        Point x1 = Point.POSITIVE_INFINITY;
        Point y1 = Point.valueOf(14);
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(14);
        Point y2 = Point.POSITIVE_INFINITY;
        Interval intervalTwo = Interval.between(x2, y2);

        assertTrue(intervalOne.equals(intervalTwo));
        assertTrue(intervalTwo.equals(intervalOne));
    }

    @Test
    public void equals_isTransitive() throws Exception {
        Point x1 = Point.NEGATIVE_INFINITY;
        Point y1 = Point.valueOf(14);
        Interval x = Interval.between(x1, y1);

        Point x2 = Point.valueOf(14);
        Point y2 = Point.NEGATIVE_INFINITY;
        Interval y = Interval.between(x2, y2);

        Point x3 = Point.valueOf(14);
        Point y3 = Point.NEGATIVE_INFINITY;
        Interval z = Interval.between(x3, y3);

        assertTrue(x.equals(y));
        assertTrue(y.equals(z));
        assertTrue(x.equals(z));
    }

    @Test
    public void equals_isConsistent() throws Exception {
        Point x1 = Point.valueOf(100);
        Point y1 = Point.valueOf(14);
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(14);
        Point y2 = Point.valueOf(100);
        Interval intervalTwo = Interval.between(x2, y2);

        for(int i=0; i < 100; i++) {
            assertTrue(intervalOne.equals(intervalTwo));
        }
    }

    @Test
    public void equals_comparesToNull_returnFalse() throws Exception {
        Point x1 = Point.valueOf(100);
        Point y1 = Point.valueOf(14);
        Interval intervalOne = Interval.between(x1, y1);

        Interval intervalTwo = null;

        assertFalse(intervalOne.equals(intervalTwo));
    }

    @Test
    public void getLimits_randomInterval_expectedSetWithSize2() throws Exception {
        Point x = Point.valueOf(100);
        Point y = Point.valueOf(14);
        Interval interval = Interval.between(x, y);

        Set<Point> expected = new HashSet<>();
        expected.add(x);
        expected.add(y);

        assertEquals(expected, interval.getLimits());
        assertEquals(expected.size(), 2);
    }

    @Test
    public void getLimits_randomZeroLengthInterval_expectedSetWithSize1() throws Exception {
        Point x = Point.valueOf(14);
        Point y = Point.valueOf(14);
        Interval interval = Interval.between(x, y);

        Set<Point> expected = new HashSet<>();
        expected.add(x);
        expected.add(y);

        assertEquals(expected, interval.getLimits());
        assertEquals(expected.size(), 1);
    }

    @Test
    public void getLimits_negHalfInterval_expectedSetWithSize2() throws Exception {
        Point x = Point.NEGATIVE_INFINITY;
        Point y = Point.valueOf(0);
        Interval interval = Interval.between(x, y);

        Set<Point> expected = new HashSet<>();
        expected.add(x);
        expected.add(y);

        assertEquals(expected, interval.getLimits());
        assertEquals(expected.size(), 2);
    }

    @Test
    public void getLimits_posHalfInterval_expectedSetWithSize2() throws Exception {
        Point x = Point.POSITIVE_INFINITY;
        Point y = Point.valueOf(0);
        Interval interval = Interval.between(x, y);

        Set<Point> expected = new HashSet<>();
        expected.add(x);
        expected.add(y);

        assertEquals(expected, interval.getLimits());
        assertEquals(expected.size(), 2);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getLimits_attemptsToAddMorePoints_throwUnsupportedOperationException() throws Exception {
        Point x = Point.POSITIVE_INFINITY;
        Point y = Point.valueOf(0);
        Interval interval = Interval.between(x, y);

        Set<Point> points = interval.getLimits();

        Point z = Point.valueOf(10);
        points.add(z);
    }

    @Test
    public void isNeighborsWith_twoRandomIntervalsWithOneCommonBorderAndIsNotContainsOneAnother_symmetric_returnTrue()
            throws Exception {
        Point x1 = Point.valueOf(100);
        Point y1 = Point.valueOf(14);
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(121);
        Point y2 = Point.valueOf(100);
        Interval intervalTwo = Interval.between(x2, y2);

        assertTrue(intervalOne.isNeighborsWith(intervalTwo));
        assertTrue(intervalTwo.isNeighborsWith(intervalOne));
    }

    @Test
    public void isNeighborsWith_twoRandomIntervalsWithNoCommonBorder_returnFalse() throws Exception {
        Point x1 = Point.valueOf(100);
        Point y1 = Point.valueOf(140);
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(141);
        Point y2 = Point.valueOf(200);
        Interval intervalTwo = Interval.between(x2, y2);

        assertFalse(intervalOne.isNeighborsWith(intervalTwo));
    }

    @Test
    public void isNeighborsWith_twoRandomEqualIntervals_returnFalse() throws Exception {
        Point x1 = Point.valueOf(100);
        Point y1 = Point.valueOf(140);
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(140);
        Point y2 = Point.valueOf(100);
        Interval intervalTwo = Interval.between(x2, y2);

        assertTrue(intervalOne.equals(intervalTwo));
        assertFalse(intervalOne.isNeighborsWith(intervalTwo));
    }

    @Test
    public void isNeighborsWith_twoRandomIntervalsWithCommonBorderAndOneContainsAnother_returnFalse() throws Exception {
        Point x1 = Point.valueOf(100);
        Point y1 = Point.valueOf(140);
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(100);
        Point y2 = Point.valueOf(120);
        Interval intervalTwo = Interval.between(x2, y2);

        assertFalse(intervalOne.isNeighborsWith(intervalTwo));
    }

    @Test
    public void isContains_twoRandomIntervalsWithNoEqualLimitsAndOneContainsAnother_returnTrue() throws Exception {
        Point x1 = Point.valueOf(100);
        Point y1 = Point.valueOf(140);
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(101);
        Point y2 = Point.valueOf(119);
        Interval intervalTwo = Interval.between(x2, y2);

        assertTrue(intervalOne.isContains(intervalTwo));
    }

    @Test
    public void isContains_twoRandomIntervalsWithOneEqualLimitAndOneContainsAnother_returnTrue() throws Exception {
        Point x1 = Point.valueOf(100);
        Point y1 = Point.valueOf(140);
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(100);
        Point y2 = Point.valueOf(119);
        Interval intervalTwo = Interval.between(x2, y2);

        assertTrue(intervalOne.isContains(intervalTwo));
    }

    @Test
    public void isContains_twoRandomEqualIntervals_symmetric_returnTrue() throws Exception {
        Point x1 = Point.valueOf(100);
        Point y1 = Point.valueOf(140);
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(100);
        Point y2 = Point.valueOf(140);
        Interval intervalTwo = Interval.between(x2, y2);
        assertEquals(intervalOne, intervalTwo);

        assertTrue(intervalOne.isContains(intervalTwo));
        assertTrue(intervalTwo.isContains(intervalOne));
    }

    @Test
    public void isContains_twoRandomIntervalsWithNoEqualLimitsButHasSuperposition_returnFalse() throws Exception {
        Point x1 = Point.valueOf(100);
        Point y1 = Point.valueOf(140);
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(120);
        Point y2 = Point.valueOf(200);
        Interval intervalTwo = Interval.between(x2, y2);

        assertFalse(intervalOne.isContains(intervalTwo));
        assertFalse(intervalTwo.isContains(intervalOne));
    }

    @Test
    public void isContains_twoRandomIntervalsWithNoEqualLimitsAndHasNoSuperposition_returnFalse() throws Exception {
        Point x1 = Point.valueOf(100);
        Point y1 = Point.valueOf(140);
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(150);
        Point y2 = Point.valueOf(200);
        Interval intervalTwo = Interval.between(x2, y2);

        assertFalse(intervalOne.isContains(intervalTwo));
        assertFalse(intervalTwo.isContains(intervalOne));
    }

    @Test
    public void hasSuperpositionWith_twoRandomIntervalsThatHasSuperposition_returnTrue() throws Exception {
        Point x1 = Point.valueOf(100);
        Point y1 = Point.valueOf(140);
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(139);
        Point y2 = Point.valueOf(200);
        Interval intervalTwo = Interval.between(x2, y2);

        assertTrue(intervalOne.hasSuperpositionWith(intervalTwo));
        assertTrue(intervalTwo.hasSuperpositionWith(intervalOne));
    }

    @Test
    public void hasSuperpositionWith_twoRandomIntervalsThatHasNoSuperposition_returnFalse() throws Exception {
        Point x1 = Point.valueOf(100);
        Point y1 = Point.valueOf(140);
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(141);
        Point y2 = Point.valueOf(200);
        Interval intervalTwo = Interval.between(x2, y2);

        assertFalse(intervalOne.hasSuperpositionWith(intervalTwo));
        assertFalse(intervalTwo.hasSuperpositionWith(intervalOne));
    }

    @Test
    public void hasSuperpositionWith_twoRandomIntervalsAndOneContainsAnother_returnTrue() throws Exception {
        Point x1 = Point.valueOf(100);
        Point y1 = Point.valueOf(200);
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(101);
        Point y2 = Point.valueOf(199);
        Interval intervalTwo = Interval.between(x2, y2);

        assertTrue(intervalOne.hasSuperpositionWith(intervalTwo));
        assertTrue(intervalTwo.hasSuperpositionWith(intervalOne));
    }

    @Test
    public void hasSuperpositionWith_infiniteAndRandomIntervals_returnTrue() throws Exception {
        Point x1 = Point.NEGATIVE_INFINITY;
        Point y1 = Point.POSITIVE_INFINITY;
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(101);
        Point y2 = Point.valueOf(199);
        Interval intervalTwo = Interval.between(x2, y2);

        assertTrue(intervalOne.hasSuperpositionWith(intervalTwo));
        assertTrue(intervalTwo.hasSuperpositionWith(intervalOne));
    }
}