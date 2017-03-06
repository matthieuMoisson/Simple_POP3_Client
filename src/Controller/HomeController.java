package Controller;


import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import sample.Command;
import sample.Connexion;
import sample.Message;
import sample.Settings;

import java.io.IOException;
import java.util.Optional;

public class HomeController {

    public PasswordField password;
    public TextField username;
    public Label logs;

    private Connexion connexion = null;

    public void openSettings() {
        TextInputDialog dialog = new TextInputDialog(Settings.getHost());
        dialog.setTitle("Settings");
        dialog.setHeaderText("Server Settings");
        dialog.setContentText("Server: ");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(Settings::setSettings);
    }

    public void connect() {
        try {
            this.connexion = new Connexion();
        } catch (IOException e) {
            e.printStackTrace();
            log(e.getMessage());
        }

        if (this.connexion != null) {
            Message result = connexion.authentication(username.getText(), password.getText());
            if (result.getCommand() != Command.OK) {
                log(result.getArgComplet());
            } else {
                System.out.println("Open new screen");
                // TODO Open Mailbox
            }
        }

    }

    public void log(String log) {
        this.logs.setText(logs.getText() + "\n" + log);
    }




}
