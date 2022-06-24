package com.github.victormpcmun.bookmark2md.model;


import java.io.File;

public class Parameters {

    private final static Parameters NOT_VALID = new Parameters(false);

    private String bookmarkSubFolderName;
    private String destinationFolder;
    private String bookmarkInputFile;
    private String mdOutputFile;
    private String htmlOutputFile;
    private String rawHtmlOuputFile;
    private final boolean valid;

    private Parameters(boolean valid) {
        this.valid = valid;
    }

    public static Parameters build(String[] args) {

        if (args.length == 6) {
            Parameters parameters = new Parameters(true);
            parameters.bookmarkInputFile = args[0];
            parameters.bookmarkSubFolderName = args[1];
            parameters.destinationFolder = args[2];

            parameters.mdOutputFile = args[3];
            parameters.htmlOutputFile = args[4];
            parameters.rawHtmlOuputFile = args[5];
            return parameters;
        } else {
            return NOT_VALID;
        }
    }

    public boolean isValidParameters() {
        return valid;
    }

    public String getBookmarkSubFolderName() {
        return bookmarkSubFolderName;
    }

    public String getDestinationFolder() {
        return destinationFolder;
    }

    public String getBookmarkInputFile() {
        return bookmarkInputFile;
    }

    @Override
    public String toString() {
        return "Parameters{" +
                "bookmarkFolderName='" + bookmarkSubFolderName + '\'' +
                ", destinationFolder='" + destinationFolder + '\'' +
                ", bookmarkInputFile='" + bookmarkInputFile + '\'' +
                ", mdOutputFile='" + mdOutputFile + '\'' +
                ", htmlOutputFile='" + htmlOutputFile + '\'' +
                ", rawHtmlOuputFile='" + rawHtmlOuputFile + '\'' +
                ", valid=" + valid +
                '}';
    }

    public String getMdOutputFilePath() {
        return calculatePath(mdOutputFile);
    }

    public String getHtmlOutputFilePath() {
        return calculatePath(htmlOutputFile);
    }

    public String getRawHtmlOuputFilePath() {
        return calculatePath(rawHtmlOuputFile);
    }

    private String calculatePath(String fileName) {
        return getDestinationFolder() + File.separator + fileName;
    }
}
