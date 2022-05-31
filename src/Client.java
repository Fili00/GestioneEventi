import java.io.*;
import java.net.*;

public class Client {
    private Socket socket;
    private DataOutputStream pr;
    private String comando;
    public Client(int port, String comando){
        try {
            this.comando = comando;
            socket=new Socket("localhost",port);
            pr = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException("Cannot connect port 50000", e);
        }
    }


    public void prenota(){
        try{
            pr.write((comando).getBytes());
        }catch (IOException e){
            throw new RuntimeException("Cannot send message", e);
        }
    }

}
