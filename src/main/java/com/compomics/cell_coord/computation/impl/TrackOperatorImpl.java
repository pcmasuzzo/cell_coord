/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.computation.impl;

import com.compomics.cell_coord.computation.ConvexHullOperator;
import com.compomics.cell_coord.computation.TrackOperator;
import com.compomics.cell_coord.entity.ConvexHull;
import com.compomics.cell_coord.entity.GeometricPoint;
import com.compomics.cell_coord.entity.MostDistantPointsPair;
import com.compomics.cell_coord.entity.Track;
import com.compomics.cell_coord.entity.TrackSpot;
import com.compomics.cell_coord.utils.ComputationUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * An implementation for the track operator.
 *
 * @author Paola
 */
@Component("trackOperator")
public class TrackOperatorImpl implements TrackOperator {

    @Autowired
    private GrahamScanAlgorithm grahamScanAlgorithm;
    @Autowired
    private ConvexHullOperator convexHullOperator;

    /**
     * Prepare the time indexes.
     *
     * @param track
     */
    @Override
    public void prepareTimeIndexes(Track track) {
        List<TrackSpot> trackSpots = track.getTrackSpots();
        double[] timeIndexes = new double[trackSpots.size()];
        for (int i = 0; i < timeIndexes.length; i++) {
            timeIndexes[i] = trackSpots.get(i).getTime();
        }
        track.setTimeIndexes(timeIndexes);
    }

    /**
     * Prepare raw coordinates matrix for a track.
     *
     * @param track
     */
    @Override
    public void prepareCoordinates(Track track) {
        List<TrackSpot> trackSpots = track.getTrackSpots();
        Double[][] coordinates = new Double[trackSpots.size()][2];
        for (int i = 0; i < coordinates.length; i++) {
            TrackSpot trackSpot = trackSpots.get(i);
            Double x = trackSpot.getX();
            Double y = trackSpot.getY();
            coordinates[i] = new Double[]{x, y};
            GeometricPoint geometricPoint = new GeometricPoint(x, y);
            trackSpot.setGeometricPoint(geometricPoint);
        }
        track.setCoordinates(coordinates);
    }

    /**
     * Prepare the shifted coordinates matrix for a track.
     *
     * @param track
     */
    @Override
    public void prepareShiftedCoordinates(Track track) {
        Double[][] coordinates = track.getCoordinates();
        Double[][] shiftedCoordinates = new Double[coordinates.length][2];
        Double[] firstCoordinates = coordinates[0];
        double x0 = firstCoordinates[0];
        double y0 = firstCoordinates[1];
        for (int row = 0; row < coordinates.length; row++) {
            Double currentX = coordinates[row][0];
            Double currentY = coordinates[row][1];
            shiftedCoordinates[row] = new Double[]{currentX - x0, currentY - y0};
        }
        track.setShiftedCoordinates(shiftedCoordinates);
    }

    @Override
    public void computeCoordinatesRanges(Track track) {
        Double[][] coordinates = track.getCoordinates();
        Double[][] transpCoord = ComputationUtils.transpose2DArray(coordinates);
        List<Double> xCoordAsList = Arrays.asList(transpCoord[0]);
        List<Double> yCoordAsList = Arrays.asList(transpCoord[1]);
        Double xMin = Collections.min(xCoordAsList);
        Double xMax = Collections.max(xCoordAsList);
        Double yMin = Collections.min(yCoordAsList);
        Double yMax = Collections.max(yCoordAsList);
        Double[][] coordinatesRanges = new Double[][]{{xMin, xMax}, {yMin, yMax}};
        track.setCoordinateRanges(coordinatesRanges);
        track.setxNetDisplacement(xMax - xMin);
        track.setyNetDisplacement(yMax - yMin);
    }

    @Override
    public void computeShiftedCoordinatesRanges(Track track) {
        Double[][] shiftedCoordinates = track.getShiftedCoordinates();
        Double[][] transpCoord = ComputationUtils.transpose2DArray(shiftedCoordinates);
        List<Double> xCoordAsList = Arrays.asList(transpCoord[0]);
        List<Double> yCoordAsList = Arrays.asList(transpCoord[1]);
        Double xMin = Collections.min(xCoordAsList);
        Double xMax = Collections.max(xCoordAsList);
        Double yMin = Collections.min(yCoordAsList);
        Double yMax = Collections.max(yCoordAsList);
        Double[][] shiftedCoordRanges = new Double[][]{{xMin, xMax}, {yMin, yMax}};
        track.setShiftedCoordinateRanges(shiftedCoordRanges);
    }

