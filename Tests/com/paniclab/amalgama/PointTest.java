package com.paniclab.amalgama;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by Сергей on 04.07.2017.
 */
public class PointTest {

    @Test(expected = PointException.class)
    public void valueOf_takesBadData_throwPointException() throws Exception {
        Point x = Point.valueOf("fwe");
    }

    @Test
    public void equals_isReflexive() throws Exception {
        Point x = Point.valueOf("3");
        assertTrue(x.equals(x));
    }

    @Test
    public void equals_isSymmetric() throws Exception {
        Point x = Point.valueOf(3.14);
        Point y = Point.valueOf("3.14");
        assertEquals(x, y);
        assertTrue(x.equals(y));
        assertTrue(y.equals(x));
    }

    @Test
    public void equals_isTransitive() throws Exception {
        Point x = Point.valueOf(3.14);
        Point y = Point.valueOf("3.14");
        Point z = Point.valueOf(new BigDecimal("3.14"));
        assertEquals(x, y);
        assertEquals(y, z);

        assertTrue(x.equals(y));
        assertTrue(y.equals(z));
        assertTrue(x.equals(z));
    }

    @Test
    public void equals_isConsistent() throws Exception {
        Point x = Point.valueOf(3.14);
        Point y = Point.valueOf("3.14");

        for(int i = 0; i < 100; i++) {
            assertTrue(x.equals(y));
        }
    }

    @Test
    public void equals_takesNull_returnFalse() throws Exception {
        Point x = Point.valueOf("3.14");
        Point y = null;

        assertFalse(x.equals(y));
    }

    @Test
    public void equals_posInfinityComparesWithPosInfinity_returnTrue() throws Exception {
        Point x = Point.POSITIVE_INFINITY;
        Point y = Point.POSITIVE_INFINITY;
        assertTrue(x.equals(y));
    }

    @Test
    public void equals_posInfinityComparesWithNegInfinity_returnFalse() throws Exception {
        Point x = Point.POSITIVE_INFINITY;
        Point y = Point.NEGATIVE_INFINITY;
        assertFalse(x.equals(y));
    }

    @Test
    public void equals_negInfinityComparesWithNegInfinity_returnTrue() throws Exception {
        Point x = Point.NEGATIVE_INFINITY;
        Point y = Point.NEGATIVE_INFINITY;
        assertTrue(x.equals(y));
    }

    @Test
    public void equals_negInfinityComparesWithPosInfinity_returnFalse() throws Exception {
        Point x = Point.NEGATIVE_INFINITY;
        Point y = Point.POSITIVE_INFINITY;
        assertFalse(x.equals(y));
    }

    @Test
    public void equals_posInfinityWithZero_returnFalse() throws Exception {
        Point x = Point.POSITIVE_INFINITY;
        Point y = Point.valueOf(BigDecimal.ZERO);

        assertFalse(x.equals(y));
    }

    @Test
    public void equals_negInfinityWithZero_returnFalse() throws Exception {
        Point x = Point.NEGATIVE_INFINITY;
        Point y = Point.valueOf(BigDecimal.ZERO);

        assertFalse(x.equals(y));
    }

    @Test
    public void hashCode_test() throws Exception {
        Point x = Point.valueOf("3.14");
        BigDecimal number = new BigDecimal("3.14");
        Point y = Point.valueOf(number);

        int expected = x.hashCode();
        int result = y.hashCode();

        assertEquals(expected, result);
    }


    @Test
    public void compareTo_isConsistentWithEquals_takesEqualValues_returnZero() throws Exception {
        Point x = Point.valueOf(3.14);
        Point y = Point.valueOf("3.14");

        assertEquals(x, y);
        assertTrue(x.compareTo(y) == 0);
    }

    @Test
    public void compareTo_biggerOne_returnNegativeValue() throws Exception {
        Point x = Point.valueOf(3.14);
        Point y = Point.valueOf("100");

        assertTrue(x.compareTo(y) < 0);
    }

