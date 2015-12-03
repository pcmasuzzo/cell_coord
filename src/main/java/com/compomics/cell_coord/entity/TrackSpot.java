/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.entity;

import java.util.Objects;

/**
 * A track spot object.
 *
 * @author Paola
 */
public class TrackSpot {

    // an id
    private Long trackSpotid;
    // x coordinate
    private double x;
    // y coordinate
    private double y;
    // the time
    private double time;
    // the track the spot belongs to
    private Track track;
    // a geometric point associate dwith this spot
    private GeometricPoint geometricPoint;

    /**
     * Empty constructor
     */
    public TrackSpot() {
    }

    /**
     * Constructors of convenience.
     *
     * @param x
     * @param y
     */
    public TrackSpot(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public TrackSpot(Long trackSpotid, double x, double y, double time, Track track) {
        this.trackSpotid = trackSpotid;
        this.x = x;
        this.y = y;
        this.time = time;
        this.track = track;
    }

    public TrackSpot(Long trackSpotid, double x, double y, double time) {
        this.trackSpotid = trackSpotid;
        this.x = x;
        this.y = y;
        this.time = time;
    }

    public Long getTrackSpotid() {
        return trackSpotid;
    }

    public void setTrackSpotid(Long trackSpotid) {
        this.trackSpotid = trackSpotid;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public GeometricPoint getGeometricPoint() {
        return geometricPoint;
    }

    public void setGeometricPoint(GeometricPoint geometricPoint) {
        this.geometricPoint = geometricPoint;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.trackSpotid);
        hash = 43 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 43 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
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
        final TrackSpot other = (TrackSpot) obj;
        if (!Objects.equals(this.trackSpotid, other.trackSpotid)) {
            return false;
        }
        if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
            return false;
        }
        return true;
    }
}
