package com.paniclab.amalgama;

import java.math.BigDecimal;
import java.util.*;

import static com.paniclab.amalgama.Util.isNot;

/**
 * Created by Сергей on 04.07.2017.
 * Класс представляет собой абстракцию одного из отрезков подмножества.
 * Экземпляр класса создается методом статической генерации. Передача null в качестве любого из аргументов влечет
 * возбуждение NullPointerException.
 * В случае правильно построенного отрезка метод getPoints() возвращает Set, состоящий из двух экземпляров класса Point.
 * В случае отрезка нулевой длины метод возвращает Set, состоящий из одного экземпляра класса Point.
 * Экземпляры класса неизменяемы и безопасны в многопоточной среде.
 */
public class Segment {
    private Point lesserLimit;
    private Point largerLimit;

    private Segment(Point one, Point another) {
        if(one == null || another == null) throw new NullPointerException();
        if(one.equals(another) && another.isInfinity()) throw new WrongSegmentException("Ошибка при создании отрезка. " +
                "Обе точки отрезка равны минус или плюс бесконечность");

        if(one.compareTo(another) < 0) {
            this.lesserLimit = one;
            this.largerLimit = another;
        }
        if(one.compareTo(another) > 0) {
            this.lesserLimit = another;
            this.largerLimit = one;
        }
        if(one.compareTo(another) == 0) {
            this.lesserLimit = one;
            this.largerLimit = one;
        }
    }

    public static Segment newInstance(Point one, Point another) {
        return new Segment(one, another);
    }


    public boolean hasZeroLength() {
        return lesserLimit.compareTo(largerLimit) == 0;
    }

    public boolean hasInfiniteLength() {
        return lesserLimit() == Point.NEGATIVE_INFINITY &&
                largerLimit() == Point.POSITIVE_INFINITY;
    }

    public boolean isHalfRange() {
        if(this.hasInfiniteLength()) return false;
        return lesserLimit().isInfinity() || largerLimit().isInfinity();
    }

    public boolean isNegativeHalfRange() {
        return this.isHalfRange() && this.lesserLimit().isInfinity();
    }

    public boolean isPositiveHalfRange() {
        return this.isHalfRange() && this.largerLimit().isInfinity();
    }

    //TODO
    public boolean isBiggerThen(Segment other) {
        return this.length().compareTo(other.length()) > 0;
    }

    //TODO
    public boolean isSmallerThen(Segment other) {
        return this.length().compareTo(other.length()) < 0;
    }

    public boolean isSubsegmentOf(Segment other) {
        if(this.lesserLimit().lessThen(other.lesserLimit())) return false;
        if(this.lesserLimit().moreThen(other.largerLimit())) return false;
        if(this.largerLimit().lessThen(other.lesserLimit())) return false;
        if(this.largerLimit().moreThen(other.largerLimit())) return false;
        return true;
    }

    public boolean hasCommonBorderWith(Segment other) {
        return this.getPoints().contains(other.lesserLimit()) ||
                this.getPoints().contains(other.largerLimit());
    }


    public Point lesserLimit() {
        return lesserLimit;
    }

    public Point largerLimit() {
        return largerLimit;
    }

    private BigDecimal length() {
        if(this.hasInfiniteLength() || this.isHalfRange()) throw new WrongSegmentException("Ошибка при попытке " +
                "определения длины бесконечного отрезка или полуинтервала");

        return largerLimit().value().subtract(lesserLimit().value());
    }


    public Set<Point> getPoints() {
        Set<Point> pointSet = new HashSet<>(2);
        pointSet.add(lesserLimit);
        pointSet.add(largerLimit);
        return pointSet;
    }


    @Override
    public int hashCode() {
        return Objects.hash(lesserLimit, largerLimit);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(this == obj) return true;
        if(this.hashCode() != obj.hashCode()) return false;
        if(isNot(this.getClass().equals(obj.getClass()))) return false;

        Segment other = (Segment) obj;
        return this.largerLimit.equals(other.largerLimit) &&
                this.lesserLimit.equals(other.lesserLimit);
    }
}
