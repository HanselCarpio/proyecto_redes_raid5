package GUI;

import Client_RAID5.Client_RAID5;
import Objects.Nodes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LoginWindow extends JFrame implements ActionListener {

    //Instances
    //Label
    private JLabel jLabel_Menu;
    private JLabel jlabel_path;
    private JLabel jlabel_numberNodes;
    //TextField
    private JTextField jTextField_path;
    private JTextField jTextField_numberNodes;
    //Button
    private JButton jButton_Continue;

    public LoginWindow() {
        this.setTitle("Ingreso de la Ruta de Carpetas y # de Nodos");
        this.setResizable(false);
        this.setSize(490, 400);
    }

    public void init() {
        this.setLayout(null);

        //Instances
        //Label
        this.jLabel_Menu = new JLabel("Ingresar la siguiente información para empezar:");
        this.jlabel_numberNodes = new JLabel("Número de nodos:");
        this.jlabel_path = new JLabel("URL donde se van a crear las carpetas:");

        //TextField
        this.jTextField_path = new JTextField();
        this.jTextField_numberNodes = new JTextField();

        //Buttons
        this.jButton_Continue = new JButton("Continuar");

        //Placing
        this.jLabel_Menu.setBounds(108, 30, 400, 50);
        this.jlabel_path.setBounds(133, 100, 250, 20);
        this.jlabel_numberNodes.setBounds(174, 160, 120, 20);
        this.jTextField_path.setBounds(15, 130, 440, 20);
        this.jTextField_numberNodes.setBounds(170, 190, 120, 20);
        this.jButton_Continue.setBounds(170, 225, 120, 30);

        //Adding to Window
        this.add(this.jLabel_Menu);
        this.add(this.jlabel_path);
        this.add(this.jlabel_numberNodes);
        this.add(this.jTextField_path);
        this.add(this.jTextField_numberNodes);
        this.add(this.jButton_Continue);
        this.jButton_Continue.addActionListener(this);

        //Atributes
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) { //Button action Log In

        if (arg0.getSource() == this.jButton_Continue) { //Button action register

            try {
                Nodes usuario = new Nodes();
                usuario.addNodes(jTextField_path.getText(), jTextField_numberNodes.getText());

                Client_RAID5.getClient().getOut().println("iniciar sesion");
                Client_RAID5.getClient().getOut().println(jTextField_path.getText());
                Client_RAID5.getClient().getOut().println(jTextField_numberNodes.getText());

            } catch (Exception ae) {
            }

        }

    }

}
