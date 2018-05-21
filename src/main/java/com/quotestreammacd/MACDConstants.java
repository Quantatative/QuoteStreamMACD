/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quotestreammacd;

import java.text.DecimalFormat;

/**
 *
 * @author
 */
public class MACDConstants {

    public static String delQueryMACDHistory = "DELETE FROM MACDHISTORY WHERE [DATE] < DATEADD(year, -1, CONVERT(DATETIME, GETDATE()))";
    public static String codeListQuery = "SELECT DISTINCT([TICKER]) FROM [Interactive].[dbo].[QSTREAMEXCEL]";
    public static String dbsql = "DBSQL";
    public static String dbmax = "DBMAX";
    public static String MACDAbove = "A";
    public static String MACDBelow = "B";
    public static final int MACD_12 = 12;
    public static final int MACD_26 = 26;
    public static final int MACD_9 = 9;
    public static final double MULTIPLIER12 = (double) 2 / (MACD_12 + 1);
    public static final double MULTIPLIER1_12 = 1.0 - MULTIPLIER12;
    public static final double MULTIPLIER26 = (double) 2 / (MACD_26 + 1);
    public static final double MULTIPLIER1_26 = 1.0 - MULTIPLIER26;
    public static final double MULTIPLIER9 = (double) 2 / (MACD_9 + 1);
    public static final double MULTIPLIER1_9 = 1.0 - MULTIPLIER9;
    public static final int SEVEN_HOUR = 7;
    public static final int THIRTY_MIN = 30;
    public static final int ELEVEN_HOUR = 11;
    public static final int FIFTEEN_MIN = 15;
    public static final DecimalFormat DEC5FORMAT = new DecimalFormat("#.#####");
    public static final int THREAD_INTERVAL = 10;
}
