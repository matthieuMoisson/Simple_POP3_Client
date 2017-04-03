package connexion;

import java.io.IOException;

/**
 * Created by p1509413 on 03/04/2017.
 */
public class Pop3Connexion extends Connexion {

    private Pop3Connexion() throws IOException {
        super();
    }

    public static Connexion getInstance() throws IOException {
        if (connexion == null) {
            connexion = new Pop3Connexion();
        }
        return connexion;
    }
}
