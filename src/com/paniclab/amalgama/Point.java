package com.paniclab.amalgama;

import java.math.BigDecimal;
import java.util.Objects;

import static com.paniclab.amalgama.Util.isNot;

/**
 * Абстракция, представляющая точку на плоскости координат.
 * Плюс и минус бесконечности, а также точка "ноль" представлены специальными константами.
 *
 *              Point.NEGATIVE_INFINITY;
 *              Point.POSITIVE_INFINITY;
 *              Point.ZERO;
 *
 * Согласно контракта, точка "плюс бесконечность" всегда больше любой другой точки, точка "минус бесконечность" всегда
 * меньше любой другой точки. Две плюс бесконечности или две минус бесконечности равны между собой.
 * Попытка вызвать методы value() и absValue() для бесконечной точки приводит к возбуждению исключения PointException.
 * Экземпляр класса создается одним из методов статической генерации:
 *
 *              Point.valueOf(String str);
 *              Point.valueOf(Number number);
 *              Point.valueOf(BigDecimal bd);
 *
 * Класс реализует интерфейс Comparable<Point>, так что его экземпляры обладают свойством natural ordering.
 * Первый из них бросает в runtime исключение PointException в случае, если не удается интерпретировать строку.
 * Названия методов класса говорят сами за себя, и их назначение интуитивно понятно.
 * Экземпляры класса неизменяемы, их использование в многопоточной среде безопасно.
 */
public class Point implements Comparable<Point> {

    public static final Point POSITIVE_INFINITY = new Point(INFINITY.POSITIVE);
    public static final Point NEGATIVE_INFINITY = new Point(INFINITY.NEGATIVE);
    public static final Point ZERO = valueOf(0);

    private final BigDecimal value;
    private final INFINITY infinityFlag;

    private Point(INFINITY status) {
        this.value = BigDecimal.ZERO;
        this.infinityFlag = status;
    }

    private Point(String str) {
        try {
            value = new BigDecimal(str);
        } catch (NumberFormatException ex) {
            throw new PointException("Ощибка при создании объекта Point - не удалось интерпретировать вводимые " +
                    "данные." + System.lineSeparator() + "Ошибочный аргумент: " + str, ex);
        }
        infinityFlag = null;
    }

    private Point(BigDecimal val) {
        this.value = val;
        infinityFlag = null;
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


    public BigDecimal value() {
        if(this.isInfinity()) throw new PointException("Попытка получить значение координаты бесконечности." +
        System.lineSeparator() + this);
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


    /**
     * Метод определяет, находится ли точка в пределах заданного отрезка, возвращая true в этом случае. Если точка равна
     * одной из границ отрезка, метод возвращает true.
    */
    public boolean isIn(Interval interval) {
        if(this.lessThen(interval.lesserLimit())) return false;
        if(this.moreThen(interval.largerLimit())) return false;
        return true;
    }

    public boolean isBorderOf(Interval interval) {
        return this.equals(interval.lesserLimit()) || this.equals(interval.largerLimit());
    }


    @Override
    public int hashCode() {
        if (this.isInfinity()) {
            return Objects.hash(Point.ZERO, infinityFlag);
        } else {
            return Objects.hash(value());
        }
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
    public String toString() {
        if (isInfinity()) {
            return "Объект Point: value = " + infinityFlag + "_INFINITY";
        }
        return "Объект Point: value = " + value();
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
