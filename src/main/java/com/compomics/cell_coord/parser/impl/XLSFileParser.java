/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.parser.impl;

import com.compomics.cell_coord.entity.Track;
import com.compomics.cell_coord.entity.TrackSpot;
import com.compomics.cell_coord.exception.FileParserException;
import com.compomics.cell_coord.parser.TrackFileParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * An implementation of the track file parser: parse an Excel file.
 *
 * @author Paola
 */
public class XLSFileParser implements TrackFileParser {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(XLSFileParser.class);

    @Override
    public List<Track> parseTrackFile(File trackFile) throws FileParserException {
        List<Track> list = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(trackFile);
            Workbook workbook = null;
            // xls extension
            if (trackFile.getName().endsWith("xls")) {
                workbook = new HSSFWorkbook(fileInputStream);
            } else if (trackFile.getName().endsWith("xlsx")) { // xlsx extension
                workbook = new XSSFWorkbook(fileInputStream);
            }
            if (workbook != null) {
                // check that at least one sheet is present
                if (workbook.getNumberOfSheets() > 0) {
                    Track currentTrack = null;
                    List<TrackSpot> currentTrackPointList = new ArrayList<>();
                    Long currentId = 0L;
                    Sheet sheet = workbook.getSheetAt(0);
                    // iterate through all the rows, starting from the second one to skip the header
                    for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
                        // get the row
                        Row row = sheet.getRow(i);
                        // check the track id
                        Long trackid = (long) row.getCell(0).getNumericCellValue();
                        if (!Objects.equals(currentId, trackid)) {
                            currentTrack = new Track();
                            currentTrack.setTrackid(trackid);
                            list.add(currentTrack);
                            currentId = trackid;
                            currentTrackPointList = new ArrayList<>();
                        }
                        // create new Track Spot object
                        Long spotid = (long) row.getCell(1).getNumericCellValue();
                        double x = row.getCell(2).getNumericCellValue();
                        double y = row.getCell(3).getNumericCellValue();
                        double time = row.getCell(4).getNumericCellValue();
                        TrackSpot trackSpot = new TrackSpot(spotid, x, y, time, currentTrack);
                        currentTrackPointList.add(trackSpot);
                        currentTrack.setTrackSpots(currentTrackPointList);
                    }
                } else {
                    throw new FileParserException("It seems an Excel file does not have any sheets!\nPlease check your files!");
                }
            } else {
                throw new FileParserException("The parser did not find a single workbook!\nCheck your files!!");
            }
        } catch (IOException ex) {
            LOG.error(ex.getMessage(), ex);
        } catch (NumberFormatException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new FileParserException("It seems like a line does not contain a number!\nPlease check your files!");
        }
        return list;
    }
}
