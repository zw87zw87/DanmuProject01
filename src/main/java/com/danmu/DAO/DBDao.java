package com.danmu.DAO;

import java.sql.*;

public class DBDao {
    public static void main(String[] args) throws Exception {
        Connection conn = null;
        String db_host;
        String db_port;
        String db_user;
        String db_pass;
        String db_name;
        String url;

//        db_host = "172.18.1.55";
        db_host = "localhost";
        db_port = "3306";
        db_name = "danmu";
        db_user = "root";
        db_pass = "1234";

        url = "jdbc:mysql://"
                + db_host + ":"
                + db_port + "/"
                + db_name + "?"
                + "user=" + db_user + "&"
                + "password=" + db_pass
                + "&useUnicode=true&characterEncoding=UTF8";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String sql = "";

            sql = "select * from chatmsg";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println(rs.getString(1) + "\t" + rs.getString(2));
            }

        } catch (SQLException e) {
            System.out.println("MySQL操作错误！");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }
}
