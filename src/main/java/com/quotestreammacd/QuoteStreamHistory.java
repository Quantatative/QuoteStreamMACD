/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quotestreammacd;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author
 */
public class QuoteStreamHistory {

    public String getSID() throws MalformedURLException, ProtocolException, IOException {
        String sid = "";
        String url = "https://app.quotemedia.com/auth/p/authenticate/v0/";
        URL object = null;
        try {
            object = new URL(url);
            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");
            String input = "{\n"
                    + "\"wmId\":\"501\",\n"
                    + "\"username\":\"shyam99\",\n"
                    + "\"password\":\"Anjali99\"\n"
                    + "}";
            OutputStream os = con.getOutputStream();
            os.write(input.getBytes());
            os.flush();
            int responseCode = con.getResponseCode();
            StringBuilder response = new StringBuilder();
            if (responseCode == 200) {
                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()))) {
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                }
            }
            JSONObject myResponse = new JSONObject(response.toString());
            sid = myResponse.getString("sid");

        } catch (MalformedURLException ex) {
            Logger.getLogger(QuoteStreamHistory.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return sid;
    }

    public void getShareDetails(String CODE, String sid, String startDate, String endDate) throws MalformedURLException, IOException, ClassNotFoundException, SQLException {
//        CODE = "CCC:CA";
//        startDate = "2017-05-01";
//        endDate = "2014-01-01";
        String url = "https://app.quotemedia.com/data/getFullHistory.json?symbol=" + CODE + "&start=" + startDate + "&end=" + endDate + "&webmasterId=501&sid=" + sid;
        System.out.println("url :: " + url);
//        String url = "http://app.quotemedia.com/data/getSymbolHistory.csv?symbol=IN&start=2010-04-18&end=2011-05-17&webmasterId=501&sid=" + sid;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        System.out.println("CODE :: " + CODE + " con.getResponseCode() :: " + con.getResponseCode());
        if (con.getResponseCode() == 200) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                inputLine = in.readLine();
////                while ((inputLine = in.readLine()) != null) {
////                    System.out.println("inputLine :: "+inputLine);
////                }
                new Gson().toJson(inputLine);
                JSONObject myResponse = new JSONObject(inputLine);
                JSONObject myResponse1 = new JSONObject(myResponse.get("results").toString());
                if (myResponse1.has("history")) {
                    System.out.println("IF 111111111111111111111111");
                    JSONArray myResponse3 = new JSONArray(myResponse1.get("history").toString());
                    for (int i = 0; i < myResponse3.length(); i++) {
                        JSONObject obj1 = myResponse3.getJSONObject(i);
                        if (obj1.has("eoddata")) {
                            System.out.println("IF 222222222222222222222222");
                            JSONArray myResponse4 = new JSONArray(obj1.get("eoddata").toString());
                            for (int j = 0; j < myResponse4.length(); j++) {
                                JSONObject obj2 = myResponse4.getJSONObject(j);
                                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                                Connection con1 = DriverManager.getConnection("jdbc:jtds:sqlserver://DBSQL;user=interactive;password=oracle;appName=newajax");
                                Statement stmtMACDLiveStat = con1.createStatement();
                                if (obj2.has("date") && obj2.has("open") && obj2.has("high") && obj2.has("low") && obj2.has("close")) {
                                    String insQuery = "If NOT EXISTS (SELECT * FROM [QUOTESTREAMHISTORY] WHERE [SYMBOL]='" + CODE + "' AND [SHARE_DATE] = '" + obj2.get("date").toString() + "') BEGIN"
                                            + " INSERT INTO [QUOTESTREAMHISTORY] VALUES ('" + CODE + "','" + obj2.get("date").toString() + "'," + Double.parseDouble(obj2.get("open").toString())
                                            + "," + Double.parseDouble(obj2.get("high").toString()) + "," + Double.parseDouble(obj2.get("low").toString())
                                            + "," + Double.parseDouble(obj2.get("close").toString()) + ") END";
//                                    System.out.println("insQuery :: "+insQuery);
                                    stmtMACDLiveStat.executeUpdate(insQuery);
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("DB Updated!");
    }
}
