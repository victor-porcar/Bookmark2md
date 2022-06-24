package com.github.victormpcmun.bookmark2md.model;

import java.util.List;

public abstract class Item {
    private BookmarkFolder parentBookmarkFolder;
    protected List<BookmarkFolder> bookmarkFolderHierarchyList;
    protected int spaces;

    public abstract boolean isBookmarkLink();
    public abstract boolean isFolder();
    public abstract void calculateFolderHierarchyListIfRequired();

    public int getSpaces() {
        return spaces;
    }
    public BookmarkFolder getParentBookmarkFolder() {
        return parentBookmarkFolder;
    }
    public void setParentBookmarkFolder(BookmarkFolder parentBookmarkFolder) {
        this.parentBookmarkFolder = parentBookmarkFolder;
    }
    public List<BookmarkFolder> getFolderHierarchyList() {
        return bookmarkFolderHierarchyList;
    }
}
