/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quotestreammacd;

import static com.quotestreammacd.MACDConstants.ELEVEN_HOUR;
import static com.quotestreammacd.MACDConstants.FIFTEEN_MIN;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author
 */
public class Main {

    public static MACDLogger macdLogger = MACDLogger.getInstance();

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
//        if (calendar.get(Calendar.HOUR_OF_DAY) == ELEVEN_HOUR && calendar.get(Calendar.MINUTE) >= FIFTEEN_MIN) {
        // String[] codeArray = {/*"BE:CNX", "BIGG:CNX", "BLO:CNX", "BTL", "CCC", "HC:CNX", "IBAT:CNX", "IN", "LION:CNX", "NXT", "PAS", "PWM", "MARI:CNX",
        // "RHT", "RYU", "SQA:CNX", "XMG:CNX", "ZFR"*/};
//        String[] codeArray = {"IN"};
        String[] codeArray1 = {"AAJ:CA", "ABJ:CNX", "ABN:CA", "ACG:CNX", "ACST", "ADD:CA", "AFI:CNX", "AGC", "AGRL:CA", "AGZ", "AHU:CA", "AKU.U:CA", "ALG", "AME", "ANG:CA", "ANK:CA", "APC", "ARCH", "ASI:CA", "ASTI:CNX", "AT", "ATW:CA", "AUN:CA", "AVU:CA", "AWS:CA", "AXIS:CA", "BAC:CNX", "BANC", "BCT:CA", "BE:CNX", "BEA:CA", "BHS:CA", "BIGG:CNX", "BIS:CNX", "BLK:CNX", "BLO:CNX", "BLOC.WT:CNX", "BLOK", "BNKR:CNX", "BOLT:CNX", "BRG.H:CA", "BSK:CA", "BTL:CA", "BTU", "BZI:CNX", "CA.H:CA", "CAD:CA", "CALI:CNX", "CAV:CA", "CCC", "CCC:CA", "CEA", "CGLD", "CGM:CA", "CHR:CA", "CLM", "CMD", "CME:CNX", "CPTO:CA", "CRE:CA", "CRF:CNX", "CROP:CNX", "CUBE:CNX", "CUX:CA", "CVM", "CVX", "CXC:CNX", "CYL:CA", "DAU:CA", "DIA", "DMGI:CA", "DMI:CA", "DRC.H:CA", "DVA", "DYA:CA", "DYG:CA", "EAS:CA", "EDT:CA", "EGM:CA", "EOX:CA", "ESI:CA", "ESR:CA", "ESU:CA", "EV", "EXS", "EYC:CA", "EZNC.H:CA", "FCD.UN:CA", "FG", "FPX", "FRE:CA", "FTI", "GET:CNX", "GGX:CA", "GOT:CA", "GRA", "GSA", "GTBE:CNX", "HC:CNX", "HIVE", "HRH:CA", "IBAT:CNX", "IDM:CA", "IES:CA", "IME:CNX", "IN:CA", "INR:CNX", "INX:CA", "ION:CA", "IPCI", "ISO:CA", "ITG", "JUGR:CA", "KASH:CA", "KBB:CNX", "KBLT:CA", "KMT", "KNR:CNX", "KSI:CA", "KTR", "LDS:CNX", "LEN", "LEXI:CA", "LFX:CA", "LHS:CNX", "LION:CNX", "LME:CA", "LMG:CA", "LNB:CNX", "LPS:CA", "MAE:CA", "MAH:CA", "MARI:CNX", "MCLD:CA", "MCO", "MDL:CA", "MDM:CNX", "MEG:CA", "MEX:CA", "MGM", "ML:CA", "MMJ:CNX", "MON", "MY:CNX", "MYA:CA", "NEM", "NIP:CA", "NLH:CA", "NNN", "NOT:CA", "NRE", "NTRL:CA", "NUR:CNX", "NVM:CA", "NVX:CA", "NXS:CA", "NXT:CA", "NZP:CA", "OCO:CA", "OEC", "PAC", "PAS:CA", "PERU:CA", "PGC", "PHD", "PLU:CA", "PLY:CA", "PMO.H:CA", "POG:CA", "POI.H:CA", "PPM:CA", "PREV:CNX", "PRN", "PROS:CA", "PTK:CA", "PTM", "PUC:CA", "PWM:CA", "PYR:CA", "QBOT:CNX", "QCA:CNX", "QCC:CNX", "QQ:CA", "QTA:CA", "RAY.A:CA", "REW", "RG:CA", "RHT", "RISE:CNX", "RLSC:CNX", "RLV", "ROC.H:CA", "RPX:CA", "RRS:CA", "RVX", "RYO:CA", "RYU", "SAAS:CNX", "SAM", "SB", "SCY:CA", "SGZ:CA", "SKE:CA", "SME:CA", "SNG:CA", "SNN:CNX", "SRA:CA", "STC", "STLC:CA", "SWA:CA", "TBP:CA", "TETH:CA", "TGI.H:CA", "TGOD:CA", "TIM:CA", "TMD:CA", "TNY:CNX", "TOE:CA", "VAI:CNX", "VCV", "VIDA:CNX", "VIR:CA", "VIS", "VLC:CA", "VLT", "VPI:CA", "VRR:CA", "VS:CNX", "WELL", "WGC:CNX", "WHY:CA", "WKM:CA", "WML", "WPQ:CA", "WRR:CA", "XIM:CA", "XMG:CNX", "ZFR:CA", "ZMA:CA"};
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String sid = "", startDate = LocalDate.now().minusMonths(6).format(formatter), endDate = LocalDate.now().format(formatter);
        System.out.println("startDate : " + startDate + " endDate : " + endDate);
        ArrayList<String> codeList = new MACDUtil().getCodeList();
        try {
            sid = new QuoteStreamHistory().getSID();
            for (String code : codeArray1) {
                new QuoteStreamHistory().getShareDetails(code, sid, startDate, endDate);
            }
        } catch (MalformedURLException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(QuoteStreamHistory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(QuoteStreamHistory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(QuoteStreamHistory.class.getName()).log(Level.SEVERE, null, ex);
        }
//        }
//        try {
//            macdLogger.initializeLog();
//            MACDUpdate macdUpdate = new MACDUpdate();
//            macdUpdate.startThread();
//            macdLogger.fileHandlerClose();
//        } catch (IOException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
