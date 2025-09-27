package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    
    public static Connection getConexion() {
        // URL de conexión a SQL Server
        // Agregamos encrypt=true y trustServerCertificate=true para evitar errores SSL en desarrollo
        String url = "jdbc:sqlserver://localhost:1433;"
                   + "databaseName=Escuela;"
                   + "user=sa;"
                   + "password=12345678;"
                   + "encrypt=true;"
                   + "trustServerCertificate=true;";

        try {
            Connection con = DriverManager.getConnection(url);
            System.out.println("Conexión exitosa a la base de datos");
            return con;
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.toString());
            return null;
        }
    }
    
    // Método de prueba (opcional)
    public static void main(String[] args) {
        getConexion();
    }
}

