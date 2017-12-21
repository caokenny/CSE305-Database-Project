package Database;

import java.sql.*;

public class Database {
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/CSE305_PROJECT?autoReconnect=true&useSSL=false";
    private static final String username = "root";
    private static final String password = "cse3051";

    public static Connection getConnection() throws Exception {
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static ResultSet executeQ(String query) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        return resultSet;
    }

    public static void executeU(String query) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();
    }
}
