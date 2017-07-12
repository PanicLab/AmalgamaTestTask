package com.paniclab.amalgama;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by Сергей on 12.07.2017.
 */
public class Superposition {
    private List<Subset> subsetList;
    private Subset.Builder builder = Subset.builder();

    private Superposition() {}

    private Superposition(List<Subset> subsetList) {
        this.subsetList = Collections.unmodifiableList(subsetList);
    }

    private Superposition(Builder builder) {
        this.subsetList = Collections.unmodifiableList(builder.subsetList);
    }

    public static Superposition createFrom(List<Subset> subsetList) {
        if (subsetList == null) throw new IllegalStateException("Попытка создания пустого объекта типа " +
                "Superposition");
        return new Superposition(subsetList);
    }

    public static Superposition.Builder builder() {
        return new Builder();
    }

    public Subset resolve() {
        if (subsetList.isEmpty()) return Subset.EMPTY;
        if (subsetList.size() == 1) return subsetList.get(0);

        for (Interval interval : subsetList.get(0).getIntervals()) {
            builder.add(interval);
        }

        for(int i = 1; i < subsetList.size(); i++) {
            Subset currentSubset = subsetList.get(i);

            Subset.Builder temp = Subset.builder();
            for (Interval interval : builder.getIntervals()) {
                Set<Interval> newSuperposition = interval.getSuperposition(currentSubset);
                temp.add(newSuperposition);
            }
            builder = temp;
        }

        return builder.create();
    }


    public static class Builder {
        private List<Subset> subsetList = new ArrayList<>();

        private Builder() {}

        public Builder add(Subset subset) {
            subsetList.add(subset);
            return this;
        }

        public Superposition build() {
            if (subsetList == null) throw new IllegalStateException("Попытка создания пустого объекта типа " +
                    "Superposition");
            return new Superposition(this);
        }
    }
}
