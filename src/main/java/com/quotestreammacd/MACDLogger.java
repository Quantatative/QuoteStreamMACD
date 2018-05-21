/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quotestreammacd;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class MACDLogger {

    public java.util.logging.Logger logger;
    FileHandler fileHandler;
    private static MACDLogger macdLogger;

    /**
     * Create private constructor
     */
    private MACDLogger() {
        logger = java.util.logging.Logger.getLogger("MACDAppLog");
    }

    /**
     * Create a static method to get instance.
     *
     * @return
     */
    public static MACDLogger getInstance() {
        if (macdLogger == null) {
            macdLogger = new MACDLogger();
        }
        return macdLogger;
    }

    public void initializeLog() throws IOException {
        File dir = new File("C:\\MACDAppLogs");
        if (!dir.exists()) {
            try {
                dir.mkdir();
            } catch (SecurityException e) {
                logger.info(e.toString());
            }
        }
        LocalDate localDate = LocalDate.now();
        fileHandler = new FileHandler("C:\\MACDAppLogs\\MACDAppLog_" + DateTimeFormatter.ofPattern("yyy_MM_dd").format(localDate) + ".log", true);
        logger.addHandler(fileHandler);
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);
    }

    public void fileHandlerClose() {
        fileHandler.close();
    }
}
