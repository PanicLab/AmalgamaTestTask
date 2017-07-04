package com.paniclab.amalgama;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by Сергей on 04.07.2017.
 */
public class PointTest {

    @Test
    public void equals_isReflexive() throws Exception {
        Point x = Point.newInstance("3");
        assertTrue(x.equals(x));
    }

    @Test
    public void equals_isSymmetric() throws Exception {
        Point x = Point.newInstance(3.14);
        Point y = Point.newInstance("3.14");
        assertEquals(x, y);
        assertTrue(x.equals(y));
        assertTrue(y.equals(x));
    }

    @Test
    public void equals_isTransitive() throws Exception {
        Point x = Point.newInstance(3.14);
        Point y = Point.newInstance("3.14");
        Point z = Point.newInstance(new BigDecimal("3.14"));
        assertEquals(x, y);
        assertEquals(y, z);

        assertTrue(x.equals(y));
        assertTrue(y.equals(z));
        assertTrue(x.equals(z));
    }

    @Test
    public void equals_isConsistent() throws Exception {
        Point x = Point.newInstance(3.14);
        Point y = Point.newInstance("3.14");

        for(int i = 0; i < 100; i++) {
            assertTrue(x.equals(y));
        }
    }

    @Test
    public void equals_takesNull_returnFalse() throws Exception {
        Point x = Point.newInstance("3.14");
        Point y = null;

        assertFalse(x.equals(y));
    }


    @Test
    public void hashCode_test() throws Exception {
        Point x = Point.newInstance("3.14");
        BigDecimal number = new BigDecimal("3.14");

        int expected = x.hashCode();
        int result = number.hashCode();

        assertEquals(expected, result);
    }


    @Test
    public void compareTo_isConsistentWithEquals_takesEqualValues_returnZero() throws Exception {
        Point x = Point.newInstance(3.14);
        Point y = Point.newInstance("3.14");

        assertEquals(x, y);
        assertTrue(x.compareTo(y) == 0);
    }

    @Test
    public void compareTo_biggerOne_returnNegativeValue() throws Exception {
        Point x = Point.newInstance(3.14);
        Point y = Point.newInstance("100");

        assertTrue(x.compareTo(y) < 0);
    }

    @Test
    public void compareTo_smallerOne_returnPositiveValue() throws Exception {
        Point x = Point.newInstance(3.14);
        Point y = Point.newInstance("-1");

        assertTrue(x.compareTo(y) > 0);
    }


    @Test
    public void newInstance() throws Exception {
        Point x = Point.newInstance(3.14);
        Point y = Point.newInstance("3.14");
        Point z = Point.newInstance(new BigDecimal("3.14"));

        assertTrue(x.equals(y));
        assertTrue(y.equals(z));
        assertTrue(x.equals(z));
    }

    @Test(expected = NullPointerException.class)
    public void newInstance_takesNull_throwsNullPointerException() throws Exception {
        String s = null;
        Point x = Point.newInstance(s);
    }

    @Test
    public void value_createInstanceWithBigDecimalArg_returnSame() throws Exception {
        BigDecimal number = new BigDecimal("3.1415");
        Point x = Point.newInstance(number);

        assertEquals(x.value(), number);
    }
}