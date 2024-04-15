package com.mycompany.mylogin;

import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class Scene2Controller {
    @FXML
    Label nameLabel;
    @FXML
    Rectangle rect1;
    @FXML
    ImageView tank;
    double posX = 0.0;
    double posY = 0.0;
    //File file = new File("tank.png");
    //Image img = new Image(file.toURI().toString());
    //ImageView tank = new ImageView(img);
    //Image img = new Image(getClass().getResourceAsStream("/img/tank.png"));
    //ImageView tank = new ImageView(img);
    
    
    /*public void displayName(String username, DataInputStream dis, DataOutputStream dos){
        nameLabel.setText("Hello " + username);
        Cliente.sendMessages(dis,dos);
    }*/
    
    public void getPosition(Cliente cliente) throws IOException{
        
        Cliente.sendMessage("JUG@RYA");
        String X = Cliente.dis.readUTF();
        String Y = Cliente.dis.readUTF();
        
        System.out.println("Poscion");
        System.out.println(X);
        System.out.println(Y);
        posX = Double.parseDouble(X);
        posY = Double.parseDouble(Y);
        
        tank.setLayoutX(posX);
        tank.setLayoutX(posY);
    }

    public void moveUp(){
        tank.setY(tank.getY() -10);
        //rect1.setY(rect1.getY() - 10);
    }
    public void moveDown(){
        tank.setY(tank.getY() +10);
        //rect1.setY(rect1.getY() + 10);
    }
    public void moveRight(){
        tank.setX(tank.getX() +10);
        //rect1.setX(rect1.getX() + 10);
    }
    public void moveLeft(){
        tank.setX(tank.getX() -10);
        //rect1.setX(rect1.getX() - 10);
    }
    public void rotateLeft(){
        //giro normal sin animacion
        //rect1.setRotate(rect1.getRotate() + 90.0);
        
        
        //rotacion con animacion
        RotateTransition rotacion = new RotateTransition();
        rotacion.setDuration(Duration.millis(1000));
        //rotacion.setNode(rect1);
        rotacion.setNode(tank);
        rotacion.setByAngle(90.0);
        rotacion.setCycleCount(1);
        rotacion.setAutoReverse(false);
        rotacion.play();
    }
    public void rotateRight(){
        //giro normal
        //rect1.setRotate(rect1.getRotate() - 90.0);
        
        
        //rotacion con animacion
        RotateTransition rotacion = new RotateTransition();
        rotacion.setDuration(Duration.millis(1000));
        rotacion.setNode(rect1);
        rotacion.setNode(tank);
        rotacion.setByAngle(-90.0);
        rotacion.setCycleCount(1);
        rotacion.setAutoReverse(false);
        rotacion.play();
    }
}
