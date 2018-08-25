package ilyaPn.LogMonitoring;

/**
 * Created by ilyaP on 21.08.2018.
 */
public class Log {
    private long timestamp;
    private String userId;
    private String url;
    private long seconds;

    public Log(long timestamp, String userId, String url, long seconds) {
        this.timestamp = timestamp;
        this.userId = userId;
        this.url = url;
        this.seconds = seconds;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public String getUrl() {
        return url;
    }

    public long getSeconds() {
        return seconds;
    }
}
