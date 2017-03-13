package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import logger.LogMessage;
import logger.LogType;
import logger.Logger;
import sample.Connexion;
import sample.Settings;
import transaction.Command;
import transaction.DeleteAction;
import transaction.RetrAction;
import transaction.StatAction;

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

    private int idMail = 0;

    @FXML
    public void initialize() {
        try {
            this.connexion = Connexion.getInstance();
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
    }

    public void handleDelete(ActionEvent actionEvent) {
        runDelete(this.idMail);
    }
}
