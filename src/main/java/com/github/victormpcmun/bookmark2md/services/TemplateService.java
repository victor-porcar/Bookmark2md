package com.github.victormpcmun.bookmark2md.services;

import com.github.victormpcmun.bookmark2md.model.TextDocument;

public class TemplateService {

    public static final TemplateService INSTANCE = new TemplateService();

    private final static String BLOCK_TO_REPLACE = "BLOCK_TO_REPLACE";
    private final static String TITLE_TO_REPLACE = "TITLE_TO_REPLACE";
    final FileService fileService = new FileService();

    public TextDocument applyTemplate(String templateName, TextDocument bookmarkFileTextDocument, String title) {

        TextDocument result = applyTemplate(templateName, bookmarkFileTextDocument);
        result.replaceSubString(TITLE_TO_REPLACE, title + " bookmarks");
        return result;
    }

    public TextDocument applyTemplate(String templateName, TextDocument bookmarkFileTextDocument) {

        TextDocument templateTextDocument = fileService.readFileFromResource(templateName);
        templateTextDocument.replaceWholeLineContainingSubstring(BLOCK_TO_REPLACE, bookmarkFileTextDocument);
        return templateTextDocument;
    }
}
