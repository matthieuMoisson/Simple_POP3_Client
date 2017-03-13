package controller;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logger.LogMessage;
import logger.LogType;
import logger.Logger;
import transaction.*;
import sample.Connexion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    public void handleDisconnect(ActionEvent actionEvent) {
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
            this.populateListView(statAction.getNbMails());
        } else if (o instanceof QuitAction) {
            QuitAction quitAction = (QuitAction) o;
            if (quitAction.isConnexionClosed()) {
                this.connexion.close();
                anchorPane.getScene().getWindow().hide();
            } else {
                Logger.log("Could not handleDisconnect");
            }
        } else if (o instanceof ResetAction) {
            ResetAction resetAction = (ResetAction) o;
            Logger.log(resetAction.getMessage().toString());
        }
    }

    public void handleMouseClickListView(MouseEvent mouseEvent) {
        int idMailClicked = listView.getSelectionModel().getSelectedIndex() + 1;
        // Open Mail Screen
        System.out.println(idMailClicked);
        openMail(idMailClicked);
    }

    private void populateListView(int nbMail) {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < nbMail; i++) {
            data.add("Mail n°" + String.valueOf(i+1));
        }
        ObservableList<String> items = FXCollections.observableList(data);
        listView.setItems(items);
    }

    private void openMail(int idMail) {
        try {

            FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("../view/mail.fxml"));
            Parent newWindow = fxmlLoader.load();

            MailController mailController = fxmlLoader.getController();
            mailController.setIdMail(idMail);
            Stage stage = new Stage();
            stage.setTitle("Mail");
            stage.setScene(new Scene(newWindow, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("Could not open Mail");
        }
    }

    public void handleReset(ActionEvent actionEvent) {
        ResetAction resetAction = new ResetAction(connexion);
        resetAction.addObserver(this);
        Platform.runLater(resetAction);
    }
}
