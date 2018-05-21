/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quotestreammacd;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author
 */
public class Indicator implements Serializable {

    private static final long serialVersionUID = 1L;
    private LocalDate dDate;
    private double value;
    private double signalValue;
    private String statusValue;
    private String code;
    private double ema13;
    private double ema26;

    public Indicator(LocalDate dDate, String code, double ema13, double ema26 ,double value, double signalValue) {
        this.dDate = dDate;
        this.code = code;
        this.ema13 = ema13;
        this.ema26 = ema26;
        this.value = value;
        this.signalValue = signalValue;
    }

    public void setDDate(LocalDate dDate) {
        this.dDate = dDate;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LocalDate getDDate() {
        return (this.dDate);
    }

    public void setStatusValue(String value) {
        this.statusValue = value;
    }

    public String getStatusValue() {
        return (this.statusValue);
    }
    
    public void setEma13(double ema13) {
        this.ema13 = ema13;
    }

    public double getEma13() {
        return (this.ema13);
    }
    
    public void setEma26(double ema26) {
        this.ema26 = ema26;
    }

    public double getEma26() {
        return (this.ema26);
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return (this.code);
    }

    public double getValue() {
        return (this.value);
    }

    public void setSignalValue(double value) {
        this.signalValue = value;
    }

    public double getSignalValue() {
        return (this.signalValue);
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(dDate).append("\t");
        buffer.append(code).append("\t");
//        buffer.append("EMA13 : ").append("\t");
        buffer.append(ema13).append("\t");
//        buffer.append("EMA26 : ").append("\t");
        buffer.append(ema26).append("\t");
//        buffer.append("MACD : ").append("\t");
        buffer.append(value).append("\t");
//        buffer.append("SIGNAL : ").append("\t");
        buffer.append(signalValue).append("\t");
//        buffer.append("STATUS : ").append("\t");
//        buffer.append(statusValue).append("\t");
        return buffer.toString();
    }
}
