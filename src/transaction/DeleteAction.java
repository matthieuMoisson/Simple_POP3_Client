package transaction;

import sample.Connexion;
import sample.Message;

/**
 * Created by Gaetan on 11/03/2017.
 * List command
 */
public class DeleteAction extends Transaction {

    private int idMail;

    public DeleteAction(Connexion connexion, int idMail) {
        super(connexion);
        this.idMail = idMail;
    }

    // private Mail mail;

    @Override
    public void run() {
        this.connexion.send(new Message(Command.DELE, String.valueOf(idMail)));
        this.message = this.connexion.receive();
        if (message.getCommand() == Command.OK) {
           setChanged();
           notifyObservers(this);
        }
    }
}
