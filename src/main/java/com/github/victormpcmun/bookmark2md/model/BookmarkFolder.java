package com.github.victormpcmun.bookmark2md.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookmarkFolder extends Item {
    private BookmarkFolder parentBookmarkFolder;
    private String name;
    private List<Item> itemList;
    private List<Item> flattenItemsList;

    public static BookmarkFolder buildRoot(String name) {
        BookmarkFolder bookmarkFolder = buildFolder(name, 0);
        bookmarkFolder.setParentBookmarkFolder(bookmarkFolder);
        return bookmarkFolder;
    }

    public static BookmarkFolder buildFolder(String name, int spaces) {
        BookmarkFolder bookmarkFolder = new BookmarkFolder();
        bookmarkFolder.name = name;
        bookmarkFolder.spaces = spaces;
        bookmarkFolder.itemList = new ArrayList<>();
        return bookmarkFolder;
    }

    public void addBookmarkLink(BookmarkLink bookmarkLink) {
        itemList.add(bookmarkLink);
    }

    public void addItem(Item item) {
        itemList.add(item);
    }

    public List<Item> getFlattenItemsList() {
        return flattenItemsList;
    }

    public boolean isRoot() {
        return this == parentBookmarkFolder;
    }

    public BookmarkFolder getParentBookmarkFolder() {
        return parentBookmarkFolder;
    }

    public String getName() {
        return name;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setParentBookmarkFolder(BookmarkFolder parentBookmarkFolder) {
        this.parentBookmarkFolder = parentBookmarkFolder;
    }

    @Override
    public boolean isBookmarkLink() {
        return false;
    }

    @Override
    public boolean isFolder() {
        return true;
    }

    public int getLevel() {

        if (this.isRoot()) {
            return 1;
        }
        return bookmarkFolderHierarchyList.size();
    }

    @Override
    public void calculateFolderHierarchyListIfRequired() {

        if (bookmarkFolderHierarchyList == null) {
            bookmarkFolderHierarchyList = new ArrayList<>();

            if (this.isRoot()) {
                bookmarkFolderHierarchyList.add(this);

            } else {
                BookmarkFolder bookmarkFolder = this;
                do {
                    bookmarkFolderHierarchyList.add(bookmarkFolder);
                    bookmarkFolder = bookmarkFolder.parentBookmarkFolder;
                } while (!bookmarkFolder.isRoot());

                bookmarkFolderHierarchyList.add(bookmarkFolder); // always root
            }
            Collections.reverse(bookmarkFolderHierarchyList);
        }
    }

    public BookmarkFolder findAncestorWithSpaces(int spacesCount) {
        BookmarkFolder currentBookmarkFolder = this;
        do {
            if (spacesCount > currentBookmarkFolder.getSpaces()) {
                return currentBookmarkFolder;
            }
            currentBookmarkFolder = currentBookmarkFolder.parentBookmarkFolder;

        } while (!currentBookmarkFolder.isRoot());

        return currentBookmarkFolder;
    }

    public void calculateFlattenItemsList() {
        flattenItemsList = new ArrayList<>();
        for (Item item : itemList) {
            if (item.isBookmarkLink()) {
                BookmarkLink bookmarkLink = (BookmarkLink) item;
                flattenItemsList.add(bookmarkLink);
            } else if (item.isFolder()) {
                BookmarkFolder bookmarkFolder = (BookmarkFolder) item;
                flattenItemsList.add(bookmarkFolder);
                bookmarkFolder.calculateFlattenItemsList();
                flattenItemsList.addAll(bookmarkFolder.getFlattenItemsList());
            }
        }
    }
}
