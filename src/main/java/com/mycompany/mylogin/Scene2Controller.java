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

    class tanque{
        int X;
        int Y;
        boolean activo;
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
        double posX = 0.0;
        double posY = 0.0;
        String username = "";
        int dir = 0;
        public static ImageView[] imageviewTanks;
        static LinkedHashMap<String, tanque> users = new LinkedHashMap<>();
        static LinkedHashMap<String, ImageView> tanks = new LinkedHashMap<>();
        double width;
        double height;
        Stage primaryStage;

        @FXML
        public void initialize() {
            imageviewTanks = new ImageView[]{tank, tank2, tank3, tank4};
            //obtenerPositionX();
            //obtenerPositionY();
            Platform.runLater(()-> {
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
            temTank.X = Integer.parseInt(posX);
            temTank.Y = Integer.parseInt(posY);
            temTank.activo = true;
            
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
            tank.X = Integer.parseInt(X);
            tank.Y = Integer.parseInt(Y);
            tank.activo = true;
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

        public int moveUp(String usr){
            ImageView myTank = tanks.get(usr);
            TranslateTransition translateT = new TranslateTransition(Duration.millis(500),myTank);
            TranslateTransition translateR = new TranslateTransition(Duration.millis(500),rect1);

            if(dir == 3 || dir == 1 || dir == -1 || dir == -3)
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
                return 1;
            }
            return 0;

            //myTank.setY(myTank.getY() -10);
        }
        public int moveDown(String usr){
            ImageView myTank = tanks.get(usr);
            TranslateTransition translateT = new TranslateTransition(Duration.millis(500),myTank);
            TranslateTransition translateR = new TranslateTransition(Duration.millis(500),rect1);

            if(dir == 1 || dir == 3 || dir == -1 || dir == -3){
                 //animacion para movimiento del tanque
                translateT.setByY(50);
                translateT.setCycleCount(1);
                translateT.setAutoReverse(true);
                translateT.play();
               

                //animacion del movimiento del disparo
                //rect1.setY(rect1.getY() - 10);
                translateR.setByY(50f);
                translateR.setCycleCount(1);
                translateR.setAutoReverse(true);
                translateR.play();
                return 1;
            }
            return 0;
            //myTank.setY(myTank.getY() +10);
            //rect1.setY(rect1.getY() + 10);
        }
        public int moveRight(String usr){
            ImageView myTank = tanks.get(usr);
            TranslateTransition translateT = new TranslateTransition(Duration.millis(500),myTank);
            TranslateTransition translateR = new TranslateTransition(Duration.millis(500),rect1);

            if(dir == 0 || dir == 2 || dir == -2){
                 //animacion para movimiento del tanque
                translateT.setByX(50);
                translateT.setCycleCount(1);
                translateT.setAutoReverse(true);
                translateT.play();

                //animacion del movimiento del disparo
                //rect1.setY(rect1.getY() - 10);
                translateR.setByX(50f);
                translateR.setCycleCount(1);
                translateR.setAutoReverse(true);
                translateR.play();
                return 1;
            }
            return 0;
            //myTank.setX(myTank.getX() +10);
            //rect1.setX(rect1.getX() + 10);
        }
        public int moveLeft(String usr){
            ImageView myTank = tanks.get(usr);
            TranslateTransition translateT = new TranslateTransition(Duration.millis(500),myTank);
            TranslateTransition translateR = new TranslateTransition(Duration.millis(500),rect1);

            if(dir == 0 || dir == 2 || dir == -2){
                 //animacion para movimiento del tanque
                translateT.setByX(-50);
                translateT.setCycleCount(1);
                translateT.setAutoReverse(true);
                translateT.play();

                //animacion del movimiento del disparo
                //rect1.setY(rect1.getY() - 10);
                translateR.setByX(-50f);
                translateR.setCycleCount(1);
                translateR.setAutoReverse(true);
                translateR.play();
                return 1;
            }
            return 0;

            //myTank.setX(myTank.getX() -10);
            //rect1.setX(rect1.getX() - 10);
        }
        public void rotateRight(String usr){
            ImageView myTank = tanks.get(usr);

            dir+=1;
            resetPosition();

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
        public void rotateLeft(String usr){
            ImageView myTank = tanks.get(usr);

            dir-=1;
            resetPosition();

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

        public void shot(){
            TranslateTransition tt = new TranslateTransition(Duration.millis(1000),rect1);
            
            switch(dir)
            {
                case 0:
                        
                        
                        tt.setByX(100f);
                        tt.setCycleCount(1);
                        tt.setAutoReverse(true);
                        tt.play();
                    break;
                case 1:
                    tt.setByY(100f);
                    tt.setCycleCount(1);
                    tt.setAutoReverse(true);
                    tt.play();
                    break;
                case 2:
                    tt.setByX(-100f);
                    tt.setCycleCount(1);
                    tt.setAutoReverse(true);
                    tt.play();
                    break;
                case 3:
                     tt.setByY(-100f);
                    tt.setCycleCount(1);
                    tt.setAutoReverse(true);
                    tt.play();
                    break;
                case -1:
                    tt.setByY(-100f);
                    tt.setCycleCount(1);
                    tt.setAutoReverse(true);
                    tt.play();
                    break;
                case -2:
                     tt.setByX(-100f);
                    tt.setCycleCount(1);
                    tt.setAutoReverse(true);
                    tt.play();
                    break;
                case -3:
                    tt.setByY(100f);
                    tt.setCycleCount(1);
                    tt.setAutoReverse(true);
                    tt.play();
                    break;

            }
        }

        void resetPosition()
        {
            if(dir == 4 || dir == -4 )
            {
                dir = 0;
            }
        }

        void obtenerPositionX()
        {
            Timeline timeline = new Timeline(
               new KeyFrame(Duration.seconds(1), event -> {
                   double x = rect1.getLayoutX();
                   x += rect1.getTranslateX();
                   System.out.println("X: " + x);
               })
            );
            timeline.setCycleCount(Timeline.INDEFINITE);
            // Iniciar el Timeline
            timeline.play();
            
        }

        void obtenerPositionY()
        {
            Timeline timeline = new Timeline(
               new KeyFrame(Duration.seconds(1), event -> {
                   double x = rect1.getLayoutY();
                   x += rect1.getTranslateY();
                   System.out.println("Y: " + x);
               })
            );
            timeline.setCycleCount(Timeline.INDEFINITE);
            // Iniciar el Timeline
            timeline.play();
        }
    }
