package otenkInvestitii;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/*
подключение к БД
 */
public class DBConnect {
    private final String userName = "root";
    private final String password = "root";
    private final String connectionUrl = "jdbc:mysql://localhost:3306/myinvestytii";

    Connection connection;

    public DBConnect() {
        try {
            connection = DriverManager.getConnection(connectionUrl, userName, password);
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
