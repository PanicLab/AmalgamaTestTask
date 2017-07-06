package com.paniclab.amalgama;

import java.math.BigDecimal;
import java.util.Objects;

import static com.paniclab.amalgama.Util.isNot;

/**
 * Created by Сергей on 04.07.2017.
 */
public class Point implements Valuable<BigDecimal>, Comparable<Point> {

    public static final Point POSITIVE_INFINITY = new Point(INFINITY.POSITIVE);
    public static final Point NEGATIVE_INFINITY = new Point(INFINITY.NEGATIVE);
    public static final Point ZERO = valueOf(0);

    private BigDecimal value;
    private INFINITY infinityFlag = null;

    private Point(INFINITY status) {
        this.value = BigDecimal.ZERO;
        this.infinityFlag = status;
    }

    private Point(String str) {
        value = new BigDecimal(str);
    }

    private Point(BigDecimal val) {
        this.value = val;
    }


    public static Point valueOf(String str) {
        return new Point(str);
    }

    public static Point valueOf(Number number) {
        return new Point(number.toString());
    }

    public static Point valueOf(BigDecimal bd) {
        return new Point(bd);
    }


    @Override
    public BigDecimal value() {
        return value;
    }


    public BigDecimal absValue() {
        return this.value().abs();
    }


    public boolean isInfinity() {
        return infinityFlag != null;
    }


    public boolean isNegativeInfinity() {
        return this.infinityFlag == INFINITY.NEGATIVE;
    }


    public boolean isPositiveInfinity() {
        return this.infinityFlag == INFINITY.POSITIVE;
    }


    public boolean lessThen(Point other) {
        return this.compareTo(other) < 0;
    }


    public boolean lessThenOrEquals(Point other) {
        return this.compareTo(other) <= 0;
    }


    public boolean moreThen(Point other) {
        return this.compareTo(other) > 0;
    }


    public boolean moreThenOrEquals(Point other) {
        return this.compareTo(other) >= 0;
    }


    @Override
    public int hashCode() {
        return Objects.hash(value().hashCode(), infinityFlag);
    }


    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(this == obj) return true;
        if(isNot(this.hashCode() == obj.hashCode())) return false;
        if(isNot(getClass().equals(obj.getClass()))) return false;
        Point other = (Point) obj;
        if(this.isInfinity() || other.isInfinity()) return this == other;
        return value().equals(other.value());
    }


    @Override
    public int compareTo(Point other) {
        if(this == other) return 0;
        if(this.isInfinity()) {
            return this.isPositiveInfinity() ? 1 : -1;
        }
        if(other.isInfinity()) {
            return other.isPositiveInfinity() ? -1 : 1;
        }
        return value().compareTo(other.value());
    }


    private enum INFINITY {
        POSITIVE, NEGATIVE
    }
}