    @Test
    public void compareTo_smallerOne_returnPositiveValue() throws Exception {
        Point x = Point.valueOf(3.14);
        Point y = Point.valueOf("-1");

        assertTrue(x.compareTo(y) > 0);
    }

    @Test
    public void compareTo_posInfinityWithPosInfinity_returnZero() throws Exception {
        Point x = Point.POSITIVE_INFINITY;
        Point y = Point.POSITIVE_INFINITY;

        assertTrue(x.compareTo(y) == 0);
    }

    @Test
    public void compareTo_posInfinityWithNegInfinity_returnPositiveValue() throws Exception {
        Point x = Point.POSITIVE_INFINITY;
        Point y = Point.NEGATIVE_INFINITY;

        assertTrue(x.compareTo(y) > 0);
    }

    @Test
    public void compareTo_negInfinityWithNegInfinity_returnZero() throws Exception {
        Point x = Point.NEGATIVE_INFINITY;
        Point y = Point.NEGATIVE_INFINITY;

        assertTrue(x.compareTo(y) == 0);
    }

    @Test
    public void compareTo_negInfinityWithPosInfinity_returnNegativeValue() throws Exception {
        Point x = Point.NEGATIVE_INFINITY;
        Point y = Point.POSITIVE_INFINITY;

        assertTrue(x.compareTo(y) < 0 );
    }

    @Test
    public void compareTo_negInfinityWithRandomValue_returnNegativeValue() throws Exception {
        Point x = Point.NEGATIVE_INFINITY;
        Point y = Point.valueOf(-1000000000);

        assertTrue(x.compareTo(y) < 0);
    }

    @Test
    public void compareTo_RandomValueWithNegativeInfinity_returnPositiveValue() throws Exception {
        Point x = Point.valueOf(-100000000000000000L);
        Point y = Point.NEGATIVE_INFINITY;

        assertTrue(x.compareTo(y) > 0);
    }

    @Test
    public void compareTo_posInfinityWithRandomValue_returnPositiveValue() throws Exception {
        Point x = Point.POSITIVE_INFINITY;
        Point y = Point.valueOf(Integer.MAX_VALUE);

        assertTrue(x.compareTo(y) > 0);
    }

    @Test
    public void compareTo_randomValueWithPosInfinity_returnNegativeValue() throws Exception {
        Point x = Point.valueOf(Integer.MAX_VALUE);
        Point y = Point.POSITIVE_INFINITY;

        assertTrue(x.compareTo(y) < 0);
    }

    @Test
    public void compareTo_zeroWithPosInfinity_returnNegativeValue() throws Exception {
        Point x = Point.valueOf(BigDecimal.ZERO);
        Point y = Point.POSITIVE_INFINITY;

        assertTrue(x.compareTo(y) < 0);
    }

    @Test
    public void compareTo_zeroWithNegInfinity_returnPositiveValue() throws Exception {
        Point x = Point.valueOf(BigDecimal.ZERO);
        Point y = Point.NEGATIVE_INFINITY;

        assertTrue(x.compareTo(y) > 0);
    }

    @Test
    public void newInstance() throws Exception {
        Point x = Point.valueOf(3.14);
        Point y = Point.valueOf("3.14");
        Point z = Point.valueOf(new BigDecimal("3.14"));

        assertTrue(x.equals(y));
        assertTrue(y.equals(z));
        assertTrue(x.equals(z));
    }

    @Test(expected = NullPointerException.class)
    public void newInstance_takesNull_throwsNullPointerException() throws Exception {
        String s = null;
        Point x = Point.valueOf(s);
    }

    @Test
    public void value_createInstanceWithBigDecimalArg_returnArg() throws Exception {
        BigDecimal number = new BigDecimal("3.1415");
        Point x = Point.valueOf(number);

        assertEquals(x.value(), number);
    }

