package connexion;

import sample.Settings;

import javax.net.SocketFactory;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;

/**
 * Created by p1509413 on 03/04/2017.
 * Pop3Connexion
 */
public class Pop3Connexion extends Connexion {

    private Pop3Connexion() throws IOException {
        SSLServerSocketFactory serverFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SocketFactory factory= SSLSocketFactory.getDefault() ;
        SSLSocket clientSocket = (SSLSocket) factory.createSocket(Settings.getIpServerPop3(), Settings.getPortPop3());
        clientSocket.setEnabledCipherSuites(serverFactory.getSupportedCipherSuites());
        is = clientSocket.getInputStream();
        os = clientSocket.getOutputStream();
    }

    public static Connexion getInstance() throws IOException {
        if (connexion == null) {
            connexion = new Pop3Connexion();
        }
        return connexion;
    }
}
