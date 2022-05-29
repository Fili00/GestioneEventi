import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class WorkerRunnable implements Runnable{
    private Socket clientSocket = null;
    private final EventManager em;

    public WorkerRunnable(Socket clientSocket, EventManager em) {
        this.clientSocket = clientSocket;
        this.em = em;
    }

    private String parseRequest(String request){
        String[] parts = request.split(" ");
        String response;
        if(parts.length == 0)
            return "Invalid request";
        response = switch (parts[0].toLowerCase()) {
            case "book" -> elaborateBook(parts);
            case "add" -> elaborateAdd(parts);
            case "close" -> elaborateClose(parts);
            case "list" -> elaborateList();
            default -> "Invalid request";
        };
        return response;
    }

    public void run() {
        try {
            InputStream input = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();

            byte[] b = new byte[100];
            var request = new String(b);

            output.write(parseRequest(request).getBytes());

            output.close();
            input.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String elaborateBook(String[] parts){
        if(parts.length < 3)
            return "Invalid request";
        var capacity = Integer.parseInt(parts[2]);
        if(capacity <= 0)
            return "Invalid capacity";

        if(em.book(parts[1], capacity))
            return "Booked";

        return "Event ("+parts[1]+") not available";
    }

    private String elaborateAdd(String[] parts){
        if(parts.length < 3)
            return "Invalid request";
        var capacity = Integer.parseInt(parts[2]);
        if(capacity <= 0)
            return "Invalid capacity";

        if(em.book(parts[1], capacity))
            return "Added";

        return "Event ("+parts[1]+") already present";
    }

    private String elaborateClose(String[] parts){
        if(parts.length < 2)
            return "Invalid request";
        if(em.close(parts[1]))
            return "Closed";
        return "Event ("+parts[1]+") not available";
    }

    private String elaborateList(){
        return em.toString();
    }

}
