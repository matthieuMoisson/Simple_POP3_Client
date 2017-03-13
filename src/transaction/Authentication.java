package transaction;

import sample.Connexion;
import sample.Message;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Gaetan on 07/03/2017.
 * Authentication
 */
public class Authentication extends Transaction{

    private String username;
    private String password;
    private String timestamp;
    private String encodedPassword;

    public Authentication(Connexion connexion, String username, String password, String timestamp) {
        super(connexion);
        this.username = username;
        this.password = password;
        this.timestamp = timestamp;
        this.encodedPassword = this.getEncodedPassword(this.password);
    }

    public String getEncodedPassword(String password)
    {
        MessageDigest messageDigest;
        try {
            String fullPhrase = this.timestamp + password;
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(fullPhrase.getBytes());
            byte[] messageDigestMD5 = messageDigest.digest();
            StringBuilder stringBuffer = new StringBuilder();
            for (byte bytes : messageDigestMD5) {
                stringBuffer.append(String.format("%02x", bytes & 0xff));
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public void run() {
        this.connexion.send(new Message(Command.APOP, username + " " + encodedPassword));
        this.message = this.connexion.receive();
        setChanged();
        notifyObservers(this);
    }
}
