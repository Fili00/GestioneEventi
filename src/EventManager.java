import java.util.LinkedList;
import java.util.List;

public class EventManager {
    private final List<Event> eventList;

    public EventManager(){
        eventList = new LinkedList<Event>();
    }

    public boolean addEvent(String name, int capacity){
        eventList.add(new Event(name, capacity)); //TODO: AGGIUNGERE CONTROLLI VARI
        return true;
    }

}
