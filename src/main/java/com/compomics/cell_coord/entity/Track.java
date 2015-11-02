/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.entity;

import java.util.List;
import java.util.Objects;

/**
 * Track Object.
 *
 * @author Paola
 */
public class Track {

    // the track trackid
    private Long trackid;
    // the sample the track belongs to
    private Sample sample;
    // a collection of spots in the track
    private List<TrackSpot> trackSpots;
    // coordinates matrix
    private Double[][] coordinates;
    // shifted coordinates
    private Double[][] shiftedCoordinates;
    // coordinate ranges: [xmin, xmax, ymin, ymax]
    private Double[][] coordinateRanges;
    // shifted coordinate ranges: [xmin, xmax, ymin, ymax]
    private Double[][] shiftedCoordinateRanges;
    // the net displacement in the x direction
    private Double xNetDisplacement;
    // the net displacement in the y direction
    private Double yNetDisplacement;

    /**
     * Empty constructor
     */
    public Track() {
    }

    /**
     * Constructor
     *
     * @param trackId
     * @param trackSpots
     */
    public Track(Long trackId, List<TrackSpot> trackSpots) {
        this.trackid = trackId;
        this.trackSpots = trackSpots;
    }

    public Track(Long trackid) {
        this.trackid = trackid;
    }

    public Long getTrackid() {
        return trackid;
    }

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public void setTrackid(Long trackid) {
        this.trackid = trackid;
    }

    public List<TrackSpot> getTrackSpots() {
        return trackSpots;
    }

    public void setTrackSpots(List<TrackSpot> trackSpots) {
        this.trackSpots = trackSpots;
    }

    public Double[][] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Double[][] coordinates) {
        this.coordinates = coordinates;
    }

    public Double[][] getShiftedCoordinates() {
        return shiftedCoordinates;
    }

    public void setShiftedCoordinates(Double[][] shiftedCoordinates) {
        this.shiftedCoordinates = shiftedCoordinates;
    }

    public Double[][] getCoordinateRanges() {
        return coordinateRanges;
    }

    public void setCoordinateRanges(Double[][] coordinateRanges) {
        this.coordinateRanges = coordinateRanges;
    }

    public Double[][] getShiftedCoordinateRanges() {
        return shiftedCoordinateRanges;
    }

    public void setShiftedCoordinateRanges(Double[][] shiftedCoordinateRanges) {
        this.shiftedCoordinateRanges = shiftedCoordinateRanges;
    }

    public Double getxNetDisplacement() {
        return xNetDisplacement;
    }

    public void setxNetDisplacement(Double xNetDisplacement) {
        this.xNetDisplacement = xNetDisplacement;
    }

    public Double getyNetDisplacement() {
        return yNetDisplacement;
    }

    public void setyNetDisplacement(Double yNetDisplacement) {
        this.yNetDisplacement = yNetDisplacement;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.trackid);
        hash = 29 * hash + Objects.hashCode(this.trackSpots);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Track other = (Track) obj;
        if (!Objects.equals(this.trackid, other.trackid)) {
            return false;
        }
        if (!Objects.equals(this.trackSpots, other.trackSpots)) {
            return false;
        }
        return true;
    }
}
