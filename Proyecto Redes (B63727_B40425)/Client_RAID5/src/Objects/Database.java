package Objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Database Class where we enable the connection with MySQL Database
public class Database {
String URL = "jdbc:mysql://127.0.0.1/";
    String BD = "proyecto1_Redes";
    String USER = "root";
    String PASSWORD = "1234";
    String a="?useSSL=false";
    public Connection conexion = null;

    @SuppressWarnings("finally")
    public Connection connectDB() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(URL + BD+a, USER, PASSWORD);
            if (conexion != null) {
                System.out.println("Se estableció conexión con la base de datos.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return conexion;
        }
    }
}//End Database