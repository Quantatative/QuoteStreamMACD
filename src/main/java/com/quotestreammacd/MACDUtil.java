/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quotestreammacd;

import static com.quotestreammacd.MACDConstants.*;
import static com.quotestreammacd.Main.macdLogger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author
 */
public class MACDUtil {

    DataAccessUtil dataAccessUtil;
    Calendar calendar;

    public MACDUtil() {
        dataAccessUtil = new DataAccessUtil();
    }

    public static String round5DecS(double d) {
        return DEC5FORMAT.format(d);
    }

    public IndicatorList buildIndicator(ShareList shareList) {
        IndicatorList indicatorList = new IndicatorList();
        if (shareList.getSize() > 0) {
            Share yesterdayCurrency = shareList.getShare(0);
            double yesterdayema12 = yesterdayCurrency.getClosePrice();
            double yesterdayema26 = yesterdayCurrency.getClosePrice();
            double signalValue = 0.0;
            double yesterdaysignalValue = 0.0;
            for (int i = 1; i < shareList.getSize(); i++) {
                Share todayShare = shareList.getShare(i);
                Double close = todayShare.getClosePrice();
                Hashtable resultHash = calculateMACD(close, yesterdayema12, yesterdayema26, yesterdaysignalValue);
                double macdValue = (double) resultHash.get("macdValue");
                if (i == (MACD_26 - 1)) {
                    signalValue = macdValue;
                    yesterdaysignalValue = macdValue;
                } else if (i > (MACD_26 - 1)) {
                    signalValue = (double) resultHash.get("signalValue");
                }
                Indicator indicator = new Indicator(todayShare.getSharedate(), todayShare.getCode(), (double) resultHash.get("ema12"),
                        (double) resultHash.get("ema26"), Double.parseDouble(MACDUtil.round5DecS(macdValue)), Double.parseDouble(MACDUtil.round5DecS(signalValue)));
                if (macdValue > signalValue) {
                    indicator.setStatusValue(MACDAbove);
                } else {
                    indicator.setStatusValue(MACDBelow);
                }
                indicatorList.addIndicator(indicator);
                yesterdayema12 = (double) resultHash.get("ema12");
                yesterdayema26 = (double) resultHash.get("ema26");
                yesterdaysignalValue = signalValue;
            }
        }
        return indicatorList;
    }

    public Hashtable calculateMACD(double close, double yesterdayma1Perc, double yesterdayma2Perc, double yesterdaysignalVal) {
        double ema12 = (close * MULTIPLIER12) + (yesterdayma1Perc * MULTIPLIER1_12);
        double ema26 = (close * MULTIPLIER26) + (yesterdayma2Perc * MULTIPLIER1_26);
        double macdValue = ema12 - ema26;
        double signalValue = (macdValue * MULTIPLIER9) + (yesterdaysignalVal * MULTIPLIER1_9);
        Hashtable retHash = new Hashtable();
        retHash.put("macdValue", macdValue);
        retHash.put("signalValue", signalValue);
        retHash.put("ema12", ema12);
        retHash.put("ema26", ema26);
        return retHash;
    }
    
