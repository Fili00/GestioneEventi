import java.security.InvalidParameterException;
import java.util.Objects;

public class Event {
    private final String name;
    private final int maxCapacity;
    private int currentCapacity;

    public Event(String name, int maxCapacity){
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.currentCapacity = maxCapacity;
    }

    public String getName() {
        return name;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public void setCurrentCapacity(int capacity){
        if(capacity > getMaxCapacity()) throw new InvalidParameterException("Capacity more than max capacity");
        if(capacity > getCurrentCapacity()) throw new InvalidParameterException("Capacity more than current capacity");
        currentCapacity -= capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        Event event = (Event) o;
        return getName().equals(event.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
