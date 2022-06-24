#!/bin/sh
cd "$(dirname "$0")"
java -jar ./dist/Bookmark2md.jar $1 $2 $3 $4 $5 $6
