package controller;

import connexion.Connexion;
import connexion.Pop3Connexion;
import connexion.SmtpConnexion;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import mail.Mail;
import transaction.Command;
import transaction.smtp.SendAction;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by p1509413 on 13/03/2017.
 */
public class NewMailController implements Observer {

    @FXML
    public AnchorPane anchorPane;

    private Connexion connexion;

    @FXML
    private TextField subject;

    @FXML
    private TextArea content;

    @FXML
    private TextField recipients;

    @FXML
    public void initialize() {
//        try {
//            this.connexion = SmtpConnexion.getInstance();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public NewMailController() {
        super();
    }

    public void setRecipients(String recipients) {
        this.recipients.setText(recipients);
    }

    private void runSend() {

        if (recipients.getText() == null || recipients.getText().trim().isEmpty() || recipients.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Recipients empty");
            alert.setContentText("Recipients must be specified");
            alert.show();
            return;
        }

        Mail mail = null;
        try {
            mail = new Mail(Pop3Connexion.getInstance().getCurrentUsername(), recipients.getText(), subject.getText(), content.getText());
            SendAction sendAction = new SendAction(mail);
            sendAction.addObserver(this);
            Platform.runLater(sendAction);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof SendAction) {
            SendAction sendAction = (SendAction) o;
            if (sendAction.getMessage().getCommand().equals(Command.OK)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Message Sent! ");
                alert.setContentText("Your mail was sent successfully! ");
                alert.show();
            }
        }
    }

    public void handleSend(ActionEvent actionEvent) {
        runSend();
    }
}
