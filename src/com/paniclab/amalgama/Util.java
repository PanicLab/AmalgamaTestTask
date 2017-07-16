package com.paniclab.amalgama;

import java.util.*;

/**
 * Created by Сергей on 04.07.2017.
 */
final class Util {
    static boolean isNot(boolean b) {
        return !b;
    }


    static Interval getSuperpositionOf(Interval first, Interval second) {
        if(isNot(first.isOverlapsWith(second))) return null;
        if(first.equals(second)) return first;

        Set<Point> pointSet = new HashSet<>(4+1, 1.0f);
        pointSet.addAll(first.getLimits());
        pointSet.addAll(second.getLimits());

        Collection<Point> extremePoints = getMinAndMaxElementsOf(pointSet);
        pointSet.removeAll(extremePoints);

        int behavior = pointSet.size();
        final int BOTH_INTERVALS_IS_NOT_ZERO_LENGTH_AND_NOT_EQUALS = 2;
        final int ONE_INTERVAL_IS_ZERO_LENGTH = 1;
        final int BOTH_INTERVALS_IS_NOT_EQUALS_ZERO_LENGTH = 0;

        switch (behavior) {
            case BOTH_INTERVALS_IS_NOT_ZERO_LENGTH_AND_NOT_EQUALS: {
                Iterator<Point> iterator = pointSet.iterator();
                Point x = iterator.next();
                Point y = iterator.next();
                return Interval.between(x, y);
            }
            case ONE_INTERVAL_IS_ZERO_LENGTH: {
                return first.hasZeroLength() ? first : second;
            }
            case BOTH_INTERVALS_IS_NOT_EQUALS_ZERO_LENGTH: {
                return first;
            }
            default: {
                throw new InternalError("Не удалось найти общий участок двух отрезков из-за внутренней ошибки. " +
                        "Обратитесь к разработчику.");
            }
        }
    }


    static <T extends Comparable<T>> Collection<T> getMinAndMaxElementsOf(Collection<T> collection) {
        if(collection.isEmpty()) return collection;
        T min = null;
        T max = null;
        Iterator<T> iterator = collection.iterator();
        if(iterator.hasNext()) {
            T element = iterator.next();
            min = element;
            max = element;

            while (iterator.hasNext()) {
                element = iterator.next();
                if(element.compareTo(min) < 0) min = element;
                if(element.compareTo(max) > 0) max = element;
            }
        }
        Collection<T> result = new ArrayList<>(2);
        if(min == null || max == null) throw new InternalError("Непредвиденная ошибка при обработке экстремальных " +
                "значений набора точек двух отрезков. Обратитесь к разработчику.");
        result.add(min);
        result.add(max);
        return result;
    }
}
