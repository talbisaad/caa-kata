package com.caa.kata.service.printing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrintingService implements IPrintingService{
    private static final Logger logger = LoggerFactory.getLogger(PrintingService.class);

    @Override
    public void display(String content) {
        logger.info(content);

    }
}
