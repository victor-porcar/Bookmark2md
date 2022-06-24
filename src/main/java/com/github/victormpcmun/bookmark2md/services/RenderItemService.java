package com.github.victormpcmun.bookmark2md.services;

import com.github.victormpcmun.bookmark2md.model.BookmarkFolder;
import com.github.victormpcmun.bookmark2md.model.BookmarkLink;
import com.github.victormpcmun.bookmark2md.model.Item;
import com.github.victormpcmun.bookmark2md.model.TextDocument;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RenderItemService {
    public static final RenderItemService INSTANCE = new RenderItemService();
    private static final Pattern ANCHORED_LINE_IMAGE_LINK_PATTERN = Pattern.compile("(.+?)<DT><A HREF=\"(.+?)\"(.*?)ICON=\"(.+?)\">(.*?)</A>");
    private static final Pattern ANCHORED_LINE_NO_IMAGE_LINK_PATTERN = Pattern.compile("(.+?)<DT><A HREF=\"(.+?)\"(.*?)>(.*?)</A>");

    private static final Pattern ANCHORED_FOLDER_NAME_LINK_PATTERN = Pattern.compile("(.+?)<DT><H3(.*?)>(.*?)</H3>");

    public BookmarkFolder render(TextDocument listOfLines, String folderName) {

        BookmarkFolder bookmarkFolderRoot = BookmarkFolder.buildRoot(folderName);
        BookmarkFolder bookmarkFolder = bookmarkFolderRoot;

        for (String s : listOfLines.getLinesList()) {
            bookmarkFolder = render(s, bookmarkFolder);

        }
        bookmarkFolderRoot.calculateFlattenItemsList();

        for (Item item : bookmarkFolderRoot.getFlattenItemsList()) {
            item.calculateFolderHierarchyListIfRequired();
        }
        return bookmarkFolderRoot;
    }

    private BookmarkFolder render(String line, BookmarkFolder bookmarkFolder) {

        Matcher lineLinkMatcherWithImage = ANCHORED_LINE_IMAGE_LINK_PATTERN.matcher(line);
        if (lineLinkMatcherWithImage.find()) {
            return processBookmarkLinkWithImage(bookmarkFolder, lineLinkMatcherWithImage);
        }

        //
        Matcher lineLinkMatcherNoImage = ANCHORED_LINE_NO_IMAGE_LINK_PATTERN.matcher(line);
        if (lineLinkMatcherNoImage.find()) {
            return processBookmarkLinkWithNoImage(bookmarkFolder, lineLinkMatcherNoImage);
        }

        //

        Matcher folderLinkMatcher = ANCHORED_FOLDER_NAME_LINK_PATTERN.matcher(line);
        if (folderLinkMatcher.find()) {
            return getFolder(bookmarkFolder, folderLinkMatcher);
        }

        return bookmarkFolder;
    }

    private BookmarkFolder getFolder(BookmarkFolder bookmarkFolder, Matcher titleLinkMatcher) {
        String spaces = titleLinkMatcher.group(1);
        String title = titleLinkMatcher.group(3);
        int spacesCount = spaces.length();
        BookmarkFolder newBookmarkFolder = BookmarkFolder.buildFolder(title, spacesCount);

        if (spacesCount > bookmarkFolder.getSpaces()) {
            newBookmarkFolder.setParentBookmarkFolder(bookmarkFolder);
            bookmarkFolder.addItem(newBookmarkFolder);
        } else if (spacesCount == bookmarkFolder.getSpaces()) {
            bookmarkFolder.getParentBookmarkFolder().addItem(newBookmarkFolder);
            newBookmarkFolder.setParentBookmarkFolder(bookmarkFolder.getParentBookmarkFolder());
        } else { //spacesCount<folder.getSpaces()
            BookmarkFolder brother = bookmarkFolder.findAncestorWithSpaces(spacesCount);
            brother.getParentBookmarkFolder().addItem(newBookmarkFolder);
            newBookmarkFolder.setParentBookmarkFolder(brother.getParentBookmarkFolder());
        }
        return newBookmarkFolder;
    }

    private BookmarkFolder processBookmarkLinkWithNoImage(BookmarkFolder bookmarkFolder, Matcher lineLinkMatcher) {
        String spaces = lineLinkMatcher.group(1);
        String link = lineLinkMatcher.group(2);

        int spacesCount = spaces.length();

        String linkText = lineLinkMatcher.group(4);

        BookmarkLink bookmarkLink = BookmarkLink.buildNoImage(spacesCount, link, linkText);

        bookmarkFolder = bookmarkFolder.findAncestorWithSpaces(spacesCount);
        bookmarkFolder.addBookmarkLink(bookmarkLink);
        bookmarkLink.setParentBookmarkFolder(bookmarkFolder);
        return bookmarkFolder;
    }

    private BookmarkFolder processBookmarkLinkWithImage(BookmarkFolder bookmarkFolder, Matcher lineLinkMatcher) {
        String spaces = lineLinkMatcher.group(1);
        String image = lineLinkMatcher.group(4);
        String link = lineLinkMatcher.group(2);

        int spacesCount = spaces.length();

        String linkText = lineLinkMatcher.group(5);

        BookmarkLink bookmarkLink = BookmarkLink.buildWithImage(spacesCount, link, linkText, image);

        bookmarkFolder = bookmarkFolder.findAncestorWithSpaces(spacesCount);
        bookmarkFolder.addBookmarkLink(bookmarkLink);
        bookmarkLink.setParentBookmarkFolder(bookmarkFolder);
        return bookmarkFolder;
    }
}
