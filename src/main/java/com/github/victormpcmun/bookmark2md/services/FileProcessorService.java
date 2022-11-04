package com.github.victormpcmun.bookmark2md.services;

import com.github.victormpcmun.bookmark2md.generator.HTMLGenerator;
import com.github.victormpcmun.bookmark2md.generator.MDGenerator;
import com.github.victormpcmun.bookmark2md.generator.RawHTMLGenerator;
import com.github.victormpcmun.bookmark2md.model.BookmarkFolder;
import com.github.victormpcmun.bookmark2md.model.Parameters;
import com.github.victormpcmun.bookmark2md.model.TextDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileProcessorService {

    private static final int NUMBER_OF_SPACES_FOR_TAB = 4;
    private static final Logger log = LoggerFactory.getLogger(FileProcessorService.class);
    private static final String SUCCESS_GENERATING_FILE = "Files successfully generated.";

    public static final FileProcessorService INSTANCE = new FileProcessorService();


    public void go(Parameters parameters) {
        log.info("Beginning conversion process with parameters:" + parameters);


        String bookmarkHtmlFile = parameters.getBookmarkHtmlFile();
        TextDocument fullBookmarkFileTextDocument = FileService.INSTANCE.readFileAsTextDocument(bookmarkHtmlFile);
        TextDocument fullBookmarkFileTextDocumentTabSafe = fullBookmarkFileTextDocument.replaceTabsForSpaces(NUMBER_OF_SPACES_FOR_TAB);
        String folderName = parameters.getFolderName();
        TextDocument  bookmarkFileTextDocument = BookmarkSubFolderNameExtractorService.INSTANCE.getLinesForBookmarkFolder(fullBookmarkFileTextDocumentTabSafe, folderName);

        if (bookmarkFileTextDocument.isEmpty()) {
            log.error("No bookmarks to convert found, perhaps the folderName is not correct (it is case-sensitive)" + bookmarkHtmlFile);
            return;
        }

        processOutputFiles(parameters, bookmarkFileTextDocument);

        log.info(SUCCESS_GENERATING_FILE);
    }



    public void processOutputFiles(Parameters parameters, TextDocument bookmarkFileTextDocument) {

        TextDocument linesToBeRendered = bookmarkFileTextDocument.getSubDocument(1);

        //--------------
        String bookmarkFolderName = parameters.getFolderName();
        BookmarkFolder bookmarkFolder = RenderItemService.INSTANCE.render(linesToBeRendered, bookmarkFolderName);

        // md
        TextDocument outputTextDocumentMD = MDGenerator.INSTANCE.generate(bookmarkFolder);
        FileService.INSTANCE.writeFile(parameters.getMdOutputFilePath(), outputTextDocumentMD);


        // html
        TextDocument outputTextDocumentHTML = HTMLGenerator.INSTANCE.generate(bookmarkFolder);
        FileService.INSTANCE.writeFile(parameters.getHtmlPrettyOutputFile(), outputTextDocumentHTML);


        // raw html
        TextDocument rawHtmlOutputTextDocument = RawHTMLGenerator.INSTANCE.generate(bookmarkFileTextDocument);
        FileService.INSTANCE.writeFile(parameters.getRawHtmlOutputFilePath(), rawHtmlOutputTextDocument);

    }
}
