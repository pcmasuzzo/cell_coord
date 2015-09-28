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
    // a collection of spots in the track
    private List<TrackSpot> trackSpots;

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

    public Long getTrackid() {
        return trackid;
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
