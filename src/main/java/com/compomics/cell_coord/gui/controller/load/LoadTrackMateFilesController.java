/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.gui.controller.load;

import com.compomics.cell_coord.entity.Track;
import com.compomics.cell_coord.exception.FileParserException;
import com.compomics.cell_coord.gui.load.LoadTrackMateFilesPanel;
import com.compomics.cell_coord.parser.impl.TrackMateFileParser;
import com.compomics.cell_coord.utils.GuiUtils;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
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
 * A controller component to load a list of TrackMate models.
 *
 * @author Paola
 */
@Component("loadTrackMateFilesController")
public class LoadTrackMateFilesController {

    private static final Logger LOG = Logger.getLogger(LoadTrackMateFilesController.class);

    // model
    private JTableBinding trackSpotsTableBinding;
    // view 
    private LoadTrackMateFilesPanel loadTrackMateFilesPanel;
    // parent controller
    @Autowired
    private LoadTracksController loadTracksController;
    // child controllers
    // services
    private GridBagConstraints gridBagConstraints;
    private TrackMateFileParser trackMateFileParser;

    /**
     * Initialize controller
     */
    public void init() {
        trackMateFileParser = new TrackMateFileParser();
        gridBagConstraints = GuiUtils.getDefaultGridBagConstraints();
        // init main view
        initLoadTrackMateFilesPanel();
    }

    /**
     * On loading TrackMate files - render the right GUI view.
     */
    public void onLoadingTrackMateFilesGui() {
        loadTracksController.getCardLayout().show(loadTracksController.getLoadTracksPanel().getTopPanel(),
                  loadTracksController.getLoadTracksPanel().getTrackMateParentPanel().getName());
    }

    /**
     * Initialize the main view.
     */
    private void initLoadTrackMateFilesPanel() {
        // make new view
        loadTrackMateFilesPanel = new LoadTrackMateFilesPanel();

        /**
         * Add action listeners.
         */
        // load directory:
        loadTrackMateFilesPanel.getLoadDirectoryButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // we check if the directory was already loaded before
                if (loadTracksController.getDirectory() == null) {
                    loadTracksController.chooseDirectoryAndLoadData(loadTrackMateFilesPanel.getDirectoryTree());
                    loadTrackMateFilesPanel.getDirectoryTextArea().setText(loadTracksController.getDirectory().getAbsolutePath());
                } else {
                    // otherwise we ask the user if they want to reload the directory
                    Object[] options = {"Load a different directory", "Cancel"};
                    int showOptionDialog = JOptionPane.showOptionDialog(null, "It seems a directory was already loaded.\nWhat do you want to do?", "", JOptionPane.CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
                    switch (showOptionDialog) {
                        case 0: // load a different directory:
                            // reset the model of the directory tree
                            DefaultTreeModel model = (DefaultTreeModel) loadTrackMateFilesPanel.getDirectoryTree().getModel();
                            DefaultMutableTreeNode rootNote = (DefaultMutableTreeNode) model.getRoot();
                            rootNote.removeAllChildren();
                            model.reload();
                            loadTracksController.chooseDirectoryAndLoadData(loadTrackMateFilesPanel.getDirectoryTree());
                            loadTrackMateFilesPanel.getDirectoryTextArea().setText(loadTracksController.getDirectory().getAbsolutePath());
                            break;  // cancel: do nothing
                    }
                }
            }
        });

        // import the selected files
        loadTrackMateFilesPanel.getImportFilesButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get the selected file(s)
                TreePath[] selectionPaths = loadTrackMateFilesPanel.getDirectoryTree().getSelectionPaths();
                if (selectionPaths != null && selectionPaths.length != 0) {
                    for (TreePath path : selectionPaths) {
                        String fileName = (String) path.getLastPathComponent().toString();
                        File trackFile = new File(loadTracksController.getDirectory().getAbsolutePath() + File.separator + fileName);
                        try {
                            List<Track> currentTracks = trackMateFileParser.parseTrackFile(trackFile);
                            for (Track track : currentTracks) {
                                loadTracksController.getTrackSpotsBindingList().addAll(track.getTrackSpots());
                            }
                            if (trackSpotsTableBinding == null) {
                                trackSpotsTableBinding = SwingBindings.createJTableBinding(AutoBinding.UpdateStrategy.READ, loadTracksController.getTrackSpotsBindingList(), loadTrackMateFilesPanel.getTracksTable());
                                loadTracksController.showTracksInTable(trackSpotsTableBinding);
                            }
                        } catch (FileParserException ex) {
                            LOG.error(ex.getMessage(), ex);
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
        loadTracksController.getLoadTracksPanel().getTrackMateParentPanel().add(loadTrackMateFilesPanel, gridBagConstraints);
    }
}
