package ClientLogic;

import Client_RAID5.Client_RAID5;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

//Thread Class
public class ClassClient {

    //Thread Instances
    private DataOutputStream dos;
    private BufferedOutputStream bos;
    private String inputLineWindow;
    private Socket serverSocket;
    private PrintWriter out;
    private BufferedReader in;
    public static ReciveAndSendServer reciveFromServer;
    public static Sincronize sincronize;
    private int state;
    private String absolutepath;
    private ArrayList arrayList;

    //Function in charge of start the connection
    public boolean startConnection(String ip, int port) {
        try {
            absolutepath = "";
            arrayList = new ArrayList();
            serverSocket = new Socket(ip, port);
            out = new PrintWriter(serverSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            state = 0;
            sincronize = new Sincronize();
            sincronize.start();
            reciveFromServer = new ReciveAndSendServer();
            reciveFromServer.start();

            this.dos = new DataOutputStream(serverSocket.getOutputStream());
            this.bos = new BufferedOutputStream(serverSocket.getOutputStream());

            return true;
        } catch (IOException e) {
            return false;
        }

    }//End Start Connection

    //Function in charge to stop connection
    public void stopConnection() {
        try {
            in.close();
            out.close();
            reciveFromServer.interrupt();
            serverSocket.close();
        } catch (IOException e) {

        }

    }//End StopConnection

    //Getters & Setters
    public PrintWriter getOut() {
        return out;
    }

    public String getInputLineWindow() {
        return inputLineWindow;
    }

    public void setInputLineWindow(String inputLineWindow) {
        this.inputLineWindow = inputLineWindow;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }

    public ArrayList getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList arrayList) {
        this.arrayList = arrayList;
    }

    public BufferedReader getIn() {
        return in;
    }

    public String getAbsolutepath() {
        return absolutepath;
    }

