package Client_RAID5;

import GUI.MainWindow;
import GUI.MainClientWindow;
import ClientLogic.ClassClient;
import GUI.LoginWindow;
import java.net.UnknownHostException;

public class Client_RAID5 {

    //Class Instances
    private static ClassClient client;
    private static MainClientWindow mainClientWindow;
    private static LoginWindow loginWindow;
    private static MainWindow mainWindow;

    //Main
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