    @Test(expected = PointException.class)
    public void value_thisIsPosInfinity_throwPointException() throws Exception {
        Point x = Point.POSITIVE_INFINITY;
        x.value();
    }

    @Test(expected = PointException.class)
    public void value_thisIsNegInfinity_throwPointException() throws Exception {
        Point x = Point.NEGATIVE_INFINITY;
        x.value();
    }

    @Test
    public void value_isZero_returnPointZeroValue() throws Exception {
        Point x = Point.valueOf(0);
        assertTrue(x.value().equals(Point.ZERO.value()));
    }

    @Test
    public void isInfinity_createdAsInfinity_returnTrue() throws Exception {
        Point x = Point.POSITIVE_INFINITY;
        Point y = Point.NEGATIVE_INFINITY;

        assertTrue(x.isInfinity());
        assertTrue(y.isInfinity());
    }

    @Test
    public void isInfinity_createdAsNotInfinity_returnFalse() throws Exception {
        Point x = Point.valueOf(BigDecimal.ZERO);
        assertFalse(x.isInfinity());
    }

    @Test
    public void isNegativeInfinity_createdAsNegInfinity_returnTrue() throws Exception {
        Point x = Point.NEGATIVE_INFINITY;
        assertTrue(x.isNegativeInfinity());
    }

    @Test
    public void isNegativeInfinity_createdAsPosInfinity_returnFalse() throws Exception {
        Point x = Point.POSITIVE_INFINITY;
        assertFalse(x.isNegativeInfinity());
    }

    @Test
    public void isNegativeInfinity_createdAsNotInfinity_returnFalse() throws Exception {
        Point x = Point.valueOf("0");
        assertFalse(x.isNegativeInfinity());
    }

    @Test
    public void isPositiveInfinity_createdAsPosInfinity_returnTrue() throws Exception {
        Point x = Point.POSITIVE_INFINITY;
        assertTrue(x.isPositiveInfinity());
    }

    @Test
    public void isPositiveInfinity_createdAsNegInfinity_returnFalse() throws Exception {
        Point x = Point.NEGATIVE_INFINITY;
        assertFalse(x.isPositiveInfinity());
    }

    @Test
    public void isPositiveInfinity_createdAsNotInfinity_returnFalse() throws Exception {
        Point x = Point.valueOf("0");
        assertFalse(x.isPositiveInfinity());
    }

    @Test
    public void lessThen_negInfinityComparesRandomValue_returnTrue() throws Exception {
        Point x = Point.NEGATIVE_INFINITY;
        Point y = Point.valueOf(-1000000000000L);

        assertTrue(x.lessThen(y));
    }

    @Test
    public void lessThen_randomValueComparesWithNegInfinity_returnFalse() throws Exception {
        Point x = Point.valueOf(-999999999999999L);
        Point y = Point.NEGATIVE_INFINITY;
        assertFalse(x.lessThen(y));
    }

    @Test
    public void lessThenOrEquals_lesserRandomValueComparesBiggerRandomValue_returnTrue() throws Exception {
        Point x = Point.valueOf(-100);
        Point y = Point.valueOf(100);

        assertTrue(x.lessThenOrEquals(y));
    }

    @Test
    public void lessThenOrEquals_comparesTwoEqualRandomValues_returnTrue() throws Exception {
        Point x = Point.valueOf(-100);
        Point y = Point.valueOf(-100);

        assertTrue(x.lessThenOrEquals(y));
    }

    @Test
    public void lessThenOrEquals_comparesTwoNegInfinities_returnTrue() throws Exception {
        Point x = Point.NEGATIVE_INFINITY;
        Point y = Point.NEGATIVE_INFINITY;

        assertTrue(x.lessThenOrEquals(y));
    }

