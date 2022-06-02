import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyListener implements ActionListener {
    private GUI client;

    private MyWorkerReload worker1;
    private MyWorkerBook worker2;
    private int type;

    public MyListener(GUI client, int type) {
        this.client = client;
        this.type=type;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {

    }
}
