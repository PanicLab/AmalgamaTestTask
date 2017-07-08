package com.paniclab.amalgama;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Сергей on 04.07.2017.
 */
public class Subset {
    private Set<Interval> intervalSet;
    private Set<Point> pointSet;

    private Subset(Builder builder) {
        this.intervalSet = Collections.unmodifiableSet(builder.intervals);
        this.pointSet = createPointSet();
    }

    private Set<Point> createPointSet() {
        Set<Point> points = new HashSet<>(getIntervals().size()*2 + 1, 1.0f);
        for (Interval interval : getIntervals()) {
            points.addAll(interval.getLimits());
        }
        return Collections.unmodifiableSet(points);
    }

    public static Builder builder() {
        return new Builder();
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

    public static class Builder {
        private Set<Interval> intervals;

        private Builder() {
            this.intervals = new HashSet<>();
        }

        public Builder addSegment(Interval interval) {
            intervals.add(interval);
            return this;
        }

        public Subset create() {
            validateSegmentSet();
            return new Subset(this);
        }

        private void validateSegmentSet() {}
    }


}
