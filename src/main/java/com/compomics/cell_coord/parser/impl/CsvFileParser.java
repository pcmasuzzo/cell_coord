/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.parser.impl;

import com.compomics.cell_coord.entity.Track;
import com.compomics.cell_coord.exception.FileParserException;
import com.compomics.cell_coord.parser.TrackFileParser;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

/**
 * An implementation of the track file parser: parse a comma-separated-values
 * file.
 *
 * @author Paola
 */
public class CsvFileParser implements TrackFileParser {

    @Override
    public List<Track> parseTrackFile(File trackFile) throws FileParserException {
        List<Track> list = new ArrayList<>();
        try {
            CSVParser parser = CSVParser.parse(trackFile.getName(), CSVFormat.RFC4180);
        } catch (IOException ex) {
            Logger.getLogger(CsvFileParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
