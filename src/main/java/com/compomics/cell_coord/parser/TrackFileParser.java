/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.parser;

import com.compomics.cell_coord.entity.Track;
import com.compomics.cell_coord.exception.FileParserException;
import java.io.File;
import java.util.List;

/**
 * An interface to parse a single track File.
 *
 * @author Paola
 */
public interface TrackFileParser {

    /**
     * Parse a track file.
     *
     * @param trackFile
     * @return the correspondent list of tracks
     * @throws com.compomics.cell_coord.exception.FileParserException: an
     * exception is something is wrong!
     */
    List<Track> parseTrackFile(File trackFile) throws FileParserException;
}
