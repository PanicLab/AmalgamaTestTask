package com.paniclab.amalgama;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by root on 11.07.2017.
 */
public class SubsetTest {

    @Test
    public void creation_builderIsEmpty_returnSubsetEmpty() throws Exception {
        Subset subset = Subset.builder().create();
        assertTrue(subset == Subset.EMPTY);
    }

    @Test
    public void creation_addThreeNotOverlappedIntervals_returnValidSubsetSized3() throws Exception {
        Point x1 = Point.NEGATIVE_INFINITY;
        Point y1 = Point.valueOf(-100);
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(-50);
        Point y2 = Point.valueOf(10);
        Interval intervalTwo = Interval.between(x2, y2);

        Point x3 = Point.valueOf(400);
        Point y3 = Point.POSITIVE_INFINITY;
        Interval intervalThree = Interval.between(x3, y3);

        Subset subset = Subset.builder()
                                .addInterval(intervalOne)
                                .addInterval(intervalTwo)
                                .addInterval(intervalThree)
                                .create();
        System.out.println("Тест класса Subset - добавлены три не перекрывающихся отрезка. Результат:");
        System.out.println(subset.toString());

        assertNotNull(subset);
        assertTrue(subset.size() == 3);
    }

    @Test
    public void creation_addOneRandomInterval_returnValidSubsetSized1() throws Exception {
        Point x2 = Point.valueOf(-50);
        Point y2 = Point.valueOf(10);
        Interval intervalTwo = Interval.between(x2, y2);

        Subset subset = Subset.builder()
                                .addInterval(intervalTwo)
                                .create();
        System.out.println("Тест класса Subset - добавлен один не бесконечный отрезок. Результат:");
        System.out.println(subset.toString());

        assertNotNull(subset);
        assertTrue(subset.size() == 1);
    }

    @Test
    public void creation_addThreeRandomZeroLengthIntervals_returnValidSubsetSized3() throws Exception {
        Point x1 = Point.valueOf(-100);
        Point y1 = Point.valueOf(-100);
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(-50);
        Point y2 = Point.valueOf(-50);
        Interval intervalTwo = Interval.between(x2, y2);

        Point x3 = Point.valueOf(400);
        Point y3 = Point.valueOf(400);
        Interval intervalThree = Interval.between(x3, y3);

        Subset subset = Subset.builder()
                .addInterval(intervalOne)
                .addInterval(intervalTwo)
                .addInterval(intervalThree)
                .create();
        System.out.println("Тест класса Subset - добавлены три случайных отрезка нулевой длины. Результат:");
        System.out.println(subset.toString());

        assertNotNull(subset);
        assertTrue(subset.size() == 3);
    }

    @Test
    public void creation_addFourRandomIntervalsWhereTwoIsOverlapped_modeNormalize_returnValidSubsetSized3()
            throws Exception {
        Point x1 = Point.NEGATIVE_INFINITY;
        Point y1 = Point.valueOf(-100);
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(-50);
        Point y2 = Point.valueOf(50);
        Interval intervalTwo = Interval.between(x2, y2);

        Point x3 = Point.valueOf(100);
        Point y3 = Point.valueOf(400);
        Interval intervalThree = Interval.between(x3, y3);

        Point x4 = Point.valueOf(250);
        Point y4 = Point.valueOf(800);
        Interval intervalFour = Interval.between(x4, y4);

        Subset subset = Subset.builder(Subset.Mode.NORMALIZE)
                .addInterval(intervalOne)
                .addInterval(intervalTwo)
                .addInterval(intervalThree)
                .addInterval(intervalFour)
                .create();
        System.out.println("Тест класса Subset - добавлены четыре случайных отрезка, два из которых перекрываются. " +
                "Результат:");
        System.out.println(subset.toString());

        assertNotNull(subset);
        assertTrue(subset.size() == 3);
    }

    @Test(expected = NotNormalizedSubsetException.class)
    public void creation_addFourRandomIntervalsWhereTwoIsOverlapped_modeDefault_throwNotNormSubsetExc()
            throws Exception {
        Point x1 = Point.NEGATIVE_INFINITY;
        Point y1 = Point.valueOf(-100);
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(-50);
        Point y2 = Point.valueOf(50);
        Interval intervalTwo = Interval.between(x2, y2);

        Point x3 = Point.valueOf(100);
        Point y3 = Point.valueOf(400);
        Interval intervalThree = Interval.between(x3, y3);

        Point x4 = Point.valueOf(250);
        Point y4 = Point.valueOf(800);
        Interval intervalFour = Interval.between(x4, y4);

        Subset subset = Subset.builder()
                .addInterval(intervalOne)
                .addInterval(intervalTwo)
                .addInterval(intervalThree)
                .addInterval(intervalFour)
                .create();
    }

