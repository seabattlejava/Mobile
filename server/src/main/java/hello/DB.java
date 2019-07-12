package hello;

import java.sql.*;

public class DB {
    private static Connection con;
    private static Statement st;
    private static ResultSet rs;
    private static String query;

    public static String check(String login, String password) {
        String URL = "jdbc:mysql://localhost:3306/users"+"?serverTimezone=UTC";

        try {
            con = DriverManager.getConnection(URL, "root", "1004");
            st = con.createStatement();
            query = "SELECT * FROM users.user";
            rs = st.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String pasword = rs.getString("password");
                String role = rs.getString("role");

                System.out.println(id + " " + name + " ");
                return name;
            }
        } catch (SQLException error) {
            System.out.println(error);
        }
        return "lol";
    }

}
