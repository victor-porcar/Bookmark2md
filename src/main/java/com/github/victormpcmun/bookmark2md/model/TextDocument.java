package com.github.victormpcmun.bookmark2md.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TextDocument {

    private List<String> linesList;

    public static TextDocument build() {
        TextDocument textDocument = new TextDocument();
        textDocument.linesList = new ArrayList<>();
        return textDocument;
    }

    public void addTextLine(String line) {
        linesList.add(line);
    }

    public void addBlankLine() {
        addTextLine("");
    }

    public void addTextLines(Collection<String> lines) {
        linesList.addAll(lines);
    }

    public void addTextDocument(TextDocument textDocument) {
        linesList.addAll(textDocument.getLinesList());
    }

    public List<String> getLinesList() {
        return linesList;
    }

    public boolean isEmpty() {
        return linesList.isEmpty();
    }

    public int size() {
        return linesList.size();
    }

    public TextDocument getSubDocument(int from) {
        TextDocument subDocument = TextDocument.build();
        subDocument.addTextLines(getLinesList().subList(from, size()));
        return subDocument;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String line : getLinesList()) {
            sb.append(line).append(System.lineSeparator());
        }
        return sb.toString();
    }


    public TextDocument replaceTabsForSpaces(int numberOfSpaces) {
        String replacement = new String(new char[numberOfSpaces]).replace("\0", " ");
        TextDocument newDocument = build();

        for (String line:getLinesList()) {
            String replacedLine = line.replaceAll("\\t", replacement);
            newDocument.addTextLine(replacedLine);
        }
        return newDocument;
    }


}
