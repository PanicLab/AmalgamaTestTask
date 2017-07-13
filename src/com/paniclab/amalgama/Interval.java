package com.paniclab.amalgama;

import java.math.BigDecimal;
import java.util.*;

import static com.paniclab.amalgama.Util.isNot;

/**
 * Created by Сергей on 04.07.2017.
 * Класс представляет собой абстракцию одного из отрезков подмножества.
 * Экземпляр класса создается методами статической генерации:
 *                  between(Point, Point);
 *                  newInstance(Point, Point);
 * Последний за кулисами просто вызывает between(Point, Point).
 * Передача null в качестве любого из аргументов влечет возбуждение NullPointerException. При попытке создать отрезок
 * на базе двух одинаковых бесконечностей возбуждается исключение IntervalException. Создание отрезка на базе плюс- и
 * минус- бесконечностей допустимо, два таких отрезка считаются равными.
 * В случае правильно построенного отрезка метод getLimits() возвращает неизменяемый экземпляр Set, состоящий из двух
 * экземпляров класса Point. В случае отрезка нулевой длины метод возвращает Set, состоящий из одного экземпляра класса
 * Point.
 * Экземпляры класса неизменяемы, их использование в многопоточной среде безопасно.
 * Метод IsOverlapsWith() возвращает true, если два отрезка содержат один другой, примыкают друг к другу или
 * накладываются один на другой.
 */
public class Interval {
    private Point lesserLimit;
    private Point largerLimit;

    private Interval(Point limit, Point anotherLimit) {
        if(limit == null || anotherLimit == null) throw new NullPointerException();
        if(limit.equals(anotherLimit) && anotherLimit.isInfinity()) throw new IntervalException("Ошибка при создании отрезка. " +
                "Обе точки отрезка равны минус или плюс бесконечность");

        if(limit.compareTo(anotherLimit) < 0) {
            this.lesserLimit = limit;
            this.largerLimit = anotherLimit;
        }
        if(limit.compareTo(anotherLimit) > 0) {
            this.lesserLimit = anotherLimit;
            this.largerLimit = limit;
        }
        if(limit.compareTo(anotherLimit) == 0) {
            this.lesserLimit = limit;
            this.largerLimit = limit;
        }
    }

    public static Interval between(Point limit, Point anotherLimit) {
        return new Interval(limit, anotherLimit);
    }

    public static Interval between(Number limit, Number anotherLimit) {
        Point x = Point.valueOf(limit);
        Point y = Point.valueOf(anotherLimit);
        return between(x, y);
    }

    public static Interval between(String limit, String anotherLimit) {
        Point x = Point.valueOf(limit);
        Point y = Point.valueOf(anotherLimit);
        return between(x, y);
    }

    public static Interval between(BigDecimal limit, BigDecimal anotherLimit) {
        Point x = Point.valueOf(limit);
        Point y = Point.valueOf(anotherLimit);
        return between(x, y);
    }

    public static Interval newInstance(Point limit, Point anotherLimit) {
        return between(limit, anotherLimit);
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


    public boolean isIncludedBy(Interval other) {
        if(this.lesserLimit().lessThen(other.lesserLimit())) return false;
        if(this.lesserLimit().moreThen(other.largerLimit())) return false;
        if(this.largerLimit().lessThen(other.lesserLimit())) return false;
        if(this.largerLimit().moreThen(other.largerLimit())) return false;
        return true;
    }


    public boolean isNeighborsWith(Interval another) {
        if(this.equals(another)) return false;
        if(this.isContains(another) || another.isContains(this)) return false;
        return this.getLimits().contains(another.lesserLimit()) ||
                this.getLimits().contains(another.largerLimit());
    }

    public boolean isContains(Point point) {
        return point.isBelongsTo(this);
    }

    public boolean isContains(Interval another) {
        if(this.lesserLimit().moreThen(another.lesserLimit())) return false;
        if(this.largerLimit().lessThen(another.largerLimit())) return false;
        return true;
    }

    public boolean isOverlapsWith(Interval another) {
        return this.lesserLimit().isBelongsTo(another) ||
                this.largerLimit().isBelongsTo(another) ||
                another.lesserLimit().isBelongsTo(this) ||
                another.largerLimit().isBelongsTo(this);
    }


    public Point lesserLimit() {
        return lesserLimit;
    }


    public Point largerLimit() {
        return largerLimit;
    }


    public Set<Point> getLimits() {
        Set<Point> pointSet = new HashSet<>(2 + 1, 1.0f);
        pointSet.add(lesserLimit);
        pointSet.add(largerLimit);
        return Collections.unmodifiableSet(pointSet);
    }

    public Interval mergeWith(Interval other) {
        if(isNot(this.isOverlapsWith(other))) throw new IntervalException("Ошибка при попытке слить два отрезка, " +
                "не имеющих наложения друг на друга." + System.lineSeparator() + this + System.lineSeparator() + other);

        Point newLesserLimit;
        Point newLargerLimit;
        if(this.lesserLimit().lessThenOrEquals(other.lesserLimit())) {
            newLesserLimit = this.lesserLimit();
        } else {
            newLesserLimit = other.lesserLimit();
        }
        if(this.largerLimit().moreThenOrEquals(other.largerLimit())) {
            newLargerLimit = this.largerLimit();
        } else {
            newLargerLimit = other.largerLimit();
        }

        return Interval.between(newLesserLimit, newLargerLimit);
    }

    public Interval getSuperposition(Interval other) {
        if(isNot(this.isOverlapsWith(other))) throw new IntervalException("Ошибка при попытке получить суперпозицию " +
                "двух отрезков, не имеющих наложения друг на друга." + System.lineSeparator() + this +
                System.lineSeparator() + other);

        Point newLesserLimit;
        Point newLargerLimit;
        if(this.lesserLimit().lessThenOrEquals(other.lesserLimit())) {
            newLesserLimit = other.lesserLimit();
        } else {
            newLesserLimit = this.lesserLimit();
        }
        if(this.largerLimit().moreThenOrEquals(other.largerLimit())) {
            newLargerLimit = other.largerLimit();
        } else {
            newLargerLimit = this.largerLimit();
        }

        return Interval.between(newLesserLimit, newLargerLimit);
    }

    public Set<Interval> getSuperposition(Subset subset) {
        if (subset == Subset.EMPTY) return Collections.emptySet();
        List<Interval> resultList = new LinkedList<>();
        for(Interval other : subset.getIntervals()) {
            if(isOverlapsWith(other)) resultList.add(getSuperposition(other));
        }
        Set<Interval> resultSet = new HashSet<>(resultList.size()+1, 1.0f);
        resultSet.addAll(resultList);
        return resultSet;
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

    @Override
    public String toString() {
        String lesser;
        String larger;
        if(lesserLimit.isNegativeInfinity()) {
            lesser = "NEGATIVE INFINITY";
        } else {
            lesser = lesserLimit.value().toString();
        }
        if(largerLimit.isPositiveInfinity()) {
            larger = "POSITIVE INFINITY";
        } else {
            larger = largerLimit.value().toString();
        }

        return "Interval(" + lesser + ", " + larger + ") @" + hashCode();
    }
}
