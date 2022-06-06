package server;

import java.util.concurrent.ConcurrentHashMap;

public class EventManager {
    private final ConcurrentHashMap<String, Event> events;
    public EventManager(){
        events = new ConcurrentHashMap<String, Event>();
    }
    public boolean addEvent(String name, int capacity){
        return events.putIfAbsent(name, new Event(name, capacity)) == null;
    }

    private boolean bookAux(Event ev, int capacity) throws InterruptedException {
        if(ev.isClosed()) {
            return false;
        }
        try {
            ev.setCurrentCapacity(capacity);
            return true;
        }catch (Exception e){
            ev.wait();
            return bookAux(ev, capacity);
        }
    }

    //book [name] [capacity]
    public boolean book(String name, int capacity) {  //metodo 'Prenota'
        var event = events.get(name);
        if(event == null)
            return false;

        try {
            boolean r;
            synchronized (event){
                r = bookAux(event, capacity);
            }
            return r;
        }catch (Exception e){
            return false;
        }
    }

    //addcapacity [name] [capacity]
    public boolean addCapacity(String name, int capacity){ //metodo 'Aggiungi'
        var event = events.get(name);

        if(event == null)
            return false;

        synchronized (event){
            if(event.isClosed())
                return false;
            try {
                event.setMaxCapacity(capacity);
                event.notifyAll();
            }catch (Exception e){
                return false;
            }
        }

        return true;
    }

    //close [name]
    public boolean close(String name){ //metodo 'Chiudi'
        var event = events.get(name);

        if(event == null)
            return false;

        synchronized (event){
            if(event.isClosed())
                return false;
            event.close();
            events.remove(name);
            event.notifyAll();
        }
        return true;
    }

    //list
    @Override
    public String toString() { //metodo 'ListaEventi'
        StringBuilder sb = new StringBuilder();
        for(var e : events.entrySet()){
            sb.append(e.getValue().toString());
            sb.append('\n');
        }
        return sb.toString();
    }
}
