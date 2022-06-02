import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyListenerReload implements ActionListener{
    private GUI clientGUI;
    private MyWorkerReload worker;

    public MyListenerReload(GUI clientGUI) {
        this.clientGUI = clientGUI;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        worker = new MyWorkerReload(clientGUI);
        worker.execute();
    }
}
