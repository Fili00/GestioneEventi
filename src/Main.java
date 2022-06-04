public class Main {
    public static void main(String[] args){
        Server s = new Server(50000);
        Thread t = new Thread(s);
        t.start();
        GUI c = new GUI();
        try {

            Thread.sleep(5000);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        s.stop();
    }
}