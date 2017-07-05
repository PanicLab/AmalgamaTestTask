package com.paniclab.amalgama;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Сергей on 04.07.2017.
 */
public class Subset {
    private Set<Segment> segmentSet;
    private Set<Point> pointSet;

    private Subset(Builder builder) {
        this.segmentSet = builder.segments;
        this.pointSet = createPointSet();
    }

    private Set<Point> createPointSet() {
        Set<Point> points = new HashSet<>(getSegments().size()*2 + 1, 1.0f);
        for (Segment segment: getSegments()) {
            points.addAll(segment.getPoints());
        }
        return points;
    }

    public static Builder builder() {
        return new Builder();
    }


    public Set<Segment> getSegments() {
        return segmentSet;
    }


    public Set<Point> getPoints() {
        return pointSet;
    }

    public int size() {
        return segmentSet.size();
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
