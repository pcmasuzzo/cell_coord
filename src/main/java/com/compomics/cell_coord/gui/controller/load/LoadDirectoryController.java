/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.gui.controller.load;

import com.compomics.cell_coord.entity.Track;
import com.compomics.cell_coord.exception.FileParserException;
import com.compomics.cell_coord.factory.TrackFileParserFactory;
import com.compomics.cell_coord.gui.load.LoadDirectoryPanel;
import com.compomics.cell_coord.parser.TrackFileParser;
import com.compomics.cell_coord.utils.GuiUtils;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.SwingBindings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A controller component to load a directory containing tracking files.
 *
 * @author Paola
 */
@Component("loadDirectoryController")
public class LoadDirectoryController {

    private static final Logger LOG = Logger.getLogger(LoadDirectoryController.class);
    // model
    private JTableBinding trackSpotsTableBinding;
    // view
    private LoadDirectoryPanel loadDirectoryPanel;
    // parent controller
    @Autowired
    private LoadTracksController loadTracksController;
    // child controllers
    // services
    private GridBagConstraints gridBagConstraints;

    /**
     * Initialize controller.
     */
    public void init() {
        gridBagConstraints = GuiUtils.getDefaultGridBagConstraints();
        // init main view
        initLoadSparseFilesPanel();
    }

    /**
     * On loading sparse files - render the right GUI view.
     */
    public void onLoadingSparseFilesGui() {
        loadTracksController.getCardLayout().show(loadTracksController.getLoadTracksPanel().getTopPanel(),
                  loadTracksController.getLoadTracksPanel().getDirectoryParentPanel().getName());
    }

    /**
     * Parse a specific track file.
     *
     * @param trackFile
     * @return list of tracks
     */
    private List<Track> parseTrackFile(File trackFile) throws FileParserException {
        // get the selected file format -- call the correspondent file parser
        String parserName = (String) loadDirectoryPanel.getFileFormatComboBox().getSelectedItem();
        TrackFileParser parser = TrackFileParserFactory.getInstance().getParser(parserName);
        return parser.parseTrackFile(trackFile);
    }

    /**
     * Initialize main view.
     */
    private void initLoadSparseFilesPanel() {
        // make new view
        loadDirectoryPanel = new LoadDirectoryPanel();

        // fill in combo box: get all parser methods from the factory
        Set<String> parsers = TrackFileParserFactory.getInstance().getParserBeanNames();
        for (String parser : parsers) {
            loadDirectoryPanel.getFileFormatComboBox().addItem(parser);
        }

        /**
         * Add action listeners.
         */
        // load directory:
        loadDirectoryPanel.getLoadDirectoryButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // we check if the directory was already loaded before
                if (loadTracksController.getDirectory() == null) {
                    loadTracksController.chooseDirectoryAndLoadData(loadDirectoryPanel.getDirectoryTree());
                    // if loading the directory was successful (i.e. the directory is still not null), set the ttext in the JComponent
                    if (loadTracksController.getDirectory() != null) {
                        loadDirectoryPanel.getDirectoryTextArea().setText(loadTracksController.getDirectory().getAbsolutePath());
                    }
                } else {
                    // otherwise we ask the user if they want to reload the directory
                    Object[] options = {"Load a different directory", "Cancel"};
                    int showOptionDialog = JOptionPane.showOptionDialog(null, "It seems a directory was already loaded.\nWhat do you want to do?", "", JOptionPane.CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
                    switch (showOptionDialog) {
                        case 0: // load a different directory:
                            // reset the model of the directory tree
                            DefaultTreeModel model = (DefaultTreeModel) loadDirectoryPanel.getDirectoryTree().getModel();
                            DefaultMutableTreeNode rootNote = (DefaultMutableTreeNode) model.getRoot();
                            rootNote.removeAllChildren();
                            model.reload();
                            loadTracksController.chooseDirectoryAndLoadData(loadDirectoryPanel.getDirectoryTree());
                            loadDirectoryPanel.getDirectoryTextArea().setText(loadTracksController.getDirectory().getAbsolutePath());
                            break;  // cancel: do nothing
                    }
                }
            }
        });

        // import the selected files
        loadDirectoryPanel.getImportFilesButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get the selected file(s)
                TreePath[] selectionPaths = loadDirectoryPanel.getDirectoryTree().getSelectionPaths();
                if (selectionPaths != null && selectionPaths.length != 0) {
                    for (TreePath path : selectionPaths) {
                        String fileName = (String) path.getLastPathComponent().toString();
                        File trackFile = new File(loadTracksController.getDirectory().getAbsolutePath() + File.separator + fileName);
                        try {
                            // get the tracks and add all the spots to the binding list
                            List<Track> currentTracks = parseTrackFile(trackFile);
                            for (Track track : currentTracks) {
                                loadTracksController.getTrackSpotsBindingList().addAll(track.getTrackSpots());
                            }
                            if (trackSpotsTableBinding == null) {
                                trackSpotsTableBinding = SwingBindings.createJTableBinding(AutoBinding.UpdateStrategy.READ, loadTracksController.getTrackSpotsBindingList(), loadDirectoryPanel.getTracksTable());
                                loadTracksController.showTracksInTable(trackSpotsTableBinding);
                            }
                        } catch (FileParserException ex) {
                            LOG.error("Could not parse the file: " + trackFile, ex);
                            loadTracksController.showMessage((String) loadDirectoryPanel.getFileFormatComboBox().getSelectedItem() + " expected!",
                                      "Error parsing file", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    loadTracksController.showMessage(selectionPaths.length + " files successfully imported!", "success loading", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // inform the user that no file was selected!
                    loadTracksController.showMessage("You have to select at least one file!", "no files selected", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // add panel to parent container
        loadTracksController.getLoadTracksPanel().getDirectoryParentPanel().add(loadDirectoryPanel, gridBagConstraints);
    }
}
