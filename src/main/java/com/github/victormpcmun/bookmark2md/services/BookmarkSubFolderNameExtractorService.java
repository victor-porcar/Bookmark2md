package com.github.victormpcmun.bookmark2md.services;

import com.github.victormpcmun.bookmark2md.model.TextDocument;

public class BookmarkSubFolderNameExtractorService {

    public static final BookmarkSubFolderNameExtractorService INSTANCE = new BookmarkSubFolderNameExtractorService();

    public TextDocument getLinesForBookmarkFolder(TextDocument bookmarkFileTextDocument, String bookmarkSubFolderName) {

        TextDocument textDocument = TextDocument.build();
        boolean foundSubFolder = false;
        int folderIndentationLevel = -1;

        for (String line : bookmarkFileTextDocument.getLinesList()) {

            if (!foundSubFolder) {
                foundSubFolder = lineContainsFolderName(line, bookmarkSubFolderName);
                if (foundSubFolder) {
                    folderIndentationLevel = calculateIndentation(line);
                }
            }

            if (foundSubFolder) {
                if (line.indexOf("</DL><p>") == folderIndentationLevel) {
                    textDocument.addTextLine(line);
                    break;
                }
                textDocument.addTextLine(line);
            }
        }
        return textDocument;
    }


    private boolean lineContainsFolderName(String line, String subfolderName) {
        String folderNameAndH3Tag = subfolderName + "</H3>";
        int expectedPositionForFolderNameAndH3Tag = line.length() - folderNameAndH3Tag.length();
        int actualPositionForFolderNameAndH3Tag = line.lastIndexOf(folderNameAndH3Tag);
        boolean matchPosition = expectedPositionForFolderNameAndH3Tag == actualPositionForFolderNameAndH3Tag;
        return (actualPositionForFolderNameAndH3Tag >= 0 && matchPosition);
    }

    private int calculateIndentation(String line) {
        return line.indexOf("<"); // all lines begin with <
    }
}
