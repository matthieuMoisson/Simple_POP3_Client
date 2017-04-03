package controller;

import connexion.Pop3Connexion;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logger.Logger;
import mail.Mail;
import connexion.Connexion;
import transaction.pop3.DeleteAction;
import transaction.pop3.RetrAction;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by p1509413 on 13/03/2017.
 */
public class MailController implements Observer {

    @FXML
    public AnchorPane anchorPane;

    private Connexion connexion;

    @FXML
    private Label fromLabel;

    @FXML
    private Label subjectLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label contentLabel;

    @FXML
    private Label toLabel;

    private int idMail = 0;

    @FXML
    public void initialize() {
        try {
            this.connexion = Pop3Connexion.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MailController() {
        super();
    }


    public void setIdMail(int idMail) {
        this.idMail = idMail;
        System.out.println(idMail);
        runRetr(idMail);
    }

    private void runRetr(int idMail) {
        RetrAction retrAction = new RetrAction(connexion, idMail);
        retrAction.addObserver(this);
        Platform.runLater(retrAction);
    }

    private void runDelete(int idMail) {
        DeleteAction deleteAction = new DeleteAction(connexion, idMail);
        deleteAction.addObserver(this);
        Platform.runLater(deleteAction);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof DeleteAction) {
            DeleteAction deleteAction = (DeleteAction) o;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Information");
            alert.setContentText("Your message has been marked deleted!");
            alert.showAndWait();
            System.out.println(deleteAction.getMessage());
            anchorPane.getScene().getWindow().hide();
        }
        if (o instanceof RetrAction) {
            RetrAction retrAction = (RetrAction) o;
            Mail mail = retrAction.getMail();
            this.subjectLabel.setText(mail.getSubject());
            this.toLabel.setText(mail.getReceiver());
            this.fromLabel.setText(mail.getSender());
            this.dateLabel.setText(mail.getDate());
            this.contentLabel.setText(mail.getContent());
        }
    }

    public void handleDelete(ActionEvent actionEvent) {
        runDelete(this.idMail);
    }

    public void handleReply(ActionEvent actionEvent) {
        openNewMail();
    }

    private void openNewMail() {
        try {

            FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("../view/newmail.fxml"));
            Parent newWindow = fxmlLoader.load();

            NewMailController newMailController = fxmlLoader.getController();
            newMailController.setRecipients(this.fromLabel.getText());

            Stage stage = new Stage();
            stage.setTitle("New Mail");
            stage.setScene(new Scene(newWindow, 600, 400));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("Could not open New Mail");
        }
    }
}
