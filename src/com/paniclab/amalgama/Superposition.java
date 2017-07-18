package com.paniclab.amalgama;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.paniclab.amalgama.Util.isNot;

/**
 * Класс, инкапсулирующий в себе алгоритм пересечения нескольких подмножеств. Экземпляр создается методом статической
 * генерации:
 *
 *                                  createFrom(List<Subset> subsetList);
 *
 * Другой способ создания экземпляра - при помощи статического метода builder(), возвращающего экземпляр внутреннего
 * класса Superposition.Builder, при помощи которого создается экземпляр:
 *
 *                              Superposition resolver = Superposition.builder()
 *                                                                      .add(subset_1)
 *                                                                      .add(subset_2)
 *                                                                      .add(subset_3)
 *                                                                      .build();
 *
 * Создание экземпляра - недорогая операция. Расчёт пересечения подмножеств производится методом resolve(),
 * возвращающего объект типа Subset. Последующий вызов метода не приводит к новым вычислениям, а просто возвращает
 * расчитанный ранее результат. Узнать состояние экземпляра можно при помощи метода isResolved(), если это необходимо.
 * Методы класса не синхронизированы, доступ к методам экземпляра в многопоточной среде должен синхронизироваться извне.
 *
 * Итоговый результат получается следующим образом:
 *
 *                              List<Interval> intervals = Superposition.builder()
 *                                                                          .add(subset_1)
 *                                                                          .add(subset_2)
 *                                                                          .add(subset_3)
 *                                                                          .build()
 *                                                                          .resolve()
 *                                                                          .getIntervalList();
 */
public class Superposition {
    private List<Subset> subsetList;
    private Subset result = null;

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
        if (isResolved()) return result;
        if (subsetList.isEmpty()) return Subset.EMPTY;
        if (subsetList.size() == 1) return subsetList.get(0);

        Subset.Builder subsetBuilder = Subset.builder();
        for (Interval interval : subsetList.get(0).getIntervals()) {
            subsetBuilder.add(interval);
        }

        for(int i = 1; i < subsetList.size(); i++) {
            Subset currentSubset = subsetList.get(i);

            Subset.Builder temp = Subset.builder();
            for (Interval interval : subsetBuilder.getIntervals()) {
                Set<Interval> newSuperposition = interval.getSuperposition(currentSubset);
                temp.add(newSuperposition);
            }
            subsetBuilder = temp;
        }

        result = subsetBuilder.create();
        return result;
    }


    public boolean isResolved() {
        return isNot(result == null);
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
