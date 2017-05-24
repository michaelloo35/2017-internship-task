import com.sun.org.apache.xpath.internal.SourceTree;
import org.junit.After;
import org.junit.Before;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static java.lang.Thread.sleep;
import static org.testng.Assert.*;

/**
 * Created by Michał Matusiak
 */
public class SchedulerTest {


    // 2 timeouts (2x timed out, 2x handled)
    @Test
    public void test1() throws Exception {

        Scheduler s = new Scheduler();
        System.out.println("TEST1");
        Runnable task1 = () -> System.out.println("handled");
        TimerID myTimer = s.start(1,task1);
        TimerID myTimer2 = s.start(2,task1);

        sleep(5000);
    }

    // 1 timeout 1 stopped (1x timed out, 2x handled
    @Test
    public void test2() throws Exception {
        Scheduler s = new Scheduler();
        System.out.println("TEST2");
        Runnable task1 = () -> System.out.println(" handled");
        TimerID myTimer = s.start(1,task1);
        TimerID myTimer2 = s.start(2,task1);
        s.stop(myTimer);

        sleep(5000);
    }

}