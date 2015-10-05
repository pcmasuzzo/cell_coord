/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.parser;

import com.compomics.cell_coord.entity.Track;
import com.compomics.cell_coord.exception.FileParserException;
import com.compomics.cell_coord.parser.impl.TrackMateFileParser;
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
 * A unit test for TrackMate XML model parsing.
 *
 * @author Paola
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:mySpringXMLConfig.xml")
public class TrackMateFileParserTest {

    private final TrackFileParser trackMateFileParser = new TrackMateFileParser();

    @Test
    public void testTrackMateFileParsing() {

        File trackingFile = new File(CSVFileParserTest.class.getClassLoader().getResource("track_file_test.xml").getPath());
        List<Track> trackList = new ArrayList<>();
        // try to parse the file
        try {
            trackList = trackMateFileParser.parseTrackFile(trackingFile);
        } catch (FileParserException ex) {
            Logger.getLogger(CSVFileParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertTrue(!trackList.isEmpty());
        assertEquals(1, trackList.size());
    }
}
