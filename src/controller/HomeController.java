package controller;


import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import sample.Command;
import sample.Connexion;
import sample.Message;
import sample.Settings;
import transaction.Authentication;
import transaction.Transaction;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

public class HomeController implements Observer{

    public PasswordField password;
    public TextField username;
    public Label logs;

    private Connexion connexion = null;

    public HomeController() {
        try {
            this.connexion = new Connexion();
            System.out.println(this.connexion.receive());
        } catch (IOException e) {
            e.printStackTrace();
            log(e.getMessage());
        }
    }


    public void openSettings() {
        TextInputDialog dialog = new TextInputDialog(Settings.getHost());
        dialog.setTitle("Settings");
        dialog.setHeaderText("Server Settings");
        dialog.setContentText("Server: ");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(Settings::setSettings);
    }

    public void connect() {
        if (this.connexion != null) {
            Authentication authentication = new Authentication(connexion, username.getText(), password.getText());
            authentication.addObserver(this);
            Platform.runLater(authentication);
        }
    }

    private void log(String log) {
        this.logs.setText("");
        this.logs.setText(logs.getText() + "\n" + log);
    }


    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Transaction) {
            Authentication transaction = (Authentication) o;
            Message message = transaction.getMessage();
            if (message.getCommand() == Command.OK) {
                log("Open new screen");
            } else {
                log(transaction.getMessage().toString());
            }
        }
    }
}
