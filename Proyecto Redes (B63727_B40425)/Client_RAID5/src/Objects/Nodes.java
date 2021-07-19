package Objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;

//Object Nodes
public class Nodes {

    //Attributes
    private String path;
    private String nodesNumber;

    //Constructor without parameters
    public Nodes() {
    }

    //Constructor with parameters
    public Nodes(String path, String nodesNumber) {
        this.path = path;
        this.nodesNumber = nodesNumber;
    }

    //Getters & Setters
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNodesNumber() {
        return nodesNumber;
    }

    public void setNodesNumber(String nodesNumber) {
        this.nodesNumber = nodesNumber;
    }

    //Function in charge of add nodes and create the 
    public void addNodes(String path, String nodesNumber) throws SQLException {

        if (path != "" && nodesNumber != "") {
            
            //Mensaje de Éxito
            System.out.println("Se crearón las carpetas de los nodos exitosamente en la ruta: " + path);
            
            //Creación de las carpetas de los nodos
            
            for(int i = 1; i <= Integer.parseInt(nodesNumber); i = i + 1){
                File directorio = new File(path + "\\" + String.valueOf(i));
                directorio.mkdir();
            }

        } else {
            System.out.println("Error - No se crear las carpetas de los nodos.");
        }

    }//End addNodes
    
    //checkUser
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
    }//End Check user

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
