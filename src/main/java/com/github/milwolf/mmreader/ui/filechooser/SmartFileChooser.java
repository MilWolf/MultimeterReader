package com.github.milwolf.mmreader.ui.filechooser;

import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.List;

/**
 * @author MilWolf
 */
public class SmartFileChooser {

    private final FileChooser fileChooser = new FileChooser();

    private File lastDirectory;
    private FileChooserContext context;

    public SmartFileChooser() {

    }

    public SmartFileChooser(FileChooserContext context) {

        this.context = context;
    }

    public File showOpenDialog(Window ownerWindow) {

        if (lastDirectory != null) {
            fileChooser.setInitialDirectory(lastDirectory);
        } else if (context != null) {
            File lastOpenedDirectory = context.getLastOpenedDirectory();
            fileChooser.setInitialDirectory(lastOpenedDirectory);
        }

        if (fileChooser.getInitialDirectory() != null && !fileChooser.getInitialDirectory().exists()) {
            fileChooser.setInitialDirectory(null);
        }

        File selectedFile = fileChooser.showOpenDialog(ownerWindow);

        if (selectedFile != null) {
            lastDirectory = selectedFile.getParentFile();

            if (context != null) {
                context.setLastOpenedDirectory(lastDirectory);
            }
        }

        return selectedFile;
    }

    public List<File> showOpenMultipleDialog(Window ownerWindow) {

        if (lastDirectory != null) {
            fileChooser.setInitialDirectory(lastDirectory);
        } else if (context != null) {
            File lastOpenedDirectory = context.getLastOpenedDirectory();
            fileChooser.setInitialDirectory(lastOpenedDirectory);
        }

        if (fileChooser.getInitialDirectory() != null && !fileChooser.getInitialDirectory().exists()) {
            fileChooser.setInitialDirectory(null);
        }

        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(ownerWindow);

        if (selectedFiles != null && selectedFiles.size() > 0) {
            File firstFile = selectedFiles.get(0);
            lastDirectory = firstFile.getParentFile();

            if (context != null) {
                context.setLastOpenedDirectory(lastDirectory);
            }
        }

        return selectedFiles;
    }

    public File showSaveDialog(Window ownerWindow) {

        if (lastDirectory != null) {
            fileChooser.setInitialDirectory(lastDirectory);
        } else if (context != null) {
            File lastOpenedDirectory = context.getLastOpenedDirectory();
            fileChooser.setInitialDirectory(lastOpenedDirectory);
        }

        if (fileChooser.getInitialDirectory() != null && !fileChooser.getInitialDirectory().exists()) {
            fileChooser.setInitialDirectory(null);
        }

        File selectedFile = fileChooser.showSaveDialog(ownerWindow);

        if (selectedFile != null) {
            lastDirectory = selectedFile.getParentFile();

            if (context != null) {
                context.setLastSaveDirectory(lastDirectory);
            }
        }

        return selectedFile;
    }

    public FileChooser getFileChooser() {
        return fileChooser;
    }
}
