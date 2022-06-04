import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.lang.Integer;

public class WorkerRunnable implements Runnable{
    private Socket clientSocket = null;
    private final EventManager em;

    public WorkerRunnable(Socket clientSocket, EventManager em) {
        this.clientSocket = clientSocket;
        this.em = em;
    }

    private String parseRequest(String request){
        request=request.substring(0,request.indexOf('\0'));
        String[] parts = request.split(" ");
        System.out.println();
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
        System.out.println(response);
        return response;
    }

    public void run() {
        try {
            DataInputStream input = new DataInputStream(clientSocket.getInputStream());
            OutputStream output = clientSocket.getOutputStream();

            byte[] b = new byte[100];
            String request="";
            if(input.read(b) > 0) {
                request=new String(b);
            }
            output.write(parseRequest(request).getBytes());



            clientSocket.close();
            input.close();
            output.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String elaborateBook(String[] parts){
        if(parts.length < 3)
            return "Invalid request 'book'";
        try{
            var capacity = Integer.parseInt(parts[2]);
            if(capacity <= 0)
                return "Invalid capacity 'book'";

            if(em.book(parts[1], capacity))
                return "Booked";

            return "Event ("+parts[1]+") not available 'book'";
        }catch(NumberFormatException e){
            return "Number needed";
        }


    }

    private String elaborateAdd(String[] parts){
        if(parts.length < 3)
            return "Invalid request 'Add'";
        var capacity = Integer.parseInt(parts[2]);
        if(capacity <= 0)
            return "Invalid capacity 'Add'";

        if(em.addEvent(parts[1], capacity))
            return "Added";

        return "Event ("+parts[1]+") already present 'Add'";
    }

    private String elaborateClose(String[] parts){
        if(parts.length < 2)
            return "Invalid request 'Close'";
        if(em.close(parts[1]))
            return "Closed";
        return "Event ("+parts[1]+") not available 'Close'";
    }

    private String elaborateList(){
        return em.toString();
    }

}