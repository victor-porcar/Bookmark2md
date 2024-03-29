package com.github.victormpcmun.bookmark2md.services;

import com.github.victormpcmun.bookmark2md.exception.ConversionException;
import com.github.victormpcmun.bookmark2md.model.TextDocument;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileService {
    private static final  java.nio.charset.Charset CHARSET=StandardCharsets.UTF_8;
    public static final FileService INSTANCE = new FileService();

    public void writeFile(String pathLocation, TextDocument textDocument) {
        try {
            Path path = Paths.get(pathLocation);
            Files.write(path, textDocument.getLinesList(),CHARSET);
        } catch (IOException e) {
            throw new ConversionException("Can not write file:" + pathLocation+". Does this path exist?.", e);
        }
    }

    public TextDocument readFileAsTextDocument(String pathLocation) {
        TextDocument textDocument = TextDocument.build();
        try (Stream<String> stream = Files.lines(Paths.get(pathLocation), CHARSET)) {
            List<String> list = stream .collect(Collectors.toList());
            textDocument.addTextLines(list);
        } catch (IOException e) {
            throw new ConversionException("Can not read file: " + pathLocation, e);
        }

        return textDocument;
    }


    public TextDocument readFileFromResource(String fileName) {
        TextDocument textDocument = TextDocument.build();
        try (InputStream inputStream = getClass().getResourceAsStream("/" + fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                textDocument.addTextLine(line);
            }

        } catch (Exception e) {
            throw new ConversionException("Can not read file from resources", e);
        }
        return textDocument;
    }
}
