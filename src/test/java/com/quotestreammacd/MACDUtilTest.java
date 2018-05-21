/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quotestreammacd;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author
 */
public class MACDUtilTest {

    public MACDUtilTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of round5DecS method, of class MACDUtil.
     */
    @Test
    public void testRound5DecS() {
        double d = 5.12345678;
        String result = MACDUtil.round5DecS(d);
        String expResult = "5.12346";
        assertEquals(expResult, result);       
    }

    /**
     * Test of buildIndicator method, of class MACDUtil.
     */
    @Test
    public void testBuildIndicator() {
        System.out.println("buildIndicator test");
        MACDUtil instance = new MACDUtil();
        ShareList shareList = instance.getShareList("ACST");
        IndicatorList expResult = null;
        LocalDate date = LocalDate.now().minusDays(1);
        Indicator expectedIndicator = new Indicator(date, "ACST", 0.7047675085561922, 0.7899851973400855, -0.08522, -0.09049);
        IndicatorList result = instance.buildIndicator(shareList);
        Indicator resultIndicator  = result.getIndicatorData(result.getSize() - 1);
        assertEquals(expectedIndicator.getDDate(), resultIndicator.getDDate());
        assertEquals(expectedIndicator.getCode(), resultIndicator.getCode());
        assertEquals(expectedIndicator.getEma13(), resultIndicator.getEma13(),0);
//        assertEquals(expectedIndicator, resultIndicator);
    }
//
    /**
     * Test of calculateMACD method, of class MACDUtil.
     */
    @Test
    public void testCalculateMACD() {
        System.out.println("calculateMACD");
        double close = 0.04000000;
        double yesterdayma1Perc = 0.04361180;
        double yesterdayma2Perc = 0.04578154;
        double yesterdaysignalVal = -0.00178000;
        MACDUtil instance = new MACDUtil();
        Hashtable expResult = new Hashtable();
        expResult.put("ema26", 0.045353277777777784);
        expResult.put("ema12", 0.04305613846153846);
        expResult.put("macdValue", -0.002297139316239323);
        expResult.put("signalValue", -0.0018834278632478645);
        Hashtable result = instance.calculateMACD(close, yesterdayma1Perc, yesterdayma2Perc, yesterdaysignalVal);
        System.out.println("result :: "+result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getCodeList method, of class MACDUtil.
     */
    @Test
    public void testGetCodeList() {
        System.out.println("getCodeList");
        MACDUtil instance = new MACDUtil();
        ArrayList expResult = null;
        ArrayList result = instance.getCodeList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

//    /**
//     * Test of getNoOfDays method, of class MACDUtil.
//     */
//    @Test
//    public void testGetNoOfDays() {
//        System.out.println("getNoOfDays");
//        String code = "";
//        String stat = "";
//        MACDUtil instance = new MACDUtil();
//        int expResult = 0;
//        int result = instance.getNoOfDays(code, stat);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getPreviousIndicatorValues method, of class MACDUtil.
//     */
//    @Test
//    public void testGetPreviousIndicatorValues() {
//        System.out.println("getPreviousIndicatorValues");
//        String code = "";
//        double yesterdayma1Perc = 0.0;
//        double yesterdayma2Perc = 0.0;
//        double yesterdaysignalVal = 0.0;
//        MACDUtil instance = new MACDUtil();
//        HashMap expResult = null;
//        HashMap result = instance.getPreviousIndicatorValues(code, yesterdayma1Perc, yesterdayma2Perc, yesterdaysignalVal);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of updateMACDLiveStatus method, of class MACDUtil.
//     */
//    @Test
//    public void testUpdateMACDLiveStatus() {
//        System.out.println("updateMACDLiveStatus");
//        String timeStamp = "";
//        String code = "";
//        double close = 0.0;
//        int noOfDays = 0;
//        String stat = "";
//        double macdVal = 0.0;
//        double signalVal = 0.0;
//        MACDUtil instance = new MACDUtil();
//        instance.updateMACDLiveStatus(timeStamp, code, close, noOfDays, stat, macdVal, signalVal);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getMACDStatus method, of class MACDUtil.
//     */
//    @Test
//    public void testGetMACDStatus() {
//        System.out.println("getMACDStatus");
//        String code = "";
//        MACDUtil instance = new MACDUtil();
//        ResultSet expResult = null;
//        ResultSet result = instance.getMACDStatus(code);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getLiveMidPrice method, of class MACDUtil.
//     */
//    @Test
//    public void testGetLiveMidPrice() {
//        System.out.println("getLiveMidPrice");
//        String code = "";
//        MACDUtil instance = new MACDUtil();
//        ResultSet expResult = null;
//        ResultSet result = instance.getLiveMidPrice(code);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getMACD method, of class MACDUtil.
//     */
//    @Test
//    public void testGetMACD() {
//        System.out.println("getMACD");
//        String code = "";
//        MACDUtil instance = new MACDUtil();
//        ResultSet expResult = null;
//        ResultSet result = instance.getMACD(code);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getShareList method, of class MACDUtil.
//     */
//    @Test
//    public void testGetShareList() {
//        System.out.println("getShareList");
//        String code = "";
//        MACDUtil instance = new MACDUtil();
//        ShareList expResult = null;
//        ShareList result = instance.getShareList(code);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getMACDHistoryList method, of class MACDUtil.
//     */
//    @Test
//    public void testGetMACDHistoryList() {
//        System.out.println("getMACDHistoryList");
//        String code = "";
//        MACDUtil instance = new MACDUtil();
//        ResultSet expResult = null;
//        ResultSet result = instance.getMACDHistoryList(code);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of updateMACD method, of class MACDUtil.
//     */
//    @Test
//    public void testUpdateMACD() {
//        System.out.println("updateMACD");
//        Indicator indicator = null;
//        MACDUtil instance = new MACDUtil();
//        instance.updateMACD(indicator);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of updateMACDStatus method, of class MACDUtil.
//     */
//    @Test
//    public void testUpdateMACDStatus() {
//        System.out.println("updateMACDStatus");
//        IndicatorList indicatorList = null;
//        MACDUtil instance = new MACDUtil();
//        instance.updateMACDStatus(indicatorList);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getNumberOfDays method, of class MACDUtil.
//     */
//    @Test
//    public void testGetNumberOfDays() {
//        System.out.println("getNumberOfDays");
//        IndicatorList indicatorList = null;
//        MACDUtil instance = new MACDUtil();
//        int expResult = 0;
//        int result = instance.getNumberOfDays(indicatorList);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of updateNumberOfDays method, of class MACDUtil.
//     */
//    @Test
//    public void testUpdateNumberOfDays() {
//        System.out.println("updateNumberOfDays");
//        Indicator indicator = null;
//        int noOfDays = 0;
//        MACDUtil instance = new MACDUtil();
//        instance.updateNumberOfDays(indicator, noOfDays);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//    
}
