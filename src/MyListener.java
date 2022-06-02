import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyListener implements ActionListener {
    private Client client;

    private MyWorkerReload worker1;
    private MyWorkerBook worker2;
    private int type;

    public MyListener(Client client, int type) {
        this.client = client;
        this.type=type;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        /*client.AggiuntaEvento.setEnabled(false);
        if(type==1){
            worker1 = new MyWorkerReload(client);
           // worker1.execute();
        }else{
            worker2 = new MyWorkerBook(client);
            worker2.execute();
        }

        client.AggiuntaEvento.setEnabled(true);*/
    }
}
