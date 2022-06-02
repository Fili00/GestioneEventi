import java.io.*;
import java.net.*;



public class Client{

    //Variabili Connessione al server
    private Socket socket;
    private DataOutputStream output;
    private DataInputStream input;
    private String comando;

    //InterfacciaGrafica
    ClientGUI GUI;







    public Client(int port){

        try {
            socket=new Socket("localhost",port);
            output = new DataOutputStream(socket.getOutputStream());
            input = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Cannot connect port 50000", e);
        }
        comando ="";
        GUI = new ClientGUI(input,output);
        GUI.initComponents();
        while (true){

        }
    }


    /*public void prenota(){
        try{
            pr.write((comando).getBytes());
        }catch (IOException e){
            throw new RuntimeException("Cannot send message", e);
        }
    }*/






    public static void main(String args[]) {


        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Client(50000).setVisible(true);

            }
        });*/
    }

}



