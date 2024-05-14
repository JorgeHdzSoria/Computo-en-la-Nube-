package com.mycompany.mylogin;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static javafx.scene.input.KeyCode.LEFT;
import static javafx.scene.input.KeyCode.RIGHT;

public class Scene1Controller {
    @FXML
    TextField nameTextField;
    @FXML
    TextField passwordTextField;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Cliente cliente;
    private long lastRotationTime = System.currentTimeMillis() - 1000; // Initialize to allow immediate rotation

    public void login(ActionEvent e) {
        try {
            cliente = new Cliente();
            String username = nameTextField.getText();
            String password = passwordTextField.getText();

            Cliente.username = username;
            Cliente.password = password;

            Cliente.initializeClient();

            Cliente.initializeChat(Cliente.dis, Cliente.dos);
            
            Cliente.sendMessage("JUG@RYA");

            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

            this.moveTank(stage);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void moveTank(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene2.fxml"));
        root = loader.load();

        Scene2Controller scene2Controller = loader.getController();

        Cliente.scene2 = scene2Controller;

        scene = new Scene(root);
        scene.setOnKeyPressed(ee -> {
            switch (ee.getCode()) {
                case W:
                    int res = scene2Controller.moveUp(scene2Controller.username);
                    if(res == 1){
                        Cliente.sendMessage("UP");
                    }
                    break;
                case S:
                    res = scene2Controller.moveDown(scene2Controller.username);
                    if(res == 1){
                        Cliente.sendMessage("DOWN");
                    }
                    break;
                case D:
                    res = scene2Controller.moveRight(scene2Controller.username);
                    if(res == 1){
                        Cliente.sendMessage("RIGHT");
                    }
                    break;
                case A:
                    res = scene2Controller.moveLeft(scene2Controller.username);
                    if(res == 1){
                        Cliente.sendMessage("LEFT");
                    }
                    break;
                case RIGHT:
                case LEFT:
                    long now = System.currentTimeMillis();
                    if (now - lastRotationTime >= 1000) {
                        if (ee.getCode() == RIGHT) {
                            Cliente.sendMessage("ROT_RIGHT");
                            scene2Controller.rotateRight(scene2Controller.username);
                        } else {
                            Cliente.sendMessage("ROT_LEFT");
                            scene2Controller.rotateLeft(scene2Controller.username);
                        }
                        lastRotationTime = now;
                    }
                    break;
                case SPACE:
                    scene2Controller.shot();
                    break;
            }
        });

        stage.setScene(scene);
        stage.show();
    }
}