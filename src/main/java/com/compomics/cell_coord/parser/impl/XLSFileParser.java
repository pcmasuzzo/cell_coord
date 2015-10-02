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
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
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
            Workbook workbook;
            // xls extension
            if (trackFile.getName().endsWith("xls")) {
                workbook = new HSSFWorkbook(fileInputStream);
            } else { // xlsx extension
                workbook = new XSSFWorkbook(fileInputStream);
            }
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();
            // iterate through each row
            while (iterator.hasNext()) {

                Row row = iterator.next();
                // iterate through the cells
                Iterator<Cell> cellIterator = row.cellIterator();
                
            }
        } catch (IOException ex) {
            Logger.getLogger(CSVFileParser.class.getName()).log(Level.SEVERE, null, ex);

        } catch (NumberFormatException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new FileParserException("It seems like a line does not contain a number!\nPlease check your files!");
        }
        return null;
    }
}