    @Test
    public void lessThenOrEquals_comparesTwoPosInfinities_returnTrue() throws Exception {
        Point x = Point.POSITIVE_INFINITY;
        Point y = Point.POSITIVE_INFINITY;

        assertTrue(x.lessThenOrEquals(y));
    }

    @Test
    public void lessThenOrEquals_comparesTwoZeros_returnTrue() throws Exception {
        Point x = Point.ZERO;
        Point y = Point.ZERO;

        assertTrue(x.lessThenOrEquals(y));
    }

    @Test
    public void lessThenOrEquals_comparesNegAndPosInfinities_returnTrue() throws Exception {
        Point x = Point.NEGATIVE_INFINITY;
        Point y = Point.POSITIVE_INFINITY;

        assertTrue(x.lessThenOrEquals(y));
    }

    @Test
    public void lessThenOrEquals_comparesPosAndNegInfinities_returnFalse() throws Exception {
        Point x = Point.POSITIVE_INFINITY;
        Point y = Point.NEGATIVE_INFINITY;

        assertFalse(x.lessThenOrEquals(y));
    }


    @Test
    public void moreThen_posInfinityComparesWithRandomValue_returnTrue() throws Exception {
        Point x = Point.POSITIVE_INFINITY;
        Point y = Point.valueOf(Integer.MAX_VALUE);

        assertTrue(x.moreThen(y));
    }

    @Test
    public void moreThen_randomValueComparesWithPosInfinity_returnFalse() throws Exception {
        Point x = Point.valueOf(Integer.MAX_VALUE);
        Point y = Point.POSITIVE_INFINITY;

        assertFalse(x.moreThen(y));
    }

    @Test
    public void moreThenOrEquals_biggerRandomValueComparesLesserRandomValue_returnTrue() throws Exception {
        Point x = Point.valueOf(100);
        Point y = Point.valueOf(-100);

        assertTrue(x.moreThenOrEquals(y));
    }

    @Test
    public void moreThenOrEquals_lesserRandomValueComparesBiggerRandomValue_returnFalse() throws Exception {
        Point x = Point.valueOf(-100);
        Point y = Point.valueOf(100);

        assertFalse(x.moreThenOrEquals(y));
    }

    @Test
    public void moreThenOrEquals_comparesTwoZeros_returnTrue() throws Exception {
        Point x = Point.ZERO;
        Point y = Point.ZERO;

        assertTrue(x.moreThenOrEquals(y));
    }

    @Test
    public void moreThenOrEquals_compareNegAndPosInfinities_returnFalse() throws Exception {
        Point x = Point.NEGATIVE_INFINITY;
        Point y = Point.POSITIVE_INFINITY;

        assertFalse(x.moreThenOrEquals(y));
    }

    @Test
    public void moreThenOrEquals_comparesPosAndNegInfinities_returnTrue() throws Exception {
        Point x = Point.POSITIVE_INFINITY;
        Point y = Point.NEGATIVE_INFINITY;

        assertTrue(x.moreThenOrEquals(y));
    }

    @Test
    public void moreThenOrEquals_comparesTwoNegInfinities_returnTrue() throws Exception {
        Point x = Point.NEGATIVE_INFINITY;
        Point y = Point.NEGATIVE_INFINITY;

        assertTrue(x.moreThenOrEquals(y));
    }

    @Test
    public void moreThenOrEquals_comparesTwoPosInfinities_returnTrue() throws Exception {
        Point x = Point.POSITIVE_INFINITY;
        Point y = Point.POSITIVE_INFINITY;

        assertTrue(x.moreThenOrEquals(y));
    }

    @Test
    public void absValue() throws Exception {
        Point x = Point.valueOf(-5);
        Point y = Point.valueOf(5);

        assertEquals(x.absValue(), y.value());
    }

    @Test(expected = PointException.class)
    public void absValue_pointIsNegInfinity_throwPointException() throws Exception {
        Point x = Point.NEGATIVE_INFINITY;
        x.absValue();
    }