    @Test
    public void creation_addFourRandomIntervalsAllOverlapped_modeNormalize_returnValidSubsetSized1() throws Exception {
            Point x1 = Point.NEGATIVE_INFINITY;
            Point y1 = Point.valueOf(-100);
            Interval intervalOne = Interval.between(x1, y1);

            Point x2 = Point.valueOf(-200);
            Point y2 = Point.valueOf(150);
            Interval intervalTwo = Interval.between(x2, y2);

            Point x3 = Point.valueOf(100);
            Point y3 = Point.valueOf(400);
            Interval intervalThree = Interval.between(x3, y3);

            Point x4 = Point.valueOf(250);
            Point y4 = Point.POSITIVE_INFINITY;
            Interval intervalFour = Interval.between(x4, y4);

            Subset subset = Subset.builder(Subset.Mode.NORMALIZE)
                    .addInterval(intervalOne)
                    .addInterval(intervalTwo)
                    .addInterval(intervalThree)
                    .addInterval(intervalFour)
                    .create();

        System.out.println("Тест класса Subset - добавлены четыре случайных отрезка, и все перекрываются. " +
                "Результат:");
        System.out.println(subset.toString());

        assertNotNull(subset);
        assertTrue(subset.size() == 1);
    }

    @Test
    public void creation_addFiveIntervalsAndOneContainsTwoAnother_modeNormalize_returnValidSubsetSized3() throws Exception {
        Point x1 = Point.NEGATIVE_INFINITY;
        Point y1 = Point.valueOf(-400);
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(-50);
        Point y2 = Point.valueOf(50);
        Interval intervalTwo = Interval.between(x2, y2);

        Point x3 = Point.valueOf(100);
        Point y3 = Point.valueOf(200);
        Interval intervalThree = Interval.between(x3, y3);

        Point x4 = Point.valueOf(500);
        Point y4 = Point.POSITIVE_INFINITY;
        Interval intervalFour = Interval.between(x4, y4);

        Point x5 = Point.valueOf(-200);
        Point y5 = Point.valueOf(400);
        Interval intervalFive = Interval.between(x5, y5);

        Subset subset = Subset.builder(Subset.Mode.NORMALIZE)
                .addInterval(intervalOne)
                .addInterval(intervalTwo)
                .addInterval(intervalThree)
                .addInterval(intervalFour)
                .addInterval(intervalFive)
                .create();

        System.out.println("Тест класса Subset - добавлены пять случайных отрезков, и один вмещает в себя другие два. "
                + "Результат:");
        System.out.println(subset.toString());

        assertNotNull(subset);
        assertTrue(subset.size() == 3);
    }

    @Test(expected = NotNormalizedSubsetException.class)
    public void creation_addFiveIntervalsAndOneContainsTwoAnother_modeDefault_throwNotNormalizedSubsetException()
            throws Exception {
        Point x1 = Point.NEGATIVE_INFINITY;
        Point y1 = Point.valueOf(-400);
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(-50);
        Point y2 = Point.valueOf(50);
        Interval intervalTwo = Interval.between(x2, y2);

        Point x3 = Point.valueOf(100);
        Point y3 = Point.valueOf(200);
        Interval intervalThree = Interval.between(x3, y3);

        Point x4 = Point.valueOf(500);
        Point y4 = Point.POSITIVE_INFINITY;
        Interval intervalFour = Interval.between(x4, y4);

        Point x5 = Point.valueOf(-200);
        Point y5 = Point.valueOf(400);
        Interval intervalFive = Interval.between(x5, y5);

        Subset subset = Subset.builder()
                .addInterval(intervalOne)
                .addInterval(intervalTwo)
                .addInterval(intervalThree)
                .addInterval(intervalFour)
                .addInterval(intervalFive)
                .create();
    }

    @Test
    public void creation_addFiveIntrvlsWhereOneContainsTwoAnotherAndOverlappedAnotherTwo_modeNorm_retrnValdSbsetSized1()
            throws Exception {
        Point x1 = Point.NEGATIVE_INFINITY;
        Point y1 = Point.valueOf(-400);
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(-50);
        Point y2 = Point.valueOf(50);
        Interval intervalTwo = Interval.between(x2, y2);

        Point x3 = Point.valueOf(100);
        Point y3 = Point.valueOf(200);
        Interval intervalThree = Interval.between(x3, y3);

        Point x4 = Point.valueOf(500);
        Point y4 = Point.POSITIVE_INFINITY;
        Interval intervalFour = Interval.between(x4, y4);

        Point x5 = Point.valueOf(-500);
        Point y5 = Point.valueOf(900);
        Interval intervalFive = Interval.between(x5, y5);

        Subset subset = Subset.builder(Subset.Mode.NORMALIZE)
                .addInterval(intervalOne)
                .addInterval(intervalTwo)
                .addInterval(intervalThree)
                .addInterval(intervalFour)
                .addInterval(intervalFive)
                .create();

        System.out.println("Тест класса Subset - добавлены пять случайных отрезков, и один вмещает в себя другие два и " +
                "перекрывается еще с двумя. Результат:");
        System.out.println(subset.toString());

        assertNotNull(subset);
        assertTrue(subset.size() == 1);
    }

