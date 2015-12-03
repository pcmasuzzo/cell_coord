/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.computation;

import com.compomics.cell_coord.entity.Track;

/**
 * An interface to operate computationally on a track.
 *
 * @author Paola
 */
public interface TrackOperator {

    /**
     * Prepare the time indexes.
     *
     * @param track
     */
    void prepareTimeIndexes(Track track);

    /**
     * Prepare the coordinates matrix for a track.
     *
     * @param track
     */
    void prepareCoordinates(Track track);

    /**
     * Prepare the shifted coordinates matrix for a track.
     *
     * @param track
     */
    void prepareShiftedCoordinates(Track track);

    /**
     * Compute [xmin, xmax, ymin, ymax] for a track.
     *
     * @param track
     */
    void computeCoordinatesRanges(Track track);

    /**
     * Compute [xmin, xmax, ymin, ymax] shifted-to-zero for a track.
     *
     * @param track
     */
    void computeShiftedCoordinatesRanges(Track track);

    /**
     * Compute steps (step movements) for a track.
     *
     * @param track
     */
    void computeSteps(Track track);

    /**
     * Compute the step displacements - stepX² + stepY - for a track.
     *
     * @param track
     */
    void computeStepDisplacements(Track track);

    /**
     * Compute the turning angles for a track.
     *
     * @param track
     */
    void computeAngles(Track track);

    /**
     * Compute the cumulative distance for a track.
     *
     * @param track
     */
    void computeCumulativeDistance(Track track);

    /**
     * Compute the Euclidean distance for a track.
     *
     * @param track
     */
    void computeEuclideanDistance(Track track);

    /**
     * Compute the directionality.
     *
     * @param track
     */
    void computeEndPointDirectionality(Track track);

    /**
     * Compute the convex hull.
     *
     * @param track
     */
    void computeConvexHull(Track track);

    /**
     * Compute the displacement ratio.
     *
     * @param track
     */
    void computeDisplacementRatio(Track track);

    /**
     * Compute the outreach ratio.
     *
     * @param track
     */
    void computeOutreachRatio(Track track);
}
