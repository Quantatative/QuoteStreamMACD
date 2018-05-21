package com.quotestreammacd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.quotestreammacd.MACDConstants.*;
import static com.quotestreammacd.Main.macdLogger;
import java.util.Arrays;
import java.util.List;

public class MACDUpdate implements Runnable {

    final private ScheduledExecutorService SCHEDULERTHREAD;
    DateTimeFormatter dateTimeFormatter;
    DataAccessUtil dataAccessUtil;
    MACDUtil macdUtil;
    List<String> codeList = new ArrayList<>();
    Calendar calendar;

    public MACDUpdate() {
        SCHEDULERTHREAD = Executors.newSingleThreadScheduledExecutor();
        dataAccessUtil = new DataAccessUtil();
        macdUtil = new MACDUtil();
//        String[] codeArray = {"BE:CNX", "BIGG:CNX", "BLO:CNX", "BTL", "CCC", "HC:CNX", "IBAT:CNX", "IN", "LION:CNX", "NXT", "PAS", "PWM", "MARI:CNX", 
//"RHT", "RYU", "SQA:CNX", "XMG:CNX", "ZFR"};
//        codeList = Arrays.asList(codeArray);
        codeList = macdUtil.getCodeList();
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        calendar = Calendar.getInstance();
    }

    public void startThread() {
        SCHEDULERTHREAD.scheduleAtFixedRate(this, 0, THREAD_INTERVAL, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        updateMACDTable();
        updateMACDLiveStatus();
        System.gc();
    }

    private void updateMACDTable() {
//        if (calendar.get(Calendar.HOUR_OF_DAY) == SEVEN_HOUR && calendar.get(Calendar.MINUTE) == THIRTY_MIN) {
        codeList.stream().map((code) -> macdUtil.getShareList(code)).map((subShareList) -> macdUtil.buildIndicator(subShareList))
                .filter((indicatorList) -> (indicatorList.getSize() > 0)).forEach((indicatorList) -> {
            Indicator indicator = indicatorList.getIndicatorData(indicatorList.getSize() - 1);
            macdUtil.updateMACD(indicator);
            macdUtil.updateMACDStatus(indicatorList);
        });
//        }
    }

    private void updateMACDLiveStatus() {
        for (String code : codeList) {
            try {
                double yesterdayema12 = 0.0, yesterdayema26 = 0.0, yesterdaysignalValue = 0.0, close = 0.0, macdValue = 0.0, signalValue = 0.0;
                String timeStamp = "", stat = MACDBelow;
                int noOfDays = 0;
                HashMap resultMap = macdUtil.getPreviousIndicatorValues(code, yesterdayema12, yesterdayema26, yesterdaysignalValue);
                if (!resultMap.isEmpty()) {
                    yesterdayema12 = (double) resultMap.get("yesterdayema12");
                    yesterdayema26 = (double) resultMap.get("yesterdayema26");
                    yesterdaysignalValue = (double) resultMap.get("yesterdaysignalVal");
                    ResultSet resultSet = macdUtil.getLiveMidPrice(code);
                    if (resultSet.next() == false) {
                        macdLogger.logger.log(Level.INFO, "updateMACDLiveStatus Result Set is empty");
                    } else {
                        do {
                            if (resultSet.getString(2) != null) {
                                close = Double.parseDouble(resultSet.getString(2));
                            }
                            Hashtable resultHash = macdUtil.calculateMACD(close, yesterdayema12, yesterdayema26, yesterdaysignalValue);
                            macdValue = (double) resultHash.get("macdValue");
                            signalValue = (double) resultHash.get("signalValue");
                            if (resultSet.getString(3) != null) {
                                timeStamp = resultSet.getString(3);
                            }
                            if (macdValue > signalValue) {
                                stat = MACDAbove;
                            }
                            noOfDays = macdUtil.getNoOfDays(code, stat);
                        } while (resultSet.next());
                    }
                }
                System.out.println("timeStamp :: " + timeStamp + " code :: " + code + " close :: " + close + " noOfDays :: " + noOfDays + " stat :: " + stat + " macdVal :: " + macdValue + " signalVal :: " + signalValue);
                new MACDUtil().updateMACDLiveStatus(timeStamp, code, close, noOfDays, stat, macdValue, signalValue);
            } catch (SQLException ex) {
                Logger.getLogger(MACDUpdate.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
