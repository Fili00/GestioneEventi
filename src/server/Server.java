package server;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    protected int serverPort;
    protected ServerSocket serverSocket = null;
    protected boolean isStopped = false;
    protected ExecutorService threadPool;

    private EventManager em;

    public Server(int port) {
        this.serverPort = port;
        threadPool = Executors.newFixedThreadPool(16);
        em = new EventManager();
    }

    public void run() {

        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port "+ this.serverPort, e);
        }

        while (!isStopped()) {
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
                System.out.println("CLiente connesso");
            } catch (IOException e) {
                if (isStopped()) {
                    System.out.println("Server Stopped.");
                    return;
                }
                throw new RuntimeException(
                        "Error accepting client connection", e);
            }
            this.threadPool.execute(new WorkerRunnable(clientSocket, this));
        }

        System.out.println("Server Stopped.");
    }

    public EventManager getEventManager() {
        return em;
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    public void initTest(){
        em.addEvent("Event1", 50);
        em.addEvent("Event2", 30);
    }

    public static void main(String[] args){
        Server s = new Server(50000);
        Thread t = new Thread(s);
        t.start();

        s.initTest(); //TODO: REMOVE
        try {
            t.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}