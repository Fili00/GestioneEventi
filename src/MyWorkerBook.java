import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class MyWorkerBook implements ActionListener {
    private ClientGUI client;

    public MyWorkerBook(ClientGUI client){
        this.client=client;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        String request="";
        try {
            DataInputStream input = client.getInput();
            OutputStream output = client.getOutput();

            output.write(("list").getBytes());

            byte[] b = new byte[1000];

            if(input.read(b) > 0) {
                request=new String(b);
            }

            //System.out.println(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        client.aggiornaTabella(request);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //client.prenota();

    }
}

