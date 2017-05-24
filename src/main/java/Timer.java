/**
 * Created by Michał Matusiak
 */
public class Timer implements Comparable<Timer>{
    private final TimerID timerID;
    private final long endTime;

    public Runnable getCallback() {
        return callback;
    }

    private final Runnable callback;
    public long getEndTime() {
        return endTime;
    }

    public TimerID getTimerID() {

        return timerID;
    }

    public Timer(TimerID timerID, long endTime, Runnable callback) {
        this.timerID = timerID;
        this.endTime = endTime;
        this.callback = callback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Timer timer = (Timer) o;

        return timerID.equals(timer.timerID);
    }

    @Override
    public int hashCode() {
        return timerID.hashCode();
    }

    @Override
    public int compareTo(Timer o) {
        if (this.endTime == o.endTime)
            return 0;
        else if(this.endTime > o.endTime)
            return 1;
        else
            return -1;

    }
}
