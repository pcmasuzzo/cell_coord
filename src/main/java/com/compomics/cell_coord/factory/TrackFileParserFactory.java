/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.factory;

import com.compomics.cell_coord.parser.TrackFileParser;
import com.compomics.cell_coord.spring.ApplicationContextProvider;
import java.util.Map;
import java.util.Set;
import org.springframework.context.ApplicationContext;

/**
 * Factory for file handlers.
 *
 * @author Paola
 */
public class TrackFileParserFactory {

    private final Map<String, TrackFileParser> parsers;
    private static final TrackFileParserFactory trackFileParserFactory = new TrackFileParserFactory();

    /**
     * Private constructor
     */
    private TrackFileParserFactory() {
        ApplicationContext context = ApplicationContextProvider.getInstance().getApplicationContext();
        parsers = context.getBeansOfType(TrackFileParser.class);
    }

    public static TrackFileParserFactory getInstance() {
        return trackFileParserFactory;
    }

    /**
     * Get the parser according to the parser bean name.
     *
     * @param beanName
     * @return
     */
    public TrackFileParser getParser(String beanName) {
        return parsers.get(beanName);
    }

    /**
     * Get the all set of strings for the parser beans names.
     *
     * @return a set of strings from the map.
     */
    public Set<String> getParserBeanNames() {
        return parsers.keySet();
    }
}
