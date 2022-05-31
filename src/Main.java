public class Main {
    public static void main(String[] args){
        Server s = new Server(50000);
        Thread t = new Thread(s);
        t.start();
        Client c = new Client(50000, "add sis 100");
        Client c2 = new Client(50000, "add sis 100");
        Client c3 = new Client(50000, "add sas 100");
        try {
            c.prenota();
            c2.prenota();
            c3.prenota();
            Thread.sleep(60000);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        s.stop();
    }
}