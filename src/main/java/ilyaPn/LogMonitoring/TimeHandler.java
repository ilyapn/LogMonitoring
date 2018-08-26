package ilyaPn.LogMonitoring;

/**
 * Created by ilyaP on 26.08.2018.
 */
public class TimeHandler {
    public final static long secondsInDay = 60 * 60 * 24;

    public static boolean itHappensInOneDay(long datetime, long seconds) {
        if (datetime / secondsInDay == (datetime + seconds) / secondsInDay)
            return true;
        return false;
    }
}
