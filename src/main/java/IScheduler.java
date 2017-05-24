
/**
 * Created by MichałMatusiak
 */
public interface IScheduler {

    TimerID start(long expireTimeMillis, Runnable callback);

    void stop(TimerID timerId);
}
