package com.paniclab.amalgama;

import java.math.BigDecimal;
import java.util.*;

import static com.paniclab.amalgama.Util.isNot;

/**
 * Класс представляет собой абстракцию подмножества, состоящего из некоторого набора отрезков и/или полуинтервалов,
 * которые НЕ НАКЛАДЫВАЮТСЯ друг на друга.
 * Экземпляр класса создается следующим образом:
 *
 *                              Subset subset = Subset.builder()
 *                                                      .add(interval1)
 *                                                      .add(interval2)
 *                                                      .add(interval3)
 *                                                      .add(interval4)
 *                                                      .add(interval5)
 *                                                      .create();
 *
 * Пустое подмножество представлено константой Subset.EMPTY. Метод builder() возвращают экземпляр вложенного класса
 * Subset.Builder, который используется для построения экземпляра класса Subset. Однако при добавлении интревалов
 * может возникнуть ситуация, когда два или более интервала перекрывают друг друга. Такое подмножество требует
 * приведения к правильному виду, при котором отрезки подмножества не накладываются друг на друга. Метод
 * builder(Subset.Mode mode) позволяет задать правила, применяемые при создании подмножества (объекта типа Subset).
 * Метод принимает в качестве аргумента экземпляр вложенного перечисления Subset.Mode:
 *
 *                              Subset subset = Subset.builder(NORMALIZE)
 *                                                      .add(interval1)
 *                                                      .add(interval2)
 *                                                      .create();
 *
 *                              Subset subset = Subset.builder(THROW)
 *                                                      .add(interval1)
 *                                                      .add(interval2)
 *                                                      .create();
 *
 * В первом случае перекрываемые друг с другом отрезки и полуинтервалы будут автоматически складываться, образуя таким
 * образом правильное подмножество. Во втором случае при обнаружении перекрываемых отрезков метод add(Interval) класса
 * Subset.Builder возбуждает исключение NotNormalizedSubsetException. Метод  builder() эквивалентен вызову метода
 * builder(THROW).
 * Метод getNearestOrElse(Point) принимает в качестве аргумента объект типа Point (точку) и возвращает сам аргумент в
 * случае, если он принадлежит подмножеству (т.е. одному из интервалов подмножества). В случае, если объект типа Point
 * не принадлежит ни одному интервалу множества, метод возвращает максимально близкую к аргументу точку (объект типа
 * Point). Если таковых будет несколько - точка будет выбрана случайно. В случае, если метод вызван для пустого
 * множества, будет возбуждено исключение IllegalStateException. Иетод гарантирует среднюю сложность O(log(N)) и
 * максимальную сложность O(2*log(N))
 * Поведение остальных методов класса соответствует названию. Методы, возвращающие колекции, за исключением метода
 * getIntervalList(), возвращают неизменяемые представления коллекций. Попытка добавления в них элементов влечет
 * возбуждение исключения UnsupportedOperationException.
 * Экземпляр класса неизменяемы, и их использование в многопоточно среде безопасно.
 */
public class Subset {
    public static final Subset EMPTY = new Subset();
    private NavigableMap<Point, Interval> table;
    private Set<Interval> intervalSet;


    private Subset() {
        this.table = Collections.emptyNavigableMap();
        this.intervalSet = Collections.emptySet();
    }

    private Subset(Builder builder) {
        this.table = Collections.unmodifiableNavigableMap(builder.table);
        this.intervalSet = Collections.unmodifiableSet(new HashSet<>(table.values()));
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


    public List<Interval> getIntervalList() {
        List<Interval> result = new ArrayList<>(getIntervals().size());
        result.addAll(getIntervals());
        return result;
    }


    public Set<Point> getPoints() {
        return Collections.unmodifiableNavigableSet(table.navigableKeySet());
    }

    public int size() {
        return intervalSet.size();
    }

    public boolean isNotEmpty() {
        return this != EMPTY;
    }


    public Point getNearestOrElse(Point specifiedPoint) {
/*      Код метода базируется на контракте класса Subset, согласно которому подмножество состоит из некоторого числа
        отрезков и/или полуинтервалов, которые НЕ ПЕРЕКРЫВАЮТСЯ. Внутренние данные класса хранятся в поле типа
        NavigableMap, в котором значениями (value) являются интервалы, а ключами (key) - точки, ограничивающие эти
        отрезки (точки представлены классом Point, реализующим интерфейс Comparable). Таким образом, мы имеем полный
        набор отрезков и точек всего подмножества, увязанных в единую структуру. Всё, что нам нужно, это выполнить две
        дорогостоящие операции - найти точки, ближайшие к заданной точке, слева и справа (в нашем случае - log(N)).
        Далее, находим отрезок, соответствующий точке слева - O(1) - и проверяем принадлежность заданной точки к этому
        отрезку O(1). Возвращаем заданную точку, если тест выдал положительный результат. Если нет - точка лежит в
        пустоте между отрезками. Тогда строим два гипотетических отрезка "крайняя точка - заданная точка" - O(1), и на
        основании их длин выбираем точку из двух крайних, ближайшую к заданной - O(1). Не забываем при этом обработать
        особые случаи, связанные с полуинтервалами O(1).
*/

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
            /*для точки справа такую проверку делать не нужно. Согласно контракта класса, интервалы не перекрываются.
            Так что, если уж мы не попали в отрезок в случае с левой точкой, значит мы попали в пустоту между отрезками
             */
            //if (table.get(nearestRight).isContains(specifiedPoint)) return specifiedPoint;
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

    @Override
    public int hashCode() {
        return Objects.hash(table);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(this == obj) return true;
        if(isNot(this.hashCode() == obj.hashCode())) return false;
        if(isNot(obj instanceof Subset)) return false;
        Subset other = Subset.class.cast(obj);
        return table.equals(other.table);
    }

    public static class Builder {
        private NavigableMap<Point, Interval> table = new TreeMap<>();
        private Mode normalizeMode = Mode.THROW;

        private Builder() {}
        private Builder(Mode mode) {
            this.normalizeMode = mode;
        }

        public Builder add(Interval interval) {
            addWithRegardingToMode(interval);
            return this;
        }

        void add(Set<Interval> intervalSet) {
            for (Interval interval : intervalSet) {
                addWithRegardingToMode(interval);
            }
        }

        private void addWithRegardingToMode(Interval interval) {
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

        public boolean isEmpty() {
            return table.isEmpty();
        }

        Set<Interval> getIntervals() {
            return new HashSet<>(table.values());
        }

        void remove(Interval interval) {
            table.remove(interval.lesserLimit());
            table.remove(interval.largerLimit());
        }
    }

    public enum Mode {
        NORMALIZE, THROW
    }
}
