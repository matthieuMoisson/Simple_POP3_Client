package sample;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Gaetan on 06/03/2017.
 * Settings
 */
public class Settings {

    private static int port = 1096;

    private static InetAddress ipServer;

    static {
        try {
            ipServer = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static String host = getHost();


    public static InetAddress getIpServer() {
        return ipServer;
    }

    public static void setIpServer(InetAddress ipServer) {
        Settings.ipServer = ipServer;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        Settings.port = port;
    }

    public static void setSettings(String host)  {
        String[] hostSplit = host.split(":");
        if (hostSplit.length == 2) {
            String hostname = hostSplit[0];
            int port = Integer.parseInt(hostSplit[1]);
            try {
                setIpServer(InetAddress.getByName(hostname));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            setPort(port);
        }
    }

    public static String getHost() {
        return getIpServer().getHostName() + ":" + getPort();
    }
}
