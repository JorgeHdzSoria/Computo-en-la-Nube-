    package com.mycompany.mylogin;

    import java.io.IOException;
    import java.util.LinkedHashMap;
    import javafx.animation.KeyFrame;
    import javafx.animation.RotateTransition;
    import javafx.animation.Timeline;
    import javafx.fxml.FXML;
    import javafx.scene.image.ImageView;
    import javafx.scene.shape.Rectangle;
    import javafx.util.Duration;
    import javafx.animation.TranslateTransition;
    import javafx.stage.Stage;
    import javafx.scene.Scene;
    import javafx.application.Platform;
    import javafx.scene.layout.Pane;
    import java.util.ArrayList;

    class tanque{
        String username;
        float X;
        float Y;
        boolean activo;
        ImageView myTank;
        int dir;
    }

    public class Scene2Controller {
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

        String username = "";

        public static ImageView[] imageviewTanks;
        private ArrayList<ImageView> bullets = new ArrayList<>();
        static LinkedHashMap<String, tanque> users = new LinkedHashMap<>();
        static LinkedHashMap<String, ImageView> tanks = new LinkedHashMap<>();
        double width;
        double height;
        double xs;
        double ys;
        double moveX;
        double moveY;
        Stage primaryStage;
        Timeline timeline;

        @FXML
        public void initialize() {
            imageviewTanks = new ImageView[]{tank, tank2, tank3, tank4};
            Platform.runLater(()-> {
                tanque tank = users.get(this.username);
                primaryStage = (Stage) rect1.getScene().getWindow();
                Scene scene = primaryStage.getScene();

                width = scene.getWidth();
                height = scene.getHeight();
                System.out.println("Ancho de la escena: " + width);
                System.out.println("Alto de la escena: " + height);
            });
        }

        public void getPosition(Cliente cliente) throws IOException{
            Cliente.sendMessage("JUG@RYA");
            
            System.out.println(users.size());
            if(users.size() == 0){
                
            }
        }

        public void setPositions(){
            System.out.println("USERS SIZE: " + users.size());
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
                    System.out.println("TANK LX: " + tank.getLayoutX());
                    System.out.println("TANK LY: " + tank.getLayoutY());
                }
            }
        }
        
        public void moveUser(String msg){
            System.out.println("MOVUSR: " + msg);
            int position = msg.indexOf(": ") + 1;
            
            String username = msg.substring(0, position - 1);
            System.out.println(username);
            
            String move = msg.substring(position + 1, msg.length());
            System.out.println(move);
            
            if(username != this.username){
                if(move.equals("UP")){
                    this.moveUp(username);
                }
                if(move.equals("DOWN")){
                    this.moveDown(username);
                }
                if(move.equals("RIGHT")){
                    this.moveRight(username);
                }
                if(move.equals("LEFT")){
                    this.moveLeft(username);
                }
                if(move.equals("ROT_RIGHT")){
                    this.rotateRight(username);
                }
                if(move.equals("ROT_LEFT")){
                    this.rotateLeft(username);
                }
                if(move.equals("SHOT")){
                    this.shot(username);
                }
            }
        }
        
        public void newUser(String X, String Y){
            System.out.println("X:" + X);
            System.out.println("Y:" + Y);
            int position = X.indexOf(": ") + 1;

            String username = X.substring(0, position - 1);
            System.out.println(username);

            String posX = X.substring(position + 1, X.length());
            System.out.println(posX);

            String posY = Y.substring(position + 1, Y.length());
            System.out.println(posY);
            
            tanque temTank = new tanque();
            temTank.X = Integer.parseInt(posX) + 60;
            temTank.Y = Integer.parseInt(posY);
            temTank.username = username;
            temTank.activo = true;
            temTank.myTank = imageviewTanks[users.size()];
            temTank.dir = 0;
            
            System.out.println("USERS SIZE: " + users.size());
            tanks.put(username ,imageviewTanks[users.size()]);
            users.put(username, temTank);
            this.setPositions();
        }

        public void setUser(String user,String X, String Y){
            int position;
            String name;
            tanque tank = new tanque();
            position = user.indexOf(": ") + 1;

            name = user.substring(position + 1, user.length());
            System.out.println("USERNAME: " + name);
            System.out.println("X: " + X);
            System.out.println("Y: " + Y);
            tank.X = Integer.parseInt(X) + 60;
            tank.Y = Integer.parseInt(Y);
            tank.username = name;
            tank.activo = true;
            tank.myTank = imageviewTanks[users.size()];
            tank.dir = 0;
            
            username = name;
            tanks.put(name ,imageviewTanks[users.size()]);
            users.put(name, tank);
            this.setPositions();
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

                temTank.X = Integer.parseInt(first) + 60;

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
                temTank.username = first;
                
                temTank.myTank = imageviewTanks[index];
                temTank.dir = 0;
                        
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

        public int moveUp(String usr){
            System.out.println("Up: " + usr);

            tanque tank = users.get(usr);
            
            if((tank.Y - 70) >= 0){
                TranslateTransition translateT = new TranslateTransition(Duration.millis(500),tank.myTank);
                TranslateTransition translateR = new TranslateTransition(Duration.millis(500),rect1);

                if(tank.dir == 3 || tank.dir == 1 || tank.dir == -1 || tank.dir == -3)
                {
                    //animacion para movimiento del tanque                
                    translateT.setByY(-50);
                    translateT.setCycleCount(1);
                    translateT.setAutoReverse(true);
                    translateT.play();

                    //animacion para movimiento del disparo
                    translateR.setByY(-50);
                    translateR.setCycleCount(1);
                    translateR.setAutoReverse(true);
                    translateR.play();

                    tank.Y -= 50;

                    System.out.println(tank.Y);
                    return 1;
                }
            }
            return 0;
        }
        
        public int moveDown(String usr){
            System.out.println("Down: " + usr);

            tanque tank = users.get(usr);
            
            if((tank.Y + 150) <= height){
                TranslateTransition translateT = new TranslateTransition(Duration.millis(500),tank.myTank);
                TranslateTransition translateR = new TranslateTransition(Duration.millis(500),rect1);

                if(tank.dir == 1 || tank.dir == 3 || tank.dir == -1 || tank.dir == -3){
                     //animacion para movimiento del tanque
                    translateT.setByY(50);
                    translateT.setCycleCount(1);
                    translateT.setAutoReverse(true);
                    translateT.play();


                    //animacion del movimiento del disparo
                    translateR.setByY(50f);
                    translateR.setCycleCount(1);
                    translateR.setAutoReverse(true);
                    translateR.play();

                    tank.Y += 50;
                    System.out.println(tank.Y);

                    return 1;
                }
            }
            return 0;
        }
        
        public int moveRight(String usr){
            System.out.println("Right: " + usr);

            tanque tank = users.get(usr);
            
            if((tank.X + 150) <= width){
                TranslateTransition translateT = new TranslateTransition(Duration.millis(500),tank.myTank);
                TranslateTransition translateR = new TranslateTransition(Duration.millis(500),rect1);

                if(tank.dir == 0 || tank.dir == 2 || tank.dir == -2){
                     //animacion para movimiento del tanque
                    translateT.setByX(50);
                    translateT.setCycleCount(1);
                    translateT.setAutoReverse(true);
                    translateT.play();

                    //animacion del movimiento del disparo
                    translateR.setByX(50f);
                    translateR.setCycleCount(1);
                    translateR.setAutoReverse(true);
                    translateR.play();

                    tank.X += 50;
                    System.out.println(tank.X);

                    return 1;
                }
            }
            return 0;
        }
        
        public int moveLeft(String usr){
            System.out.println("Left: " + usr);

            tanque tank = users.get(usr);
            
            if((tank.X - 50) >= 0){
                TranslateTransition translateT = new TranslateTransition(Duration.millis(500),tank.myTank);
                TranslateTransition translateR = new TranslateTransition(Duration.millis(500),rect1);

                if(tank.dir == 0 || tank.dir == 2 || tank.dir == -2){
                     //animacion para movimiento del tanque
                    translateT.setByX(-50);
                    translateT.setCycleCount(1);
                    translateT.setAutoReverse(true);
                    translateT.play();

                    //animacion del movimiento del disparo
                    translateR.setByX(-50f);
                    translateR.setCycleCount(1);
                    translateR.setAutoReverse(true);
                    translateR.play();

                    tank.X -= 50;
                    System.out.println(tank.X);

                    return 1;
                }
            }
            return 0;
        }
        
        public int rotateRight(String usr){
            System.out.println("Rot Right: " + usr);
            
            tanque tank = users.get(usr);
            
            if(tank.username.equals(usr)){
                System.out.println("DIR: " + tank.dir);
                tank.dir += 1;
                resetPosition(tank);

                //rotacion con animacion
                RotateTransition rotacion = new RotateTransition();
                rotacion.setDuration(Duration.millis(1000));
                //rotacion.setNode(rect1);
                rotacion.setNode(tank.myTank);
                rotacion.setByAngle(90.0);
                rotacion.setCycleCount(1);
                rotacion.setAutoReverse(false);
                rotacion.play();
                
                return 1;
            }
            return 0;
        }
        
        public int rotateLeft(String usr){
            System.out.println("Rot Left: " + usr);
            
            tanque tank = users.get(usr);
            
            if(tank.username.equals(usr)){
                System.out.println("DIR: " + tank.dir);
                tank.dir -= 1;
                resetPosition(tank);

                //rotacion con animacion
                RotateTransition rotacion = new RotateTransition();
                rotacion.setDuration(Duration.millis(1000));
                rotacion.setNode(tank.myTank);
                rotacion.setByAngle(-90.0);
                rotacion.setCycleCount(1);
                rotacion.setAutoReverse(false);
                rotacion.play();
                
                return 1;
            }
            return 0;
        }

        public void shot(String usr) {
            ImageView bulletImageView = new ImageView("/img/bullet.png");
            bulletImageView.setFitWidth(20);
            bulletImageView.setFitHeight(20);

            tanque tank = users.get(usr);

            bulletImageView.setLayoutX(tank.X + (130 / 2) - 6);
            bulletImageView.setLayoutY(tank.Y + (65 / 2) - 6);
            Scene scene = tank.myTank.getScene();


            Pane root = (Pane) scene.getRoot();
            Platform.runLater(() -> root.getChildren().add(bulletImageView));

            bullets.add(bulletImageView);

            final int[] move = {0, 0};

            switch (tank.dir) {
                case 0:
                    move[0] = 10;
                    break;
                case 1:
                case -3:
                    move[1] = 10;
                    break;
                case 2:
                case -2:
                    move[0] = -10;
                    break;
                case 3:
                case -1:
                    move[1] = -10;
                    break;
            }

            final Timeline[] timeline = new Timeline[1];

            timeline[0] = new Timeline(new KeyFrame(Duration.seconds(.05), event -> {
                Platform.runLater(() -> {
                    bulletImageView.setLayoutX(bulletImageView.getLayoutX() + move[0]);
                    bulletImageView.setLayoutY(bulletImageView.getLayoutY() + move[1]);
                    //System.out.println("coordenada del disparo: " + bulletImageView.getLayoutX());

                    for (tanque otherTank : users.values()) {
                        if (otherTank != tank && bulletImageView.getBoundsInParent().intersects(otherTank.myTank.getBoundsInParent())) {
                            System.out.println("Colisión con el tanque de usuario: " + otherTank.username);
                            root.getChildren().remove(bulletImageView);
                            bullets.remove(bulletImageView);
                            root.getChildren().remove(otherTank.myTank);
                            users.remove(otherTank.username);
                            timeline[0].stop();
                            return;
                        }
                    }

                    if (bulletImageView.getLayoutX() <= 0 || bulletImageView.getLayoutX() >= scene.getWidth() ||
                        bulletImageView.getLayoutY() <= 0 || bulletImageView.getLayoutY() >= scene.getHeight()) {
                        root.getChildren().remove(bulletImageView);
                        bullets.remove(bulletImageView); 
                        timeline[0].stop();
                    }
                });
            }));

            timeline[0].setCycleCount(Timeline.INDEFINITE);
            timeline[0].play();
        }
        
        void resetPosition(tanque tank)
        {
            if(tank.dir == 4 || tank.dir == -4 )
            {
                tank.dir = 0;
            }
        }
    }
