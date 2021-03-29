package com.github.milwolf.mmreader.ui.filechooser;

import java.io.File;

/**
 * @author MilWolf
 */
public class FileChooserContext {

    private File lastOpenedDirectory;
    private File lastSaveDirectory;

    public FileChooserContext() {
    }

    public File getLastOpenedDirectory() {
        return lastOpenedDirectory;
    }

    public void setLastOpenedDirectory(File lastOpenedDirectory) {
        this.lastOpenedDirectory = lastOpenedDirectory;
    }

    public File getLastSaveDirectory() {
        return lastSaveDirectory;
    }

    public void setLastSaveDirectory(File lastSaveDirectory) {
        this.lastSaveDirectory = lastSaveDirectory;
    }


}
