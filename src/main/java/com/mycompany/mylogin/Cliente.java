package com.mycompany.mylogin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.Stack;

class Sender extends Thread {

    final DataOutputStream dos;

    public Sender(DataOutputStream dos) {
        this.dos = dos;
    }

    public void run() {
        try {
            while (true) {
                Scanner input = new Scanner(System.in);
                String toSend = input.nextLine();
                dos.writeUTF(toSend);
                if (toSend.equals("Exit")) {
                    try {
                        Cliente.dos.close();
                        Cliente.dis.close();
                    } catch (Exception e) {
                        System.out.println("Sesión cerrada");
                    }
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error al enviar el mensaje");
        }
    }
}

class Listener extends Thread {
    public String received;
    final DataInputStream dis;
    Stack<String> mensajes = new Stack<>();

    public Listener(DataInputStream dis) {
        this.dis = dis;
    }

    public void run() {
        try {
            while (true) {
                received = dis.readUTF();
                this.historial_msg(received);
                System.out.println(received);
                if (received.equals("VAS")) {
                    Thread.sleep(1000);
                }
                if (received.contains("LISTA DE UBICACIONES")) {
                    System.out.println("Recibi una lista");
                    Cliente.scene2.setUsers(received);
                    Cliente.scene2.setPositions();
                }
                if (received.contains("JUG@RYA")) {
                    System.out.println("Nuevo jugador");
                }
                if (received.contains("USER PROPIO")) {
                    Cliente.scene2.setUser(received);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private void historial_msg(String msg){
        System.out.println("MSG: " + msg);
        if (this.mensajes.size() < 3) {
            this.mensajes.push(msg);
            if (msg.contains("JUG@RYA")) {
                Cliente.scene2.newUser(this.mensajes.get(0), this.mensajes.get(1));
            }
            if (msg.contains("UP") || msg.contains("DOWN") || msg.contains("RIGHT") || msg.contains("LEFT")
                || msg.contains("ROT_RIGHT") || msg.contains("ROT_LEFT")) {
                Cliente.scene2.moveUser(msg);
            }
        } else {
            this.mensajes.remove(0);
            this.mensajes.push(msg);
            if (msg.contains("JUG@RYA")) {
                Cliente.scene2.newUser(this.mensajes.get(0), this.mensajes.get(1));
            }
            if (msg.contains("UP") || msg.contains("DOWN") || msg.contains("RIGHT") || msg.contains("LEFT")) {
                Cliente.scene2.moveUser(msg);
            }
        }
    }
}

public class Cliente extends Thread {
    
    static Socket socket;
    public static DataInputStream dis;
    public static DataOutputStream dos;
    public static String username;
    public static String password;
    public static boolean sesion;
    public static String validUser = "";
    public static Boolean newUser = false;
    public static Scene2Controller scene2;
    
    public static void initializeClient(){
        try {
            InetAddress ip = InetAddress.getByName("10.103.160.205"); // 10.103.160.205 -> Servidor en la nube
            socket = new Socket(ip, 2555);
            
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            
            System.out.println(dis.readUTF());
            dos.writeUTF(username);
            System.out.println(username);
            validUser = dis.readUTF();
            System.out.println(validUser);
            if(validUser.equals("USUARIO INCORRECTO, ADIOS POPÓ")){
                username = "";
                password = "";
            }
            System.out.println(dis.readUTF());
            dos.writeUTF(password);
            System.out.println(password);
            
            String respuesta = dis.readUTF();
            
            if(respuesta.equals("exito")){
                System.out.println("Sesion iniciada con exito");
                sesion = true;
            }else{
                dos.writeUTF("Exit");
                username = "";
                password = "";
                sesion = false;
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Error al validar las credenciaeles");
            //System.out.println("Client error: " + e);
        }
    }
    
    public static void initializeChat(DataInputStream dis, DataOutputStream dos){
        try{
            Thread listener = new Listener(dis);
            Thread sender = new Sender(dos);

            sender.start();
            listener.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void sendMessage(String message){
        try{
            dos.writeUTF(message);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static String getUser(){
        return (username + " " +password);
    }
    
    public static void initializeSesion(){
        try{
            System.out.println(username + password);

           
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
