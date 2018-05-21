/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quotestreammacd;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author
 */
public class ShareList {

    ArrayList<Share> shareList = new ArrayList<>();

    public ShareList() {
        shareList = new ArrayList<>();
    }

    public void addShare(Share share) {
        shareList.add(share);
    }

    public Share getShare(int index) {
        Share resShare = shareList.get(index);
        return resShare;
    }
    
    public void addShareList(ShareList shareList) {
        
    }
    
    public ShareList getSubList(int from, int to) {
        List<Share>  resShareList = shareList.subList(from, to);
        ShareList sList = new ShareList();
        for(int i = 0 ; i < resShareList.size(); i ++) {
            sList.addShare(resShareList.get(i));
        }
        return sList;
    }
    
    public int getSize() {
        return shareList.size();
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < shareList.size(); i++) {
            buffer.append(shareList.get(i)).append("\t");
            buffer.append("\n");
        }
        return buffer.toString();
    }
}
