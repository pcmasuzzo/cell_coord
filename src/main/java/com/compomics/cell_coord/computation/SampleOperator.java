/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.computation;

import com.compomics.cell_coord.entity.Sample;

/**
 * An interface to operate computationally on a sample.
 *
 * @author Paola
 */
public interface SampleOperator {

    /**
     * Prepare the time indexes.
     *
     * @param sample
     */
    void prepareTimeIndexes(Sample sample);

    /**
     * Prepare the coordinates.
     *
     * @param sample
     */
    void prepareCoordinates(Sample sample);

    /**
     * Prepare the shifted coordinates.
     *
     * @param sample
     */
    void prepareShiftedCoordinates(Sample sample);

    /**
     * Compute [xmin, xmax, ymin, ymax].
     *
     * @param sample
     */
    void computeCoordinatesRanges(Sample sample);

    /**
     * Compute [xmin, xmax, ymin, ymax] shifted-to-zero.
     *
     * @param sample
     */
    void computeShiftedCoordinatesRanges(Sample sample);

    /**
     * Compute step movements.
     *
     * @param sample
     */
    void computeSteps(Sample sample);

    /**
     * Compute the step displacements.
     *
     * @param sample
     */
    void computeStepDisplacements(Sample sample);

    /**
     * Compute the angles.
     *
     * @param sample
     */
    void computeAngles(Sample sample);

    /**
     * Compute the Cumulative distances.
     *
     * @param sample
     */
    void computeCumulativeDistances(Sample sample);

    /**
     * Compute the Euclidean distances.
     *
     * @param sample
     */
    void computeEuclideanDistances(Sample sample);

    /**
     * Compute the end point directionality ratios.
     *
     * @param sample
     */
    void computeEndPointDirectionalities(Sample sample);

    /**
     * Compute the convex hulls.
     *
     * @param sample
     */
    void computeConvexHulls(Sample sample);

    /**
     * Compute the displacement ratios.
     *
     * @param sample
     */
    void computeDisplacementRatios(Sample sample);

    /**
     * Compute the outreach ratios.
     *
     * @param sample
     */
    void computeOutreachRatios(Sample sample);

}
