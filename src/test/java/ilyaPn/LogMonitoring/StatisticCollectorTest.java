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
    StatisticCollector statisticCollector = new StatisticCollector();

    @Test
    public void splitLogByDay() throws Exception {
        ArrayList<Log> arrayList = (ArrayList<Log>)
                statisticCollector.splitLogByDay(new Log(1455839970000L,"userId","URL",1000000));
        System.out.println(arrayList.get(0));
    }

    @Test
    public void addLog() throws Exception {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("2000-01-10");
        Log log = new Log(date1.getTime(),"user1","url1",10);
        User user = new User(log);
        statisticCollector.addLog(log);
        Assert.assertEquals(statisticCollector.users.get(0).equals(user),true);
    }

    @Test
    public void addLogSecondaryAddition1() throws Exception {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("2000-01-10");
        Log log = new Log(date1.getTime(),"user1","url1",10);
        statisticCollector.addLog(log);
        statisticCollector.addLog(log);
        Assert.assertEquals(statisticCollector.users.get(0).getHitCounter(),2);

    }
    @Test
    public void addLogSecondaryAddition2() throws Exception {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("2000-01-10");
        Log log1 = new Log(date1.getTime(),"user1","url1",10);
        Log log2 = new Log(date1.getTime(),"user2","url1",10);
        statisticCollector.addLog(log1);
        statisticCollector.addLog(log2);
        Assert.assertEquals(statisticCollector.users.get(0).equals(new User(log1)),true);
        Assert.assertEquals(statisticCollector.users.get(1).equals(new User(log2)),true);
    }

    @Test
    public void itHappensInOneDayTrue() throws Exception {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("2000-01-10");
        Date date2 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("2000-01-10");
        boolean res = statisticCollector.itHappensInOneDay(date1,date2);
        Assert.assertEquals(true,res);
    }
    @Test
    public void itHappensInOneDayFalse() throws Exception {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("2000-02-10");
        Date date2 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("2000-01-10");
        boolean res = statisticCollector.itHappensInOneDay(date1,date2);
        Assert.assertEquals(false,res);
    }

}