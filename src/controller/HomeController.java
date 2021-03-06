package controller;


import connexion.Pop3Connexion;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logger.LogMessage;
import logger.LogType;
import logger.Logger;
import transaction.Command;
import connexion.Connexion;
import connexion.Message;
import sample.Settings;
import transaction.pop3.Authentication;

import java.io.IOException;
import java.util.*;

public class HomeController implements Observer{

    @FXML
    public PasswordField password;
    @FXML
    public TextField username;
    @FXML
    public Label logs;
    @FXML
    public AnchorPane anchorPane;

    @FXML
    public Button btn_connect;

    private Connexion connexion = null;
    private String timestamp = null;

    public HomeController() {
        super();
    }

    @FXML
    public void initialize() {
        anchorPane.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                btn_connect.fire();
                ev.consume();
            }
        });

        Logger.setLabel(logs);
        openConnection();
    }

    private void openConnection() {
        if (this.connexion != null) {
            this.connexion.close();
        }
        try {
            this.connexion = Pop3Connexion.getInstance();
            Message m = this.connexion.receive();
            timestamp = this.connexion.getTimetamp(m);
            Logger.log(new LogMessage(LogType.SUCCESS, m.toString()));
        } catch (IOException e) {
//            e.printStackTrace();
            Logger.log(new LogMessage(e.getMessage()));
        }
    }

    @FXML
    public void connect() {
        if (this.connexion == null) {
            this.openConnection();
        }

        if (this.connexion != null) {
            if (!Objects.equals(username.getText(), "") && !Objects.equals(password.getText(), "")) {
                Authentication authentication = new Authentication(connexion, username.getText(), password.getText(), timestamp);
                authentication.addObserver(this);
                Platform.runLater(authentication);
            } else {
                Logger.log("Provide your credentials");
            }
        } else {
            Logger.log("Cannot connect to server");
        }
    }

    private void openMailBox() {

        try {
            Parent newWindow = FXMLLoader.load(getClass().getResource("../view/mailbox.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Mailbox");
            stage.setScene(new Scene(newWindow, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("Could not open Mailbox");
        }

        // Hide this current window (if this is what you want)
        anchorPane.getScene().getWindow().hide();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Authentication) {
            Authentication transaction = (Authentication) o;
            Message message = transaction.getMessage();
            if (message.getCommand() == Command.OK) {
                connexion.setCurrentUsername(transaction.getUsername());
                Logger.log(new LogMessage(LogType.SUCCESS,"Connection accepted"));
                openMailBox();
            } else {
                Logger.log(transaction.getMessage().toString());
            }
        }
    }

    @FXML
    public void openSettingsPOP3(ActionEvent actionEvent) {
        TextInputDialog dialog = createDialog(Settings.getHostPOP3(), "Settings POP3", "POP3 Server Settings", "Server: ");

        Optional<String> fullHostPop3 = dialog.showAndWait();
        fullHostPop3.ifPresent(Settings::setSettingsPop3);
        openConnection();
    }

    @FXML
    public void openSettingsSMTP(ActionEvent actionEvent) {
        TextInputDialog dialog = createDialog(Settings.getHostSMTP(), "Settings SMTP", "SMTP Server Settings", "Server: ");

        Optional<String> fullHostSMTP = dialog.showAndWait();
        fullHostSMTP.ifPresent(Settings::setSettingsSmtp);

    }

    private TextInputDialog createDialog(String defaultHost, String title, String header, String content) {
        TextInputDialog dialog = new TextInputDialog(defaultHost);
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);
        return dialog;
    }
}
