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
    private String[] events;
    public Admin(EventManager em, String name, String[] events){
        super(em, name);
        this.events = events;
    }

    @Override
    public void run() {
        Main.mySleep((int)(Math.random()*1000));

        for (String e : events)
            System.out.println("Admin:"+name+" event:"+e+" "+em.addEvent(e, 100)+" (addEvent)");
        Main.mySleep(15500);
        for (String e : events)
            System.out.println("Admin:"+name+" event:"+e+" "+em.addCapacity(e, 50)+" (addCapacity)");

        Main.mySleep(6000);
        for (String e : events)
            System.out.println("Admin:"+name+" event:"+e+" "+em.close(e)+" (close)");

    }
}

class NormalUser extends User implements Runnable {
    public NormalUser(EventManager em, String name){
        super(em, name);
    }
    @Override
    public void run() {
        String[] events = {"E1", "E2", "E3", "E4"};
        for(int i=0; i<50; i++){
            for(String e : events)
                System.out.println("I:"+i+" User:"+name+" event:"+e+" "+em.book(e, 5));
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
        String[] ev1 = {"E1", "E2"};
        String[] ev2 = {"E3", "E4"};

        EventManager em = new EventManager();

        NormalUser u1 = new NormalUser(em, "U1");
        NormalUser u2 = new NormalUser(em, "U2");
        Admin a1 = new Admin(em, "A1", ev1);
        Admin a2 = new Admin(em, "A2", ev2);

        Thread TA1 = new Thread(a1);
        TA1.start();
        Thread TA2 = new Thread(a2);
        TA2.start();
        Thread TU1 = new Thread(u1);
        TU1.start();
        Thread TU2 = new Thread(u2);
        TU2.start();

        TA1.join();
        TA2.join();
        TU1.join();
        TU2.join();
    }
}
