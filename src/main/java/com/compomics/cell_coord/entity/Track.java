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
    // the temporal indexes for the track
    private double[] timeIndexes;
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
    // the steps in both directions
    private Double[][] steps;
    // the step displacements
    private Double[] stepDisplacements;
    // the turning angles
    private Double[] angles;
    // the cumulative distance
    private double cumulativeDistance;
    // the euclidean distance
    private double euclideanDistance;
    // directionality: this is  the ratio between the euclidean and the cumulative distance
    private double endPointDirectionality;
    // the convex hull
    private ConvexHull convexHull;
    // displacement ratio: displacement/maximal displacement
    private double displacementRatio;
    // outreach ratio: maximal displacement/path length
    private double outreachRatio;

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

    public double[] getTimeIndexes() {
        return timeIndexes;
    }

    public void setTimeIndexes(double[] timeIndexes) {
        this.timeIndexes = timeIndexes;
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

    public Double[][] getSteps() {
        return steps;
    }

    public void setSteps(Double[][] steps) {
        this.steps = steps;
    }

    public Double[] getStepDisplacements() {
        return stepDisplacements;
    }

    public void setStepDisplacements(Double[] stepDisplacements) {
        this.stepDisplacements = stepDisplacements;
    }

    public Double[] getAngles() {
        return angles;
    }

    public void setAngles(Double[] angles) {
        this.angles = angles;
    }

    public double getCumulativeDistance() {
        return cumulativeDistance;
    }

    public void setCumulativeDistance(double cumulativeDistance) {
        this.cumulativeDistance = cumulativeDistance;
    }

    public double getEuclideanDistance() {
        return euclideanDistance;
    }

    public void setEuclideanDistance(double euclideanDistance) {
        this.euclideanDistance = euclideanDistance;
    }

    public double getEndPointDirectionality() {
        return endPointDirectionality;
    }

    public void setEndPointDirectionality(double endPointDirectionality) {
        this.endPointDirectionality = endPointDirectionality;
    }

    public ConvexHull getConvexHull() {
        return convexHull;
    }

    public void setConvexHull(ConvexHull convexHull) {
        this.convexHull = convexHull;
    }

    public double getDisplacementRatio() {
        return displacementRatio;
    }

    public void setDisplacementRatio(double displacementRatio) {
        this.displacementRatio = displacementRatio;
    }

    public double getOutreachRatio() {
        return outreachRatio;
    }

    public void setOutreachRatio(double outreachRatio) {
        this.outreachRatio = outreachRatio;
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
