package com.paniclab.amalgama;

import java.math.BigDecimal;
import java.util.*;

import static com.paniclab.amalgama.Util.isNot;

/**
 * Created by Сергей on 04.07.2017.
 * Класс представляет собой абстракцию одного из отрезков/полуинтервалов подмножества.
 * Экземпляр класса создается методами статической генерации:
 *
 *                  between(Point, Point);
 *                  newInstance(Point, Point);
 *
 * Последний за кулисами просто вызывает between(Point, Point). Кроме указанных, класс предоставляет другие, более
 * удобные, методы статической генерации:
 *
 *                  between(Number limit, Number anotherLimit);
 *                  between(String limit, String anotherLimit);
 *                  between(BigDecimal limit, BigDecimal anotherLimit);
 *
 * Передача null в качестве любого из аргументов влечет возбуждение NullPointerException. При попытке создать отрезок
 * на базе двух одинаковых бесконечностей возбуждается исключение IntervalException. Создание отрезка на базе плюс- и
 * минус- бесконечностей допустимо, два таких отрезка считаются равными.
 * Метод getLimits() возвращает множество, состоящее из границ отрезка/полуинтервала. В случае правильно построенного
 * отрезка метод getLimits() возвращает неизменяемый объект типа Set, состоящий из двух экземпляров класса Point. В
 * случае отрезка нулевой длины метод возвращает неизменяемый объект типа Set, состоящий из одного экземпляра класса
 * Point. Меньшую границу отрезка можно получить вызовом метода lesserLimit(), большую - вызовом метода largerLimit().
 * Отношения между двумя экземплярами класса описываются следующими методами:
 *
 *      - isIncludedBy(Interval other)
 *      - isContains(Interval another)
 *      - isNeighborsWith(Interval another)
 *      - isOverlapsWith(Interval another)
 *
 * Поведение первых двух проистекает из их названия. Метод isNeighborsWith(Interval another) возвращает true в случае,
 * если два отрезка примыкают друг к другу (имеют общую границу). Метод вернет false, если два отрезка содержат один
 * другой, даже если у них есть общая граница. Метод isOverlapsWith(Interval another) возвращает true, если либо два
 * отрезка содержат один другой, либо они накладываются друг на друга (имеют общий участок), либо примыкают друг к
 * другу - метод возвращает true также в случае, если метод isNeighborsWith(Interval another) возвращает true.
 * Если метод isOverlapsWith(Interval another) возвращает true, то два отрезка можно объединить в один, вызвав метод
 * mergeWith(Interval other). Метод возвращает новый экземпляр класса. В случае, если отрезки не перекрываются, вызов
 * метода бросит в runtime исключение IntervalException.
 * Метод getSuperposition(Interval other) возвращает общий отрезок двух накладывающихся друг на друга отрезков или
 * полуинтервалов в виде нового экземпляра класса. В случае,  если отрезки не перекрываются, вызов метода бросит в
 * runtime исключение IntervalException.
 * Метод getSuperposition(Subset subset) возвращает объект типа Set<Interval> или пустое множество, если пересечения
 * между отрезком и подмножеством не существует.
 * Метод isContains(Point point) позволяет определить принадлежность точки к данному отрезку или полуинтвервалу,
 * включая границы отрезка или полуинтервала.
 * Назначение других методов класса понятно из их названия.
 * Экземпляры класса неизменяемы, их использование в многопоточной среде безопасно.
 */
public final class Interval {
    private final Point lesserLimit;
    private final Point largerLimit;

    private Interval(Point limit, Point anotherLimit) {
        if(limit == null || anotherLimit == null) throw new NullPointerException();
        if(limit.equals(anotherLimit) && anotherLimit.isInfinity()) throw new IntervalException("Ошибка при создании " +
                "отрезка или полуинтервала. Обе точки отрезка равны минус или плюс бесконечность" +
                System.lineSeparator() + "limit = " + limit + System.lineSeparator() + "anotherLimit = " +
                anotherLimit);

        Point min = null, max = null;
        if(limit.compareTo(anotherLimit) < 0) {
            min = limit;
            max = anotherLimit;
        }
        if(limit.compareTo(anotherLimit) > 0) {
            min = anotherLimit;
            max = limit;
        }
        if(limit.compareTo(anotherLimit) == 0) {
            min = limit;
            max = limit;
        }

        lesserLimit = min;
        largerLimit = max;
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
        return point.isIn(this);
    }

    public boolean isContains(Interval another) {
        if(this.lesserLimit().moreThen(another.lesserLimit())) return false;
        if(this.largerLimit().lessThen(another.largerLimit())) return false;
        return true;
    }

    public boolean isOverlapsWith(Interval another) {
        return this.lesserLimit().isIn(another) ||
                this.largerLimit().isIn(another) ||
                another.lesserLimit().isIn(this) ||
                another.largerLimit().isIn(this);
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
