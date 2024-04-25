package com.mycompany.mylogin;

import java.io.IOException;
import java.util.LinkedHashMap;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

class tanque{
    int X;
    int Y;
    boolean activo;
}

public class Scene2Controller {
    @FXML
    Label nameLabel;
    @FXML
    Rectangle rect1;
    @FXML
    ImageView tank;
    @FXML
    ImageView tank2;
    @FXML
    ImageView tank3;
    @FXML
    ImageView tank4;
    double posX = 0.0;
    double posY = 0.0;
    String username = "";
    
    public static ImageView[] imageviewTanks;
    static LinkedHashMap<String, tanque> users = new LinkedHashMap<>();
    static LinkedHashMap<String, ImageView> tanks = new LinkedHashMap<>();
    
    @FXML
    public void initialize() {
        imageviewTanks = new ImageView[]{tank, tank2, tank3, tank4};
    }
    
    public void getPosition(Cliente cliente) throws IOException{
        Cliente.sendMessage("JUG@RYA");

        String X = Cliente.dis.readUTF();
        String Y = Cliente.dis.readUTF();

        posX = Double.parseDouble(X);
        posY = Double.parseDouble(Y);

        tank.setLayoutX(posX);
        tank.setLayoutX(posY);
    }
    
    public void setPositions(){
        for (String key : users.keySet()) {
            System.out.println(key);
            tanque info = users.get(key);
            System.out.println(info.X);
            System.out.println(info.Y);
            if(info.activo == true){
                ImageView tank = tanks.get(key);
                tank.setLayoutX(info.X);
                tank.setLayoutY(info.Y);
                tank.setVisible(true);
            }
        }
    }
    
    public void setUser(String user){
        int position;
        String first;
        tanque tank = new tanque();
        position = user.indexOf(": ") + 1;

        first = user.substring(position + 1, user.length());
        System.out.println(first);
        username = first;
        users.put(first, tank);
        tanks.put(first, imageviewTanks[0]);
    }
    
    public void setUsers(String texto){
        int position,position2;
        int index = 0;
        String first = "";
        
        String[] usuarios = new String[4];
        
        while(texto.length() > 5){
            position = texto.indexOf("{") + 1;
            position2 = texto.indexOf("}");

            first = texto.substring(position, position2);

            texto = texto.substring(position2 + 3,texto.length());
        
            usuarios[index] = first;
            index++;
        }
        
        users.clear();
        tanks.clear();
        
        for (index = 0; index < 4 ; index ++){
            tanque temTank = new tanque();
            texto = usuarios[index];
            System.out.println(texto);
            
            position = texto.indexOf(": ") + 1;
            position2 = texto.indexOf(",");

            first = texto.substring(position + 1, position2);
            System.out.println(first);
            
            temTank.X = Integer.parseInt(first);

            texto = texto.substring(position2+1,texto.length());

            position = texto.indexOf(": ") + 1;
            position2 = texto.indexOf(",");

            first = texto.substring(position + 1, position2);
            System.out.println(first);
            
            temTank.Y = Integer.parseInt(first);

            texto = texto.substring(position2+1,texto.length());

            position = texto.indexOf(": ") + 1;
            position2 = texto.indexOf(",");

            first = texto.substring(position + 1, position2);
            System.out.println(first);
            
            temTank.activo = Boolean.valueOf(first);

            texto = texto.substring(position2+1,texto.length());

            position = texto.indexOf(": ") + 1;
            position2 = texto.indexOf(",") - 1;

            first = texto.substring(position + 1, texto.length());
            System.out.println(first);
            
            if(first.equals("none")){
                break;
            }else{
                if(users.get(first) == null){
                    users.put(first, temTank);
                    tanks.put(first,imageviewTanks[index]);
                }else{
                    users.remove(first);
                    users.put(first, temTank);
                    tanks.put(first,imageviewTanks[index]);
                }
            }
        }
    }

    public void moveUp(){
        ImageView myTank = tanks.get(username);
        myTank.setY(myTank.getY() -10);
    }
    public void moveDown(){
        ImageView myTank = tanks.get(username);
        myTank.setY(myTank.getY() +10);
        //rect1.setY(rect1.getY() + 10);
    }
    public void moveRight(){
        ImageView myTank = tanks.get(username);
        myTank.setX(myTank.getX() +10);
        //rect1.setX(rect1.getX() + 10);
    }
    public void moveLeft(){
        ImageView myTank = tanks.get(username);
        myTank.setX(myTank.getX() -10);
        //rect1.setX(rect1.getX() - 10);
    }
    public void rotateRight(){
        ImageView myTank = tanks.get(username);
        
        
        //rotacion con animacion
        RotateTransition rotacion = new RotateTransition();
        rotacion.setDuration(Duration.millis(1000));
        //rotacion.setNode(rect1);
        rotacion.setNode(myTank);
        rotacion.setByAngle(90.0);
        rotacion.setCycleCount(1);
        rotacion.setAutoReverse(false);
        rotacion.play();
    }
    public void rotateLeft(){
        ImageView myTank = tanks.get(username);
        
        
        //rotacion con animacion
        RotateTransition rotacion = new RotateTransition();
        rotacion.setDuration(Duration.millis(1000));
        rotacion.setNode(rect1);
        rotacion.setNode(myTank);
        rotacion.setByAngle(-90.0);
        rotacion.setCycleCount(1);
        rotacion.setAutoReverse(false);
        rotacion.play();
    }
}
