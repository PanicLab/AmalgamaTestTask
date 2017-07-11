package com.paniclab.amalgama;

import java.math.BigDecimal;
import java.util.*;

import static com.paniclab.amalgama.Util.isNot;

/**
 * Created by Сергей on 04.07.2017.
 */
public class Subset {
    public static final Subset EMPTY = new Subset();
    private NavigableMap<Point, Interval> table;
    private Set<Interval> intervalSet;
    private Set<Point> pointSet;

    private Subset() {
        this.table = Collections.emptyNavigableMap();
        this.intervalSet = Collections.emptySet();
        this.pointSet = Collections.emptySet();
    }

    private Subset(Builder builder) {
        this.table = Collections.unmodifiableNavigableMap(builder.table);
        this.intervalSet = Collections.unmodifiableSet(new HashSet<>(table.values()));
        this.pointSet = Collections.unmodifiableNavigableSet(table.navigableKeySet());
    }


    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Mode mode) {
        return new Builder(mode);
    }


    public Set<Interval> getIntervals() {
        return intervalSet;
    }


    public Set<Point> getPoints() {
        return pointSet;
    }

    public int size() {
        return intervalSet.size();
    }

    public boolean isNotEmpty() {
        return this != EMPTY;
    }

    public Point getNearestOrElse(Point specifiedPoint) {
        if(this == EMPTY) {
            StringBuilder sb = new StringBuilder();
            sb.append("Попытка обращения к пустому подмножеству").append(System.lineSeparator());
            sb.append(this.toString());
            sb.append("Заданная точка:").append(System.lineSeparator());
            sb.append(specifiedPoint.toString()).append(System.lineSeparator());
            throw new IllegalStateException(sb.toString());
        }
        Point nearestLeft, nearestRight;
        nearestLeft = table.lowerKey(specifiedPoint);
        if(isNot(nearestLeft == null)) {
            if (table.get(nearestLeft).isContains(specifiedPoint)) return specifiedPoint;
        }
        nearestRight = table.higherKey(specifiedPoint);
        if(isNot(nearestRight == null)) {
            if (table.get(nearestRight).isContains(specifiedPoint)) return specifiedPoint;
            if (nearestLeft == null) return nearestRight;
        }
        if(isNot(nearestLeft == null) && nearestRight == null) return nearestLeft;

        BigDecimal left = nearestLeft.value().subtract(specifiedPoint.value()).abs();
        BigDecimal right = nearestRight.value().subtract(specifiedPoint.value()).abs();

        return  (left.compareTo(right) <= 0) ? nearestLeft: nearestRight;
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Объект Subset @").append(hashCode()).append(System.lineSeparator());
        result.append("Размер = ").append(size()).append(System.lineSeparator());
        for(Interval interval : getIntervals()) {
            result.append("\t").append(interval.toString()).append(System.lineSeparator());
        }
        return result.toString();
    }

    public static class Builder {
        private NavigableMap<Point, Interval> table = new TreeMap<>();
        private Mode normalizeMode = Mode.THROW;

        private Builder() {}
        private Builder(Mode mode) {
            this.normalizeMode = mode;
        }

        public Builder addInterval(Interval interval) {
            normalizeAndAdd(interval);
            return this;
        }

        private void normalizeAndAdd(Interval interval) {
            Point nearestFromLeft;
            for(Point examinePoint: interval.getLimits()) {
                nearestFromLeft = table.lowerKey(examinePoint);

                Interval intervalThatPotentiallyCanContainOurExaminePoint;
                Interval intervalThatContainsOurExaminePoint;
                if (isNot(nearestFromLeft == null)) {
                    intervalThatPotentiallyCanContainOurExaminePoint = table.get(nearestFromLeft);

                    if (intervalThatPotentiallyCanContainOurExaminePoint.isContains(examinePoint)) {
                        checkThrowOrProceedRegardingTo(normalizeMode);
                        intervalThatContainsOurExaminePoint = intervalThatPotentiallyCanContainOurExaminePoint;
                        table.remove(intervalThatContainsOurExaminePoint.lesserLimit());
                        table.remove(intervalThatContainsOurExaminePoint.largerLimit());
                        interval = interval.mergeWith(intervalThatContainsOurExaminePoint);
                    }
                }
            }

            table.put(interval.lesserLimit(), interval);
            table.put(interval.largerLimit(), interval);

            nearestFromLeft = table.lowerKey(interval.largerLimit());
            if(isNot(nearestFromLeft == null) && isNot(interval.hasZeroLength())) {
                while(isNot(nearestFromLeft.equals(interval.lesserLimit()))) {
                    checkThrowOrProceedRegardingTo(normalizeMode);
                    table.remove(nearestFromLeft);
                    nearestFromLeft = table.lowerKey(interval.largerLimit());
                }
            }
        }


        private void checkThrowOrProceedRegardingTo(Mode mode) {
            if (mode == Mode.THROW) {
                throw new NotNormalizedSubsetException("Попытка создания подмножества (объекта Subset) из" +
                        "отрезков и/или полуинтервалов, перекрывающих друг друга. Проверьте правильность ввода," +
                        " или измените режим объекта Builder при создании для нормализации подмнеожества");
            }
        }

        public Subset create() {
            return table.isEmpty() ? Subset.EMPTY : new Subset(this);
        }
    }

    public enum Mode {
        NORMALIZE, THROW
    }


}
