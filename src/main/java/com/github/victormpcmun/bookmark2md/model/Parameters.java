package com.github.victormpcmun.bookmark2md.model;


import java.io.File;

public class Parameters {

    private final static Parameters NOT_VALID = new Parameters(false);

    private String bookmarkHtmlFile;
    private String outputFolder;
    private String mdOutputFile;
    private String htmlPrettyOutputFile;
    private String rawHtmlOutputFile;
    private String folderName;

    private final boolean valid;

    private Parameters(boolean valid) {
        this.valid = valid;
    }

    public static Parameters build(String[] args) {

        if (args.length==6) {
            Parameters parameters = new Parameters(true);
            parameters.bookmarkHtmlFile = args[0];
            parameters.outputFolder = args[1];
            parameters.mdOutputFile = args[2];
            parameters.htmlPrettyOutputFile = args[3];
            parameters.rawHtmlOutputFile = args[4];
            parameters.folderName = args[5];
            return parameters;
        } else {
            return NOT_VALID;
        }
    }

    public boolean isValidParameters() {
        return valid;
    }

    public String getFolderName() {
        return folderName;
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    public String getBookmarkHtmlFile() {
        return bookmarkHtmlFile;
    }

    public String getMdOutputFilePath() {
        return calculatePath(mdOutputFile);
    }

    public String getHtmlPrettyOutputFile() {
        return calculatePath(htmlPrettyOutputFile);
    }

    public String getRawHtmlOutputFilePath() {
        return calculatePath(rawHtmlOutputFile);
    }

    private String calculatePath(String fileName) {
        return getOutputFolder() + File.separator + fileName;
    }


    @Override
    public String toString() {
        return "Parameters{" +
                "bookmarkHtmlFile='" + bookmarkHtmlFile + '\'' +
                ", outputFolder='" + outputFolder + '\'' +
                ", mdOutputFile='" + mdOutputFile + '\'' +
                ", htmlPrettyOutputFile='" + htmlPrettyOutputFile + '\'' +
                ", rawHtmlOutputFile='" + rawHtmlOutputFile + '\'' +
                ", folderName='" + folderName + '\'' +
                ", valid=" + valid +
                '}';
    }
}
