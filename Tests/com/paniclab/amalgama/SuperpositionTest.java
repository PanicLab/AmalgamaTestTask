package com.paniclab.amalgama;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Сергей on 14.07.2017.
 */
public class SuperpositionTest {

    @Test
    public void resolve_createsFromEmptyList_returnSubsetEmpty() throws Exception {
        List<Subset> subsetList = new ArrayList<>();

        Superposition superposition = Superposition.createFrom(subsetList);
        assertEquals(Subset.EMPTY, superposition.resolve());
    }

    @Test
    public void resolve_createsFromListWithSingleSubset_returnThatSubset() throws Exception {
        Point negInfinity = Point.NEGATIVE_INFINITY;
        Point y = Point.valueOf(-1000);
        Interval interval1 = Interval.between(negInfinity, y);

        Interval interval2 = Interval.between(-500, 500);

        Point posInfinity = Point.POSITIVE_INFINITY;
        Point x = Point.valueOf(1000);
        Interval interval3 = Interval.between(x, posInfinity);

        Subset subset1 = Subset.builder()
                .add(interval1)
                .add(interval2)
                .add(interval3)
                .create();

        List<Subset> subsetList = new ArrayList<>();
        subsetList.add(subset1);

        Superposition superposition = Superposition.createFrom(subsetList);

        assertEquals(subset1, superposition.resolve());
    }

    @Test
    public void resolve_createsFromTwoSubsetThtsHasSupPos_returnSupPos() throws Exception {
        Point negInfinity = Point.NEGATIVE_INFINITY;
        Point y = Point.valueOf(-1000);
        Interval interval1 = Interval.between(negInfinity, y);

        Interval interval2 = Interval.between(-500, 500);

        Point posInfinity = Point.POSITIVE_INFINITY;
        Point x = Point.valueOf(1000);
        Interval interval3 = Interval.between(x, posInfinity);

        Subset subset1 = Subset.builder()
                                .add(interval1)
                                .add(interval2)
                                .add(interval3)
                                .create();

        Subset subset2 = Subset.builder()
                                .add(Interval.between(-1500, -800))
                                .add(Interval.between(600, 700))
                                .create();

        Superposition superposition = Superposition.builder()
                                                    .add(subset1)
                                                    .add(subset2)
                                                    .build();

        Subset expected = Subset.builder()
                                    .add(Interval.between(-1500, -1000))
                                    .create();

        assertEquals(expected, superposition.resolve());
    }


    @Test
    public void resolve_createsFromTwoSubsetThtsHasNoSupPos_returnSubsetEmpty() throws Exception {
        Point negInfinity = Point.NEGATIVE_INFINITY;
        Point y = Point.valueOf(-1000);
        Interval interval1 = Interval.between(negInfinity, y);

        Interval interval2 = Interval.between(-500, 500);

        Point posInfinity = Point.POSITIVE_INFINITY;
        Point x = Point.valueOf(1000);
        Interval interval3 = Interval.between(x, posInfinity);

        Subset subset1 = Subset.builder()
                .add(interval1)
                .add(interval2)
                .add(interval3)
                .create();

        Subset subset2 = Subset.builder()
                .add(Interval.between(-900, -800))
                .add(Interval.between(600, 700))
                .create();

        Superposition superposition = Superposition.builder()
                .add(subset1)
                .add(subset2)
                .build();

        Subset expected = Subset.EMPTY;

        assertEquals(expected, superposition.resolve());
    }

    @Test
    public void resolve_createsFromTwoSubsetAndHasComplexSupPos_returnSubsetSized4() throws Exception {
        Point negInfinity = Point.NEGATIVE_INFINITY;
        Point y = Point.valueOf(-1000);
        Interval interval1 = Interval.between(negInfinity, y);

        Interval interval2 = Interval.between(-500, 500);

        Point posInfinity = Point.POSITIVE_INFINITY;
        Point x = Point.valueOf(1000);
        Interval interval3 = Interval.between(x, posInfinity);

        Subset subset1 = Subset.builder()
                .add(interval1)
                .add(interval2)
                .add(interval3)
                .create();

        Subset subset2 = Subset.builder()
                .add(Interval.between(-1100, -400))
                .add(Interval.between(300, 1500))
                .create();

        Superposition superposition = Superposition.builder()
                .add(subset1)
                .add(subset2)
                .build();

        Subset expected = Subset.builder()
                                    .add(Interval.between(-1100, -1000))
                                    .add(Interval.between(-500, -400))
                                    .add(Interval.between(300, 500))
                                    .add(Interval.between(1000, 1500))
                                    .create();

        Subset result = superposition.resolve();
        assertTrue(result.size() == 4);
        assertEquals(expected, result);
    }

    @Test
    public void resolve_createsFromTwoSubsetAndHasComplexSupPos_multipleCall_returnAlwaysSameSubset() throws Exception {
        Point negInfinity = Point.NEGATIVE_INFINITY;
        Point y = Point.valueOf(-1000);
        Interval interval1 = Interval.between(negInfinity, y);

        Interval interval2 = Interval.between(-500, 500);

        Point posInfinity = Point.POSITIVE_INFINITY;
        Point x = Point.valueOf(1000);
        Interval interval3 = Interval.between(x, posInfinity);

        Subset subset1 = Subset.builder()
                .add(interval1)
                .add(interval2)
                .add(interval3)
                .create();

        Subset subset2 = Subset.builder()
                .add(Interval.between(-1100, -400))
                .add(Interval.between(300, 1500))
                .create();

        Superposition superposition = Superposition.builder()
                .add(subset1)
                .add(subset2)
                .build();

        Subset expected = Subset.builder()
                .add(Interval.between(-1100, -1000))
                .add(Interval.between(-500, -400))
                .add(Interval.between(300, 500))
                .add(Interval.between(1000, 1500))
                .create();

        Subset result = superposition.resolve();
        assertTrue(result.size() == 4);
        assertEquals(expected, result);

        result = superposition.resolve();
        assertTrue(result.size() == 4);
        assertEquals(expected, result);

        result = superposition.resolve();
        assertTrue(result.size() == 4);
        assertEquals(expected, result);
    }
}