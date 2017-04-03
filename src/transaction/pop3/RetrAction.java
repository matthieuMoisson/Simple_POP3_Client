package transaction.pop3;

import mail.Mail;
import connexion.Connexion;
import connexion.Message;
import transaction.Command;
import transaction.Transaction;

/**
 * Created by Gaetan on 11/03/2017.
 * List command
 */
public class RetrAction extends Transaction {

    private int idMail;

    public RetrAction(Connexion connexion, int idMail) {
        super(connexion);
        this.idMail = idMail;
    }

    private Mail mail;

    @Override
    public void run() {
        this.connexion.send(new Message(Command.RETR, String.valueOf(idMail)));
        this.message = this.connexion.receive();
        if (message.getCommand() == Command.OK) {
            Message content = this.message = this.connexion.receive();
            mail = new Mail(content.toString());
            this.connexion.receive();
            System.out.println("----");
            System.out.println(content);
                setChanged();
                notifyObservers(this);
        }
    }

    public Mail getMail() {
        return mail;
    }
}
