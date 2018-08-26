package ilyaPn.LogMonitoring;

import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ilyaP on 22.08.2018.
 */
public class StatisticCollectorTest {
    StatisticCollector statisticCollector = StatisticCollector.getNewStatisticCollector();

    @Test
    public void splitLogByDay() throws Exception {
        ArrayList<Log> arrayList = (ArrayList<Log>)
                statisticCollector.splitLogByDay(new Log(1455839970L, "userId", "URL", 100));
        Assert.assertEquals(arrayList.get(0).getSeconds(), 30);
        Assert.assertEquals(arrayList.get(1).getSeconds(), 70);
    }

    @Test
    public void addLog() throws Exception {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("2000-01-10");
        Log log = new Log(date1.getTime(), "user1", "url1", 10);
        User user = new User(log);
        statisticCollector.addLog(log);
        Assert.assertEquals(statisticCollector.users.get(0).equals(user), true);
    }

    @Test
    public void addLogSecondaryAddition1() throws Exception {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("2000-01-10");
        Log log = new Log(date1.getTime(), "user1", "url1", 10);
        statisticCollector.addLog(log);
        statisticCollector.addLog(log);
        Assert.assertEquals(statisticCollector.users.get(0).getHitCounter(), 2);

    }

    @Test
    public void addLogSecondaryAddition2() throws Exception {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("2000-01-10");
        Log log1 = new Log(date1.getTime(), "user1", "url1", 10);
        Log log2 = new Log(date1.getTime(), "user2", "url1", 10);
        statisticCollector.addLog(log1);
        statisticCollector.addLog(log2);
        Assert.assertEquals(statisticCollector.users.get(0).equals(new User(log1)), true);
        Assert.assertEquals(statisticCollector.users.get(1).equals(new User(log2)), true);
    }

}