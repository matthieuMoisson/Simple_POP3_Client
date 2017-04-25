package sample;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Gaetan on 06/03/2017.
 * Settings
 */
public class Settings {

    private static int portPop3 = 1096;

    private static int portSMTP = 1097;

    private static InetAddress ipServerPop3;

    private static InetAddress ipServerSMTP;

    static {
        try {
            ipServerPop3 = InetAddress.getByName("PC-ASUS-Gaetan");
            ipServerSMTP = InetAddress.getByName("PC-ASUS-Gaetan");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static InetAddress getIpServerPop3() {
        return ipServerPop3;
    }

    public static void setIpServerPop3(InetAddress ipServerPop3) {
        Settings.ipServerPop3 = ipServerPop3;
    }

    public static int getPortPop3() {
        return portPop3;
    }

    public static void setPortPop3(int portPop3) {
        Settings.portPop3 = portPop3;
    }

    public static void setSettingsPop3(String host)  {
        String[] hostSplit = host.split(":");
        if (hostSplit.length == 2) {
            String hostname = hostSplit[0];
            int port = Integer.parseInt(hostSplit[1]);
            try {
                setIpServerPop3(InetAddress.getByName(hostname));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            setPortPop3(port);
        }
    }

    public static void setSettingsSmtp(String host)  {
        String[] hostSplit = host.split(":");
        if (hostSplit.length == 2) {
            String hostname = hostSplit[0];
            int port = Integer.parseInt(hostSplit[1]);
            try {
                setIpServerSMTP(InetAddress.getByName(hostname));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            setPortSMTP(port);
        }
    }

    public static String getHostSMTP() {
        return getIpServerSMTP().getHostName() + ":" + getPortSMTP();
    }

    public static String getHostPOP3() {
        return getIpServerPop3().getHostName() + ":" + getPortPop3();
    }

    public static int getPortSMTP() {
        return portSMTP;
    }

    public static void setPortSMTP(int portSMTP) {
        Settings.portSMTP = portSMTP;
    }

    public static InetAddress getIpServerSMTP() {
        return ipServerSMTP;
    }

    public static void setIpServerSMTP(InetAddress ipServerSMTP) {
        Settings.ipServerSMTP = ipServerSMTP;
    }
}
