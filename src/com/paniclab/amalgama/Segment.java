package com.paniclab.amalgama;

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


    public boolean isZeroLengthSegment() {
        return lesserLimit.compareTo(largerLimit) == 0;
    }

    public Point lesserLimit() {
        return lesserLimit;
    }

    public Point largerLimit() {
        return largerLimit;
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
