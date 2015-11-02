/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.computation.impl;

import com.compomics.cell_coord.computation.TrackOperator;
import com.compomics.cell_coord.entity.Track;
import com.compomics.cell_coord.entity.TrackSpot;
import com.compomics.cell_coord.utils.ComputationUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * An implementation for the track operator.
 *
 * @author Paola
 */
@Component("trackOperator")
public class TrackOperatorImpl implements TrackOperator {

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
}
