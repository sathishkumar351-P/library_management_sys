package com.wipro.book.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

    public static Connection getDBConnection() throws Exception {

        Class.forName("oracle.jdbc.driver.OracleDriver");

        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String user = "system";
        String pass = "1905";
        Connection con = DriverManager.getConnection(url, user, pass);
        con.setAutoCommit(false);
        return con;
    }
}