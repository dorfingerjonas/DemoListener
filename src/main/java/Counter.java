import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Counter {
    private static Counter instance;
    private long counter;
    private long maxValue;
    private boolean reachedMaxValue = false;
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private Counter() {}

    public static Counter getInstance() {
        if (instance == null) instance = new Counter();
        return instance;
    }

    public synchronized void initialize(long maxValue) {
        this.maxValue = maxValue;
        this.counter = 0;
    }

    public long getCounter() {
        return counter;
    }

    public synchronized void increment(long count) {
        this.counter += count;

        if (!reachedMaxValue && this.counter >= maxValue) {
            reachedMaxValue = true;
            pcs.firePropertyChange("counter", counter - count, counter);
        }

        if (!reachedMaxValue && counter % 100 == 0) {
            pcs.firePropertyChange("counter", counter - count, counter);
        }
    }

    public void addListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener("counter", listener);
    }
}
