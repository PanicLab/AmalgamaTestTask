package com.paniclab.amalgama;

import java.util.*;

/**
 * Created by Сергей on 04.07.2017.
 */
public final class Util {
    public static boolean isNot(boolean b) {
        return !b;
    }


    public static Interval getSuperpositionOf(Interval first, Interval second) {
        if(isNot(first.hasSuperpositionWith(second))) return null;
        if(first.equals(second)) return first;

        Set<Point> pointSet = new HashSet<>(4+1, 1.0f);
        pointSet.addAll(first.getLimits());
        pointSet.addAll(second.getLimits());

        Collection<Point> extremePoints = getMinAndMaxElementsOf(pointSet);
        pointSet.removeAll(extremePoints);

        int behavior = pointSet.size();
        final int BOTH_INTERVALS_IS_NOT_ZERO_LENGTH = 2;
        final int ONE_INTERVAL_IS_ZERO_LENGTH = 1;
        final int BOTH_INTERVALS_IS_ZERO_LENGTH = 0;

        Iterator<Point> iterator = pointSet.iterator();
        switch (behavior) {
            case BOTH_INTERVALS_IS_NOT_ZERO_LENGTH: {
                Point x = iterator.next();
                Point y = iterator.next();
                return Interval.newInstance(x, y);
            }
            case ONE_INTERVAL_IS_ZERO_LENGTH: {
                return first.hasZeroLength() ? first : second;
            }
            case BOTH_INTERVALS_IS_ZERO_LENGTH: {
                return first;
            }
            default: {
                throw new InternalError("Не удалось найти общий участок двух отрезков из-за внутренней ошибки. " +
                        "Обратитесь к разработчику.");
            }
        }
    }


    public static <T extends Comparable<T>> Collection<T> getMinAndMaxElementsOf(Collection<T> collection) {
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
        result.add(min);
        result.add(max);
        return result;
    }
}
