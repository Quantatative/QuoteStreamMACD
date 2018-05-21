/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quotestreammacd;

import java.util.ArrayList;

/**
 *
 * @author
 */
public class IndicatorList {

    private final ArrayList<Indicator> indicatorList;

    public IndicatorList() {
        indicatorList = new ArrayList();
    }

    public void addIndicator(Indicator indf) {
        indicatorList.add(indf);
    }

    public Indicator getIndicatorData(int i) {
        return indicatorList.get(i);
    }

    public int getSize() {
        return indicatorList.size();
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("IndicatorList size: ").append(indicatorList.size()).append("\t");
        buffer.append((char) 10);
        for (int i = 0; i < indicatorList.size(); i++) {
            buffer.append(indicatorList.get(i).toString()).append((char) 10);
        }
        return buffer.toString();
    }
}
