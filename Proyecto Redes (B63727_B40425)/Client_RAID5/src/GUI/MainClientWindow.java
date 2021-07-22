package GUI;

import Client_RAID5.Client_RAID5;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 * Esta clase contiene los atributos y metodos de un MainClientWindow
 *
 * @author Bryan Keihl, Hansel Carpio y Victor Fernández
 * @version 1.0
 * @see MainClientWindow
 */
//MainClientWindow Class 
public class MainClientWindow extends JFrame implements ActionListener {
     /**
     * Actibutos del MainClientWindow
     *
     *
     */

    //Instances
    //Label 
    private JLabel label_pic;
    //Image Icon
    private ImageIcon image;
    //Buttons
    private JButton jbtn_Send;
    private JButton jbtn_Receive;
    //Table
    public static JTable jTable_FilesTable;
    private TablePanel jpanel_Table;
    //Array
    private String[] directories;
    //Boolean Instances
    public boolean flag;

    /**
     * Constructor
     *
     *
     */
    public MainClientWindow() {
        this.setTitle("saSearch - Busqueda de Libros y Metadatos");
        this.setResizable(false);
        this.setSize(600, 310);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    /**
     * Metodo init para configuara los atributos de la clase y mostrar los componentes visuales
     *
     *
     */
    public void init() {
        this.setLayout(null);

        //Instances
        //Label
        label_pic = new JLabel("");
        //Image Icon
        image = new ImageIcon("C:\\Users\\bryan\\Documents\\NetBeansProjects\\Proyecto Redes (B63727_B40425)\\Client_RAID5\\src\\img\\Send Wallpaper.png");
        //Buffered Image
        //Buttons
        this.jbtn_Send = new JButton("Cargar Libro");
        this.jbtn_Receive = new JButton("Descargar Libro");
        //Table
        this.jpanel_Table = new TablePanel();
        //Placing
        this.jbtn_Send.setBounds(380, 220, 110, 30);
        this.jbtn_Receive.setBounds(60, 220, 130, 30);
        this.label_pic.setBounds(330, 9, 200, 200);
        //Adding to window
        this.add(this.jbtn_Receive);
        this.add(this.jbtn_Send);
        this.add(this.label_pic);
        this.add(jpanel_Table);

        //Attributes
        this.jbtn_Receive.addActionListener(this);
        this.jbtn_Send.addActionListener(this);
        setVisible(true);
        label_pic.setIcon(image);
        label_pic.setVisible(true);
    }

    //Getters & Setters
    public JButton getJbtn_Send() {
        return jbtn_Send;
    }

    public void setJbtn_Send(JButton jbtn_Send) {
        this.jbtn_Send = jbtn_Send;
    }

    public JButton getJbtn_Receive() {
        return jbtn_Receive;
    }

    public void setJbtn_Receive(JButton jbtn_Receive) {
        this.jbtn_Receive = jbtn_Receive;
    }

    /**
     * Boton para enviar o descargar archivo
     *
     *
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource() == this.jbtn_Send) { //Button action Send
            try {
                Client_RAID5.getClient().getOut().println("recibir");

                if (sendMessage()) {

                }

            } catch (IOException ex) {
                Logger.getLogger(MainClientWindow.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        if (arg0.getSource() == this.jbtn_Receive) { //Button Action Download
            if (Client_RAID5.getWindowClientTFTP().jTable_FilesTable.getSelectedRow() != -1) {
                Client_RAID5.getClient().getOut().println("descargar");
            } else {
                System.err.println("Error - No se ha seleccionado un archivo.");

            }
        }
    }

    //sendMessage Method
    /**
     * Abre el fileChooser para selecionar un archivo para enviar
     *
     *
     * @return 
     * @throws java.io.IOException
     */
    public boolean sendMessage() throws IOException {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this directory: "
                    + chooser.getSelectedFile().getAbsolutePath());
        }

