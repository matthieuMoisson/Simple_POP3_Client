package transaction;

import sample.Connexion;
import sample.Message;

/**
 * Created by Gaetan on 11/03/2017.
 * List command
 */
public class ResetAction extends Transaction {

    public ResetAction(Connexion connexion) {
        super(connexion);
    }

    @Override
    public void run() {
        this.connexion.send(new Message(Command.RSET, ""));
        this.message = this.connexion.receive();
        if (message.getCommand() == Command.OK) {
            setChanged();
            notifyObservers(this);
        }
    }
}
