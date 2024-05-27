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
import javafx.concurrent.Task;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class Scene1Controller {
    @FXML
    TextField nameTextField;
    @FXML
    TextField passwordTextField;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Cliente cliente;
    private long lastRotationTime = System.currentTimeMillis() - 1000;
    private long lastShotTime = System.currentTimeMillis() - 1000;
    private long lastRightMove = System.currentTimeMillis() - 1000;
    private long lastLeftMove = System.currentTimeMillis() - 1000;
    private long lastUpMove = System.currentTimeMillis() - 1000;
    private long lastDownMove = System.currentTimeMillis() - 1000;
    private long lastMove = System.currentTimeMillis() - 1000;
    

    public void login(ActionEvent e) throws IOException{
        try {
            cliente = new Cliente();
            String username = nameTextField.getText();
            String password = passwordTextField.getText();

            Cliente.username = username;
            Cliente.password = password;

            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            
            Online(stage);
            
            //moveTank(stage);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void Online(Stage stage) throws IOException{
        String respuesta = "A";
        
            if (!Cliente.password.isEmpty() && !Cliente.username.isEmpty()) {
                respuesta = Cliente.initializeClient();
            if (!respuesta.equals("exito")) {
                Cliente.password = "";
                Cliente.username = "";
            }else if(respuesta.equals("exito")){
                Cliente.sesion = true;
                Cliente.initializeChat(Cliente.dis, Cliente.dos);
                
                this.moveTank(stage);
            }
        }  
    }

    public void moveTank(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene2.fxml"));
        root = loader.load();

        Scene2Controller scene2Controller = loader.getController();

        Cliente.scene2 = scene2Controller;
        
        Cliente.sendMessage("JUG@RYA");
        
        //Cliente.scene2.setUser("Test", "-50", "500");

        scene = new Scene(root);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ee) {
                switch (ee.getCode()) {
                    case W:
                        long nowMove = System.currentTimeMillis();
                        if (nowMove - lastMove >= 1000) {
                            int res = scene2Controller.moveUp(scene2Controller.username);
                            if(res == 1 && Cliente.sesion == true){
                                Cliente.sendMessage("UP");
                            }
                            lastMove = nowMove;
                        }
                        break;
                    case S:
                        nowMove = System.currentTimeMillis();
                        if (nowMove - lastMove >= 1000) {
                            int res = scene2Controller.moveDown(scene2Controller.username);
                            if(res == 1 && Cliente.sesion == true){
                                Cliente.sendMessage("DOWN");
                            }
                            lastMove = nowMove;
                        }
                        break;
                    case D:
                        nowMove = System.currentTimeMillis();
                        if (nowMove - lastMove >= 1000) {
                            int res = scene2Controller.moveRight(scene2Controller.username);
                            if(res == 1 && Cliente.sesion == true){
                                Cliente.sendMessage("RIGHT");
                            }
                            lastMove = nowMove;
                        }
                        break;
                    case A:
                        nowMove = System.currentTimeMillis();
                        if (nowMove - lastMove >= 1000) {
                            int res = scene2Controller.moveLeft(scene2Controller.username);
                            if(res == 1 && Cliente.sesion == true){
                                Cliente.sendMessage("LEFT");
                            }
                            lastMove = nowMove;
                        }
                        break;
                    case RIGHT:
                        long now = System.currentTimeMillis();
                        if (now - lastRotationTime >= 1000) {
                            int res = scene2Controller.rotateRight(scene2Controller.username);
                            if(res == 1 && Cliente.sesion == true){
                                Cliente.sendMessage("ROT_RIGHT");
                            }
                            lastRotationTime = now;
                        }
                    case LEFT:
                        now = System.currentTimeMillis();
                        if (now - lastRotationTime >= 1000) {
                            int res = scene2Controller.rotateLeft(scene2Controller.username);
                            if(res == 1 && Cliente.sesion == true){
                                Cliente.sendMessage("ROT_LEFT");
                            }
                            lastRotationTime = now;
                        }
                        break;
                    case SPACE:
                        now = System.currentTimeMillis();
                        if (now - lastShotTime >= 1000) {
                            scene2Controller.shot(scene2Controller.username);
                            if(Cliente.sesion == true){
                                Cliente.sendMessage("SHOT");
                            }
                            lastShotTime = now;
                        }
                        break;
                    case ESCAPE:
                        System.out.println("Cerrando la aplicación...");
                        if (Cliente.sesion == true) {
                            Cliente.sendMessage("Exit");
                        }
                        System.exit(0);
                        break;
                }
            }
        });

        stage.setScene(scene);
        stage.show();
    }
}