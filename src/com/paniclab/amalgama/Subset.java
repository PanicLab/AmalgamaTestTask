package com.paniclab.amalgama;

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
