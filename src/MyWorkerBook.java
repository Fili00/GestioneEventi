import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

public class MyWorkerBook extends SwingWorker<String, Integer> {
    private GUI client;
    private Socket socket;
    private DataOutputStream output;
    private DataInputStream input;

    public MyWorkerBook(GUI client){
        try {
            this.socket = new Socket("localhost",50000);
            this.input=new DataInputStream(this.socket.getInputStream());
            this.output=new DataOutputStream(this.socket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.client=client;
    }



    @Override
    protected String doInBackground() throws Exception {
        return "Done!";
    }

    @Override
    protected void process(List<Integer> chunks) {

    }

    @Override
    protected void done() {







        String request="";
        try {
            int column = 0;
            int row = client.TabellaEventi.getSelectedRow();
            if(row<0){
                client.MessaggioErrore.setText("Select element on the table");
                return;
            }
            String value = client.TabellaEventi.getModel().getValueAt(row, column).toString();
            output.write(("book "+value+" "+client.getQuantitaEvento()).getBytes());

            byte[] b = new byte[1000];
            if(input.read(b) > 0) {
                request=new String(b);
            }
            client.MessaggioErrore.setText(request);
            System.out.println(request);
            client.aggiornaTabella();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            this.socket.close();
            this.input.close();
            this.output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

