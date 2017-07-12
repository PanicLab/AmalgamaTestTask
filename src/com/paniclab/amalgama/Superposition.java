package com.paniclab.amalgama;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Сергей on 12.07.2017.
 */
public class Superposition {
    private List<Subset> subsetList;
    private Subset superposition;

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

    public Subset calculate() {
        if (subsetList.isEmpty()) return Subset.EMPTY;
        return null;
    }


    public static class Builder {
        private List<Subset> subsetList = new ArrayList<>();

        private Builder() {}

        public Builder addSubset(Subset subset) {
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
