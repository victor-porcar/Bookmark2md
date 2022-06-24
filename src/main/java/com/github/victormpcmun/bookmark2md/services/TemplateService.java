package com.github.victormpcmun.bookmark2md.services;

import com.github.victormpcmun.bookmark2md.model.TextDocument;

public class TemplateService {

    public static final TemplateService INSTANCE = new TemplateService();
    private final static String BLOCK = "BLOCK";
    final FileService fileService = new FileService();

    public TextDocument applyTemplate(String templateName, TextDocument bookmarkFileTextDocument) {

        TextDocument result = TextDocument.build();
        TextDocument templateTextDocument = fileService.readFileFromResource(templateName);

        for (String lineOfTemplate : templateTextDocument.getLinesList()) {
            if (lineOfTemplate.trim().contains(BLOCK)) {
                result.addTextLines(bookmarkFileTextDocument.getLinesList());
            } else {
                result.addTextLine(lineOfTemplate);
            }
        }
        return result;
    }
}
