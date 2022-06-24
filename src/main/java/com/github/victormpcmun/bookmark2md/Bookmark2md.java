package com.github.victormpcmun.bookmark2md;

import com.github.victormpcmun.bookmark2md.exception.ConversionException;
import com.github.victormpcmun.bookmark2md.model.Parameters;
import com.github.victormpcmun.bookmark2md.services.FileProcessorService;
import com.github.victormpcmun.bookmark2md.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Bookmark2md {
    private static final Logger log = LoggerFactory.getLogger(Bookmark2md.class);

    private static final String USAGE_DOCUMENT = "usage.txt";

    public static void main(String[] args) {
        Parameters parameters = Parameters.build(args);
        Bookmark2md bookmark2md = new Bookmark2md();
        bookmark2md.validateParametersOrExit(parameters);
        try {
            FileProcessorService.INSTANCE.go(parameters);
        } catch (ConversionException e) {
            log.error("Error: {}", e.getMessage());
        }


    }

    private void validateParametersOrExit(Parameters parameters) {
        if (!parameters.isValidParameters()) {
            log.error("Error: Parameters not valid");
            log.error(FileService.INSTANCE.readFileFromResource(USAGE_DOCUMENT).toString());
            System.exit(-1);
        }
    }
}


