/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.entity;

import java.util.List;

/**
 *
 * @author Paola
 */
public class MostDistantPointsPair {

    // first and second points of track, along with distance between them
    private GeometricPoint firstPoint;
    private GeometricPoint secondPoint;
    private double maxSpan;

    /**
     * Constructor
     *
     * @param track
     * @param convexHull
     */
    public MostDistantPointsPair(Track track, ConvexHull convexHull) {
        compute(track, convexHull);
    }

    /**
     * Getters
     *
     * @return
     */
    public GeometricPoint getFirstPoint() {
        return firstPoint;
    }

    public GeometricPoint getSecondPoint() {
        return secondPoint;
    }

    public double getMaxSpan() {
        return maxSpan;
    }

    /**
     * Do the actual computation
     *
     * @param track
     * @param convexHull
     */
    private void compute(Track track, ConvexHull convexHull) {
        List<TrackSpot> trackSpots = track.getTrackSpots();
        // only one point, return
        if (trackSpots.size() <= 1) {
            return;
        }
        // number of points on the hull
        int M = convexHull.getHullSize();
        // the hull, in counterclockwise order
        GeometricPoint[] hull = new GeometricPoint[M];
        int m = 0;
        for (GeometricPoint geometricPoint : convexHull.getHull()) {
            hull[m++] = geometricPoint;
        }
        // all points are equal
        if (M == 1) {
            return;
        }
        // points are collinear
        if (M == 2) {
            firstPoint = hull[0];
            secondPoint = hull[1];
            maxSpan = firstPoint.euclideanDistanceTo(secondPoint);
            return;
        }

        // check for the greatest distance
        for (GeometricPoint geometricPoint : hull) {
            for (GeometricPoint otherGeometricPoint : hull) {
                if (!geometricPoint.equals(otherGeometricPoint)) {
                    double euclideanDistance = geometricPoint.euclideanDistanceTo(otherGeometricPoint);
                    if (euclideanDistance > maxSpan) {
                        firstPoint = geometricPoint;
                        secondPoint = otherGeometricPoint;
                        maxSpan = euclideanDistance;
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "{" + firstPoint + ", " + secondPoint + "}";
    }
}
