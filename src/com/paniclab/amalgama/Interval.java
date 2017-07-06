package com.paniclab.amalgama;

import java.math.BigDecimal;
import java.util.*;

import static com.paniclab.amalgama.Util.isNot;

/**
 * Created by Сергей on 04.07.2017.
 * Класс представляет собой абстракцию одного из отрезков подмножества.
 * Экземпляр класса создается методом статической генерации. Передача null в качестве любого из аргументов влечет
 * возбуждение NullPointerException. при попытке создать отрезок на базе двух одинаковых бесконечностей возбуждается
 * исключение IntervalException. Создание отрезка на базе плюс- и минус- бесконечностей допустимо, два таких отрезка
 * считаются равными.
 * В случае правильно построенного отрезка метод getPoints() возвращает Set, состоящий из двух экземпляров класса Point.
 * В случае отрезка нулевой длины метод возвращает Set, состоящий из одного экземпляра класса Point.
 * Экземпляры класса неизменяемы и безопасны в многопоточной среде.
 */
public class Interval {
    private Point lesserLimit;
    private Point largerLimit;

    private Interval(Point one, Point another) {
        if(one == null || another == null) throw new NullPointerException();
        if(one.equals(another) && another.isInfinity()) throw new IntervalException("Ошибка при создании отрезка. " +
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

    public static Interval newInstance(Point one, Point another) {
        return new Interval(one, another);
    }


    public boolean hasZeroLength() {
        return lesserLimit.compareTo(largerLimit) == 0;
    }


    public boolean hasInfiniteLength() {
        return lesserLimit() == Point.NEGATIVE_INFINITY &&
                largerLimit() == Point.POSITIVE_INFINITY;
    }


    public boolean isHalfInterval() {
        if(this.hasInfiniteLength()) return false;
        return lesserLimit().isInfinity() || largerLimit().isInfinity();
    }


    public boolean isNegativeHalfInterval() {
        return this.isHalfInterval() && this.lesserLimit().isInfinity();
    }


    public boolean isPositiveHalfInterval() {
        return this.isHalfInterval() && this.largerLimit().isInfinity();
    }


    //TODO тесты
    public boolean isBiggerThen(Interval other) {
        if(this.hasInfiniteLength() && other.hasInfiniteLength()) return false;
        if(this.hasInfiniteLength() && isNot(other.hasInfiniteLength())) return true;
        if(other.hasInfiniteLength() && isNot(this.hasInfiniteLength())) return false;

        if(isNot(this.isHalfInterval()) && isNot(other.isHalfInterval())) {
            return this.length().compareTo(other.length()) > 0;
        }

        if(this.isHalfInterval() && isNot(other.isHalfInterval())) return true;
        if(isNot(this.isHalfInterval() && other.isHalfInterval())) return false;

        if(this.isHalfInterval() && other.isHalfInterval()) {
            if(this.isNegativeHalfInterval() && other.isNegativeHalfInterval()) {
                return this.largerLimit().moreThen(other.largerLimit());
            }
            if(this.isPositiveHalfInterval() && other.isPositiveHalfInterval()) {
                return this.lesserLimit().lessThen(other.lesserLimit());
            }

            if(this.isNegativeHalfInterval() && other.isPositiveHalfInterval()) {
                if(this.largerLimit().equals(Point.ZERO) && other.lesserLimit().equals(Point.ZERO)) {
                    return false;
                }
                if(this.largerLimit().lessThen(Point.ZERO) && other.lesserLimit().moreThen(Point.ZERO)) {
                    return this.largerLimit().absValue().compareTo(other.lesserLimit().value()) < 0;
                }
                if(this.largerLimit().moreThenOrEquals(Point.ZERO) && other.lesserLimit().moreThen(Point.ZERO)) {
                    return true;
                }
                if(this.largerLimit().lessThen(Point.ZERO) && other.lesserLimit().lessThenOrEquals(Point.ZERO)) {
                    return false;
                }
                if(this.largerLimit().moreThen(Point.ZERO) && other.lesserLimit().lessThen(Point.ZERO)) {
                    return this.largerLimit().value().compareTo(other.lesserLimit().absValue()) > 0;
                }
            }

            if(other.isNegativeHalfInterval() && this.isPositiveHalfInterval()) {
                if(other.largerLimit().equals(Point.ZERO) && this.lesserLimit().equals(Point.ZERO)) {
                    return false;
                }
                if(other.largerLimit().lessThen(Point.ZERO) && this.lesserLimit().moreThen(Point.ZERO)) {
                    return other.largerLimit().absValue().compareTo(this.lesserLimit().value()) < 0;
                }
                if(other.largerLimit().moreThenOrEquals(Point.ZERO) && this.lesserLimit().moreThen(Point.ZERO)) {
                    return false;
                }
                if(other.largerLimit().lessThen(Point.ZERO) && this.lesserLimit().lessThenOrEquals(Point.ZERO)) {
                    return true;
                }
                if(other.largerLimit().moreThen(Point.ZERO) && this.lesserLimit().lessThen(Point.ZERO)) {
                    return other.largerLimit().value().compareTo(this.lesserLimit().absValue()) < 0;
                }
            }
        }

        throw new InternalError("Попытка сравнения двух интервалов не удалась из-за внутренней ошибки. Обратитесь к " +
                "разработчику");
    }


    //TODO
    public boolean isSmallerThen(Interval other) {
        return this.length().compareTo(other.length()) < 0;
    }

    private BigDecimal length() {
        if(this.hasInfiniteLength() || this.isHalfInterval()) throw new IntervalException("Ошибка при попытке " +
                "определения длины бесконечного отрезка или полуинтервала");

        return largerLimit().value().subtract(lesserLimit().value());
    }


    public boolean isSubintervalOf(Interval other) {
        if(this.lesserLimit().lessThen(other.lesserLimit())) return false;
        if(this.lesserLimit().moreThen(other.largerLimit())) return false;
        if(this.largerLimit().lessThen(other.lesserLimit())) return false;
        if(this.largerLimit().moreThen(other.largerLimit())) return false;
        return true;
    }


    public boolean hasCommonBorderWith(Interval other) {
        return this.getPoints().contains(other.lesserLimit()) ||
                this.getPoints().contains(other.largerLimit());
    }


    public Point lesserLimit() {
        return lesserLimit;
    }


    public Point largerLimit() {
        return largerLimit;
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

        Interval other = (Interval) obj;
        return this.largerLimit.equals(other.largerLimit) &&
                this.lesserLimit.equals(other.lesserLimit);
    }
}
