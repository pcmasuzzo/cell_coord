/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.entity;

import java.util.List;
import java.util.Objects;

/**
 * Sample Object.
 *
 * @author Paola
 */
public class Sample {

    // a list of tracks for this sample
    private List<Track> tracks;

    /**
     * Empty Constructor.
     */
    public Sample() {
    }

    public Sample(List<Track> tracks) {
        this.tracks = tracks;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.tracks);
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
        final Sample other = (Sample) obj;
        if (!Objects.equals(this.tracks, other.tracks)) {
            return false;
        }
        return true;
    }
}
