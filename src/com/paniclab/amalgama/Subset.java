package com.paniclab.amalgama;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Сергей on 04.07.2017.
 */
public class Subset {
    private Set<Segment> segments;

    private Subset(Builder builder) {
        this.segments = builder.segments;
    }

    public Set<Segment> getSegments() {
        return segments;
    }

    public Set<Point> getPoints() {
        Set<Point> points = new HashSet<>(getSegments().size()*2);
        for (Segment segment: getSegments()) {
            points.addAll(segment.getPoints());
        }
        return points;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Set<Segment> segments;

        private Builder() {
            this.segments = new HashSet<>();
        }

        public Builder addSegment(Segment segment) {
            segments.add(segment);
            return this;
        }

        public Subset create() {
            validateSegmentSet();
            return new Subset(this);
        }

        private void validateSegmentSet() {}
    }
}
