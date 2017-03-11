package controller;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import logger.LogMessage;
import logger.LogType;
import logger.Logger;
import transaction.*;
import sample.Connexion;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class MailboxController implements Observer{

    @FXML
    public Label label;
    @FXML
    public AnchorPane anchorPane;

    @FXML
    public ListView<String> listView;

    private Connexion connexion;

    public MailboxController() {
        super();
    }

    @FXML
    public void initialize() {
        Logger.setLabel(label);
        try {
            this.connexion = Connexion.getInstance();
            runStat();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log(e.getMessage());
        }
    }

    public void disconnect(ActionEvent actionEvent) {
        QuitAction quitAction = new QuitAction(connexion);
        quitAction.addObserver(this);
        Platform.runLater(quitAction);
    }

    private void runStat() {
        StatAction statAction = new StatAction(connexion);
        statAction.addObserver(this);
        Platform.runLater(statAction);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof StatAction) {
            StatAction statAction = (StatAction) o;
            Logger.log(new LogMessage(LogType.INFO, statAction.getNbMails() + " mails " + statAction.getSizeMails()));
        } else if (o instanceof QuitAction) {
            QuitAction quitAction = (QuitAction) o;
            if (quitAction.isConnexionClosed()) {
                this.connexion.close();
                anchorPane.getScene().getWindow().hide();
            } else {
                Logger.log("Could not disconnect");
            }
        }
    }
}
