package com.paniclab.amalgama;

import java.math.BigDecimal;

/**
 * Created by Сергей on 04.07.2017.
 */
public class Point implements Valuable<BigDecimal>, Comparable<Point> {
    private BigDecimal value;

    private Point(String str) {
        value = new BigDecimal(str);
    }

    private Point(BigDecimal val) {
        this.value = val;
    }

    public static Point newInstance(String str) {
        return new Point(str);
    }

    public static Point newInstance(Number number) {
        return new Point(number.toString());
    }

    public static Point newInstance(BigDecimal bd) {
        return new Point(bd);
    }


    @Override
    public BigDecimal value() {
        return value;
    }

    public boolean lessThen(Point other) {
        return this.compareTo(other) < 0;
    }

    public boolean moreThen(Point other) {
        return this.compareTo(other) > 0;
    }

    @Override
    public int hashCode() {
        return value().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(this == obj) return true;
        if(!(this.hashCode() == obj.hashCode())) return false;
        if(!(getClass().equals(obj.getClass()))) return false;
        Point other = (Point) obj;
        return value().equals(other.value());
    }

    @Override
    public int compareTo(Point other) {
        return value().compareTo(other.value());
    }
}
