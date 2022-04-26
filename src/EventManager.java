import java.util.HashSet;
import java.util.Set;

public class EventManager {
    private final Set<Event> eventSet;

    public EventManager(){
        eventSet = new HashSet<Event>();
    }

    private Event getEvent(String name){
        for(var event : eventSet){
            if(event.getName().equals(name)){
                return event;
            }
        }
        return null;
    }

    public boolean addEvent(String name, int capacity){
        eventSet.add(new Event(name, capacity)); //TODO: FARE THREAD SAFE
        return true;
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
