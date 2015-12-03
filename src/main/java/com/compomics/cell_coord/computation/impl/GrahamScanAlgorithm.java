/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.computation.impl;

import com.compomics.cell_coord.computation.ConvexHullComputator;
import com.compomics.cell_coord.entity.ConvexHull;
import com.compomics.cell_coord.entity.GeometricPoint;
import com.compomics.cell_coord.entity.GrahamScan;
import com.compomics.cell_coord.entity.MostDistantPointsPair;
import com.compomics.cell_coord.entity.Track;
import com.compomics.cell_coord.entity.TrackSpot;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author Paola
 */
@Component("grahamScanAlgorithm")
public class GrahamScanAlgorithm implements ConvexHullComputator {

    @Override
    public void computeHull(Track track, ConvexHull convexHull) {
        // create a list of geometric points from the track points
        List<TrackSpot> spots = track.getTrackSpots();
        List<GeometricPoint> geometricPoints = new ArrayList<>();
        for (TrackSpot spot : spots) {
            geometricPoints.add(spot.getGeometricPoint());
        }
        // create a new Graham Scan object for these points
        GrahamScan grahamScan = new GrahamScan(geometricPoints);
        // get the hull from this object and pass it to the convex hull object
        Iterable<GeometricPoint> hull = grahamScan.getHull();
        convexHull.setHull(hull);
    }

    @Override
    public void computeHullSize(ConvexHull convexHull) {
        int n = 0;
        for (GeometricPoint geometricPoint : convexHull.getHull()) {
            n++;
        }
        convexHull.setHullSize(n);
    }

    @Override
    public void findMostDistantPoints(Track track, ConvexHull convexHull) {
        // create a new Most Distant Points Pair object and set it to the convex hull
        MostDistantPointsPair mostDistantPointsPair = new MostDistantPointsPair(track, convexHull);
        convexHull.setMostDistantPointsPair(mostDistantPointsPair);
    }
}
