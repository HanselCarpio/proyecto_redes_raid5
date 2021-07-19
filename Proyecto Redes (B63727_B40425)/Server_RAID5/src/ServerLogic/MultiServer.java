package ServerLogic;

import Objects.User;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

//Class in charge of the server methods
public class MultiServer {

    //Instances
    private ServerSocket serverSocket;
    private String nameAux;
    private String passwordAux;

    //Method in charge of start the server 
    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true) {
            new EchoClientHandler(serverSocket.accept()).start();
        }
    }//End Start

    //Method in charge of closing the sockect connection
    public void stop() throws IOException {
        serverSocket.close();
    }//End stop

    //Thread Class
    class EchoClientHandler extends Thread {

        //Instances
        private String name = "";
        private String password = "";
        private Socket clientSocket;
        private PrintWriter outline;
        private BufferedReader inputline;
        private DataOutputStream dos;
        private BufferedOutputStream bos;
        private boolean init = true;
        private boolean sendDirectories;

        int i = 0;

        public EchoClientHandler(Socket socket) throws IOException {

            this.clientSocket = socket;
            sendDirectories = false;

        }

        @Override
        public void run() {
            try {
                outline = new PrintWriter(clientSocket.getOutputStream(), true);
                inputline = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));

                this.dos = new DataOutputStream(clientSocket.getOutputStream());
                this.bos = new BufferedOutputStream(clientSocket.getOutputStream());

                String inl;

                while (((inl = inputline.readLine()) != null)) {
                    System.out.println("Petición del cliente: " + inl);

                    if (".".equals(inl)) {
                        inl = "Adios";
                        outline.println("bye");
                        System.out.println("Petición del cliente: " + inl);
                        break;
                    }

                    if (inl.equalsIgnoreCase("Hello")) {
                        outline.println("inicio");
                        inl = inputline.readLine();
                        System.out.println("A recibido?: " + inl);
                        init = false;
                    }

                    if (inl.equalsIgnoreCase("sincronizar")) {
                        inl = inputline.readLine();

                        int tam = Integer.parseInt(inl);

                        outline.println("Enviar archivos sincro");
                        System.out.println("Tamxxx: " + tam);
                        for (int j = 0; j < tam; j++) {
                            outline.println("Enviar");
                            getSincroFile();
                        }
                    } else {

                    }

                    if (inl.equalsIgnoreCase("recibir")) {

                        while (getFile() == false) {
                        }
                    }

                    if (inl.equalsIgnoreCase("iniciar sesion")) {
                        String nombre = inputline.readLine();
                        String contraseña = inputline.readLine();
                        System.out.println("A recibido?: " + nombre);
                        System.out.println("A recibido?: " + contraseña);
                        this.name = nombre;
                        this.password = contraseña;

                        if (User.checkUser(nombre, contraseña)) {
                            outline.println("acceso");
                            inl = inputline.readLine();
                            System.out.println("A recibido?: " + inl);
                            sendDirectories = true;
                        } else {
                            outline.println("Datos incorrectos");
                        }

                    }

                    if (inl.equalsIgnoreCase("descargar")) {
                        outline.println("Enviar nombre archivo");
                        inl = inputline.readLine();
                        System.out.println("Nombre Archivo server: " + inl);
                        sendFile(inl);
                    } else {
                        if (sendDirectories) {
                            outline.println("Enviar directorios");
                            inl = inputline.readLine();
                            System.out.println("A recibido?: " + inl);
                            sendDirectorie();
                        }
                    }

                }

                inputline.close();
                outline.close();
                clientSocket.close();
            } catch (IOException ex) {
                System.err.println("Error - El cliente se desconecto.");
            }
        }

        public DataOutputStream getDos() {
            return dos;
        }

        public BufferedOutputStream getBos() {
            return bos;
        }

        public void sendDirectorie() throws IOException {

            File dir = new File("Carpetas\\" + name);
            String[] ficheros = dir.list();

            if (ficheros == null) {
                System.out.println("No hay ficheros en el directorio especificado");
            } else {

                String tamaño = String.valueOf((int) ficheros.length);

                outline.println("" + tamaño);
                String inl2 = inputline.readLine();
                System.out.println("A recibido?: " + inl2);

                System.out.println("Enviando tamaño: " + tamaño);

                int tam = Integer.parseInt(tamaño);
                String inl = "";
                if (((inl = inputline.readLine()) != null)) {
                    if (inl.equalsIgnoreCase("tam recibido")) {
                        for (int j = 0; j < tam; j++) {
                            outline.println(ficheros[j]);
                        }

                    }
                }

            }

        }

        public boolean sendToClient(String inputLine) {

            if (inputLine.equals("1")) {
                outline.println("recibir directorios");

                return true;
            }
            return false;
        }

        public void getSincroFile() {

            try {
                // Creamos flujo de entrada para leer los datos que envia el cliente 
                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());

                // Obtenemos el nombre del archivo
                String nombreArchivo = dis.readUTF().toString();

                // Obtenemos el tamaño del archivo
                int tam = dis.readInt();
                System.out.println("Tam server: " + tam);
                System.out.println("Recibiendo archivo: " + nombreArchivo);

                // Creamos flujo de salida, este flujo nos sirve para 
                // indicar donde guardaremos el archivo
                FileOutputStream fos = new FileOutputStream("Carpetas\\" + name + "\\" + nombreArchivo);
                BufferedOutputStream out = new BufferedOutputStream(fos);
                BufferedInputStream in = new BufferedInputStream(clientSocket.getInputStream());

                // Creamos el array de bytes para leer los datos del archivo
                byte[] buffer = new byte[tam];

                // Obtenemos el archivo mediante la lectura de bytes enviados
                for (int i = 0; i < buffer.length; i++) {
                    buffer[i] = (byte) in.read();
                }

                // Escribimos el archivo 
                out.write(buffer);
                out.flush();
                fos.close();

                System.out.println("Archivo Recibido: " + nombreArchivo);

            } catch (Exception e) {
                System.err.println("Error no recibido : " + e.toString());

            }

        }

        public boolean getFile() {

            try {

                // Creamos flujo de entrada para leer los datos que envia el cliente 
                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());

                // Obtenemos el nombre del archivo
                String nombreArchivo = dis.readUTF().toString();

                // Obtenemos el tamaño del archivo
                int tam = dis.readInt();
                System.out.println("Archivo: " + nombreArchivo + " entrando por server.");

                // Creamos flujo de salida, este flujo nos sirve para 
                // indicar donde guardaremos el archivo
                FileOutputStream fos = new FileOutputStream("Carpetas\\" + name + "\\" + nombreArchivo);
                BufferedOutputStream out = new BufferedOutputStream(fos);
                BufferedInputStream in = new BufferedInputStream(clientSocket.getInputStream());

                // Creamos el array de bytes para leer los datos del archivo
                byte[] buffer = new byte[tam];

                // Obtenemos el archivo mediante la lectura de bytes enviados
                for (int i = 0; i < buffer.length; i++) {
                    buffer[i] = (byte) in.read();
                }

                // Escribimos el archivo 
                out.write(buffer);
                out.flush();
                fos.close();

                return true;
            } catch (Exception e) {
                System.err.println("Error - Archivo No Recibido.");
                return false;
            }

        }

        //Method in Charge of sending the file to client
        public boolean sendFile(String nombreArchivo) {

            try {
                // Creamos el archivo que vamos a enviar
                System.out.println("Nombre del Archivo: " + nombreArchivo);
                File archivo = new File("Carpetas\\" + name + "\\" + nombreArchivo);

                // Obtenemos el tamaño del archivo
                int fileLength = (int) archivo.length();
                System.out.println("Enviando Archivo: " + archivo.getName());
                System.out.println("Tamaño del archivo: " + fileLength);
                // Enviamos el nombre del archivo 
                getDos().writeUTF(archivo.getName());

                // Enviamos el tamaño del archivo
                getDos().writeInt(fileLength);

                // Creamos flujo de entrada para realizar la lectura del archivo en bytes
                FileInputStream fis = new FileInputStream("Carpetas\\" + name + "\\" + nombreArchivo);
                BufferedInputStream bis = new BufferedInputStream(fis);

                // Creamos un array de tipo byte con el tamaño del archivo 
                byte[] buffer = new byte[fileLength];

                // Leemos el archivo y lo introducimos en el array de bytes 
                bis.read(buffer);

                // Realizamos el envio de los bytes que conforman el archivo
                for (int i = 0; i < buffer.length; i++) {
                    getBos().write(buffer[i]);
                }

                System.out.println("Archivo Enviado: " + archivo.getName());
                // Cerramos socket y flujos
                bis.close();
                getBos().flush();
                getDos().flush();

                return true;

            } catch (Exception e) {
                System.err.println("Error - Archivo no enviado - " + e.toString());
                return false;
            }
        }//End SendFile

    }

}
