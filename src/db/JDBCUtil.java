package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Driver;

public class JDBCUtil {

    public static Connection getConnection() {
        Connection c = null;

        try {
            // Đăng kí driver manager
            DriverManager.registerDriver(new Driver());

            String url = "jdbc:mySQL://localhost:3306/quanlycuahangsach";
            String username = "root";
            String password = "";

            c = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return c;
    }

    public static void closeConnection(Connection c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