    @Test(expected = PointException.class)
    public void absValue_pointIsPosInfinity_throwPointException() throws Exception {
        Point x = Point.POSITIVE_INFINITY;
        x.absValue();
    }

    @Test
    public void isBelongsTo_randomPointAndIntervalWithRandomLimits_pointIsIn_returnTrue() throws Exception {
        Point point = Point.valueOf(10);
        Point x = Point.valueOf(-1);
        Point y = Point.valueOf(11);
        Interval interval = Interval.between(x, y);
        assertTrue(point.isBelongsTo(interval));
    }

    @Test
    public void isBelongsTo_randomPointAndIntervalWithRandomLimits_pointIsOut_returnFalse() throws Exception {
        Point point = Point.valueOf(22);
        Point x = Point.valueOf(-1);
        Point y = Point.valueOf(11);
        Interval interval = Interval.between(x, y);
        assertFalse(point.isBelongsTo(interval));
    }

    @Test
    public void isBelongsTo_randomPointAndIntervalWithRandomLimits_pointEqualsLesserLimit_returnTrue() throws Exception {
        Point point = Point.valueOf(-1);
        Point x = Point.valueOf(-1);
        Point y = Point.valueOf(11);
        Interval interval = Interval.between(x, y);
        assertTrue(point.isBelongsTo(interval));
    }

    @Test
    public void isBelongsTo_randomPointAndIntervalWithRandomLimits_pointEqualsLargerLimit_returnTrue() throws Exception {
        Point point = Point.valueOf(11);
        Point x = Point.valueOf(-1);
        Point y = Point.valueOf(11);
        Interval interval = Interval.between(x, y);
        assertTrue(point.isBelongsTo(interval));
    }

    @Test
    public void isBelongsTo_randomPointAndZeroLengthInterval_pointIsEquals_returnTrue() throws Exception {
        Point point = Point.valueOf(11);
        Point x = Point.valueOf(11);
        Point y = Point.valueOf(11);
        Interval interval = Interval.between(x, y);
        assertTrue(point.isBelongsTo(interval));
    }

    @Test
    public void isBorderOf_randomPointAndIntervalWithRandomLimits_pointIsLesserLimit_returnTrue() throws Exception {
        Point point = Point.valueOf(-2);
        Point x = Point.valueOf(-2);
        Point y = Point.valueOf(11);
        Interval interval = Interval.between(x, y);
        assertTrue(point.isBorderOf(interval));
    }

    @Test
    public void isBorderOf_randomPointAndIntervalWithRandomLimits_pointIsLargerLimit_returnTrue() throws Exception {
        Point point = Point.valueOf(11);
        Point x = Point.valueOf(-2);
        Point y = Point.valueOf(11);
        Interval interval = Interval.between(x, y);
        assertTrue(point.isBorderOf(interval));
    }

    @Test
    public void isBorderOf_randomPointAndIntervalWithRandomLimits_pointIsInButNotLimit_returnFalse() throws Exception {
        Point point = Point.valueOf(10);
        Point x = Point.valueOf(-2);
        Point y = Point.valueOf(11);
        Interval interval = Interval.between(x, y);
        assertFalse(point.isBorderOf(interval));
    }

    @Test
    public void isBorderOf_randomPointAndZeroLengthInterval_pointIsEquals_returnTrue() throws Exception {
        Point point = Point.valueOf(-2);
        Point x = Point.valueOf(-2);
        Point y = Point.valueOf(-2);
        Interval interval = Interval.between(x, y);
        assertTrue(point.isBorderOf(interval));
    }

    @Test
    public void isBorderOf_randomPointAndIntervalWithRandomLimits_pointIsOut_returnFalse() throws Exception {
        Point point = Point.valueOf(99);
        Point x = Point.valueOf(-2);
        Point y = Point.valueOf(11);
        Interval interval = Interval.between(x, y);
        assertFalse(point.isBorderOf(interval));
    }
}