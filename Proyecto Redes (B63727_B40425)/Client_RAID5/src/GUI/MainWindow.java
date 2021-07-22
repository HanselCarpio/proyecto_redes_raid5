package GUI;

import Client_RAID5.Client_RAID5;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * Esta clase contiene los atributos y metodos de un MainWindow
 *
 * @author Bryan Keihl, Hansel Carpio y Victor Fernández
 * @version 1.0
 * @see MainWindow
 */
public class MainWindow extends JFrame implements ActionListener {

    /**
     * Actibutos del MainWindow
     *
     *
     */
    //Instances
    //Label
    private JLabel jlabel_IP;
    private JLabel jLabel_Welcome;
    private JLabel jLabel_Message;
    //TextField
    private JTextField jtextField_IP;
    //Button
    private JButton jbtn_ConnectIP;

    JDesktopPane jdesktop_pane_principal;

    /**
     * Constructor
     *
     *
     */
    public MainWindow() {
        this.setTitle("Proyecto II - Implementación de un RAID 5");
        this.setResizable(false);
        this.setSize(490, 400);
    }

    /**
     * Metodo init para configuara los atributos de la clase y mostrar los componentes visuales
     *
     *
     * @throws java.net.UnknownHostException
     */
    public void init() throws UnknownHostException {
        this.setLayout(null);

        //Instances
        //Label 
        this.jLabel_Welcome = new JLabel("Simulador de un RAID 5");
        this.jLabel_Message = new JLabel("saSearch - Busqueda de Libros y Metadatos");
        this.jlabel_IP = new JLabel("Para continuar ingrese la dirección IP del controllerNode: ");

        //Text Field
        this.jtextField_IP = new JTextField();

        //Buttons
        this.jbtn_ConnectIP = new JButton("Conectar");

        //Desktop Pane
        jdesktop_pane_principal = new JDesktopPane();

        //Placing
        this.jLabel_Welcome.setBounds(162, 40, 200, 50);
        this.jLabel_Message.setBounds(95, 70, 350, 50);
        this.jlabel_IP.setBounds(62, 105, 350, 50);
        this.jtextField_IP.setBounds(168, 170, 120, 20);
        this.jbtn_ConnectIP.setBounds(182, 220, 90, 30);

        //Adding to window
        this.jbtn_ConnectIP.addActionListener(this);
        this.add(this.jlabel_IP);
        this.add(this.jtextField_IP);
        this.add(this.jbtn_ConnectIP);
        this.add(this.jLabel_Welcome);
        this.add(this.jLabel_Message);
        this.add(this.jLabel_Welcome);

        //Atributes
        this.jtextField_IP.setText((InetAddress.getLocalHost().getHostAddress()).trim());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.jdesktop_pane_principal.setVisible(false);
        this.jdesktop_pane_principal.setSize(490, 400);
        this.add(this.jdesktop_pane_principal);
        this.jdesktop_pane_principal.setVisible(false);
    }

    /**
     * Boton que nos deja conectarcon el servidor
     *
     *
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {

        if (arg0.getSource() == this.jbtn_ConnectIP) {

            if (!jtextField_IP.getText().equalsIgnoreCase("") && (Client_RAID5.getClient().startConnection(jtextField_IP.getText(), 5555))) {
                Client_RAID5.getClient().getOut().println("Hello");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error - Dirección IP Inválida");
            }
        }
    }
}
