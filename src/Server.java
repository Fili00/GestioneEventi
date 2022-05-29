import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    protected int serverPort;
    protected ServerSocket serverSocket = null;
    protected boolean isStopped = false;
    protected Thread runningThread = null;
    protected ExecutorService threadPool;

    private EventManager em;

    public Server(int port) {
        this.serverPort = port;
        threadPool = Executors.newCachedThreadPool();
        em = new EventManager();
    }

    public void run() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port 8080", e);
        }

        while (!isStopped()) {
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if (isStopped()) {
                    System.out.println("Server Stopped.");
                    return;
                }
                throw new RuntimeException(
                        "Error accepting client connection", e);
            }
            this.threadPool.execute(new WorkerRunnable(clientSocket, em));
        }

        System.out.println("Server Stopped.");
    }

    /*
    private void processClientRequest(Socket clientSocket) throws Exception {
        InputStream input = clientSocket.getInputStream();
        OutputStream output = clientSocket.getOutputStream();
        long time = System.currentTimeMillis();
        byte[] responseDocument="prova".getBytes();
        byte[] responseHeader="prova".getBytes();
        output.write(responseHeader);
        output.write(responseDocument);
        output.close();
        input.close();
        System.out.println("Request processed: " + time);
    }*/

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
}