        if (sendFile(chooser.getSelectedFile().getAbsolutePath())) {
            return true;
        } else {
            return false;
        }
    }//End sendMessage Method

    //SendFile Method
    /**
     * Metodo que envia el archivo al servidor
     *
     *
     * @param fileName
     * @return 
     * @throws java.io.FileNotFoundException
     */
    public boolean sendFile(String fileName) throws FileNotFoundException, IOException {

        try {

            System.out.println("Nombre del Archivo: " + fileName);
            File file = new File(fileName);

            // Capturing the file length
            int fileLength = (int) file.length();

            System.out.println("Tam cliente: " + fileLength);

            System.out.println("Enviando Archivo: " + file.getName());
            // Sending the file name
            Client_RAID5.getClient().getDos().writeUTF(file.getName());

            // Sending file length
            Client_RAID5.getClient().getDos().writeInt(fileLength);

            FileInputStream fis = new FileInputStream(fileName);
            BufferedInputStream bis = new BufferedInputStream(fis);

            // Array Byte for file length
            byte[] buffer = new byte[fileLength];

            bis.read(buffer);

            // We send the array full of bytes
            for (int i = 0; i < buffer.length; i++) {
                Client_RAID5.getClient().getBos().write(buffer[i]);
            }

            System.out.println("Archivo Enviado: " + file.getName());
            bis.close();
            Client_RAID5.getClient().getBos().flush();
            Client_RAID5.getClient().getDos().flush();

        } catch (Exception e) {
            System.err.println("Error - Archivo no enviado." + e);
            return false;
        }

        return true;

    }//endSendFile

    //Getters & Setters
    public String[] getFiles() {
        return directories;
    }

    public void setDirectories(String[] directories) {
        this.directories = directories;

    }

    //downloadFile
    /**
     * Metodo que descargar archivo desde el servidor
     *
     *
     * @return 
     */
    public boolean downloadFile() {

        while (true) {

            try {
                DataInputStream dis = new DataInputStream(Client_RAID5.getClient().getServerSocket().getInputStream());

                // Getting the file name
                String nombreArchivo = dis.readUTF().toString();

                // Getting file length
                int tam = (int) dis.readInt();

                System.out.println("Tam: " + tam);
                System.out.println("Recibiendo archivo: " + nombreArchivo);

                // Letting know where to save the file
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
                int returnVal = chooser.showOpenDialog(this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    System.out.println("You chose to open this directory: "
                            + chooser.getSelectedFile().getAbsolutePath());
                }

                FileOutputStream fos = new FileOutputStream(chooser.getSelectedFile().getAbsolutePath() + "\\" + nombreArchivo);
                BufferedOutputStream out = new BufferedOutputStream(fos);
                BufferedInputStream in = new BufferedInputStream(Client_RAID5.getClient().getServerSocket().getInputStream());

                // Creating byte array
                byte[] buffer = new byte[tam];

                // We get the file reading the array full of bytes
                for (int i = 0; i < buffer.length; i++) {
                    buffer[i] = (byte) in.read();
                }

                // Writting the file
                out.write(buffer);
                out.flush();
                fos.close();

                System.out.println("Archivo Recibido: " + nombreArchivo);
                return true;
            } catch (Exception e) {
                System.err.println("Error - " + e.toString());
                return false;

            }
        }
    }//End downloadFile

    //Table Panel
    class TablePanel extends JPanel {

        JScrollPane scroll;
        JTable jTable_table;
        int init = 1;
        String[] direcRep;

        public TablePanel() {
            this.setLayout(null);
            this.setBounds(10, 10, 300, 200);

            initJtable();
            this.setVisible(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getFiles() != null && getFiles() != direcRep) {
                setUpTableData(directories);
                direcRep = getFiles();
            }
            repaint();
        }

        public void initJtable() {

            String[] colName = {"Nombre archivo"};
            if (jTable_table == null) {
                jTable_table = new JTable() {
                    @Override
                    public boolean isCellEditable(int nRow, int nCol) {
                        return false;
                    }
                };
            }

            DefaultTableModel contactTableModel = (DefaultTableModel) jTable_table
                    .getModel();
            contactTableModel.setColumnIdentifiers(colName);

            jTable_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            jTable_table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

            jTable_table.setBounds(0, 0, 259, 200);

            scroll = new JScrollPane(jTable_table);
            scroll.setBounds(0, 0, 259, 200);
            scroll.setVisible(true);
            this.add(scroll);

        }

        public void setUpTableData(String[] directorio) {
            DefaultTableModel tableModel = (DefaultTableModel) jTable_table.getModel();
            delete();

            for (int i = 0; i < directorio.length; i++) {
                String[] data = new String[1];

                data[0] = directorio[i];

                tableModel.addRow(data);

            }
            tableModel.fireTableDataChanged();
            jTable_table.setModel(tableModel);
            jTable_FilesTable = jTable_table;
            /**/

        }

        public void delete() {
            DefaultTableModel tb = (DefaultTableModel) jTable_table.getModel();
            int a = jTable_table.getRowCount() - 1;
            for (int i = a; i >= 0; i--) {
                tb.removeRow(tb.getRowCount() - 1);
            }
        }

        public JTable getjTable_table() {
            return jTable_table;
        }

        public void setjTable_table(JTable jTable_table) {
            this.jTable_table = jTable_table;
        }

    }

}
