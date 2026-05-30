package br.com.fiap.conexoes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ConexaoFactory {

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl",
                "rm568510", "281299"
        );
    }
}
