/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quotestreammacd;

import static com.quotestreammacd.MACDConstants.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author
 */
public class DataAccessUtil {

    Connection connection, delConnection, insConnection, selConnection,updConnection;
    ResultSet resultSet;
    Statement statement;

    private Connection getConnection(String database) {
        String connectionStr = "";
        try {
            if (database.equals(dbsql)) {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                connectionStr = "jdbc:jtds:sqlserver://DBSQL;user=interactive;password=oracle;appName=newajax";
            } else if (database.equals(dbmax)) {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                connectionStr = "jdbc:jtds:sqlserver://DBMAX;appName=newajax;user=BackTradeData;password=oracle";
            }
            connection = DriverManager.getConnection(connectionStr);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DataAccessUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }

    public void insertRows(String database, String query) {
        try {
            insConnection = getConnection(database);
            statement = insConnection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessUtil.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    public void deleteRows(String database, String query) {
        try {
            delConnection = getConnection(database);
            statement = delConnection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateRows(String database, String query) {
        try {
            updConnection = getConnection(database);
            statement = updConnection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet selectRows(String database, String query) {
        try {
            selConnection = getConnection(database);
            statement = selConnection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
            if (insConnection != null) {
                insConnection.close();
            }
            if (delConnection != null) {
                delConnection.close();
            }
            if (updConnection != null) {
                updConnection.close();
            }
            if (selConnection != null) {
                selConnection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
