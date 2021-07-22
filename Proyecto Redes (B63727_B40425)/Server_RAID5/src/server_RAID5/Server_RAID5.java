package server_RAID5;

import ServerLogic.MultiServer;
import java.io.IOException;

/**
 * Esta clase contiene los atributos y metodos de un Server_RAID5
 *
 * @author Bryan Keihl, Hansel Carpio y Victor Fernández
 * @version 1.0
 * @see Server_RAID5
 */
public class Server_RAID5 {

    private static MultiServer server;

    /**
     * @param args the command line arguments
     */
    /**
     * Main para iniciar el Server_RAID5
     *
     *
     */
    public static void main(String[] args) throws IOException {
        server = new MultiServer();
        server.start(5555);

    }

    public static MultiServer getServer() {
        return server;
    }

    public static void setServer(MultiServer server) {
        Server_RAID5.server = server;
    }

}
