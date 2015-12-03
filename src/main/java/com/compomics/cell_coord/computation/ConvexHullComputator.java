/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.computation;

import com.compomics.cell_coord.entity.ConvexHull;
import com.compomics.cell_coord.entity.Track;

/**
 * A calculator for the convex hull of a track.
 *
 * @author Paola
 */
public interface ConvexHullComputator {

    /**
     * Compute the hull, the real convex polygon associated to the track.
     *
     * @param track: the track we need to get the geometric points from
     * @param convexHull: the convex hull object for which the hull is being
     * calculated
     */
    void computeHull(Track track, ConvexHull convexHull);

    /**
     * Compute the size of the convex hull, i.e. the number of vertices.
     *
     * @param convexHull
     */
    void computeHullSize(ConvexHull convexHull);

    /**
     * Find the pair of geometric points that are most distant from each other
     * on a track. The distance between the two points will be the diameter (max
     * span) of the convex hull object.
     *
     * @param track
     * @param convexHull
     */
    void findMostDistantPoints(Track track, ConvexHull convexHull);
}
