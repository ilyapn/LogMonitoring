package ilyaPn.LogMonitoring;

import java.util.ArrayList;
import java.util.List;

import static ilyaPn.LogMonitoring.TimeHandler.itHappensInOneDay;

/**
 * Created by ilyaP on 21.08.2018.
 */

public class StatisticCollector {
    public ArrayList<User> users = new ArrayList<>();
    private long secondsInDay = TimeHandler.secondsInDay;

    private StatisticCollector() {
    }

    public static StatisticCollector getNewStatisticCollector() {
        return new StatisticCollector();
    }

    public ArrayList<User> getUsers() {
        return users;
    }


    public void addLog(Log log) {

        if (itHappensInOneDay(log.getTimestamp(), log.getSeconds())) {
            writeLogInUsersList(log);
        } else {
            ArrayList<Log> logs = (ArrayList<Log>) splitLogByDay(log);
            for (Log l : logs) {
                writeLogInUsersList(l);
            }
        }
    }

    private void writeLogInUsersList(Log log) {
        User user = new User(log);
        if (hasRecord(log)) {
            updateLogday(user);
        } else {
            users.add(user);
        }
    }

    public List<Log> splitLogByDay(Log log) {
        ArrayList<Log> logs = new ArrayList<>();
        long seconds = log.getSeconds();
        long timestampInSeconds = log.getTimestamp();
        long timestampOfEndDay = ((timestampInSeconds + seconds) / secondsInDay) * secondsInDay;
        long secondsInEndDay = (timestampInSeconds + seconds) % secondsInDay;
        long secondsInFirstDay = seconds - secondsInEndDay;
        logs.add(new Log(log.getTimestamp(), log.getUserId(), log.getUrl(), secondsInFirstDay));
        logs.add(new Log(timestampOfEndDay, log.getUserId(), log.getUrl(), secondsInEndDay));

        return logs;
    }

    public boolean hasRecord(Log log) {
        boolean res = false;
        String id = log.getUserId();
        String url = log.getUrl();
        for (User user : users)
            if (itHappensInOneDay(user.getTimestamp(), log.getSeconds())
                    && user.getId().equals(id)
                    && user.getURL().equals(url))
                res = true;
        return res;
    }

    public void updateLogday(User user) {
        for (User existUser : users) {
            Long differentInSeconds = existUser.getTimestamp() > user.getTimestamp()
                    ? existUser.getTimestamp() - user.getTimestamp()
                    : user.getTimestamp() - existUser.getTimestamp();
            if (itHappensInOneDay(existUser.getTimestamp(), differentInSeconds)
                    && existUser.getId().equals(user.getId())
                    && existUser.getURL().equals(user.getURL())) {
                existUser.addSeconds(user.getSumSeconds());
                return;
            }
        }
    }
}
