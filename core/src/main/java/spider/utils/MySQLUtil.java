package spider.utils;


import spider.pipeline.impl.mysql.ConManager;

import java.sql.*;

public class MySQLUtil {

    private static String mysqlInfo = "jdbc:mysql://120.77.47.148/proxy?user=root&password=201011.4Wydtx&useUnicode=true&characterEncoding=UTF-8&useSSL=false";

    private static Connection getCon() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(mysqlInfo);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return con;
    }


    public static String getProxy() {
        Statement smt = null;
        Connection con = null;
        ResultSet resultSet = null;
        String rs = "";
        try {
            con = getCon();
            smt = con.createStatement();
            resultSet =  smt.executeQuery
                    ("select ip, port from proxys where types=0 and protocol=0 and score=10 ORDER BY rand() limit 1");
            while (resultSet.next()) {
                rs = resultSet.getString(1) + ":" + resultSet.getInt(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {}
            }
            if (smt != null) {
                try {
                    smt.close();
                } catch (SQLException e) {}
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {}
            }
        }
        return rs;
    }
}
