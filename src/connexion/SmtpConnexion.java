package connexion;

import java.io.IOException;

/**
 * Created by p1509413 on 03/04/2017.
 */
public class SmtpConnexion extends Connexion {

    private SmtpConnexion() throws IOException {
        super();
    }

    public static Connexion getInstance() throws IOException {
        if (connexion == null) {
            connexion = new SmtpConnexion();
        }
        return connexion;
    }
}
