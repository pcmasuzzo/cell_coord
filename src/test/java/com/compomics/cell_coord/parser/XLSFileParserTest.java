/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.parser;

import com.compomics.cell_coord.entity.Sample;
import com.compomics.cell_coord.entity.Track;
import com.compomics.cell_coord.entity.TrackSpot;
import com.compomics.cell_coord.exception.FileParserException;
import com.compomics.cell_coord.parser.impl.XLSFileParser;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A unit test for XLS (Excel) file parsing.
 *
 * @author Paola
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:mySpringXMLConfig.xml")
public class XLSFileParserTest {

    private final TrackFileParser xlsFileParser = new XLSFileParser();

    @Test
    public void testXLSFileParsing() {
        File XLSFile = new File(CSVFileParserTest.class.getClassLoader().getResource("track_file_test.xls").getPath());
        Sample sample = null;
        try {
            sample = xlsFileParser.parseTrackFile(XLSFile);
        } catch (FileParserException ex) {
            Logger.getLogger(XLSFileParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Track> trackList = sample.getTracks();
        assertTrue(!trackList.isEmpty());
        assertEquals(45, trackList.size());
        // this is the last parsed track
        Track lastTrack = trackList.get(trackList.size() - 1);
        List<TrackSpot> trackSpots = lastTrack.getTrackSpots();
        assertEquals(58, trackSpots.size());
        // the first track spot
        TrackSpot trackSpot = trackSpots.get(0);
        // the spot memebers
        double x = trackSpot.getX();
        double y = trackSpot.getY();
        double time = trackSpot.getTime();
        assertEquals(887.138889, x);
        assertEquals(827.611111, y);
        assertEquals(0.0, time);
    }

    @Test
    public void testXLSXFileParsing() {
        File XLSFile = new File(CSVFileParserTest.class.getClassLoader().getResource("track_file_test.xlsx").getPath());
        Sample sample = null;
        try {
            sample = xlsFileParser.parseTrackFile(XLSFile);
        } catch (FileParserException ex) {
            Logger.getLogger(XLSFileParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Track> trackList = sample.getTracks();
        assertTrue(!trackList.isEmpty());
        assertEquals(45, trackList.size());
    }
}
