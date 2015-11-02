/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.parser.impl;

import com.compomics.cell_coord.entity.Sample;
import com.compomics.cell_coord.entity.Track;
import com.compomics.cell_coord.entity.TrackSpot;
import com.compomics.cell_coord.exception.FileParserException;
import com.compomics.cell_coord.parser.TrackFileParser;
import fiji.plugin.trackmate.Model;
import fiji.plugin.trackmate.Spot;
import fiji.plugin.trackmate.TrackModel;
import fiji.plugin.trackmate.io.TmXmlReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * An implementation of the track file parser to parse a TrackMate XML model.
 *
 * @author Paola
 */
public class TrackMateFileParser implements TrackFileParser {

    @Override
    public Sample parseTrackFile(File trackFile) throws FileParserException {
        // create a new sample object -- watch out to set the relationships!
        Sample sample = new Sample(trackFile.getName());
        // initialize an empty list of tracks
        List<Track> list = new ArrayList<>();
        // check that we actually have to parse an xml TrackMate model, and if not, throw an exception
        if (trackFile.getName().endsWith("xml")) {
            // instantiate the reader from the file
            TmXmlReader reader = new TmXmlReader(trackFile);
            if (reader.isReadingOk()) {
                // the full TM model
                Model model = reader.getModel();
                // the track model
                TrackModel trackModel = model.getTrackModel();
                // get the track ids
                Set<Integer> trackIDs = trackModel.trackIDs(true);
                for (Integer id : trackIDs) {
                    List<TrackSpot> trackSpotList = new ArrayList<>();
                    // create a new track
                    Track track = new Track(id.longValue());
                    Set<Spot> trackSpots = trackModel.trackSpots(id);
                    for (Spot spot : trackSpots) {
                        Double x = spot.getFeature("POSITION_X");
                        Double y = spot.getFeature("POSITION_Y");
                        Double time = spot.getFeature("FRAME");
                        // create a new TrackSpot
                        TrackSpot trackSpot = new TrackSpot((long) spot.ID(), x, y, time, track);
                        trackSpotList.add(trackSpot);
                    }
                    track.setTrackSpots(trackSpotList);
                    track.setSample(sample);
                    list.add(track);
                }
            } else {
                // error in building the XML reader
                throw new FileParserException("Error instantiating the XML reader!\n" + reader.getErrorMessage());
            }
        } else {
            throw new FileParserException("One or more files does not seem to be a TrackMate xml model!\nPlease check your files!");
        }
        sample.setTracks(list);
        return sample;
    }
}
