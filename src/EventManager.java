import java.util.concurrent.ConcurrentHashMap;

public class EventManager {
    private final ConcurrentHashMap<String,Event> events;

    public EventManager(){
        events = new ConcurrentHashMap<String, Event>();
    }

    private Event getEvent(String name){
        return events.get(name);
    }

    public boolean addEvent(String name, int capacity){
        Event e = new Event(name, capacity);
        var newE = events.putIfAbsent(name, e);
        return e == newE;
    }

    public boolean book(String name, int capacity){
        var event = getEvent(name);

        if(event == null)
            return false;

        synchronized (event){
            try {
                event.setCurrentCapacity(capacity);
            }catch (Exception e){
                return false;
            }
        }

        return true;
    }

    public boolean addCapacity(String name, int capacity){
        var event = getEvent(name);

        if(event == null)
            return false;

        synchronized (event){
            try {
                event.setMaxCapacity(capacity);
            }catch (Exception e){
                return false;
            }
        }

        return true;
    }
}
