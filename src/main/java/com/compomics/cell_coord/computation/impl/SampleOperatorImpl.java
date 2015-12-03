/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.computation.impl;

import com.compomics.cell_coord.computation.SampleOperator;
import com.compomics.cell_coord.computation.TrackOperator;
import com.compomics.cell_coord.entity.Sample;
import com.compomics.cell_coord.entity.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * An implementation for the sample operator.
 *
 * @author Paola
 */
@Component("sampleOperator")
public class SampleOperatorImpl implements SampleOperator {

    @Autowired
    private TrackOperator trackOperator;

    @Override
    public void prepareTimeIndexes(Sample sample) {
        for (Track track : sample.getTracks()) {
            trackOperator.prepareTimeIndexes(track);
        }
    }

    @Override
    public void prepareCoordinates(Sample sample) {
        for (Track track : sample.getTracks()) {
            trackOperator.prepareCoordinates(track);
        }
    }

    @Override
    public void prepareShiftedCoordinates(Sample sample) {
        for (Track track : sample.getTracks()) {
            trackOperator.prepareShiftedCoordinates(track);
        }
    }

    @Override
    public void computeCoordinatesRanges(Sample sample) {
        for (Track track : sample.getTracks()) {
            trackOperator.computeCoordinatesRanges(track);
        }
    }

    @Override
    public void computeShiftedCoordinatesRanges(Sample sample) {
        for (Track track : sample.getTracks()) {
            trackOperator.computeShiftedCoordinatesRanges(track);
        }
    }

    @Override
    public void computeSteps(Sample sample) {
        for (Track track : sample.getTracks()) {
            trackOperator.computeSteps(track);
        }
    }

    @Override
    public void computeStepDisplacements(Sample sample) {
        for (Track track : sample.getTracks()) {
            trackOperator.computeStepDisplacements(track);
        }
    }

    @Override
    public void computeAngles(Sample sample) {
        for (Track track : sample.getTracks()) {
            trackOperator.computeAngles(track);
        }
    }

    @Override
    public void computeCumulativeDistances(Sample sample) {
        for (Track track : sample.getTracks()) {
            trackOperator.computeCumulativeDistance(track);
        }
    }

    @Override
    public void computeEuclideanDistances(Sample sample) {
        for (Track track : sample.getTracks()) {
            trackOperator.computeEuclideanDistance(track);
        }
    }

    @Override
    public void computeEndPointDirectionalities(Sample sample) {
        for (Track track : sample.getTracks()) {
            trackOperator.computeEndPointDirectionality(track);
        }
    }

    @Override
    public void computeConvexHulls(Sample sample) {
        for (Track track : sample.getTracks()) {
            trackOperator.computeConvexHull(track);
        }
    }

    @Override
    public void computeDisplacementRatios(Sample sample) {
        for (Track track : sample.getTracks()) {
            trackOperator.computeDisplacementRatio(track);
        }
    }

    @Override
    public void computeOutreachRatios(Sample sample) {
        for (Track track : sample.getTracks()) {
            trackOperator.computeOutreachRatio(track);
        }
    }
}