    @Test(expected = NotNormalizedSubsetException.class)
    public void addFiveIntrvlsWhereOneContainsTwoAnotherAndOverlappedAnotherTwo_modeDeflt_retrnNNSE()
            throws Exception {
        Point x1 = Point.NEGATIVE_INFINITY;
        Point y1 = Point.valueOf(-400);
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(-50);
        Point y2 = Point.valueOf(50);
        Interval intervalTwo = Interval.between(x2, y2);

        Point x3 = Point.valueOf(100);
        Point y3 = Point.valueOf(200);
        Interval intervalThree = Interval.between(x3, y3);

        Point x4 = Point.valueOf(500);
        Point y4 = Point.POSITIVE_INFINITY;
        Interval intervalFour = Interval.between(x4, y4);

        Point x5 = Point.valueOf(-500);
        Point y5 = Point.valueOf(900);
        Interval intervalFive = Interval.between(x5, y5);

        Subset subset = Subset.builder()
                .addInterval(intervalOne)
                .addInterval(intervalTwo)
                .addInterval(intervalThree)
                .addInterval(intervalFour)
                .addInterval(intervalFive)
                .create();
    }

    @Test
    public void creation_addThreeRandomIntervalsAndTwoIsNeighborEachOther_returnValidSubsetSized2() throws Exception {
        Point x1 = Point.valueOf(-1000);
        Point y1 = Point.valueOf(-100);
        Interval intervalOne = Interval.between(x1, y1);

        Point x2 = Point.valueOf(-50);
        Point y2 = Point.valueOf(50);
        Interval intervalTwo = Interval.between(x2, y2);

        Point x3 = Point.valueOf(50);
        Point y3 = Point.valueOf(400);
        Interval intervalThree = Interval.between(x3, y3);

        Subset subset = Subset.builder(Subset.Mode.NORMALIZE)
                .addInterval(intervalOne)
                .addInterval(intervalTwo)
                .addInterval(intervalThree)
                .create();
        System.out.println("Тест класса Subset - добавлены три случайных отрезка, из них два прилегают друг к другу." +
                " Результат:");
        System.out.println(subset.toString());

        assertNotNull(subset);
        assertTrue(subset.size() == 2);
    }

    @Test
    public void isNotEmpty_subsetIsEmpty_returnFalse() throws Exception {
        Subset subset = Subset.EMPTY;
        assertFalse(subset.isNotEmpty());
    }

    @Test
    public void isNotEmpty_subsetIsNotEmpty_returnTrue() throws Exception {
        Point x1 = Point.valueOf(-1000);
        Point y1 = Point.valueOf(-100);
        Interval intervalOne = Interval.between(x1, y1);

        Subset subset = Subset.builder(Subset.Mode.NORMALIZE)
                                .addInterval(intervalOne)
                                .create();

        assertTrue(subset.isNotEmpty());
    }

    @Test(expected = IllegalStateException.class)
    public void getNearestOrElse_subsetIsEmptyPointIsRandom_throwIllegalStateException() throws Exception {
        Subset subset = Subset.EMPTY;
        Point point = Point.valueOf(10);

        subset.getNearestOrElse(point);
    }

    @Test
    public void getNearestOrElse_subsetIsNotEmptyPointIsRandomAndIsIn_returnPoint() throws Exception {
        Point x1 = Point.valueOf(-1000);
        Point y1 = Point.valueOf(100);
        Interval intervalOne = Interval.between(x1, y1);

        Subset subset = Subset.builder(Subset.Mode.NORMALIZE)
                .addInterval(intervalOne)
                .create();

        Point specifiedPoint = Point.valueOf(99);

        Point expected = subset.getNearestOrElse(specifiedPoint);
        assertEquals(expected, specifiedPoint);
    }

    @Test
    public void getNearestOrElse_subsetIsNotEmptyPointIsRandomAndIsOut_returnNearest() throws Exception {
        Point x1 = Point.valueOf(-1000);
        Point y1 = Point.valueOf(100);
        Interval intervalOne = Interval.between(x1, y1);

        Subset subset = Subset.builder(Subset.Mode.NORMALIZE)
                .addInterval(intervalOne)
                .create();

        Point specifiedPoint = Point.valueOf(105);

        Point expected = intervalOne.largerLimit();
        Point result = subset.getNearestOrElse(specifiedPoint);
        assertEquals(expected, result);
    }

    @Test
    public void getNearestOrElse_subsetIsNotEmptyPointIsInfinityAndIsOut_returnNearest() throws Exception {
        Point x1 = Point.valueOf(-1000);
        Point y1 = Point.valueOf(100);
        Interval intervalOne = Interval.between(x1, y1);

        Subset subset = Subset.builder(Subset.Mode.NORMALIZE)
                .addInterval(intervalOne)
                .create();

        Point specifiedPoint = Point.NEGATIVE_INFINITY;

        Point expected = intervalOne.lesserLimit();
        Point result = subset.getNearestOrElse(specifiedPoint);
        assertEquals(expected, result);
    }
}