    @Override
    public void computeSteps(Track track) {
        Double[][] coordinates = track.getCoordinates();
        Double[][] steps = new Double[coordinates.length - 1][coordinates[0].length];
        // need to start from the second row
        for (int row = 1; row < coordinates.length; row++) {
            // compute the steps
            double stepX = coordinates[row][0] - coordinates[row - 1][0];
            double stepY = coordinates[row][1] - coordinates[row - 1][1];
            steps[row - 1] = new Double[]{stepX, stepY};
        }
        track.setSteps(steps);
    }

    @Override
    public void computeStepDisplacements(Track track) {
        Double[][] steps = track.getSteps();
        Double[] stepDisplacements = new Double[steps.length];
        for (int row = 0; row < stepDisplacements.length; row++) {
            Double stepDispl = Math.hypot(steps[row][0], steps[row][1]);
            stepDisplacements[row] = stepDispl;
        }
        track.setStepDisplacements(stepDisplacements);
    }

    @Override
    public void computeAngles(Track track) {
        Double[][] steps = track.getSteps();
        Double[] angles = new Double[steps.length];
        for (int row = 0; row < angles.length; row++) {
            // This method computes the phase theta by computing an arc tangent of y/x in the range of -pi to pi.
            double d = steps[row][1] / steps[row][0]; // if division is 0, the cell is going exactly back on its path!
            Double angleRadians = Math.atan(d) + 0; // need to add 0 to avoid signed 0 in Java
            // go from radians to degrees
            Double angleDegrees = Math.toDegrees(angleRadians);
            // if the angle is NaN (both deltaX and deltaY are zero), the cell stays exactly on place 
            if (angleDegrees < 0) {
                angleDegrees = angleDegrees + 360;
            }
            angles[row] = angleDegrees;
        }
        track.setAngles(angles);
    }

    @Override
    public void computeCumulativeDistance(Track track) {
        double cumulativeDistance = 0;
        Double[] stepDisplacements = track.getStepDisplacements();
        for (Double stepDisplacement : stepDisplacements) {
            if (stepDisplacement != null) {
                cumulativeDistance += stepDisplacement;
            }
        }
        track.setCumulativeDistance(cumulativeDistance);
    }

    @Override
    public void computeEuclideanDistance(Track track) {
        List<TrackSpot> trackSpots = track.getTrackSpots();
        TrackSpot firstSpot = trackSpots.get(0);
        TrackSpot lastSpot = trackSpots.get(trackSpots.size() - 1);
        double euclideanDistance = lastSpot.getGeometricPoint().euclideanDistanceTo(firstSpot.getGeometricPoint());
        track.setEuclideanDistance(euclideanDistance);
    }

    @Override
    public void computeEndPointDirectionality(Track track) {
        double endPointDirectionality = track.getEuclideanDistance() / track.getCumulativeDistance();
        track.setEndPointDirectionality(endPointDirectionality);
    }

    @Override
    public void computeConvexHull(Track track) {
        ConvexHull convexHull = new ConvexHull();
        grahamScanAlgorithm.computeHull(track, convexHull);
        grahamScanAlgorithm.computeHullSize(convexHull);
        grahamScanAlgorithm.findMostDistantPoints(track, convexHull);
        convexHullOperator.computePerimeter(convexHull);
        convexHullOperator.computeArea(convexHull);
        convexHullOperator.computeAcircularity(convexHull);
        convexHullOperator.computeDirectionality(convexHull);
        track.setConvexHull(convexHull);
    }

    @Override
    public void computeDisplacementRatio(Track track) {
        ConvexHull convexHull = track.getConvexHull();
        MostDistantPointsPair mostDistantPointsPair = convexHull.getMostDistantPointsPair();
        double maxSpan = mostDistantPointsPair.getMaxSpan();
        double displacementRatio = track.getEuclideanDistance() / maxSpan;
        track.setDisplacementRatio(displacementRatio);
    }

    @Override
    public void computeOutreachRatio(Track track) {
        ConvexHull convexHull = track.getConvexHull();
        MostDistantPointsPair mostDistantPointsPair = convexHull.getMostDistantPointsPair();
        double maxSpan = mostDistantPointsPair.getMaxSpan();
        double outreachRatio = maxSpan / track.getCumulativeDistance();
        track.setOutreachRatio(outreachRatio);
    }
}
