package client;

import client.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyListenerBook implements ActionListener{
    private GUI clientGUI;
    private MyWorkerBook worker;

    public MyListenerBook(GUI clientGUI) {
        this.clientGUI = clientGUI;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        worker = new MyWorkerBook(clientGUI);
        worker.execute();

    }


}
