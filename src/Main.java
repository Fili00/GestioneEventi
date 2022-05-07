import static java.lang.Thread.sleep;

class User {
    protected EventManager em;
    protected String name;
    public User(EventManager em, String name){
        this.em = em;
        this.name = name;
    }
}

class Admin extends User implements Runnable {
    public Admin(EventManager em, String name){
        super(em, name);
    }

    @Override
    public void run() {
        em.addEvent("E1", 100);
        Main.mySleep(15500);
        em.addCapacity("E1", 50);
        Main.mySleep(6000);
        em.close("E1");
    }
}

class NormalUser extends User implements Runnable {
    public NormalUser(EventManager em, String name){
        super(em, name);
    }
    @Override
    public void run() {
        String[] cars = {"E1", "E2", "E3", "E4"};
        for(int i=0; i<50; i++){
            for(String c : cars)
                System.out.println("I:"+i+" User:"+name+" event:"+c+" "+em.book(c, 5));
            Main.mySleep(500);
        }
    }
}

public class Main {
    public static void mySleep(int time){
        try {
            sleep(time);
        } catch (InterruptedException ignored) {
        }
    }
    public static void main(String[] args) throws InterruptedException {
        EventManager em = new EventManager();
        NormalUser u1 = new NormalUser(em, "U1");
        Admin a1 = new Admin(em, "A1");
        NormalUser u2 = new NormalUser(em, "U2");

        Thread TA1 = new Thread(a1);
        TA1.start();
        Thread TU1 = new Thread(u1);
        TU1.start();
        Thread TU2 = new Thread(u2);
        TU2.start();

        TA1.join();
        TU1.join();

    }
}
