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
}
