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
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * An implementation of the track file parser: parse a tab-separated-values
 * file.
 *
 * @author Paola
 */
public class TSVFileParser implements TrackFileParser {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(TSVFileParser.class);

    private static final String[] FILE_HEADER_MAPPING = {"trackid", "spotid", "x", "y", "time"};
    private static final String TRACK_ID = "trackid";
    private static final String SPOT_ID = "spotid";
    private static final String X_COORD = "x";
    private static final String Y_COORD = "y";
    private static final String TIME = "time";

    @Override
    public Sample parseTrackFile(File trackFile) throws FileParserException {
        // create a new sample object -- watch out to set the relationships!
        Sample sample = new Sample(trackFile.getName());
        // initialize an empty list of tracks
        List<Track> list = new ArrayList<>();
        CSVParser tsvFileParser;
        FileReader fileReader;
        CSVFormat csvFileFormat = CSVFormat.TDF.withHeader(FILE_HEADER_MAPPING);
        try {
            // initialize the file reader
            fileReader = new FileReader(trackFile);
            //initialize CSVParser object
            tsvFileParser = new CSVParser(fileReader, csvFileFormat);
            // get the csv records
            List<CSVRecord> csvRecords = tsvFileParser.getRecords();
            Track currentTrack = null;
            List<TrackSpot> currentTrackPointList = new ArrayList<>();
            Long currentId = 0L;

            //Read the CSV file records starting from the second record to skip the header
            for (int i = 1; i < csvRecords.size(); i++) {
                CSVRecord cSVRecord = csvRecords.get(i);
                // get the fields
                Long trackid = Long.parseLong(cSVRecord.get(TRACK_ID));
                if (!Objects.equals(currentId, trackid)) {
                    currentTrack = new Track();
                    currentTrack.setTrackid(trackid);
                    list.add(currentTrack);
                    currentId = trackid;
                    currentTrackPointList = new ArrayList<>();
                }
                // create new Track Spot object
                Long spotid = Long.parseLong(cSVRecord.get(SPOT_ID));
                double x = Double.parseDouble(cSVRecord.get(X_COORD));
                double y = Double.parseDouble(cSVRecord.get(Y_COORD));
                double time = Double.parseDouble(cSVRecord.get(TIME));
                TrackSpot trackSpot = new TrackSpot(spotid, x, y, time, currentTrack);
                currentTrackPointList.add(trackSpot);
                currentTrack.setTrackSpots(currentTrackPointList);
                currentTrack.setSample(sample);
            }
        } catch (IOException ex) {
            LOG.error(ex.getMessage(), ex);
        } catch (NumberFormatException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new FileParserException("It seems like a line does not contain a number!\nPlease check your files!");
        }
        sample.setTracks(list);
        return sample;
    }
}
