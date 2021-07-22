package Client_RAID5;

import GUI.MainWindow;
import GUI.MainClientWindow;
import ClientLogic.ClassClient;
import GUI.LoginWindow;
import java.net.UnknownHostException;

/**
 * Esta clase contiene los atributos y metodos de un Client_RAID5
 *
 * @author Bryan Keihl, Hansel Carpio y Victor Fernández
 * @version 1.0
 * @see Client_RAID5
 */
public class Client_RAID5 {

    //Class Instances
    private static ClassClient client;
    private static MainClientWindow mainClientWindow;
    private static LoginWindow loginWindow;
    private static MainWindow mainWindow;

    //Main
    /**
     * Main para iniciar el cliente
     *
     *
     * @param args
     * @throws java.lang.InterruptedException
     * @throws java.net.UnknownHostException
     */
    public static void main(String[] args) throws InterruptedException, UnknownHostException {
        //Instances are made
        client = new ClassClient(); 
        mainClientWindow = new MainClientWindow();
        loginWindow = new LoginWindow();
        //Init MainWindow
        mainWindow = new MainWindow();
        mainWindow.init();
    }

    public static ClassClient getClient() {
        return client;
    }

    public static MainClientWindow getWindowClientTFTP() {
        return mainClientWindow;
    }

    public static LoginWindow getLoginWindow() {
        return loginWindow;
    }

}
