package ilyaPn.LogMonitoring;

/**
 * Created by ilyaP on 21.08.2018.
 */
public class User {
    private long timestamp;
    private String id;
    private String URL;
    private long sumSeconds;
    private long hitCounter;

    public User(long timestamp, String id, String URL, long sumSeconds, long hitCounter) {
        this.timestamp = timestamp;
        this.id = id;
        this.URL = URL;
        this.sumSeconds = sumSeconds;
        this.hitCounter = hitCounter;
    }

    public User(Log log) {
        id = log.getUserId();
        URL = log.getUrl();
        timestamp = log.getTimestamp();
        sumSeconds += log.getSeconds();
        hitCounter++;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getHitCounter() {
        return hitCounter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public long getSumSeconds() {
        return sumSeconds;
    }

    public void setSumSeconds(long sumSeconds) {
        this.sumSeconds = sumSeconds;
        hitCounter++;
    }
    public void addSeconds(long sumSeconds){
        this.sumSeconds += sumSeconds;
        hitCounter++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (timestamp != user.timestamp) return false;
        if (sumSeconds != user.sumSeconds) return false;
        if (hitCounter != user.hitCounter) return false;
        if (!id.equals(user.id)) return false;
        return URL.equals(user.URL);
    }

    @Override
    public int hashCode() {
        int result = (int) (timestamp ^ (timestamp >>> 32));
        result = 31 * result + id.hashCode();
        result = 31 * result + URL.hashCode();
        result = 31 * result + (int) (sumSeconds ^ (sumSeconds >>> 32));
        result = 31 * result + (int) (hitCounter ^ (hitCounter >>> 32));
        return result;
    }
}
