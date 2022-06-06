package client;

import client.GUI;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class MyWorkerBook extends SwingWorker<String, Integer> {
    private GUI client;
    private Socket socket;
    private DataOutputStream output;
    private DataInputStream input;
    private String response;

    public MyWorkerBook(GUI client){
        this.client=client;
        this.response = "";
        try {
            this.socket = new Socket(client.getUrl(),client.getPort());
            this.input=new DataInputStream(this.socket.getInputStream());
            this.output=new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            this.response = "Server unreachable";
        }
    }



    @Override
    protected String doInBackground() throws Exception {
        if(this.response.equals("Server unreachable"))
            return "Error";
        try {
            int column = 0;
            int row = client.TabellaEventi.getSelectedRow();
            if(row<0){
                response = "Select element on the table";
                return "error";
            }
            String value = client.TabellaEventi.getModel().getValueAt(row, column).toString();
            output.write(("book "+value+" "+client.getQuantitaEvento()).getBytes());

            byte[] b = new byte[1000];
            if(input.read(b) > 0) {
                response=new String(b);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Done!";
    }

    @Override
    protected void process(List<Integer> chunks) {

    }

    @Override
    protected void done() {
        if(this.response.equals("Server unreachable")) {
            client.MessaggioErrore.setText(response);
            return;
        }
        client.MessaggioErrore.setText(response);
        client.aggiornaTabella();
        try {
            this.socket.close();
            this.input.close();
            this.output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

