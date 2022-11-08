package com.github.victormpcmun.bookmark2md.generator;

import com.github.victormpcmun.bookmark2md.model.BookmarkFolder;
import com.github.victormpcmun.bookmark2md.model.BookmarkLink;
import com.github.victormpcmun.bookmark2md.model.Item;
import com.github.victormpcmun.bookmark2md.model.TextDocument;
import com.github.victormpcmun.bookmark2md.services.TemplateService;

import java.util.Collections;

public class HTMLGenerator {

    private static final String TINIEST_IMAGE="data:image/gif;base64,R0lGODlhAQABAAD/ACwAAAAAAQABAAACADs=";

    public static final HTMLGenerator INSTANCE = new HTMLGenerator();

    public TextDocument generate(BookmarkFolder rootBookmarkFolder, String title) {
        TextDocument outputTextDocument = calculateOutputTextDocument(rootBookmarkFolder);
        return TemplateService.INSTANCE.applyTemplate("HTMLTemplate.html", outputTextDocument, title);
    }

    private TextDocument calculateOutputTextDocument(BookmarkFolder rootBookmarkFolder) {
        return calculateOutputTextDocument(rootBookmarkFolder, 2);
    }

    private TextDocument calculateOutputTextDocument(BookmarkFolder bookmarkFolder, int hTagLevel) {

        TextDocument outputTextDocument = TextDocument.build();

        String spacesLeftFolder = calculateSpacesLeft(bookmarkFolder.getLevel());

        outputTextDocument.addTextLine(spacesLeftFolder + "<DT><H" + hTagLevel + ">" + bookmarkFolder.getName() + "</H" + hTagLevel + ">");
        outputTextDocument.addTextLine(spacesLeftFolder + "<DL><p>");

        String spacesLeftChildren = calculateSpacesLeft(bookmarkFolder.getLevel() + 1);
        for (Item item : bookmarkFolder.getItemList()) {

            if (item.isBookmarkLink()) {
                processBookmarkLink((BookmarkLink) item, outputTextDocument, spacesLeftChildren);
            } else if (item.isFolder()) {
                processBookmarkFolder((BookmarkFolder) item, outputTextDocument);
            }
        }

        outputTextDocument.addTextLine(spacesLeftFolder + "</DL><p>");
        return outputTextDocument;
    }

    private void processBookmarkLink(BookmarkLink bookmarkLink, TextDocument outputTextDocument, String spacesLeft) {
        String line;

        String formattedText = bookmarkLink.getText();

        formattedText = GeneratorFormatter.replaceBold(formattedText, s -> "<span class=\"bold-text\">" + s + "</span>");
        formattedText = GeneratorFormatter.replaceItalic(formattedText, s -> "<span class=\"italic-text\">" + s + "</span>");
        formattedText = GeneratorFormatter.replaceBoldItalic(formattedText, s -> "<span class=\"bolditalic-text\">" + s + "</span>");

        String image =calculateImage(bookmarkLink);

        line = "<DT><IMG SRC=\"" + image + "\"" + " ><A HREF=\"" + bookmarkLink.getUrl() + "\">" + formattedText + "</A>";

        outputTextDocument.addTextLine(spacesLeft + line);
    }


    private void processBookmarkFolder(BookmarkFolder bookmarkFolder, TextDocument outputTextDocument) {
        outputTextDocument.addTextDocument(calculateOutputTextDocument(bookmarkFolder, 3));
    }

    private String calculateSpacesLeft(int level) {
        return String.join("", Collections.nCopies(level, " "));
    }

    private String calculateImage(BookmarkLink bookmarkLink) {
        if (bookmarkLink.hasImage()) {
            return bookmarkLink.getImage();
        } else {
            return TINIEST_IMAGE;
        }
    }
}
