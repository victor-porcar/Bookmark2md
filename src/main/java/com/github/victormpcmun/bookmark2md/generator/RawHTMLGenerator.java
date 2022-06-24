package com.github.victormpcmun.bookmark2md.generator;

import com.github.victormpcmun.bookmark2md.model.TextDocument;
import com.github.victormpcmun.bookmark2md.services.TemplateService;

public class RawHTMLGenerator {

    private static final String RAW_HTML_TEMPLATE = "RawHTMLTEMPLATE.html";
    public static final RawHTMLGenerator INSTANCE = new RawHTMLGenerator();

    public TextDocument generate(TextDocument bookmarkFileTextDocument) {
        return TemplateService.INSTANCE.applyTemplate(RAW_HTML_TEMPLATE, bookmarkFileTextDocument);
    }

}
