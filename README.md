## Bookmark2md

This is a converter from exported browser bookmarks (as html) into:

* Markdown (.md)
* Pretty HTML
* Raw importable HTML *NETSCAPE-Bookmark-file-1" DOCTYPE* to import back to any browser


### Usage

The application is built as a executable java jar component that requires 6 mandatory parameters

```	
java -jar dist/bookmark2md.jar <BOOKMARK_HTML> <OUTPUT_FOLDER> <OUTPUT_MD_FILE> <OUTPUT_PRETTY_HTML_FILE> <OUTPUT_RAW_BOOKMARK_EXPORT_FILE> <FOLDER_NAME>
```	
Where

- BOOKMARK_HTML_FILE is the absolute or relative path to the html file exported right from the browser:
  - in Chrome you can export this file in *Bookmark* -> *Bookmark manager* -> *Export Bookmarks*
  - in Firefox you can export this file in *Bookmark* -> *Manage Bookmarks* -> *Import and Backup* -> "Export Bookmarks to HTML ..."
  
- OUTPUT_FOLDER is the absolute or relative destination folder where the generated files will be created
- OUTPUT_MD_FILE is the generated markdown file .md name
- OUTPUT_PRETTY_HTML_FILE is the generated pretty HTML file .html name
- OUTPUT_RAW_BOOKMARK_EXPORT_FILE is the generated "NETSCAPE-Bookmark-file-1" DOCTYPE compliant file name ready to be imported in any browser
- FOLDER_NAME this parameter is the case-sensitive bookmark folder name for the conversion. The conversion will include the bookmarks belonging to this folder and its folder children recursively


example:

convert bookmarks contained in bookmark folder called "TECHNICAL"

```	
java -jar ./dist/Bookmark2md.jar ./bookmarks_22_05_22.html  ./OUTPUT_FOLDER  bookmarks.md pretty_bookmarks.html bookmarks_to_import.html TECHNICAL
```	

### Build

Generate jar (in **/dist** )

```	
git clone https://github.com/victormpcmun/bookmark2md	
mvn clean package	
```

### Bookmark text format

The bookmark text may be formatted as follows:

text between * is rendered in italic
text between ** is rendered in bold
text between *** is rendered in italic and bold


### Improvements

Although I don't plan to continue working on this tool, there is plenty of room for improvement, feel free if you feel like.
