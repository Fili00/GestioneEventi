import java.security.InvalidParameterException;
import java.util.Objects;

public class Event {
    private final String name;
    private int maxCapacity;
    private int currentCapacity;

    public Event(String name, int maxCapacity){
        if(maxCapacity < 0) throw new InvalidParameterException("maxCapacity less than zero");
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
        if(capacity < 0 && getCurrentCapacity() - capacity > getMaxCapacity()) throw new InvalidParameterException("New capacity more than max capacity");
        if(capacity > getCurrentCapacity()) throw new InvalidParameterException("Capacity more than current capacity");
        currentCapacity -= capacity;
    }

    public void setMaxCapacity(int capacity){
        if(capacity + getMaxCapacity() < getCurrentCapacity()) throw new InvalidParameterException("Max capacity less than current capacity");
        maxCapacity += capacity;
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

    @Override
    public String toString() {
        return "Event " + name + " maxCapacity=" + maxCapacity + ", currentCapacity=" + currentCapacity;
    }
}
