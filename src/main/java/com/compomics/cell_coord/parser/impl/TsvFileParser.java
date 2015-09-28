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
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the track file parser: parse a tab-separated-values
 * file.
 *
 * @author Paola
 */
public class TsvFileParser implements TrackFileParser {

    @Override
    public List<Track> parseTrackFile(File trackFile) throws FileParserException {
        List<Track> list = new ArrayList<>();

        return list;
    }
}
