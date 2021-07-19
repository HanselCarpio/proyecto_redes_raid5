package server_RAID5;

import ServerLogic.MultiServer;
import java.io.IOException;

public class Server_RAID5 {

    private static MultiServer server;

    /**
     * @param args the command line arguments
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