    public void setAbsolutepath(String absolutepath) {
        this.absolutepath = absolutepath;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Socket getServerSocket() {
        return serverSocket;
    }

    public DataOutputStream getDos() {
        return dos;
    }

    public void setDos(DataOutputStream dos) {
        this.dos = dos;
    }

    public BufferedOutputStream getBos() {
        return bos;
    }

    public void setBos(BufferedOutputStream bos) {
        this.bos = bos;
    }

    public ReciveAndSendServer getReciveFromServer() {
        return reciveFromServer;
    }
    //End Getters & Setters

    //Function in charge of check if there are any files in directory
    public boolean difFiles() {
        try {
            File aux = new File(absolutepath);
            String[] dirClientFile = aux.list();

            if (dirClientFile == null) {
                System.out.println("No hay ficheros en el directorio especificado");
            }

            String[] dirServerFile = Client_RAID5.getWindowClientTFTP().getFiles();
            ArrayList serverList = new ArrayList();
            for (int i = 0; i < dirServerFile.length; i++) {
                serverList.add(dirServerFile[i]);
            }
            ArrayList clientList = new ArrayList();
            for (int i = 0; i < dirClientFile.length; i++) {
                clientList.add(dirClientFile[i]);
            }
            ArrayList listAux = new ArrayList();
            for (int i = 0; i < clientList.size(); i++) {
                if (!serverList.contains(clientList.get(i))) {
                    listAux.add(absolutepath + "\\" + clientList.get(i));
                }
            }
            if (listAux.size() != 0 && !arrayList.equals(listAux)) {
                arrayList = listAux;
                Client_RAID5.getClient().getOut().println("sincronizar");

                return true;
            }

        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }//End difFiles

    //Function in Charge of sending and downloading files
    public void start() {
        String inputLine = "";
        try {
            if ((inputLine = Client_RAID5.getClient().getIn().readLine()) != null) {

                if (inputLine.equalsIgnoreCase("Enviar nombre archivo")) {
                    if (Client_RAID5.getWindowClientTFTP().jTable_FilesTable.getSelectedRow() != -1) {
                        DefaultTableModel model = (DefaultTableModel) Client_RAID5.getWindowClientTFTP().jTable_FilesTable.getModel();
                        String nombre = (String) model.getValueAt(Client_RAID5.getWindowClientTFTP().jTable_FilesTable.getSelectedRow(), 0);
                        Client_RAID5.getClient().getOut().println(nombre);
                        Client_RAID5.getWindowClientTFTP().downloadFile();
                    }
                }

                if (inputLine.equalsIgnoreCase("Enviar nombre archivo a eliminar")) {
                    if (Client_RAID5.getWindowClientTFTP().jTable_FilesTable.getSelectedRow() != -1) {
                        DefaultTableModel model = (DefaultTableModel) Client_RAID5.getWindowClientTFTP().jTable_FilesTable.getModel();
                        String nombre = (String) model.getValueAt(Client_RAID5.getWindowClientTFTP().jTable_FilesTable.getSelectedRow(), 0);
                        Client_RAID5.getClient().getOut().println(nombre);
                        Client_RAID5.getClient().difFiles();
                    }
                }

                if (inputLine.equalsIgnoreCase("inicio")) {
                    Client_RAID5.getLoginWindow().init();
                    Client_RAID5.getClient().getOut().println("Recibido inicio");
                }

                if (inputLine.equalsIgnoreCase("Enviar archivos sincro")) {

                    for (int i = 0; i < arrayList.size(); i++) {
                        inputLine = in.readLine();
                        if (inputLine.equalsIgnoreCase("Enviar")) {
                            sendSincroFiles(String.valueOf(arrayList.get(i)));
                        }
                    }
                }

                if (inputLine.equalsIgnoreCase("acceso")) {
                    Client_RAID5.getWindowClientTFTP().init();
                    Client_RAID5.getClient().getOut().println("Recibido acceso");
                    Client_RAID5.getLoginWindow().dispose();
                }
                if (inputLine.equalsIgnoreCase("Datos incorrectos")) {
                    JOptionPane.showMessageDialog(Client_RAID5.getWindowClientTFTP(), "Introduzca datos validos de usuario");
                }

                if (inputLine.equalsIgnoreCase("Enviar directorios")) {
                    Client_RAID5.getClient().getOut().println("Recibido: Enviar directorios");
                    int tam = Integer.parseInt(Client_RAID5.getClient().getIn().readLine());
                    Client_RAID5.getClient().getOut().println("Recibido tam");
                    System.out.println("tamaño:  " + tam);
                    Client_RAID5.getClient().getOut().println("tam recibido");

                    String[] directorios = new String[tam];
                    for (int j = 0; j < tam; j++) {
                        directorios[j] = Client_RAID5.getClient().getIn().readLine();
                    }
                    for (int j = 0; j < directorios.length; j++) {
                        System.out.println("Directorios: " + directorios[j]);
                    }

                    Client_RAID5.getWindowClientTFTP().setDirectories(directorios);
                }

            }

        } catch (IOException ex) {

        } catch (NumberFormatException ex) {

        }
    }//End Start

    //Function in charge of sending sincronize files
    public void sendSincroFiles(String fileName) {

        try {
            // We start creating the file who we are going to send
            System.out.println("Nombre del Archivo: " + fileName);
            File file = new File(fileName);
            // We get the lenght of the file
            int fileLength = (int) file.length();
            System.out.println("Tam cliente: " + fileLength);
            System.out.println("Enviando Archivo: " + file.getName());
           
            // We send the file
            Client_RAID5.getClient().getDos().writeUTF(file.getName());
            Client_RAID5.getClient().getDos().writeInt(fileLength);

            FileInputStream fis = new FileInputStream(fileName);
            BufferedInputStream bis = new BufferedInputStream(fis);

            // Creation of an array to put the bytes
            byte[] buffer = new byte[fileLength];

            // Lecture of the file and then we put it in the array
            bis.read(buffer);

            // We send the bytes
            for (int i = 0; i < buffer.length; i++) {
                Client_RAID5.getClient().getBos().write(buffer[i]);
            }

            System.out.println("Archivo Enviado: " + file.getName());
            // Closing sockects and flowss
            bis.close();
            Client_RAID5.getClient().getBos().flush();
            Client_RAID5.getClient().getDos().flush();

        } catch (Exception e) {
            System.err.println("Error no se envio el archivo");

        }

    }//End Sincrofiles
}//End class

//Class Recive and Send Server THREAD
class ReciveAndSendServer extends Thread {

    public ReciveAndSendServer() throws IOException {

    }

    @Override
    public void run() {
        while (true) {
            Client_RAID5.getClient().start();
        }
    }

    public boolean sendMessage(String inputLine) {
        if (inputLine.equals("1")) {
            Client_RAID5.getClient().getOut().printf("Enviar directorios");
            System.out.println("Entro 1");

            return true;
        }
        return false;
    }

}

class Sincronize extends Thread {

    public Sincronize() {
    }

    @Override
    public void run() {
        while (true) {
            System.getenv(Client_RAID5.getClient().getAbsolutepath());

            if (Client_RAID5.getClient().getAbsolutepath() != "") {
                if (Client_RAID5.getClient().difFiles()) {
                    Client_RAID5.getClient().getOut().println("" + Client_RAID5.getClient().getArrayList().size());
                }
            }

        }
    }

}//End Class