    public ArrayList getCodeList() {
        ArrayList<String> codeList = new ArrayList<>();
        try {
            ResultSet resultSet = dataAccessUtil.selectRows(dbsql, codeListQuery);
            if (resultSet.next() == false) {
                macdLogger.logger.log(Level.INFO, " Result Set is empty");
            } else {
                do {
                    String ecode = resultSet.getString(1);
                    codeList.add(ecode);
                } while (resultSet.next());
            }
        } catch (SQLException ex) {
            Logger.getLogger(MACDUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeList;
    }

    public int getNoOfDays(String code, String stat) {
        int noOfDays = 0;
        try {
            ResultSet resultSet = getMACDStatus(code);
            if (resultSet.next() == false) {
                macdLogger.logger.log(Level.INFO, "getNoOfDays Result Set is empty");
            } else {
                do {
                    if (resultSet.getString(3).trim().equals(stat)) {
                        noOfDays = Integer.parseInt(resultSet.getString(4));
                    }
                } while (resultSet.next());
            }
        } catch (SQLException ex) {
            Logger.getLogger(MACDUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return noOfDays;
    }

    public HashMap getPreviousIndicatorValues(String code, double yesterdayma1Perc, double yesterdayma2Perc, double yesterdaysignalVal) {
        HashMap retMap = new HashMap();
        try {
            ResultSet resultSet = getMACD(code);
            if (resultSet.next() == false) {
                macdLogger.logger.log(Level.INFO, "getPreviousIndicatorValues Result Set is empty");
            } else {
                do {
                    yesterdayma1Perc = Double.parseDouble(resultSet.getString(2));
                    yesterdayma2Perc = Double.parseDouble(resultSet.getString(3));
                    yesterdaysignalVal = Double.parseDouble(resultSet.getString(5));
                    retMap.put("yesterdayema12", yesterdayma1Perc);
                    retMap.put("yesterdayema26", yesterdayma2Perc);
                    retMap.put("yesterdaysignalVal", yesterdaysignalVal);
                } while (resultSet.next());
            }
        } catch (SQLException ex) {
            Logger.getLogger(MACDUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retMap;
    }

    public void updateMACDLiveStatus(String timeStamp, String code, double close, int noOfDays, String stat, double macdVal, double signalVal) {
        String selQuery = "SELECT * FROM [Interactive].[dbo].[QSTREAMMACDLiveStatus] WHERE CODE='" + code + "'";
        ResultSet resultSet = dataAccessUtil.selectRows("DBSQL", selQuery);
        try {
            if (resultSet.next() == false) {
                String insertQuery = "INSERT INTO [Interactive].[dbo].[QSTREAMMACDLiveStatus] VALUES ( '" + timeStamp + "','" + code
                        + "', " + close
                        + ", " + noOfDays
                        + ", '" + stat
                        + "', " + macdVal + ", " + signalVal + ")";
                dataAccessUtil.insertRows(dbsql, insertQuery);
            } else {
                String updateQuery = "UPDATE [Interactive].[dbo].[QSTREAMMACDLiveStatus] SET [UPDATEDTIME]='" + timeStamp + "'"
                        + ", MID=" + close
                        + ", NOOFDAYS=" + noOfDays
                        + ", STATUS='" + stat + "'"
                        + ", MACD=" + macdVal
                        + ", SIGNAL=" + signalVal
                        + " WHERE CODE='" + code + "'";
                dataAccessUtil.updateRows(dbsql, updateQuery);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MACDUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet getMACDStatus(String code) {
        String query = "SELECT [CODE] ,[Date],[Status],[NoOfDays] FROM [Interactive].[dbo].[QSTREAMMACDStatus] where code='" + code + "'";
        ResultSet resultSet = dataAccessUtil.selectRows(dbsql, query);
        return resultSet;
    }

    public ResultSet getLiveMidPrice(String code) {
        String query = "SELECT [TICKER] ,[LAST] ,[UPDATEDTIME] FROM [Interactive].[dbo].[QSTREAMEXCEL] where TICKER='" + code + "'";
        ResultSet resultSet = dataAccessUtil.selectRows(dbsql, query);
        return resultSet;
    }

    public ResultSet getMACD(String code) {
        String query1 = "SELECT [code],[ema12],[ema26],[macd],[signal],[date]  FROM [Interactive].[dbo].[QSTREAMMACD] where code='" + code + "'";
        ResultSet resultSet = dataAccessUtil.selectRows(dbsql, query1);
        return resultSet;
    }

    public ShareList getShareList(String code) {
        ShareList shareList = new ShareList();
        try {
            ResultSet resultSet = getMACDHistoryList(code);
            if (resultSet.next() == false) {
                macdLogger.logger.log(Level.INFO, "getShareList Result Set is empty");
            } else {
                do {
                    String shareDate = resultSet.getString(2);
                    String dateStr = shareDate.substring(0, 10);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                    String date = dateStr.replaceAll("-", "/");
                    LocalDate localDate = LocalDate.parse(date, formatter);
                    String symbol = resultSet.getString(1);
                    if (resultSet.getString(3) != null && resultSet.getString(4) != null && resultSet.getString(5) != null && resultSet.getString(6) != null) {
                        Share share = new Share(localDate, symbol, Double.parseDouble(resultSet.getString(3)), Double.parseDouble(resultSet.getString(4)),
                                Double.parseDouble(resultSet.getString(5)), Double.parseDouble(resultSet.getString(6)));
                        shareList.addShare(share);
                    }
                } while (resultSet.next());
            }
        } catch (SQLException ex) {
            Logger.getLogger(MACDUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return shareList;
    }

    public ResultSet getMACDHistoryList(String code) {
        String query = "SELECT [SYMBOL],[SHARE_DATE],[OPEN],[HIGH],[LOW],[CLOSE] FROM [Interactive].[dbo].[QUOTESTREAMHISTORY] where [SYMBOL] = '" + code + "' order by [SHARE_DATE]";
        ResultSet resultSet = dataAccessUtil.selectRows("DBSQL", query);
        return resultSet;
    }

    public void updateMACD(Indicator indicator) {
        String selQuery = "SELECT * FROM QSTREAMMACD WHERE CODE='" + indicator.getCode() + "'";
        ResultSet resultSet = dataAccessUtil.selectRows("DBSQL", selQuery);
        try {
            if (resultSet.next() == false) {
                String insertQuery = "INSERT INTO QSTREAMMACD VALUES ( '" + indicator.getCode() + "'," + indicator.getEma13()
                        + ", " + indicator.getEma26()
                        + ", " + indicator.getValue()
                        + ", " + indicator.getSignalValue()
                        + ", '" + indicator.getDDate() + "')";
                dataAccessUtil.insertRows(dbsql, insertQuery);
            } else {
                String updateQuery = "UPDATE QSTREAMMACD SET [EMA12]=" + indicator.getEma13()
                        + ", [EMA26]=" + indicator.getEma26()
                        + ", [MACD]=" + indicator.getValue()
                        + ", [SIGNAL]=" + indicator.getSignalValue()
                        + ", [DATE]='" + indicator.getDDate() + "'"
                        + " WHERE CODE='" + indicator.getCode() + "'";
                dataAccessUtil.updateRows(dbsql, updateQuery);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MACDUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateMACDStatus(IndicatorList indicatorList) {
        int noOfDays = getNumberOfDays(indicatorList);
        Indicator indicator = indicatorList.getIndicatorData(indicatorList.getSize() - 1);
        updateNumberOfDays(indicator, noOfDays);
    }

    public int getNumberOfDays(IndicatorList indicatorList) {
        int noOfDays = 1;
        String status = indicatorList.getIndicatorData(indicatorList.getSize() - 1).getStatusValue();
        for (int i = indicatorList.getSize() - 2; i > 0; i--) {
            String prevStatus = indicatorList.getIndicatorData(i).getStatusValue();
            if (status.equals(prevStatus)) {
                noOfDays++;
            } else {
                break;
            }
        }
        return noOfDays;
    }

    public void updateNumberOfDays(Indicator indicator, int noOfDays) {

        String selQuery = "SELECT * FROM QSTREAMMACDSTATUS WHERE CODE='" + indicator.getCode() + "'";
        ResultSet resultSet = dataAccessUtil.selectRows("DBSQL", selQuery);
        try {
            if (resultSet.next() == false) {
                String insertQuery = "INSERT INTO QSTREAMMACDSTATUS VALUES ( '" + indicator.getCode() + "','" + indicator.getDDate()
                        + "', '" + indicator.getStatusValue()
                        + "', " + noOfDays
                        + ")";
                dataAccessUtil.insertRows(dbsql, insertQuery);
            } else {
                String updateQuery = "UPDATE QSTREAMMACDSTATUS SET [DATE]='" + indicator.getDDate() + "', STATUS='"
                        + indicator.getStatusValue() + "', NOOFDAYS=" + noOfDays + " WHERE CODE='"
                        + indicator.getCode() + "'";
                dataAccessUtil.updateRows(dbsql, updateQuery);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MACDUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
