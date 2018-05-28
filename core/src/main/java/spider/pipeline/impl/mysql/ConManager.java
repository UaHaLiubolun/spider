package spider.pipeline.impl.mysql;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConManager {

    private static String mysqlInfo = "jdbc:mysql://localhost/netease?user=root";


    public static Connection getCon() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(mysqlInfo);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return con;
    }
}
