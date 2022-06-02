public class Main {
    public static void main(String[] args){
        Server s = new Server(50000);
        Thread t = new Thread(s);
        t.start();
        //Thread tc = new Thread(c);
        Client c = new Client(50000);
        try {

            Thread.sleep(60000);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        s.stop();
    }
}