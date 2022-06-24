package com.github.victormpcmun.bookmark2md.model;


import java.util.ArrayList;

public class BookmarkLink extends Item {
    private String url;
    private String image;
    private String text;

    public static BookmarkLink buildWithImage(int spaces, String url, String text, String image) {
        BookmarkLink bookmarkLink = new BookmarkLink();
        bookmarkLink.spaces = spaces;
        bookmarkLink.url = url;
        bookmarkLink.image = image;
        bookmarkLink.text = text;
        return bookmarkLink;
    }

    public static BookmarkLink buildNoImage(int spaces, String url, String text) {
        return buildWithImage(spaces, url, text, null);
    }


    @Override
    public boolean isBookmarkLink() {
        return true;
    }

    @Override
    public boolean isFolder() {
        return false;
    }

    public String getText() {
        return text;
    }

    public int getLevel() {
        return bookmarkFolderHierarchyList.size() + 1;
    }

    public String getUrl() {
        return url;
    }

    public String getImage() {
        return image;
    }

    @Override
    public void calculateFolderHierarchyListIfRequired() {
        if (bookmarkFolderHierarchyList == null) {
            bookmarkFolderHierarchyList = new ArrayList<>();
            getParentBookmarkFolder().calculateFolderHierarchyListIfRequired();
            bookmarkFolderHierarchyList.addAll(getParentBookmarkFolder().getFolderHierarchyList());
        }
    }

    public boolean hasImage() {
        return image != null;
    }
}
