package com.github.victormpcmun.bookmark2md.generator;

import com.github.victormpcmun.bookmark2md.model.BookmarkFolder;
import com.github.victormpcmun.bookmark2md.model.BookmarkLink;
import com.github.victormpcmun.bookmark2md.model.Item;
import com.github.victormpcmun.bookmark2md.model.TextDocument;
import com.github.victormpcmun.bookmark2md.services.TemplateService;

import java.util.Collections;

public class MDGenerator {

    private static final String MD_TEMPLATE_FILE_NAME ="MDTemplate.md";
    public static final MDGenerator INSTANCE = new MDGenerator();

    public TextDocument generate(BookmarkFolder rootBookmarkFolder) {
        TextDocument outputTextDocument = calculateOutputLines(rootBookmarkFolder);
        return TemplateService.INSTANCE.applyTemplate(MD_TEMPLATE_FILE_NAME, outputTextDocument);
    }

    private TextDocument calculateOutputLines(BookmarkFolder bookmarkFolder) {

        TextDocument outputTextDocument = TextDocument.build();
        String spacesLeftFolder = calculateSpacesLeft(bookmarkFolder.getLevel());
        outputTextDocument.addTextLine(renderFolder(bookmarkFolder, spacesLeftFolder));
        outputTextDocument.addBlankLine();

        String spacesLeftChildren = calculateSpacesLeft(bookmarkFolder.getLevel() + 1);
        for (Item item : bookmarkFolder.getItemList()) {

            if (item.isBookmarkLink()) {
                BookmarkLink bookmarkLink = (BookmarkLink) item;
                String line = renderBookmarkLink(bookmarkLink, spacesLeftChildren);
                String formattedLine = replaceUnsupportedCharacters(line);
                outputTextDocument.addTextLine(formattedLine);

            } else if (item.isFolder()) {
                BookmarkFolder bookmarkFolderChild = (BookmarkFolder) item;
                outputTextDocument.addTextDocument(calculateOutputLines(bookmarkFolderChild));
            }
        }

        outputTextDocument.addBlankLine();
        return outputTextDocument;
    }

    private String calculateSpacesLeft(int level) {
        return String.join("", Collections.nCopies(level - 1, "  "));
    }
    private String renderFolder(BookmarkFolder bookmarkFolder, String spaces) {
        return spaces + "* ## " + bookmarkFolder.getName();
    }

    private String renderBookmarkLink(BookmarkLink bookmarkLink, String spaces) {
        return spaces + "* [" + bookmarkLink.getText() + "](" + bookmarkLink.getUrl() + ")";
    }
    private String replaceUnsupportedCharacters(String s) {
        return s.replace("|", "-");
    }

}
