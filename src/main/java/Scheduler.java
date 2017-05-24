import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeoutException;

import static java.lang.Thread.sleep;

/**
 * Created by Michał Matusiak
 */
public class Scheduler implements IScheduler {

    private PriorityBlockingQueue<Timer> timers;
    private ConcurrentHashMap<TimerID, Timer> timerKeys;
    private Thread timeOutChecker;


    public Scheduler() {
        this.timers = new PriorityBlockingQueue<>();
        this.timerKeys = new ConcurrentHashMap<>();
        Runnable task = () -> {
            while (true) {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted returning..");
                    return;
                }
                Timer t = this.timers.peek();
                if (t != null) {
                    if (t.getEndTime() <= System.currentTimeMillis()) {
                        this.timerKeys.remove(t.getTimerID());
                        this.timers.remove(t);  // should be O(1) cause t is on the top of the que
                        System.out.println("Timer timed out");
                        t.getCallback().run();
                    }
                }
            }
        };

        this.timeOutChecker = new Thread(task);
        this.timeOutChecker.start();
    }

    public Scheduler(int initialCapacity) {
        this.timers = new PriorityBlockingQueue<>(initialCapacity);
        this.timerKeys = new ConcurrentHashMap<>(initialCapacity);
    }

    @Override
    public TimerID start(long expireTimeSeconds, Runnable callback) {
        TimerID key = new TimerID();
        // ensure that the keys are unique
        while (timerKeys.containsKey(key))
            key = new TimerID();
        Timer t = new Timer(key, System.currentTimeMillis() + expireTimeSeconds * 1000, callback);
        timerKeys.put(key, t);
        timers.add(t);
        return t.getTimerID();
    }

    @Override
    public void stop(TimerID timerId) {
        Timer t = this.timerKeys.remove(timerId);
        if (t != null) {
            this.timers.remove(t);
            t.getCallback().run();
        }
    }
    public void killScheduler(){
        this.timeOutChecker.interrupt();
    }
}
