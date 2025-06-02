package anbar.model.repository;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
    @Getter
    private static ConnectionProvider connection=new ConnectionProvider();
    private ConnectionProvider(){};

    public Connection getconnect() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe",
                "java",
                "123"
        );

    }
}
