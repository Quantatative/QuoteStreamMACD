/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quotestreammacd;

import java.time.LocalDate;

/**
 *
 * @author
 */
public class Share {

    LocalDate shareDate;
    String code;
    double openPrice;
    double high;
    double low;
    double closePrice;

    public Share(LocalDate shareDate, String code, double openPrice,double high, double low, double closePrice) {
        this.shareDate = shareDate;
        this.code = code;
        this.openPrice = openPrice;
        this.high = high;
        this.low = low;
        this.closePrice = closePrice;
    }

    public void setSharedate(LocalDate shareDate) {
        this.shareDate = shareDate;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }

    public LocalDate getSharedate() {
        return shareDate;
    }

    public String getCode() {
        return code;
    }

    public double getClosePrice() {
        return closePrice;
    }
    
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();        
        buffer.append(shareDate).append("\t");
        buffer.append(code).append("\t");
        buffer.append(openPrice).append("\t");
        buffer.append(high).append("\t");
        buffer.append(low).append("\t");
        buffer.append(closePrice).append("\t");
        return buffer.toString();
    }
}
