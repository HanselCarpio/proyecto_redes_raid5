package Objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;

//Object User
public class User {

    //Attributes
    private String name;
    private String password;

    //Constructor without parameters
    public User() {
    }

    //Constructor with parameters
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    //Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //Function in charge of add users to databse
    public void addUser(String name, String password) throws SQLException {
        String query = "";
        Database bd = new Database();
        Statement sentence = bd.connectDB().createStatement();

        String parameter = "";
        query = "INSERT INTO persona(nombre, " + "contrasena" + ") VALUES ('" + name + "', " + "'" + password
                + "');";
        if (sentence.executeUpdate(query) > 0) {
            System.out.println("Se insertó el usuario con exito.");

            File directorio = new File("Carpetas\\" + name);
            directorio.mkdir();
        } else {
            System.out.println("Error - No se pudo insertar el usuario.");
        }

        sentence.close();
        bd.conexion.close();
    }//End Add User
    
    //Check User
    public static boolean checkUser(String name, String password) {
        String query = "";
        String passwordAux = "";
        Database bd = new Database();
        try {
            query = "SELECT * FROM persona WHERE nombre = '" + name + "';";
            Statement sentence = bd.connectDB().createStatement();
            ResultSet result = sentence.executeQuery(query);

            while (result.next()) {
                String nombre1 = result.getString("nombre");
                passwordAux = result.getString("contrasena");
                System.out.format("%s, %s\n", nombre1, passwordAux);

            }

            sentence.close();
            bd.conexion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (password.equalsIgnoreCase(passwordAux) && passwordAux != "") {
            return true;
        } else {
            return false;
        }
    }//End Check User

    //Function in charge of getting users
    public void getUser(String name, String password) {
        String query = "";
        Database bd = new Database();
        try {
            query = "SELECT * FROM persona WHERE nombre = '" + name + "';";
            Statement sentence = bd.connectDB().createStatement();
            ResultSet result = sentence.executeQuery(query);

            while (result.next()) {
                String nameAux = result.getString("nombre");
                String passwordAux = result.getString("contrasena");
                System.out.format("%s, %s\n", nameAux, password);
            }

            sentence.close();
            bd.conexion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
