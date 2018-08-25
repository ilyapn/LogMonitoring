package ilyaPn.LogMonitoring;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ilyaP on 21.08.2018.
 */

public class StatisticCollector {
    public ArrayList<User> users = new ArrayList<>();

    public static StatisticCollector getNewStatisticCollector(){
        return new StatisticCollector();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public boolean itHappensInOneDay(Date date, Date nextDate) {
        if (date.getTime() > nextDate.getTime()) {
            return itHappensInOneDay(date, (date.getTime() - nextDate.getTime()) / 1000);
        } else {
            return itHappensInOneDay(nextDate, (nextDate.getTime() - date.getTime()) / 1000);
        }
    }

    public boolean itHappensInOneDay(Date date, long seconds) {
        Date dateNext = new Date(date.getTime() + seconds * 1000);
        if (daysAreEqual(date, dateNext) &&
                mountsAreEqual(date, dateNext) &&
                yearsAreEqual(date, dateNext)) {
            return true;
        }
        return false;
    }

    public boolean daysAreEqual(Date date, Date nextDate) {
        if (date.toInstant().atZone(ZoneId.of("UTC")).getDayOfMonth()
                == nextDate.toInstant().atZone(ZoneId.of("UTC")).getDayOfMonth())
            return true;
        return false;
    }

    public boolean mountsAreEqual(Date date, Date nextDate) {
        if (date.toInstant().atZone(ZoneId.of("UTC")).getMonthValue()
                == nextDate.toInstant().atZone(ZoneId.of("UTC")).getMonthValue())
            return true;
        return false;
    }

    public boolean yearsAreEqual(Date date, Date nextDate) {
        if (date.toInstant().atZone(ZoneId.of("UTC")).getYear()
                == nextDate.toInstant().atZone(ZoneId.of("UTC")).getYear())
            return true;
        return false;
    }

    public void addLog(Log log) {
        User user = new User(log);
        if (hasRecord(log)) {
            updateLogday(user);
        } else {
            users.add(user);
        }
    }

    public List<Log> splitLogByDay(Log log){
        ArrayList<Log> logs = new ArrayList<>();
        long seconds = log.getSeconds();
        long secondsInDay = 60*60*24;
        long timestampInSeconds = log.getTimestamp()/1000;
        long timestampOfEndDay = ((timestampInSeconds+seconds)/secondsInDay)*secondsInDay*1000;
        long secondsInFirstDay = (timestampInSeconds/secondsInDay+1)-log.getTimestamp()/secondsInDay;
        long secondsInEndDay = (timestampInSeconds+seconds)%secondsInDay;
        logs.add(new Log(log.getTimestamp(),log.getUserId(),log.getUrl(),secondsInFirstDay));
        logs.add(new Log(timestampOfEndDay,log.getUserId(),log.getUrl(),secondsInEndDay));

        return  logs;
    }

    public boolean hasRecord(Log log) {
        boolean res = false;
        long date = log.getTimestamp();
        String id = log.getUserId();
        String url = log.getUrl();
        for (User user : users)
            if (itHappensInOneDay(new Date(user.getTimestamp()), new Date(date))
                    && user.getId().equals(id)
                    && user.getURL().equals(url))
                res = true;
        return res;
    }

    public void updateLogday(User user) {
        for (int i = 0; i < users.size(); i++) {
            User existUser = users.get(i);
            if (itHappensInOneDay(new Date(existUser.getTimestamp()), new Date(user.getTimestamp()))
                    && existUser.getId().equals(user.getId())
                    && existUser.getURL().equals(user.getURL())) {
                existUser.addSeconds(user.getSumSeconds());
                return;
            }
        }
    }
}
