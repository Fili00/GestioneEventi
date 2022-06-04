import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

public class MyWorkerReload extends SwingWorker<String, Integer> {
    private GUI client;
    private Socket socket;
    private DataOutputStream output;
    private DataInputStream input;
    private String response;

    public MyWorkerReload(GUI client){
        this.response="";
        this.client=client;
        try {
            this.socket = new Socket("localhost",50000);
            this.input=new DataInputStream(this.socket.getInputStream());
            this.output=new DataOutputStream(this.socket.getOutputStream());

        } catch (IOException e) {
            this.response="Server unreachable";
        }
    }

    @Override
    protected String doInBackground() throws Exception {
        if(this.response.equals("Server unreachable"))
            return "Error";
        try {
            output.write(("list").getBytes());

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
    protected void done(){
        if(this.response.equals("Server unreachable")) {
            client.MessaggioErrore.setText(response);
            return;
        }
        response = response.substring(0, response.indexOf('\0'));
        String[] righe = response.split("\n");
        for (int i = 0; i < righe.length; i++) {
            righe[i] = righe[i].substring(6);
        }
        String[] informazioni = new String[2];
        DefaultTableModel model = (DefaultTableModel) client.TabellaEventi.getModel();

        for (int j = 0; j < model.getRowCount();) {
            model.removeRow(j);
        }
        for (int i = 0; i < righe.length; i++) {
            String[] tmp = righe[i].split(",");

            informazioni[0] = tmp[0].substring(0, tmp[0].indexOf(" "));
            informazioni[1] = tmp[1].substring(tmp[1].indexOf("=") + 1);

            model.addRow(informazioni);
            client.getEventsTable().repaint();
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

