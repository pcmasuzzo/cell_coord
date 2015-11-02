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
}
